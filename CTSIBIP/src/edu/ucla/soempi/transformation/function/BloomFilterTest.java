package edu.ucla.soempi.transformation.function;

import org.junit.Test;
import org.openhie.openempi.Constants;

public class BloomFilterTest
{

	@Test
	public void bloomFilterTest() {
		String nGram = "pr";
		byte[] nGramAsByteArray = nGram.getBytes(Constants.charset);
		for (byte byteElement : nGramAsByteArray) {
			this.printByte(byteElement);
		}

		System.out.println(this.getLeastSignificantBytes(nGramAsByteArray));
	}

	private void printByte(byte byteElement) {
		String s1 = String.format("%8s", Integer.toBinaryString(byteElement & 0xFF)).replace(' ', '0');
		System.out.println(s1);
	}

	private long getLeastSignificantBytes(byte[] bytes) {
		long h = 0;
		for (int i = 0; i < 7; i++) {
			h <<= 8;
			h |= ((long) bytes[i]) & 0xFF;
			System.out.println(h);
		}
		return h;
	}
}
