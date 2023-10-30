package model;

public class HighPrecisionComplexNumber extends ComplexNumber {
    private final ScaledBigDecimal real;
    private final ScaledBigDecimal imaginary;

    HighPrecisionComplexNumber(ScaledBigDecimal real, ScaledBigDecimal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public double getReal() {
        return real.doubleValue();
    }

    @Override
    public double getImaginary() {
        return imaginary.doubleValue();
    }

    @Override
    public ScaledBigDecimal getRealBD() {
        return real;
    }

    @Override
    public ScaledBigDecimal getImaginaryBD() {
        return imaginary;
    }

    @Override
    public ComplexNumber add(ComplexNumber complexNumber) {
        return new HighPrecisionComplexNumber(
                getRealBD().add(complexNumber.getRealBD()),
                getImaginaryBD().add(complexNumber.getImaginaryBD())
        );
    }

    @Override
    public ComplexNumber subtract(ComplexNumber complexNumber) {
        return new HighPrecisionComplexNumber(
                getRealBD().subtract(complexNumber.getRealBD()),
                getImaginaryBD().subtract(complexNumber.getImaginaryBD())
        );
    }

    @Override
    public ComplexNumber multiply(ComplexNumber complexNumber) {
        //(a+bi)(c+di)=ac+adi+bci-bd
        ScaledBigDecimal ac = real.multiply(complexNumber.getRealBD());
        ScaledBigDecimal bd = imaginary.multiply(complexNumber.getImaginaryBD());
        ScaledBigDecimal ad = real.multiply(complexNumber.getImaginaryBD());
        ScaledBigDecimal bc = imaginary.multiply(complexNumber.getRealBD());

        return new HighPrecisionComplexNumber(
                ac.subtract(bd),
                ad.add(bc)
        );
    }
}
