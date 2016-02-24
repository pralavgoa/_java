package ctsibip.wise.survey;

import java.math.BigInteger;

public class WISELinkEncoderDecoder {

    /**
     * encoding - convert digit-formatted ID to be the character-formatted
     * TOGGLE NAME OF THIS FUNCTION to move to production mode
     * 
     * @param charId
     *            The String to encode.
     * @return String The encoded input.
     */
    public static String encode(String userId) {
        int baseNumb = (Integer.parseInt(userId) * 31) + 97654;
        String s1 = Integer.toString(baseNumb);
        String s2 = Integer.toString(26);
        BigInteger b1 = new BigInteger(s1);
        BigInteger b2 = new BigInteger(s2);

        int counter = 0;
        String charId = new String();
        while (counter < 5) {
            BigInteger[] bs = b1.divideAndRemainder(b2);
            b1 = bs[0];
            int encodeValue = bs[1].intValue() + 65;
            charId = charId + (new Character((char) encodeValue).toString());
            counter++;
        }
        return charId;
    }

    public static String decode(String charId) {
        String result = new String();
        int sum = 0;
        for (int i = charId.length() - 1; i >= 0; i--) {
            char c = charId.charAt(i);
            int remainder = c - 65;
            sum = (sum * 26) + remainder;
        }

        sum = sum - 97654;
        int remain = sum % 31;
        if (remain == 0) {
            sum = sum / 31;
            result = Integer.toString(sum);
        } else {
            result = "invalid";
        }
        return result;
    }

    public static void main(String[] args) {
        // System.out.println(decode("OTQFA"));
        // System.out.println(decode("HUOFA"));

        String[] msgIds = { "5367" };

        for (String msgId : msgIds) {
            System.out.print("survey.ctsi.ucla.edu:8080/WISE/survey?msg=");
            System.out.print(encode(msgId));
            System.out.println("&t=HUOFA");
        }

    }
}
