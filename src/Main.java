import window.Window;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        measure(1_000_000, () -> BigDecimal.ONE.add(BigDecimal.ONE));
        new Window();
    }

    private static double measure(int times, Runnable runnable) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            runnable.run();
        }
        return (System.currentTimeMillis() - startTime) / 1000.00;
    }
}
