package renderer.calculator.colormapping;

import utilities.Utilities;

import java.awt.*;

public class ColorPostProcessor {

    private double contrast;
    private double correctionFactor;

    public ColorPostProcessor(final double contrast) {
        reconfigure(contrast);
    }

    public Color postProcess(Color color) {
        return new Color(
                correct(color.getRed()),
                correct(color.getGreen()),
                correct(color.getBlue())
        );
    }

    public void increaseContrast() {
        double currentOffset = contrast + 255;
        double newOffset = Math.min(-255 + currentOffset * 1.05, 255);
        reconfigure(newOffset);
    }

    public void decreaseContrast() {
        double currentOffset = contrast + 255;
        double newOffset = Math.max(-255 + currentOffset / 1.05, -255);
        reconfigure(newOffset);
    }

    private void reconfigure(double newContrast) {
        if (contrast < -255 || contrast > 255) {
            throw new IllegalArgumentException("Contrast should fall between -255 and 255. " + contrast + " is invalid. ");
        }
        contrast = newContrast;
        correctionFactor = calculateCorrectionFactor();
    }

    private double calculateCorrectionFactor() {
        return (259 * (contrast + 255)) / (255 * (259 - contrast));
    }

    private int correct(int original) {
        int corrected = (int) (correctionFactor * (original - 128) + 128);
        return Math.min(Math.max(corrected, 0), 255);
    }

    @Override
    public String toString() {
        return Utilities.format(contrast);
    }
}
