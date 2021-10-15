import edu.princeton.cs.algs4.In;

public class msic {
    public static void main(String[] args) {
        int c = -153;
        System.out.printf("%32s%n", Integer.toBinaryString(c));
        System.out.printf("%32s%n", Integer.toBinaryString(c >>= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c <<= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c >>>= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c <<= 2));
        c = 153;
        System.out.printf("%32s%n", Integer.toBinaryString(c >>= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c <<= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c >>>= 2));
        System.out.printf("%32s%n", Integer.toBinaryString(c <<= 2));
        System.out.println("Integer Max Value in decimal: "+Integer.MAX_VALUE + ": " +
                String.format("%32s", Integer.toBinaryString(Integer.MAX_VALUE)).
                replace(' ', '0'));
        System.out.println("Integer Min value in decimal: "+Integer.MIN_VALUE+": "+
                String.format(" %32s", Integer.toBinaryString(Integer.MIN_VALUE)).
                        replace(' ', '0'));
        System.out.println("-1:"+-1+":"+String.format("%32s",Integer.toBinaryString(-1)).replace(' ','0'));
        System.out.println("0:"+0+":"+String.format("%32s",Integer.toBinaryString(0)).replace(' ','0'));
        System.out.println("1:"+1+":"+String.format("%32s",Integer.toBinaryString(1)).replace(' ','0'));
    }
}
