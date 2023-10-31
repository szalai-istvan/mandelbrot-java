package renderer.renderer;

import renderer.calculator.HighPrecisionCalculator;
import renderer.calculator.LightWeightCalculator;
import renderer.calculator.colormapping.ColorMappingMode;
import window.ScreenPositionData;

import javax.swing.*;

public class MandelbrotSetRendererFactory {
    public static MandelbrotSetRenderer ofScreenData(ScreenPositionData screenPositionData) {
        if (screenPositionData.zoomValue() > 1E+20) {
            return new HighPrecisionRenderer(new HighPrecisionCalculator())
                    .useScreenPositionData(screenPositionData);
        }

        return new LightweightRenderer(new LightWeightCalculator())
                .useScreenPositionData(screenPositionData);
    }
}
