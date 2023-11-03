package renderer.renderer;

import model.ComplexNumber;
import renderer.calculator.ColorCalculator;
import renderer.renderer.baseclass.MandelbrotSetRenderer;

public class LightweightRenderer extends MandelbrotSetRenderer<Double> {

    LightweightRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    protected Double getStepSize(int step) {
        return 1.00 * step / screenPositionData.zoomValue();
    }

    @Override
    protected ComplexNumber stepSizeX(Double stepSize) {
        return ComplexNumber.of(stepSize, 0.00);
    }

    @Override
    protected ComplexNumber stepSizeY(Double stepSize) {
        return ComplexNumber.of(0.00, stepSize);
    }

    @Override
    protected ComplexNumber xStep(ComplexNumber coordinate, ComplexNumber stepSizeX) {
        return coordinate.add(stepSizeX);
    }

    @Override
    protected ComplexNumber yStep(ComplexNumber coordinate, ComplexNumber stepSizeY) {
        return coordinate.subtract(stepSizeY);
    }

    @Override
    protected ComplexNumber topOfColumn(ComplexNumber coordinate, ComplexNumber topLeft) {
        return ComplexNumber.of(coordinate.getReal(), topLeft.getImaginary());
    }

    @Override
    protected ComplexNumber topLeft() {
        return screenPositionData.getTopLeft();
    }
}
