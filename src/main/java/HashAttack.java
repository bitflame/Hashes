public class HashAttack {
    String s = "";

    private int hash(String s) {
        /* return zero is string length is not a power of 2*/
        if (s.length() % 2 != 0) return 0;
        else {

        }
        int hash = s.hashCode() & 0x7fffffff;
        return hash;
    }

    private int length() {
        return s.length();
    }

    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < length(); i++)
            hash = (hash * 31) + charAt(i);
        return hash;
    }

    public static void main(String[] args) {

    }
}
