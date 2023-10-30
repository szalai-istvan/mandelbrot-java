package model;

public class LightweightComplexNumber extends ComplexNumber {

    private final double real;
    private final double imaginary;

    LightweightComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public double getReal() {
        return real;
    }

    @Override
    public double getImaginary() {
        return imaginary;
    }

    @Override
    public ScaledBigDecimal getRealBD() {
        return ScaledBigDecimal.valueOf(real);
    }

    @Override
    public ScaledBigDecimal getImaginaryBD() {
        return ScaledBigDecimal.valueOf(imaginary);
    }

    @Override
    public ComplexNumber add(ComplexNumber complexNumber) {
        return new LightweightComplexNumber(
                getReal() + complexNumber.getReal(),
                getImaginary() + complexNumber.getImaginary()
        );
    }

    @Override
    public ComplexNumber subtract(ComplexNumber complexNumber) {
        return new LightweightComplexNumber(
                getReal() - complexNumber.getReal(),
                getImaginary() - complexNumber.getImaginary()
        );
    }

    @Override
    public ComplexNumber multiply(ComplexNumber complexNumber) {
        //(a+bi)(c+di)=ac+adi+bci-bd
        double re = complexNumber.getReal();
        double im = complexNumber.getImaginary();
        double ac = real * re;
        double bd = imaginary * im;
        double ad = real * im;
        double bc = imaginary * re;

        return new LightweightComplexNumber(
                ac - bd,
                ad + bc
        );
    }
}
