package cope.nova.client.manager.managers;

import cope.nova.client.features.module.Module;
import cope.nova.client.features.module.modules.client.ClickGui;
import cope.nova.client.features.module.modules.movement.NoSlow;
import cope.nova.client.features.module.modules.movement.Sprint;
import cope.nova.client.manager.Manager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ModuleManager extends Manager<Module> {
    @Override
    public void init() {
        // client
        this.elements.add(new ClickGui());

        // movement
        this.elements.add(new NoSlow());
        this.elements.add(new Sprint());

        this.elements.forEach(Module::registerAllSettings); // do not remove, or else settings wont register
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        int code = Keyboard.getEventKey();
        if (!Keyboard.getEventKeyState() && code != Keyboard.KEY_NONE && mc.currentScreen == null) {
            for (Module module : this.elements) {
                if (module.getBind() == code) {
                    module.toggle();
                }
            }
        }
    }
}
