package renderer.calculator;

import renderer.calculator.colormapping.ColorMappingMode;
import model.ComplexNumber;

import java.awt.*;

import static renderer.calculator.colormapping.ColorMappingMode.RED;
import static java.awt.Color.WHITE;

public class LightWeightCalculator extends ColorCalculator<Integer> {

    @Override
    protected int iterationCount() {
        return 255;
    }

    @Override
    protected Integer absHighThreshold() {
        return 100;
    }

    @Override
    protected ComplexNumber next(ComplexNumber prev, ComplexNumber c) {
        return prev.square().add(c);
    }

    @Override
    protected boolean exitCriteria(ComplexNumber c, Integer threshold) {
        return c.absSquare() > threshold;
    }
}
