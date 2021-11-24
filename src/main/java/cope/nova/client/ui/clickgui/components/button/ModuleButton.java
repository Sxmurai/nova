package cope.nova.client.ui.clickgui.components.button;

import cope.nova.client.features.module.Module;

public class ModuleButton extends Button {
    private final Module module;

    public ModuleButton(Module module) {
        super(module.getName());
        this.module = module;
    }
}
