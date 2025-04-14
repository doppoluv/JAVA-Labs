package FactCalculate;

public class FactCalculate {
    public static class Calc implements Runnable {
        private final int number;
        private final boolean isRecursive;
        private long rResult;
        private long iResult;

        public Calc(int number, boolean isRecursive) {
            this.number = number;
            this.isRecursive = isRecursive;
        }

        @Override
        public void run() {
            if (isRecursive) {
                rResult = calculateRecursive(number);
            } else {
                iResult = calculateIterative(number);
            }
        }

        private long calculateRecursive(int n) {
            return n <= 1 ? 1 : n * calculateRecursive(n - 1);
        }

        private long calculateIterative(int n) {
            long factorial = 1;
            for (int i = 2; i <= n; i++) {
                factorial *= i;
            }
            return factorial;
        }

        public long getRResult() {
            return rResult;
        }

        public long getIResult() {
            return iResult;
        }
    }
}
