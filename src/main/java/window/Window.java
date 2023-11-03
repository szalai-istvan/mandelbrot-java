package window;

import model.ComplexNumber;
import renderer.calculator.colormapping.ColorMappingMode;
import renderer.calculator.colormapping.ColorPostProcessor;
import renderer.renderer.baseclass.MandelbrotSetRenderer;
import renderer.renderer.MandelbrotSetRendererFactory;
import utilities.Utilities;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.WHITE;
import static renderer.calculator.colormapping.ColorMappingMode.RED;
import static utilities.Utilities.format;

public class Window extends JFrame {

    ScreenPositionData screenPosition;
    MandelbrotSetRenderer renderer;
    ColorMappingMode colorMode = RED;
    ColorPostProcessor colorPostProcessor = new ColorPostProcessor(0.00);

    boolean  displayInstructions = false;
    JPanel draw = new JPanel();

    public Window() {
        WindowSetupTool.forWindow(this)
                .initScreenSizeAndPosition()
                .addPanel()
                .setupDisplay()
                .registerZoomListener()
                .registerContrastAdjuster()
                .registerNavigation()
                .registerColorChanger()
                .registerResetKey()
                .registerSaveKey()
                .registerExitKey()
                .registerHintToggle()
                .initialRender();

    }

    public static void start() {
        new Window();
    }

    public void render() {
        createRendererObject();
        new Thread(() -> {
            renderer.renderLowResolution()
                    .pause()
                    .renderHighResolution()
                    .ifActive(() -> displayInformation());
        }).start();
    }

    public void renderHighResolution() {
        createRendererObject();
        new Thread(() -> {
            renderer.renderHighResolution()
                    .ifActive(() -> displayInformation());
        }).start();
    }

    void zoomIn() {
        renderer.stop();
        screenPosition.zoomIn();
        render();
    }

    void zoomOut() {
        renderer.stop();
        screenPosition.zoomOut();
        render();
    }

    void toggleDisplayInstructions() {
        renderer.stop();
        displayInstructions = !displayInstructions;
        render();
    }

    void increaseContrast() {
        renderer.stop();
        this.colorPostProcessor.increaseContrast();
        render();
    }

    void decreaseContrast() {
        renderer.stop();
        this.colorPostProcessor.decreaseContrast();
        render();
    }

    void recenter(double x, double y) {
        renderer.stop();
        screenPosition.recenter(x, y);
        render();
    }

    void nextColorScheme() {
        renderer.stop();
        colorMode = colorMode.next();
        renderHighResolution();
    }

    void previousColorScheme() {
        renderer.stop();
        colorMode = colorMode.previous();
        renderHighResolution();
    }

    void reset() {
        renderer.stop();
        screenPosition.reset();
        render();
    }

    void save() {
        if (displayInstructions) {
            return;
        }

        MandelbrotSetRendererFactory
                .saveToFile(screenPosition)
                .useColorMode(colorMode)
                .useTarget(draw)
                .usePostProcessor(colorPostProcessor)
                .renderHighResolution();
    }

    private void createRendererObject() {
        if (displayInstructions) {
            renderer = MandelbrotSetRendererFactory
                    .instructionsRenderer(this.screenPosition);
        } else {
            renderer = MandelbrotSetRendererFactory
                    .ofScreenData(this.screenPosition);
        }

        renderer.useColorMode(colorMode)
                .usePostProcessor(colorPostProcessor)
                .useTarget(draw);
    }

    private void displayInformation() {
        int offset = 20;
        Graphics graphics = draw.getGraphics();
        graphics.setFont(Utilities.getFont());
        graphics.setColor(colorMode.getTextColor());

        graphics.drawString(getTopInformation(), offset, offset);

        String bottomInfoPanel;
        if (displayInstructions) {
            bottomInfoPanel = "Press 'H' to hide instructions. ";
        } else {
            bottomInfoPanel = "Press 'H' to display instructions. ";
        }

        graphics.drawString(bottomInfoPanel, offset, getHeight() - offset / 2);
        graphics.dispose();
    }

    private String getTopInformation() {
        double zoom = Math.round(screenPosition.zoomValue() * 1_000) / 1_000.;
        ComplexNumber center = screenPosition.getCenter();
        return new StringBuilder()
                .append("zoom: ")
                .append(format(zoom))
                .append("; focused on ")
                .append(center)
                .append("; rendering contrast: ")
                .append(colorPostProcessor)
                .append("; color scheme: ")
                .append(colorMode)
                .toString();
    }
}

