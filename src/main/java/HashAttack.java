import java.util.ArrayList;
import java.util.List;
/* Thanks to Rene Argento for most the code here https://github.com/reneargento */
public class HashAttack {
    String s = "";

    HashAttack() {

    }

    private List<String> generateStringsInput(int n) {
        String[] values = {"Aa", "BB"};
        List<String> strings = new ArrayList<>();
        generateStrings(n, 0, strings, "", values);
        return strings;
    }

    private void generateStrings(int n, int currentIndex, List<String> strings, String currentString, String[] values) {
        if (currentIndex == n) {
            strings.add(currentString);
            return;
        }
        for (String value : values) {
            String newValue = currentString + value;
            int newIndex = currentIndex + 1;

            generateStrings(n, newIndex, strings, newValue, values);
        }
    }

    public int myHashCode(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++)
            hash = 0x7fffffff & ((hash * 31) + s.charAt(i));
        return hash;
    }

    public static void main(String[] args) {
        HashAttack h = new HashAttack();
        List<String> hashAttackInput = h.generateStringsInput(3);

        for (String string : hashAttackInput) {
            System.out.println(string + ":" + h.myHashCode(string));
        }
        hashAttackInput = h.generateStringsInput(4);
        for (String string : hashAttackInput) {
            System.out.println(string + ":" + h.myHashCode(string));
        }
        hashAttackInput = h.generateStringsInput(6);
        for (String string : hashAttackInput) {
            System.out.println(string + ":" + h.myHashCode(string));
        }
        String s1 = "AaBBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWYYZZAAbbccddeeffgghhiijjkkllmmnnoopp";
        String s2 = "AaBBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWYYZZppAAbbccddeeffgghhiijjkkllmmnnoo";
        System.out.println(h.myHashCode(s1));
        System.out.println(h.myHashCode(s2));
        // same string in a different order with length more than 31
        s1 = "AaBBCCDDEEFFGGHHIIJJKKLLMMNNOOPP";
        s2 = "AaBBCCDDEEFFGGHHIIJJKKLLMMNNPPOO";
        System.out.println(h.myHashCode(s1));
        System.out.println(h.myHashCode(s2));
        s1 = "AaBBCCDDEEFFGGHHIIJJKKLLMMNNOO";
        s2 = "AaBBCCDDEEFFGGHHIIJJKKLLMMOONN";
        System.out.println(h.myHashCode(s1));
        System.out.println(h.myHashCode(s2));
    }
}
