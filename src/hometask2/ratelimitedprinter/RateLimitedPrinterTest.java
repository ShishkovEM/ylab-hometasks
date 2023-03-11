package hometask2.ratelimitedprinter;

public class RateLimitedPrinterTest {
    static int BOUND = 1000;

    public static void main(String[] args) {
       Printer printer = new RateLimitedPrinter(BOUND);

        for (int i = 0; i < 1_000_000_000; i++) {
            printer.print(String.valueOf(i));
        }
    }
}
