import Calculator.Calculator;

public class StackCalculator {
    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        try {
            calc.readFile("commands.txt");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}