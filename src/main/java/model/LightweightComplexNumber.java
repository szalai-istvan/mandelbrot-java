package model;

import java.math.BigDecimal;

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
    public BigDecimal getRealBD() {
        return BigDecimalFactory.valueOf(real);
    }

    @Override
    public BigDecimal getImaginaryBD() {
        return BigDecimalFactory.valueOf(imaginary);
    }

    @Override
    public ComplexNumber add(ComplexNumber complexNumber) {
        if (complexNumber instanceof HighPrecisionComplexNumber) {
            return complexNumber.add(this);
        }
        return ComplexNumber.of(
                getReal() + complexNumber.getReal(),
                getImaginary() + complexNumber.getImaginary()
        );
    }

    @Override
    public ComplexNumber subtract(ComplexNumber complexNumber) {
        if (complexNumber instanceof HighPrecisionComplexNumber) {
            return complexNumber.subtract(this);
        }
        return ComplexNumber.of(
                getReal() - complexNumber.getReal(),
                getImaginary() - complexNumber.getImaginary()
        );
    }

    @Override
    public ComplexNumber multiply(ComplexNumber complexNumber) {
        if (complexNumber instanceof HighPrecisionComplexNumber) {
            return complexNumber.multiply(this);
        }
        //(a+bi)(c+di)=ac+adi+bci-bd
        double re = complexNumber.getReal();
        double im = complexNumber.getImaginary();
        double ac = real * re;
        double bd = imaginary * im;
        double ad = real * im;
        double bc = imaginary * re;

        return ComplexNumber.of(
                ac - bd,
                ad + bc
        );
    }
}
