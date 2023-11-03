package renderer.renderer;

import model.ComplexNumber;
import model.BigDecimalFactory;
import renderer.calculator.ColorCalculator;
import renderer.renderer.baseclass.MandelbrotSetRenderer;

import java.math.BigDecimal;

import static model.BigDecimalFactory.*;

public class HighPrecisionRenderer extends MandelbrotSetRenderer<BigDecimal> {

    protected HighPrecisionRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    public MandelbrotSetRenderer renderLowResolution() {
        if (proceeding) {
            render(40);
        }
        return this;
    }

    @Override
    protected BigDecimal getStepSize(int step) {
        BigDecimal stepBD = BigDecimalFactory.valueOf(step);
        return one().multiply(stepBD).divide(screenPositionData.getZoomBD(), context());
    }

    @Override
    protected ComplexNumber stepSizeX(BigDecimal stepSize) {
        return ComplexNumber.of(stepSize, zero());
    }

    @Override
    protected ComplexNumber stepSizeY(BigDecimal stepSize) {
        return ComplexNumber.of(zero(), stepSize);
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
        return ComplexNumber.of(coordinate.getRealBD(), topLeft.getImaginaryBD());
    }

    @Override
    protected ComplexNumber topLeft() {
        return screenPositionData.getTopLeftBD();
    }

}
