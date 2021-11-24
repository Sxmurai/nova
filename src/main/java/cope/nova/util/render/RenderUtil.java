package cope.nova.util.render;

import cope.nova.util.render.color.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void drawLine(double startX, double startY, double endX, double endY, float width, int color) {
        float[] colors = ColorUtil.revertOpenGl(color);

        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glColor4f(colors[0], colors[1], colors[2], colors[3]);
        GL11.glLineWidth(width);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

        GL11.glBegin(GL11.GL_LINES);

        GL11.glVertex2d(startX, startY);
        GL11.glVertex2d(endX, endY);

        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    // again, from cosmos because i am fucking stupid
    public static void drawRoundedBox(double x, double y, double w, double h, double radius, int color) {
        float[] colors = ColorUtil.revertOpenGl(color);

        GL11.glPushAttrib(GL11.GL_POINTS);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2.0;
        y *= 2.0;

        w = (w * 2.0) + x;
        h = (h * 2.0) + y;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glColor4f(colors[0], colors[1], colors[2], colors[3]);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i <= 90; ++i) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0) * radius * -1.0, y + radius + Math.cos(i * Math.PI / 180.0) * radius * -1.0);
        }

        for (int i = 90; i <= 180; ++i) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0) * radius * -1.0, h - radius + Math.cos(i * Math.PI / 180.0) * radius * -1.0);
        }

        for (int i = 0; i <= 90; ++i) {
            GL11.glVertex2d(w - radius + Math.sin(i * Math.PI / 180.0) * radius, h - radius + Math.cos(i * Math.PI / 180.0) * radius);
        }

        for (int i = 90; i <= 180; ++i) {
            GL11.glVertex2d(w - radius + Math.sin(i * Math.PI / 180.0) * radius, y + radius + Math.cos(i * Math.PI / 180.0) * radius);
        }

        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();

        GL11.glScaled(2.0, 2.0, 0.0);
        GL11.glPopAttrib();
    }

    // this is also from cosmos
    public static void drawHalfRoundedBox(double x, double y, double w, double h, double radius, int color) {
        float[] colors = ColorUtil.revertOpenGl(color);

        GL11.glPushAttrib(GL11.GL_POINTS);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2.0;
        y *= 2.0;

        w = (w * 2.0) + x;
        h = (h * 2.0) + y;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glColor4f(colors[0], colors[1], colors[2], colors[3]);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i <= 90; ++i) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0) * radius * -1.0, y + radius + Math.cos(i * Math.PI / 180.0) * radius * -1.0);
        }

        for (int i = 90; i <= 180; ++i) {
            GL11.glVertex2d((x + 1.0) + Math.sin(i * Math.PI / 180.0) * -1.0, (h - 1.0) + Math.cos(i * Math.PI / 180.0) * -1.0);
        }

        for (int i = 0; i <= 90; ++i) {
            GL11.glVertex2d((w - 1.0) + Math.sin(i * Math.PI / 180.0), (h - 1.0) + Math.cos(i * Math.PI / 180.0));
        }

        for (int i = 90; i <= 180; ++i) {
            GL11.glVertex2d(w - radius + Math.sin(i * Math.PI / 180.0) * radius, y + radius + Math.cos(i * Math.PI / 180.0) * radius);
        }

        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();

        GL11.glScaled(2.0, 2.0, 0.0);
        GL11.glPopAttrib();
    }

    public static void drawRoundedOutlinedBox(double x, double y, double w, double h, double radius, int box, float width, int outline) {
        drawRoundedBox(x, y, w, h, radius, box);

        // @todo
    }
}
