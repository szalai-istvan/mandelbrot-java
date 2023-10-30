package renderer.calculator;

import renderer.calculator.colormapping.ColorMappingMode;
import model.ComplexNumber;

import java.awt.*;

import static renderer.calculator.colormapping.ColorMappingMode.RED;
import static java.awt.Color.WHITE;

public class LightWeightCalculator implements ColorCalculator {
    private static final double ABS_HIGH_THRESHOLD = 100;
    private static final int ITERATION_COUNT = 255;

    private ColorMappingMode colorMappingMode;

    @Override
    public Color getColorOfPoint(ComplexNumber c) {
        ComplexNumber element = ComplexNumber.of(0.00, 0.00);
        for (int i = 0; i < ITERATION_COUNT; i++) {
            element = element.square().add(c);
            double abs = element.square().absSquare();
            if (abs > ABS_HIGH_THRESHOLD) {
                return colorMappingMode.getColor(i);
            }
        }
        return colorMappingMode.getColor(255);
    }

    @Override
    public void useColorMode(ColorMappingMode mode) {
        this.colorMappingMode = mode;
    }

}
