package cope.nova.util.render;

import cope.nova.util.Util;

public class ScaleUtil implements Util {
    public static float centerTextY(float y, float height) {
        return (y + (height / 2.0f)) - (mc.fontRendererObj.FONT_HEIGHT / 2.0f);
    }
}
