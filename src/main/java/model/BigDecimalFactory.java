package model;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.*;
import static java.math.RoundingMode.HALF_UP;

public class BigDecimalFactory {
    private static MathContext CONTEXT = new MathContext(10, HALF_UP);

    public static BigDecimal zero() {
        return scale(ZERO);
    }

    public static BigDecimal one() {
        return scale(ONE);
    }

    public static BigDecimal ten() {
        return scale(TEN);
    }

    public static MathContext context() {
        return new MathContext(CONTEXT.getPrecision(), CONTEXT.getRoundingMode());
    }

    public static void adjustPrecisionToZoom(double zoomValue) {
        int log10 = Double.valueOf(Math.log10(zoomValue)).intValue();
        CONTEXT = new MathContext(log10, HALF_UP);
    }

    private static BigDecimal scale(BigDecimal bigDecimal) {
        return bigDecimal.setScale(CONTEXT.getPrecision(), CONTEXT.getRoundingMode());
    }

    public static BigDecimal valueOf(double val) {
        return scale(BigDecimal.valueOf(val));
    }

    public static BigDecimal valueOf(BigDecimal val) {
        return scale(val);
    }
}
