package window;

import model.ComplexNumber;
import model.BigDecimalFactory;

import java.awt.*;
import java.math.BigDecimal;

import static model.BigDecimalFactory.adjustPrecisionToZoom;

public class ScreenPositionData {
    private static final double ZOOM_STEP = 1.2;
    private static final BigDecimal ZOOM_STEP_BD = BigDecimalFactory.valueOf(ZOOM_STEP);

    private double initialZoom;
    private ComplexNumber initialTopLeft;
    private ComplexNumber topLeft;
    private double zoomValue;
    private int zoomCounter = 0;
    private Dimension screenSize;

    public static ScreenPositionData withTopLeftCoordinatesAndInitialZoom(
            ComplexNumber topLeft,
            Dimension screenSize,
            double zoom) {
        ScreenPositionData data = new ScreenPositionData();
        data.initialTopLeft = topLeft;
        data.topLeft = topLeft;
        data.screenSize = screenSize;
        data.initialZoom = zoom;
        data.zoomValue = zoom;
        return data;
    }

    public double zoomValue() {
        return zoomValue;
    }

    public BigDecimal getZoomBD() {
        return BigDecimalFactory.valueOf(initialZoom)
                .multiply(ZOOM_STEP_BD.pow(zoomCounter));
    }

    public ComplexNumber getTopLeft() {
        return topLeft;
    }

    public ComplexNumber getTopLeftBD() {
        return ComplexNumber.of(
                BigDecimalFactory.valueOf(topLeft.getRealBD()),
                BigDecimalFactory.valueOf(topLeft.getImaginaryBD())
        );
    }

    public ComplexNumber getCenter() {
        return ComplexNumber.of(
                topLeft.getReal() + screenSize.getWidth() / 2 / zoomValue,
                topLeft.getImaginary() - screenSize.getHeight() / 2 / zoomValue
        );
    }

    public void zoomIn() {
        if (zoomValue * ZOOM_STEP < 9E+15) {
            double centerReal = topLeft.getReal() + (screenSize.getWidth() / 2) / zoomValue;
            double centerIm = topLeft.getImaginary() - (screenSize.getHeight() / 2) / zoomValue;
            zoomCounter++;
            zoomValue *= ZOOM_STEP;
            adjustPrecisionToZoom(zoomValue);
            recalculateTopLeft(centerReal, centerIm);
        }

    }

    public void zoomOut() {
        double centerReal = topLeft.getReal() + (screenSize.getWidth() / 2) / zoomValue;
        double centerIm = topLeft.getImaginary() - (screenSize.getHeight() / 2) / zoomValue;
        zoomCounter--;
        zoomValue /= ZOOM_STEP;
        adjustPrecisionToZoom(zoomValue);

        recalculateTopLeft(centerReal, centerIm);
    }

    void recenter(double x, double y) {
        double newCenterRe = topLeft.getReal() + x / zoomValue;
        double newCenterIm = topLeft.getImaginary() - y / zoomValue;
        recalculateTopLeft(newCenterRe, newCenterIm);
    }

    void reset() {
        topLeft = initialTopLeft;
        zoomValue = initialZoom;
    }

    private void recalculateTopLeft(double newCenterRe, double newCenterIm) {
        topLeft = ComplexNumber.of(
                newCenterRe - (screenSize.getWidth() / 2) / zoomValue,
                newCenterIm + (screenSize.getHeight() / 2) / zoomValue
        );
    }
}
