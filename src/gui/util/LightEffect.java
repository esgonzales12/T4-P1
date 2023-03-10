package gui.util;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;

public class LightEffect {

    private static final LightEffect lightEffect = new LightEffect();
    private final Lighting lighting;

    private LightEffect() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        lighting = new Lighting();
        lighting.setLight(light);
    }

    public static Lighting getLighting() {
        return lightEffect.lighting;
    }
}
