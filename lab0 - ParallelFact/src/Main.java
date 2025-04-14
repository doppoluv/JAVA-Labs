import FactCalculate.FactCalculate.*;

public class Main {
    public static void main(String[] args) {
        int number = 11;

        Calc r = new Calc(number, true);
        Calc i = new Calc(number, false);

        Thread rThread = new Thread(r, "R");
        Thread iThread = new Thread(i, "I");
        rThread.start();
        iThread.start();

        try {
            rThread.join();
            iThread.join();
        } catch (Exception e) {
            e.getStackTrace();
        }

        System.out.println("Рекурсивный метод: " + r.getRResult());
        System.out.println("Итеративный метод: " + i.getIResult());
    }
}