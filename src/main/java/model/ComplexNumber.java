package model;

import java.math.BigDecimal;

public abstract class ComplexNumber {

    public static ComplexNumber of(double real, double imaginary) {
        return new LightweightComplexNumber(real, imaginary);
    }

    public static ComplexNumber of(BigDecimal real, BigDecimal imaginary) {
        if (real.precision() >= 15 || imaginary.precision() >= 15) {
            return new HighPrecisionComplexNumber(real, imaginary);
        }
        return new LightweightComplexNumber(real.doubleValue(), imaginary.doubleValue());
    }

    public abstract double getReal();
    public abstract double getImaginary();
    public abstract BigDecimal getRealBD();
    public abstract BigDecimal getImaginaryBD();
    public abstract ComplexNumber add(ComplexNumber complexNumber);
    public abstract ComplexNumber subtract(ComplexNumber complexNumber);
    public abstract ComplexNumber multiply(ComplexNumber complexNumber);

    public final ComplexNumber square() {
        return multiply(this);
    }

    // Calculating the square of abs instead of abs is a performance optimization,
    // since calculating square root is CPU intensive
    public final double absSquare() {
        double re = getReal();
        double im = getImaginary();

        double realSquared = re * re;
        double imSquared = im * im;

        return realSquared + imSquared;
    }

    public final BigDecimal absSquareBD() {
        BigDecimal re = getRealBD();
        BigDecimal im = getImaginaryBD();

        BigDecimal realSquared = re.multiply(re);
        BigDecimal imSquared = im.multiply(im);
        return realSquared.add(imSquared);
    }

    public String toString() {
        String betweenCharacter = getImaginary() >= 0 ? "+" : "";
        double re = Math.round(getReal()*1_000) / 1_000.;
        double im = Math.round(getImaginary()*1_000) / 1_000.;
        return re + betweenCharacter + im + "i";
    }
}
