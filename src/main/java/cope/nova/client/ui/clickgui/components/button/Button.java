package cope.nova.client.ui.clickgui.components.button;

import cope.nova.client.ui.clickgui.components.Component;

public class Button extends Component {
    public Button(String name) {
        super(name);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseInBounds(mouseX, mouseY)) {
            this.playSound();
            this.onClick(button);
        }
    }

    public void onClick(int button) { }
}
