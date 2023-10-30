package renderer.renderer;

import model.ComplexNumber;
import renderer.calculator.ColorCalculator;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class LightweightRenderer extends MandelbrotSetRenderer {

    LightweightRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    public MandelbrotSetRenderer renderHighResolution() {
        if (proceeding) {
            render(2);
        }
        return this;
    }

    @Override
    public MandelbrotSetRenderer renderLowResolution() {
        if (proceeding) {
            render(20);
        }
        return this;
    }

    private void render(int resolution) {
        this.graphics = target.getGraphics();

        int step = resolution / 2;
        double stepSize = 1.00 * step / screenPositionData.zoomValue();
        ComplexNumber stepSizeX = ComplexNumber.of(stepSize, 0.00);
        ComplexNumber stepSizeY = ComplexNumber.of(0.00, stepSize);
        ComplexNumber topLeft = screenPositionData.getTopLeft();
        ComplexNumber coordinate = screenPositionData.getTopLeft();

        BufferedImage image = new BufferedImage(target.getWidth(), target.getHeight(), TYPE_3BYTE_BGR);

        for (int x = 0; x < target.getWidth() - resolution; x += step) {
            for (int y = 0; y < target.getHeight() - resolution; y += step) {
                if (!proceeding) {
                    graphics.dispose();
                    return;
                }
                Color colorOfPoint = calculator.getColorOfPoint(coordinate);
                setColor(image, x, y, colorOfPoint, resolution);
                coordinate = coordinate.subtract(ComplexNumber.of(0.00, stepSizeY.getImaginary()));
            }
            coordinate = ComplexNumber.of(coordinate.getReal(), topLeft.getImaginary());
            coordinate = coordinate.add(ComplexNumber.of(stepSizeX.getReal(), 0.00));
        }

        if (proceeding) {
            graphics.drawImage(image, 0, 0, null);
        }
        graphics.dispose();
    }

    private void setColor(BufferedImage image, int x, int y, Color colorOfPoint, int resolution) {
        for (int oX = 0; oX < resolution; oX++) {
            for (int oY = 0; oY < resolution; oY++) {
                image.setRGB(x + oX, y + oY, colorOfPoint.getRGB());
            }
        }
    }
}
