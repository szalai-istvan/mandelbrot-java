import model.ScaledBigDecimal;
import window.Window;

import java.math.BigDecimal;

import static model.ScaledBigDecimal.ONE;

public class Main {
    public static void main(String[] args) {
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
