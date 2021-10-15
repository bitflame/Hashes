public class CuckooHashTable<Key, Value> {
    private class Entry{
        Key key;
        Value value;

        Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
    private final class HashFunction<T> {
        private final int mA, mB;
        private final int mLgSize;

        public HashFunction(int coefficientA, int coefficientB, int lgSize){
            mA = coefficientA;
            mB = coefficientB;
            mLgSize = lgSize;
        }
        public int hash(Key key) {
            /*if object is null just evaluate to zero*/
            if (key==null) {
                return 0;
            }
            /* otherwise split its hash code into upper and lower bits */
            final int hashCode = key.hashCode();
            final int upper = hashCode >>> 16;
            final int lower = hashCode & (0xFFFF);
            /* return the pairwise product of those bits, shifted down so that
            * only lgSize bits remain in the output */
            return (upper * mA + lower * mB)>>>(32 - mLgSize);
        }
    }
}
