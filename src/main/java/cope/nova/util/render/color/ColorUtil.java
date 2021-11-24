package cope.nova.util.render.color;

public class ColorUtil {
    public static CustomColor revert(int hex) {
        return new CustomColor(hex >> 24 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, hex >> 24 & 0xFF);
    }

    public static float[] revertOpenGl(int hex) {
        CustomColor color = revert(hex);
        return new float[] { color.getR() / 255.0f, color.getG() / 255.0f, color.getB() / 255.0f, color.getA() / 255.0f };
    }

    public static int hex(CustomColor color) {
        return hex(color.getR(), color.getG(), color.getB(), color.getA());
    }

    public static int hex(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }
}
