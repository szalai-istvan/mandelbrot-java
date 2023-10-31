package renderer.calculator;

import model.ComplexNumber;
import renderer.calculator.colormapping.ColorMappingMode;

import java.awt.Color;

public abstract class ColorCalculator<THRESHOLD_TYPE> {

    protected ColorMappingMode colorMappingMode;

    public Color getColorOfPoint(ComplexNumber c) {
        int iterationCount = iterationCount();
        THRESHOLD_TYPE absHighThreshold = absHighThreshold();
        ComplexNumber element = ComplexNumber.of(0.00, 0.00);
        for (int i = 0; i < iterationCount; i++) {
            element = next(element, c);
            if (exitCriteria(element, absHighThreshold)) {
                return colorMappingMode.getColor(i);
            }
        }
        return colorMappingMode.getColor(255);
    }

    public void useColorMode(ColorMappingMode mode) {
        this.colorMappingMode = mode;
    }

    protected abstract int iterationCount();
    protected abstract THRESHOLD_TYPE absHighThreshold();
    protected abstract ComplexNumber next(ComplexNumber prev, ComplexNumber c);
    protected abstract boolean exitCriteria(ComplexNumber c, THRESHOLD_TYPE threshold);
}
