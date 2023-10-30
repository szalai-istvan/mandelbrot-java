import model.ScaledBigDecimal;
import window.Window;

import java.math.BigDecimal;

import static model.ScaledBigDecimal.ONE;

public class Main {
    public static void main(String[] args) {
/*        double primitiveTime = measure(10_000_000, () -> {
            double d = 1.00 + 1.00;
        });

        double scaledBigDecimalTime = measure(10_000_000, () -> {
            ScaledBigDecimal big = ONE.add(ONE);
        });
        BigDecimal bd = BigDecimal.ONE.setScale(10);
        double bigDecimalTime = measure(10_000_000, () -> {
            BigDecimal big = bd.add(bd);
        });*/

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