package com.mcxiv.logger.tools;

import java.awt.*;

public class RandomColor {

    int r, g, b;

    public RandomColor() {
        r = (int) (255 * Math.random());
        g = (int) (255 * Math.random());
        b = (int) (255 * Math.random());
    }

    public RandomColor(int _r, int _g, int _b) {
        r = _r;
        g = _g;
        b = _b;
    }

    public RandomColor getDark() {
        return new RandomColor(interpolate(r, 0), interpolate(g, 0), interpolate(b, 0));
    }

    public RandomColor getBright() {
        return new RandomColor(interpolate(r, 255), interpolate(g, 255), interpolate(b, 255));
    }

    public Color yieldColor() {
        return new Color(r, g, b);
    }

    public String yieldHex() {
        return Integer.toHexString(r).toUpperCase() + Integer.toHexString(g).toUpperCase() + Integer.toHexString(b).toUpperCase();
    }

    private static int interpolate(int f, int t) {
        return (f + t) / 2;
    }

}
