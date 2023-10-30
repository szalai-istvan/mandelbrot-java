package renderer.calculator;

import model.ComplexNumber;
import renderer.calculator.colormapping.ColorMappingMode;

import java.awt.Color;

public interface ColorCalculator {
    Color getColorOfPoint(ComplexNumber complexNumber);
    void useColorMode(ColorMappingMode mode);
}
