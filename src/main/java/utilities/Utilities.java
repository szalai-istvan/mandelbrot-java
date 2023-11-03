package utilities;

import java.awt.*;
import java.text.DecimalFormat;

import static java.awt.Font.BOLD;

public class Utilities {
    private static final DecimalFormat formatter;
    private static final Font FONT = new Font("Consolas", BOLD, 18);
    static {
        formatter = new DecimalFormat();
        formatter.setGroupingUsed(true);
        formatter.setMaximumFractionDigits(3);
        formatter.setMinimumFractionDigits(3);
    }
    public static String format(double d) {
        return formatter.format(d).replace(",", ".");
    }

    public static Font getFont() {
        return FONT;
    }
}
