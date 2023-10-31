package window;

import java.util.List;
import java.util.Random;

public class WindowInfoHints {
    private static final List<String> HINTS = List.of(
            "Press 'C' or 'X' to change color scheme.",
            "Press 'Esc' or 'Alt+F4' to close.",
            "Click anywhere to focus at that area.",
            "Use your mouse scroll to zoom in and out.",
            "Press 'R' to reset to initial view.",
            "Press 'H' to toggle the display of hints.",
            "Press 'S' to save current render to file."
    );

    private static final Random R = new Random();

    static String getRandomHint() {
        return HINTS.get(index());
    }

    private static int index() {
        return R.nextInt(HINTS.size());
    }
}
