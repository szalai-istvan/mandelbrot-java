package window;

import model.ComplexNumber;
import renderer.calculator.colormapping.ColorMappingMode;
import renderer.renderer.MandelbrotSetRenderer;

import javax.swing.*;

import java.awt.*;

import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static renderer.calculator.colormapping.ColorMappingMode.RED;

public class Window extends JFrame {

    boolean displayHints = true;
    ScreenPositionData screenPosition;
    JPanel draw = new JPanel();
    MandelbrotSetRenderer renderer;
    ColorMappingMode colorMode = RED;

    public Window() {
        WindowSetupTool.forWindow(this)
                .initScreenSizeAndPosition()
                .addPanel()
                .setupDisplay()
                .registerZoomListener()
                .registerNavigation()
                .registerColorChanger()
                .registerResetKey()
                .registerExitKey()
                .registerHintToggle()
                .initialRender();


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


    private void createRendererObject() {
        renderer = MandelbrotSetRenderer
                .ofScreenData(this.screenPosition)
                .useColorMode(colorMode)
                .useTarget(draw);
    }

    private void displayInformation() {
        int offset = 20;
        Graphics graphics = draw.getGraphics();
        graphics.setFont(new Font("Consolas", BOLD, 18));
        graphics.setColor(WHITE);

        graphics.drawString(getTopInformation(), offset, offset);

        if (displayHints) {
            graphics.drawString(getRandomHint(), offset, getHeight() - offset / 2);
        }
        graphics.dispose();
    }

    private String getTopInformation() {
        double zoom = Math.round(screenPosition.zoomValue() * 1_000) / 1_000.;
        ComplexNumber center = screenPosition.getCenter();
        return new StringBuilder()
                .append("zoom: ")
                .append(zoom)
                .append("; focused on ")
                .append(center)
                .toString();
    }

    private String getRandomHint() {
        return WindowInfoHints.getRandomHint();
    }
}

