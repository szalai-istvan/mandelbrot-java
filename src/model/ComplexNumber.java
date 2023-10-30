package model;

public abstract class ComplexNumber {

    public static ComplexNumber of(double real, double imaginary) {
        return new LightweightComplexNumber(real, imaginary);
    }

    public static ComplexNumber of(ScaledBigDecimal real, ScaledBigDecimal imaginary) {
        if (ScaledBigDecimal.preciseCalculationIsNeeded()) {
            return new HighPrecisionComplexNumber(real, imaginary);
        }
        return new LightweightComplexNumber(real.doubleValue(), imaginary.doubleValue());
    }

    public abstract double getReal();
    public abstract double getImaginary();
    public abstract ScaledBigDecimal getRealBD();
    public abstract ScaledBigDecimal getImaginaryBD();
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

    public final ScaledBigDecimal absSquareBD() {
        ScaledBigDecimal re = getRealBD();
        ScaledBigDecimal im = getImaginaryBD();

        ScaledBigDecimal realSquared = re.multiply(re);
        ScaledBigDecimal imSquared = im.multiply(im);
        return realSquared.add(imSquared);
    }

    public String toString() {
        String betweenCharacter = getImaginary() < 0 ? "" : "+";
        double re = Math.round(getReal()*1_000) / 1_000.;
        double im = Math.round(getImaginary()*1_000) / 1_000.;
        return re + betweenCharacter + im + "i";
    }
}
