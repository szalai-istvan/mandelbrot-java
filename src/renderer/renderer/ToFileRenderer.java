package renderer.renderer;

import renderer.calculator.ColorCalculator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ToFileRenderer extends LightweightRenderer {
    ToFileRenderer(ColorCalculator calculator) {
        super(calculator);
    }

    @Override
    protected void cancel() {

    }

    @Override
    protected void postRender(BufferedImage image) {
        try {
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String fileName = date + "_" + UUID.randomUUID().toString() + ".png";
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
