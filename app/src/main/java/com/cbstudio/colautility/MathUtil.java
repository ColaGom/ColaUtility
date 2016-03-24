package com.cbstudio.colautility;

/**
 * Created by Colabear on 2016-03-24.
 */
public class MathUtil {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
