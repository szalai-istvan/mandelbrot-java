package renderer.renderer;

import renderer.calculator.ColorCalculator;
import renderer.calculator.LightWeightCalculator;
import renderer.calculator.colormapping.ColorMappingMode;
import window.ScreenPositionData;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.*;

public abstract class MandelbrotSetRenderer {
    protected ScreenPositionData screenPositionData;
    protected JComponent target;
    protected ColorCalculator calculator;
    protected Graphics graphics;
    protected boolean proceeding = true;

    protected MandelbrotSetRenderer(ColorCalculator calculator) {
        this.calculator = calculator;
    }

    public static MandelbrotSetRenderer ofScreenData(ScreenPositionData screenPositionData) {
        // TODO Will come back here and implement high precision rendering for large zoom.
//        if (ScaledBigDecimal.preciseCalculationIsNeeded()) {
//            return null;
//        }

        return new LightweightRenderer(new LightWeightCalculator())
                .useScreenPositionData(screenPositionData);
    }

    public MandelbrotSetRenderer useColorMode(ColorMappingMode mode) {
        this.calculator.useColorMode(mode);
        return this;
    }

    public MandelbrotSetRenderer useScreenPositionData(ScreenPositionData data) {
        this.screenPositionData = data;
        return this;
    }

    public MandelbrotSetRenderer useTarget(JComponent target) {
        this.target = target;
        return this;
    }

    public abstract MandelbrotSetRenderer renderHighResolution();
    public abstract MandelbrotSetRenderer renderLowResolution();

    public MandelbrotSetRenderer pause() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1_500 && proceeding);
        return this;
    }

    public void stop() {
        proceeding = false;
    }

    public void restart() {
        proceeding = true;
    }

    public boolean isProceeding() {
        return proceeding;
    }

    public void ifActive(Runnable r) {
        if (this.isProceeding()) {
            r.run();
        }
    }
}
