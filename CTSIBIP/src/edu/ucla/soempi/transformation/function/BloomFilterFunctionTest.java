package edu.ucla.soempi.transformation.function;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openhie.openempi.Constants;
import org.openhie.openempi.model.FieldType.FieldTypeEnum;
import org.openhie.openempi.transformation.function.HMACFunction;
import org.openhie.openempi.transformation.function.bloomfilter.NgramSequencer;
import org.openhie.openempi.util.BitArray;

import com.google.common.io.Files;

public class BloomFilterFunctionTest {

    /**
     * Parameters
     */
    public static final int M = 1000;// number of bits in bloomfilter
    public static final int K = 40;
    public static final String HMAC_FUNCTION_1_NAME = "HmacMD5";
    public static final String HMAC_FUNCTION_2_NAME = "HmacSHA1";
    public static final int NGRAM_SIZE = 2;
    public static final boolean NGRAM_PADDING = true;

    private final HMACFunction hmac1;
    private final HMACFunction hmac2;
    private final byte[] salt1;
    private final byte[] salt2;

    /**
     * Globals
     */
    Map<String, BitArray> ngramBloomBitsCache = new HashMap<String, BitArray>();

    public BloomFilterFunctionTest() {
        this.hmac1 = new HMACFunction();
        this.hmac1.setHmacFunctionName(HMAC_FUNCTION_1_NAME);
        this.hmac1.setInputType(FieldTypeEnum.String);
        this.hmac1.setOutputType(FieldTypeEnum.Blob);
        this.hmac1.init();
        this.hmac2 = new HMACFunction();
        this.hmac2.setHmacFunctionName(HMAC_FUNCTION_2_NAME);
        this.hmac2.setInputType(FieldTypeEnum.String);
        this.hmac2.setOutputType(FieldTypeEnum.Blob);
        this.hmac2.init();

        this.salt1 = new byte[] { (byte) 0xe0, (byte) 0xe0, (byte) 0xe0, (byte) 0xe0 };
        this.salt2 = new byte[] { (byte) 0xe0, (byte) 0xe0, (byte) 0xe0, (byte) 0xe0 };
    }

    /**
     * End of parameteres
     */

    /**
     * Perform k number of hashes for every n-gram
     * 
     * @param nGram
     *            is an element/n-gram to register in the Bloom filter.
     */
    protected BitArray getBitArrayForNGram(String nGram) {
        BitArray bloomFilterBitArray = new BitArray(M);
        Map<String, Object> parameters1 = new HashMap<String, Object>();
        parameters1.put(Constants.SIGNING_KEY_HMAC_PARAMETER_NAME, this.salt1);
        Map<String, Object> parameters2 = new HashMap<String, Object>();
        parameters2.put(Constants.SIGNING_KEY_HMAC_PARAMETER_NAME, this.salt2);
        byte[] intermediateNGram = nGram.getBytes(Constants.charset);
        byte[] intermediateNGram1 = (byte[]) this.hmac1.transform(intermediateNGram, parameters1);
        long h1 = this.getLeastSignificantBytes(intermediateNGram1);
        byte[] intermediateNGram2 = (byte[]) this.hmac2.transform(intermediateNGram, parameters2);
        long h2 = this.getLeastSignificantBytes(intermediateNGram2);
        for (int hashRound = 0; hashRound < K; hashRound++) {
            long h = h1 + (hashRound * h2);
            // Do the modulo computation to acquire which bits to set in the
            // filter
            h = Math.abs(h % M);

            bloomFilterBitArray.set((int) h, true);
        }
        return bloomFilterBitArray;
    }

    private long getLeastSignificantBytes(byte[] bytes) {
        long h = 0;
        for (int i = 0; i < 7; i++) {
            h <<= 8;
            h |= ((long) bytes[i]) & 0xFF;
        }
        return h;
    }

    protected BitArray stringTransformCore(String field) {
        BitArray bloomFilterValue = new BitArray(M);

        NgramSequencer ngramSequencer = new NgramSequencer();

        ngramSequencer.init(field, NGRAM_SIZE, NGRAM_PADDING);
        // Go through every n-gram
        while (ngramSequencer.hasNext()) {
            String ngram = ngramSequencer.next();
            this.add(ngram, bloomFilterValue);
        }

        return bloomFilterValue;
    }

    public void add(String element, BitArray bloomFilterValue) {
        BitArray bloomFilterTemp = this.getBloomFilterBitPart(element);
        bloomFilterValue.or(bloomFilterTemp);
    }

    protected BitArray getBloomFilterBitPart(String element) {

        BitArray bloomFilterTemp = null;
        // Cache-ing mechanism
        String lookupStringM = element + "_" + M;
        String lookupStringMK = lookupStringM + "_" + K;
        if (this.ngramBloomBitsCache.containsKey(lookupStringMK)) {
            bloomFilterTemp = this.ngramBloomBitsCache.get(lookupStringMK);
        } else {
            bloomFilterTemp = this.getBitArrayForNGram(element);
            this.ngramBloomBitsCache.put(lookupStringMK, bloomFilterTemp);
        }
        return bloomFilterTemp;
    }

    public static void main(String[] args) {
        BloomFilterFunctionTest bfTest = new BloomFilterFunctionTest();
        BitArray output = bfTest.stringTransformCore("Pralav");
        byte[] outputByteArray = output.toByteArray();

        System.out.println(output);

        StringBuilder outputAsInts = new StringBuilder();
        for (byte outputByte : outputByteArray) {
            int outputInt = outputByte & 0xFF;
            outputAsInts.append(outputInt).append(",");
        }

        try {
            Files.write(outputByteArray, new File("output/bf_encoded_files/output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(outputAsInts.toString());
        System.out.println("Size: " + outputAsInts.length());
    }
}
