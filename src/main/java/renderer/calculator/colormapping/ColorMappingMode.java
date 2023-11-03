package renderer.calculator.colormapping;

import java.awt.*;

import static java.awt.Color.WHITE;

public enum ColorMappingMode {
    RED {
        @Override
        public Color getColor(int i) {
            return new Color(i, i / 3, i / 3);
        }
    }, GREEN {
        @Override
        public Color getColor(int i) {
            return new Color(i / 3, i, i / 3);
        }
    }, BLUE {
        @Override
        public Color getColor(int i) {
            return new Color(i / 2, i / 2, i);
        }
    }, CYAN {
        @Override
        public Color getColor(int i) {
            return new Color(0, i, i);
        }
    }, YELLOW {
        @Override
        public Color getColor(int i) {
            return new Color(i, i, 0);
        }
    }, MAGENTA {
        @Override
        public Color getColor(int i) {
            return new Color(i, 0, i);
        }
    }, ORANGE {
        @Override
        public Color getColor(int i) {
            return new Color(i, 2 * i / 5, 0);
        }
    }, PINK {
        @Override
        public Color getColor(int i) {
            return new Color(i, i / 3, 3 * i / 5);
        }
    }, HEAT_MAP {
        @Override
        public Color getColor(int i) {
            if (i < 128) {
                return new Color(0, 2 * i, 255 - 2*i);
            }
            return new Color(i - 128, 511 - 2 * i, 0);
        }
    }, GREY {
        @Override
        public Color getColor(int i) {
            return new Color(i, i, i);
        }
    }, GREY_INVERTED {
        @Override
        public Color getColor(int i) {
            return GREY.getColor(255 - i);
        }

        @Override
        public Color getTextColor() {
            return GREY_INVERTED_TEXT_COLOR;
        }
    };

    private static final Color GREY_INVERTED_TEXT_COLOR = new Color(64, 64, 64);
    public abstract Color getColor(int iterationCount);

    public final ColorMappingMode next() {
        int index = (ordinal() + 1) % ColorMappingMode.values().length;
        return ColorMappingMode.values()[index];
    }

    public final ColorMappingMode previous() {
        int length = ColorMappingMode.values().length;
        int index = (ordinal() + length - 1) % ColorMappingMode.values().length;
        return ColorMappingMode.values()[index];
    }

    public Color getTextColor() {
        return WHITE;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
