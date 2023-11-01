package renderer.calculator.colormapping;

import java.awt.*;

public enum ColorMappingMode {
    RED {
        @Override
        public Color getColor(int i) {
            return new Color(i, i / 2, i / 2);
        }
    }, GREEN {
        @Override
        public Color getColor(int i) {
            return new Color(i / 2, i, i / 2);
        }
    }, BLUE {
        @Override
        public Color getColor(int i) {
            return new Color(i / 2, i / 2, i);
        }
    }, ORANGE {
        @Override
        public Color getColor(int i) {
            return new Color(i, i / 2, 0);
        }
    }, PINK {
        @Override
        public Color getColor(int i) {
            return new Color(i, i / 3, 3 * i / 5);
        }
    }, YELLOW {
        @Override
        public Color getColor(int i) {
            return new Color(i, i, 0);
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
    };

    public abstract Color getColor(int iterationCount);

    public ColorMappingMode next() {
        int index = (ordinal() + 1) % ColorMappingMode.values().length;
        return ColorMappingMode.values()[index];
    }

    public ColorMappingMode previous() {
        int length = ColorMappingMode.values().length;
        int index = (ordinal() + length - 1) % ColorMappingMode.values().length;
        return ColorMappingMode.values()[index];
    }
}
