package renderer.renderer;

import model.ComplexNumber;
import renderer.calculator.ColorCalculator;
import utilities.Utilities;
import window.WindowInfoHints;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InstructionsRenderer extends LightweightRenderer {
    InstructionsRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    protected Color getColor(ComplexNumber coordinate) {
        Color color = super.getColor(coordinate);
        return new Color(
                color.getRed() / 2,
                color.getGreen() / 2,
                color.getBlue() / 2
        );
    }

    @Override
    protected void postRender(BufferedImage image) {
        if (!proceeding) {
            return;
        }

        final int rowOffset = 27;
        final int offsetX = 100;
        final int offsetY = 120;

        graphics.drawImage(image, 0, 0, null);

        graphics.setFont(Utilities.getFont());
        graphics.setColor(colorMode.getTextColor());
        graphics.drawString("Instructions: ", offsetX, offsetY);

        int lineCount = 2;
        for (String instruction : WindowInfoHints.bulletPointList()) {
            graphics.drawString(instruction, offsetX, offsetY + lineCount++ * rowOffset);
        }

        drawCredits();
        graphics.dispose();
    }

    private void drawCredits() {
        final int rowOffset = 27;
        int lineCount = 0;
        int x = target.getWidth() - 400;
        int y = target.getHeight() - 20 - rowOffset * 4;
        graphics.drawString("Made by: István Szalai", x, y + lineCount++ * rowOffset);
        graphics.drawString("   https://github.com/szalai-istvan", x, y + lineCount++ * rowOffset);
        graphics.drawString("   szalai.istvan95@gmail.com", x, y + lineCount * rowOffset);

    }

}
