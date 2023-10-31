package window;

import model.ComplexNumber;
import renderer.calculator.colormapping.ColorMappingMode;
import renderer.renderer.MandelbrotSetRenderer;
import renderer.renderer.MandelbrotSetRendererFactory;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static renderer.calculator.colormapping.ColorMappingMode.RED;

public class Window extends JFrame {

    private final DecimalFormat formatter;
    boolean displayHints = true;
    ScreenPositionData screenPosition;
    JPanel draw = new JPanel();
    MandelbrotSetRenderer renderer;
    ColorMappingMode colorMode = RED;
    private String randomHint = "";

    public Window() {
        WindowSetupTool.forWindow(this)
                .initScreenSizeAndPosition()
                .addPanel()
                .setupDisplay()
                .registerZoomListener()
                .registerNavigation()
                .registerColorChanger()
                .registerResetKey()
                .registerSaveKey()
                .registerExitKey()
                .registerHintToggle()
                .initialRender();

        formatter = new DecimalFormat();
        formatter.setGroupingUsed(true);
        formatter.setMaximumFractionDigits(3);
        formatter.setMinimumFractionDigits(3);

    }

    public void render() {
        createRendererObject();
        new Thread(() -> {
            renderer.renderLowResolution()
                    .ifActive(() -> displayInformation(false))
                    .pause()
                    .renderHighResolution()
                    .ifActive(() -> displayInformation(true));
        }).start();
    }

    public void renderHighResolution() {
        createRendererObject();
        new Thread(() -> {
            renderer.renderHighResolution()
                    .ifActive(() -> displayInformation(true));
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
        MandelbrotSetRendererFactory
                .saveToFile(screenPosition)
                .useColorMode(colorMode)
                .useTarget(draw)
                .renderHighResolution();
    }

    private void createRendererObject() {
        renderer = MandelbrotSetRendererFactory
                .ofScreenData(this.screenPosition)
                .useColorMode(colorMode)
                .useTarget(draw);
    }

    private void displayInformation(boolean randomHintRefresh) {
        int offset = 20;
        Graphics graphics = draw.getGraphics();
        graphics.setFont(new Font("Consolas", BOLD, 18));
        graphics.setColor(WHITE);

        graphics.drawString(getTopInformation(), offset, offset);

        if (displayHints) {
            graphics.drawString(getRandomHint(randomHintRefresh), offset, getHeight() - offset / 2);
        }
        graphics.dispose();
    }

    private String getTopInformation() {
        double zoom = Math.round(screenPosition.zoomValue() * 1_000) / 1_000.;
        ComplexNumber center = screenPosition.getCenter();
        return new StringBuilder()
                .append("zoom: ")
                .append(formatter.format(zoom).replace(",", "."))
                .append("; focused on ")
                .append(center)
                .toString();
    }

    private String getRandomHint(boolean refresh) {
        if (refresh) {
            randomHint = WindowInfoHints.getRandomHint();
        }
        return randomHint;
    }
}

