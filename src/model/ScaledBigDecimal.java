package model;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.RoundingMode.HALF_UP;

public class ScaledBigDecimal implements Comparable<ScaledBigDecimal> {
    private static MathContext CONTEXT = new MathContext(10, HALF_UP);
    public static final ScaledBigDecimal ZERO = valueOf(BigDecimal.ZERO);
    public static final ScaledBigDecimal ONE = valueOf(BigDecimal.ONE);
    public static final ScaledBigDecimal TEN = valueOf(BigDecimal.TEN);

    private final BigDecimal value;

    ScaledBigDecimal(BigDecimal value) {
        this.value = value;
    }

    public static int getScale() {
        return CONTEXT.getPrecision();
    }

    public static boolean preciseCalculationIsNeeded() {
        return getScale() > 15;
    }

    public static ScaledBigDecimal valueOf(double val) {
        return new ScaledBigDecimal(BigDecimal.valueOf(val));
    }

    public static ScaledBigDecimal valueOf(BigDecimal val) {
        return new ScaledBigDecimal(val.setScale(CONTEXT.getPrecision(), CONTEXT.getRoundingMode()));
    }

    public ScaledBigDecimal add(ScaledBigDecimal augend) {
        return valueOf(value.add(augend.value, CONTEXT));
    }

    public ScaledBigDecimal subtract(ScaledBigDecimal subtrahend) {
        return valueOf(value.subtract(subtrahend.value, CONTEXT));
    }

    public ScaledBigDecimal multiply(ScaledBigDecimal multiplicand) {
        return valueOf(value.multiply(multiplicand.value, CONTEXT));
    }

    public ScaledBigDecimal divide(ScaledBigDecimal divisor) {
        return valueOf(value.divide(divisor.value, CONTEXT));
    }

    public ScaledBigDecimal pow(int n) {
        return valueOf(value.pow(n, CONTEXT));
    }

    public ScaledBigDecimal abs() {
        return valueOf(value.abs(CONTEXT));
    }

    public ScaledBigDecimal sqrt() {
        return valueOf(value.sqrt(CONTEXT));
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(ScaledBigDecimal o) {
        return value.compareTo(o.value);
    }
}
