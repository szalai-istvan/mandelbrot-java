package utilities;

import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;

import static java.awt.Font.BOLD;

public class Utilities {
    private static final DecimalFormat formatter;
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
        return new Font("Consolas", BOLD, 18);
    }
}
