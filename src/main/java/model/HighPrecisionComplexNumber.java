package model;

import java.math.BigDecimal;

public class HighPrecisionComplexNumber extends ComplexNumber {
    private final BigDecimal real;
    private final BigDecimal imaginary;

    HighPrecisionComplexNumber(BigDecimal real, BigDecimal imaginary) {
        this.real = BigDecimalFactory.valueOf(real);
        this.imaginary = BigDecimalFactory.valueOf(imaginary);
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
    public BigDecimal getRealBD() {
        return real;
    }

    @Override
    public BigDecimal getImaginaryBD() {
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
        BigDecimal ac = real.multiply(complexNumber.getRealBD());
        BigDecimal bd = imaginary.multiply(complexNumber.getImaginaryBD());
        BigDecimal ad = real.multiply(complexNumber.getImaginaryBD());
        BigDecimal bc = imaginary.multiply(complexNumber.getRealBD());

        return new HighPrecisionComplexNumber(
                ac.subtract(bd),
                ad.add(bc)
        );
    }
}
