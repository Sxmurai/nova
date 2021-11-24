package cope.nova.client.ui.clickgui.components;

import cope.nova.util.render.RenderUtil;
import cope.nova.util.render.ScaleUtil;

public abstract class Frame extends Component {
    public Frame(String name, double x) {
        super(name);
        this.x = x;
        this.width = 82.0;
        this.init();
    }

    protected abstract void init();

    @Override
    public void render(int mouseX, int mouseY) {
        RenderUtil.drawRoundedBox(this.x, this.y, this.width, this.height + 14.0, 7.5, 0x77000000); // draw the transparent part first
        RenderUtil.drawHalfRoundedBox(this.x, this.y, this.width, 15.0, 7.5, 0x4287F5); // draw top part
        mc.fontRendererObj.drawString(this.name, (float) (this.x + 2.3), ScaleUtil.centerTextY((float) this.y, 15.0f), -1, true); // draw category name
    }

    @Override
    public double getHeight() {
        double height = 15.0;

        for (Component child : this.children) {
            height += (child.getHeight() + 1.5);
        }

        return height;
    }
}
