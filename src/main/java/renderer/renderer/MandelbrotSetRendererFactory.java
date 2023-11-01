package renderer.renderer;

import renderer.calculator.HighPrecisionCalculator;
import renderer.calculator.LightWeightCalculator;
import renderer.renderer.baseclass.MandelbrotSetRenderer;
import window.ScreenPositionData;

public class MandelbrotSetRendererFactory {
    public static MandelbrotSetRenderer ofScreenData(ScreenPositionData screenPositionData) {
        if (screenPositionData.zoomValue() > 1E+20) {
            return new HighPrecisionRenderer(new HighPrecisionCalculator())
                    .useScreenPositionData(screenPositionData);
        }

        return new LightweightRenderer(new LightWeightCalculator())
                .useScreenPositionData(screenPositionData);
    }

    public static MandelbrotSetRenderer saveToFile(ScreenPositionData screenPositionData) {
        return new ToFileRenderer(new LightWeightCalculator())
                .useScreenPositionData(screenPositionData);
    }

    public static MandelbrotSetRenderer instructionsRenderer(ScreenPositionData screenPosition) {
        return new InstructionsRenderer(new LightWeightCalculator())
                .useScreenPositionData(screenPosition);
    }
}
