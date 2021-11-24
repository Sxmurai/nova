package cope.nova.client.features.module.modules.client;

import cope.nova.client.features.module.Module;
import cope.nova.client.ui.clickgui.ClickGuiScreen;
import org.lwjgl.input.Keyboard;

@Module.Define(name = "ClickGui", category = Module.Category.Client)
@Module.Info(description = "Opens the chad ClickGui", bind = Keyboard.KEY_RSHIFT)
public class ClickGui extends Module {
    @Override
    protected void onEnable() {
        if (nullCheck()) {
            mc.displayGuiScreen(ClickGuiScreen.getInstance());
        } else {
            this.toggle();
        }
    }

    @Override
    protected void onDisable() {
        if (nullCheck()) {
            mc.displayGuiScreen(null);
        }
    }
}
