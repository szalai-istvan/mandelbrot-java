package renderer.renderer;

import model.ComplexNumber;
import renderer.calculator.ColorCalculator;
import renderer.renderer.baseclass.MandelbrotSetRenderer;
import utilities.Utilities;
import window.WindowInfoHints;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class InstructionsRenderer extends LightweightRenderer {
    InstructionsRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    public MandelbrotSetRenderer renderLowResolution() {
        return this;
    }

    @Override
    protected Color getColor(ComplexNumber coordinate) {
        return BLACK;
    }

    @Override
    protected void postRender(BufferedImage image) {
        if (proceeding) {
            graphics.drawImage(image, 0, 0, null);
        }
        graphics.setFont(Utilities.getFont());
        graphics.setColor(WHITE);
        graphics.drawString("Instructions: ", 100, 100);
        int line = 2;
        for (String instruction : WindowInfoHints.bulletPointList()) {
            graphics.drawString(instruction, 100, 100 + line++ * 27);
        }
        graphics.dispose();
    }

    private String instructions() {
        return "Instructions: \n\n" + WindowInfoHints.bulletPointList();

    }
}
