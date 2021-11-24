package cope.nova.client.ui.clickgui.components;

import cope.nova.client.features.Wrapper;

import java.util.ArrayList;

public class Component implements Wrapper {
    protected final String name;
    protected double x, y;
    protected double width, height;

    protected final ArrayList<Component> children = new ArrayList<>();

    public Component(String name) {
        this.name = name;
    }

    public void render(int mouseX, int mouseY) { }
    public void mouseClicked(int mouseX, int mouseY, int button) { }
    public void mouseRelease(int mouseX, int mouseY, int state) { }
    public void keyTyped(char character, int code) { }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void playSound() {
        // @todo
    }

    public boolean isMouseInBounds(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
