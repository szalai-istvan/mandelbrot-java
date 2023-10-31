package renderer.calculator;

import model.ComplexNumber;
import model.BigDecimalFactory;

import java.math.BigDecimal;

public class HighPrecisionCalculator extends ColorCalculator<BigDecimal> {

    @Override
    protected int iterationCount() {
        return 255;
    }

    @Override
    protected BigDecimal absHighThreshold() {
        return BigDecimalFactory.valueOf(100);
    }

    @Override
    protected ComplexNumber next(ComplexNumber prev, ComplexNumber c) {
        return prev.square().add(c);
    }

    @Override
    protected boolean exitCriteria(ComplexNumber c, BigDecimal threshold) {
        return c.absSquareBD().compareTo(threshold) > 0;
    }
}
