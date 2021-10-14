import edu.princeton.cs.algs4.*;

public class HashUtility<T> {
    int M = 4;
    int R = 11;
    int hash = 17;
    T t;

    public HashUtility(T t) {
        this.t = t;
    }

    public HashUtility(T t, int M, int R) {
        this.M = M;
        this.R = R;
        this.t = t;
    }

    public int getHash(T t) {
        int hash = 0;
        if (t instanceof Point2D) {
            hash = (int) (((Point2D) t).x() * R + ((Point2D) t).y()) % M;
        } else if (t instanceof Date) {
            hash = ((((Date) t).day() * R + ((Date) t).month() % M) * R + ((Date) t).year()) % M;
        } else if (t instanceof Interval1D) {
            hash = (((Interval1D) t).hashCode() & 0x7fffffff) % M;
        } else if (t instanceof Interval2D) {
            hash = (((Interval2D) t).hashCode() & 0x7fffffff) % M;
        } else if (t instanceof String) {
            for (Character c : ((String) t).toCharArray()) hash = c + (R * hash) % M;
        }
        return hash;
    }

    public static void main(String[] args) {
        // exercise 3.4.22
        Point2D p = new Point2D(0.5, 0.5);
        HashUtility h = new HashUtility(p);
        StdOut.println("Hash of the Point: " + p + " for table size 4 is :" + h.getHash(p));
        h = new HashUtility(p, 8, 11);
        StdOut.println("Hash of the Point: " + p + " for table size 8 is :" + h.getHash(p));
        // answer to exercise 3.4.23
        String test = "test";
        h = new HashUtility(test, 255, 256);
        StdOut.println("Hash of the string " + test + " with R = 256 and M = 255 is: " + h.getHash(test));
        StdOut.println("Hash of the string  estt with R = 256 and M = 255 is: " + h.getHash("estt"));
    }
}
