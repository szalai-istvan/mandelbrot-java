package window;

import model.ComplexNumber;

import java.awt.*;
import java.awt.event.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class WindowSetupTool {
    private final Window window;

    private WindowSetupTool(Window window) {
        this.window = window;
    }

    static WindowSetupTool forWindow(Window window) {
        return new WindowSetupTool(window);
    }

    WindowSetupTool initScreenSizeAndPosition() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        double initialZoom = 240.;
        double initX = screenSize.getWidth() / -2 / initialZoom;
        double initY = screenSize.getHeight() / 2 / initialZoom;

        window.screenPosition = ScreenPositionData.withTopLeftCoordinatesAndInitialZoom(
                ComplexNumber.of(initX, initY),
                screenSize,
                initialZoom
        );
        return this;
    }

    WindowSetupTool addPanel() {
        window.setLayout(null);
        window.setBackground(BLACK);
        window.add(window.draw);
        window.draw.setSize(window.getWidth(), window.getHeight());
        return this;
    }

    WindowSetupTool registerZoomListener() {
        window.addMouseWheelListener(e -> {
            if (e.getWheelRotation() > 0) {
                window.zoomOut();
            } else {
                window.zoomIn();
            }

        });
        return this;
    }

    WindowSetupTool registerNavigation() {
        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                double x = e.getPoint().getX();
                double y = e.getPoint().getY();
                window.recenter(x, y);
            }
        });
        return this;
    }

    WindowSetupTool setupDisplay() {
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setVisible(true);
        return this;
    }

    WindowSetupTool initialRender() {
        window.renderHighResolution();
        return this;
    }

    WindowSetupTool registerColorChanger() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == 'c' || keyChar == 'C') {
                    window.nextColorScheme();
                } else if (keyChar == 'x' || keyChar == 'X') {
                    window.previousColorScheme();
                }
            }
        });
        return this;
    }

    WindowSetupTool registerExitKey() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    System.exit(0);
                }
            }
        });
        return this;
    }
    public WindowSetupTool registerSaveKey() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == 's' || keyChar == 'S') {
                    window.save();
                }
            }
        });
        return this;
    }

    public WindowSetupTool registerResetKey() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == 'r' || keyChar == 'R') {
                    window.reset();
                }
            }
        });
        return this;
    }

    public WindowSetupTool registerHintToggle() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == 'h' || keyChar == 'H') {
                    window.displayHints = !window.displayHints;
                }
            }
        });
        return this;
    }
}
