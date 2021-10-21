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

    private String decode(String str) {
        StringBuilder sb = new StringBuilder();
        int lo = 0;
        int hi = 1;
        while(hi<str.length()) {
            char letter = str.charAt(lo);
            int digit = Integer.parseInt(str.substring(hi, hi + 1));
            while (digit != 0) {
                sb.append(letter);
                digit--;
            }
            lo = hi+1;
            hi = lo+1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        GfG G = new GfG();
        System.out.println(G.encode("a"));
        System.out.println(G.encode("aaaabbbccc"));
        System.out.printf(G.decode("A3B1C2D1E1"));
    }
}
