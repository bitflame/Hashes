import edu.princeton.cs.algs4.Accumulator;

import java.util.Locale;

public class GfG {
    String encode(String str) {
        if (str.equals("") || str == null || str.length() == 0)

            throw new IllegalArgumentException("String value to encode() should be valid");
        char prevChar = str.charAt(0);
        char currentChar;
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            currentChar = str.charAt(i);
            if (currentChar != prevChar) {
                sb.append(prevChar);
                sb.append(counter);
                counter = 1;
                prevChar = currentChar;
            } else {
                prevChar = currentChar;
                counter++;
            }
        }
        sb.append(prevChar);
        sb.append(counter);
        return sb.toString();
    }

    // below code from Geeks for Geeks
    void gfg_Encode(String str) {
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int count = 1;
            while (i < n - 1 && str.charAt(i) == str.charAt(i + 1)) {
                count++;
                i++;
            }
            // print character and its count
            System.out.print(str.charAt(i));
            System.out.println(count);
        }
    }

    String encode_v2(String str) {
        if (str == null || str.length() == 0) return "";
        char[] cArray = str.toCharArray();
        if (cArray.length == 1) return cArray[0] + "1";
        int counter = 0;
        int charCounter = 1;
        char prevChar = cArray[counter];
        char currentChar = cArray[counter + 1];
        StringBuilder sb = new StringBuilder();
        while (counter < cArray.length-1) {
            if (currentChar != prevChar) {
                sb.append(prevChar);
                sb.append(charCounter);
                charCounter = 1;
            }
            counter++;
            prevChar = cArray[counter - 1];
            currentChar = cArray[counter];
            if (prevChar==currentChar) charCounter++;
        }
       /* if (currentChar != prevChar) {
            sb.append(prevChar);
            sb.append(charCounter);
            sb.append(currentChar);
            sb.append(1);
        } else {
            sb.append(prevChar);
            sb.append(++charCounter);
        } */
        sb.append(prevChar);
        sb.append(charCounter);
        return sb.toString().toUpperCase();
    }

    // This method breaks if the number is larger than 9 i.e. one digit
    private String decode(String str) {
        StringBuilder sb = new StringBuilder();
        int lo = 0;
        int hi = 1;
        while (hi < str.length()) {
            char letter = str.charAt(lo);
            int digit = Integer.parseInt(str.substring(hi, hi + 1));
            while (digit != 0) {
                sb.append(letter);
                digit--;
            }
            lo = hi + 1;
            hi = lo + 1;
        }
        return sb.toString().toUpperCase(Locale.ENGLISH);
    }

    // this might be doable with a couple of pointers if we could use an array
    public String decode_v2(String str) {
        StringBuilder sb = new StringBuilder();
        char c = str.charAt(0);
        char currentChar;
        int digits = 0;
        for (int i = 0; i < str.length(); i++) {
            currentChar = str.charAt(i);
            if (!Character.isDigit(currentChar)) {

                for (int j = 0; j < digits; j++) {
                    sb.append(c);
                }
                digits = 0;
                c = currentChar;
            }
            if (Character.isDigit(currentChar)) {
                int digit = Integer.parseInt("" + currentChar);
                if (digit < 0)
                    throw new IllegalArgumentException("the value to decode_v2() has to be greater than 0.");
                digits = digits * 10 + digit;
            }
        }
        for (int i = 0; i < digits; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        GfG G = new GfG();
        System.out.println(G.encode("a"));
        System.out.println(G.encode("aaaabbbccc"));
        System.out.println("Encode version 2 gives: " + G.encode_v2("a") + " for encoding 'a' ");
        System.out.println("Encode version 2 gives: " + G.encode_v2("aaaabbbccc") + " for encoding 'aaaabbbccc' ");
        System.out.println(G.decode_v2("A3B1C2D1E1"));
        System.out.printf("\n%s\n", G.decode_v2("A10"));
        System.out.printf("%s\n", G.decode_v2("A122"));
        //System.out.printf("%s\n", G.decode_v2("A1232"));
    }
}
