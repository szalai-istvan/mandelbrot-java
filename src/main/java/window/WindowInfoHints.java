package window;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WindowInfoHints {
    private static final String[] INSTRUCTIONS = new String[]{
            " - Click anywhere to focus at that area.",
            " - Use your mouse scroll to zoom in and out.",
            "     - Please note that zooming beyond 10¹⁶ will switch automatically to high precision mode.",
            "       High precision mode will heavily slow down the rendering.",
            " - Press 'R' to reset to initial view.",
            " - Press 'C' or 'X' to change color scheme.",
            " - Use the 'up arrow' and 'down arrow' keys to change render contrast.",
            " - Press 'H' to toggle the display of instructions.",
            " - Press 'S' to save current render to file.",
            " - Press 'Esc' or 'Alt+F4' to close."
    };

    public static List<String> bulletPointList() {
        return Arrays.stream(INSTRUCTIONS)
                .collect(Collectors.toList());
    }

}
