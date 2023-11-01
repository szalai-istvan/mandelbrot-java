package renderer.renderer.baseclass;

import model.ComplexNumber;
import renderer.calculator.ColorCalculator;
import renderer.calculator.colormapping.ColorMappingMode;
import renderer.calculator.colormapping.ColorPostProcessor;
import window.ScreenPositionData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public abstract class MandelbrotSetRenderer<COORDINATE_TRACKER_TYPE> {
    protected ScreenPositionData screenPositionData;
    protected JComponent target;
    protected ColorCalculator calculator;
    protected Graphics graphics;
    protected boolean proceeding = true;
    protected ColorPostProcessor postProcessor;

    protected MandelbrotSetRenderer(ColorCalculator calculator) {
        this.calculator = calculator;
    }

    public MandelbrotSetRenderer renderHighResolution() {
        if (proceeding) {
            render(2);
        }
        return this;
    }

    public MandelbrotSetRenderer renderLowResolution() {
        if (proceeding) {
            render(20);
        }
        return this;
    }


    public MandelbrotSetRenderer useColorMode(ColorMappingMode mode) {
        this.calculator.useColorMode(mode);
        return this;
    }

    public MandelbrotSetRenderer usePostProcessor(ColorPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
        return this;
    }

    public MandelbrotSetRenderer useScreenPositionData(ScreenPositionData data) {
        this.screenPositionData = data;
        return this;
    }

    public MandelbrotSetRenderer useTarget(JComponent target) {
        this.target = target;
        return this;
    }

    protected void render(int resolution) {
        this.graphics = getGraphics();

        int step = resolution / 2;
        COORDINATE_TRACKER_TYPE stepSize = getStepSize(step);
        ComplexNumber stepSizeX = stepSizeX(stepSize);
        ComplexNumber stepSizeY = stepSizeY(stepSize);
        ComplexNumber topLeft = topLeft();
        ComplexNumber coordinate = topLeft();
        BufferedImage image = new BufferedImage(target.getWidth(), target.getHeight(), TYPE_3BYTE_BGR);

        for (int x = 0; x < target.getWidth() - resolution; x += step) {
            for (int y = 0; y < target.getHeight() - resolution; y += step) {
                if (!proceeding) {
                    cancel();
                    return;
                }
                Color colorOfPoint = getColor(coordinate);
                setColor(image, x, y, colorOfPoint, resolution);
                coordinate = yStep(coordinate, stepSizeY);
            }
            coordinate = topOfColumn(coordinate, topLeft);
            coordinate = xStep(coordinate, stepSizeX);
        }

        postRender(image);
    }

    protected Color getColor(ComplexNumber coordinate) {
        Color colorOfPoint = calculator.getColorOfPoint(coordinate);
        if (postProcessor == null) {
            return colorOfPoint;
        };
        return postProcessor.postProcess(colorOfPoint);
    }

    protected abstract COORDINATE_TRACKER_TYPE getStepSize(int step);
    protected abstract ComplexNumber stepSizeX(COORDINATE_TRACKER_TYPE stepSize);
    protected abstract ComplexNumber stepSizeY(COORDINATE_TRACKER_TYPE stepSize);
    protected abstract ComplexNumber xStep(ComplexNumber coordinate, ComplexNumber stepSizeX);
    protected abstract ComplexNumber yStep(ComplexNumber coordinate, ComplexNumber stepSizeY);
    protected abstract ComplexNumber topOfColumn(ComplexNumber coordinate, ComplexNumber topLeft);
    protected abstract ComplexNumber topLeft();

    protected void cancel() {
        graphics.dispose();
    }

    protected void postRender(BufferedImage image) {
        if (proceeding) {
            graphics.drawImage(image, 0, 0, null);
        }
        graphics.dispose();
    }

    protected Graphics getGraphics() {
        if (target != null) {
            return target.getGraphics();
        }
        return null;
    }

    private void setColor(BufferedImage image, int x, int y, Color colorOfPoint, int resolution) {
        for (int oX = 0; oX < resolution; oX++) {
            for (int oY = 0; oY < resolution; oY++) {
                image.setRGB(x + oX, y + oY, colorOfPoint.getRGB());
            }
        }
    }

    public MandelbrotSetRenderer pause() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1_500 && proceeding);
        return this;
    }

    public void stop() {
        proceeding = false;
    }

    public void restart() {
        proceeding = true;
    }

    public boolean isProceeding() {
        return proceeding;
    }

    public MandelbrotSetRenderer ifActive(Runnable r) {
        if (this.isProceeding()) {
            r.run();
        }
        return this;
    }
}
