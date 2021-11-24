package cope.nova.client.features.module.modules.movement;

import cope.nova.client.features.module.Module;
import cope.nova.client.settings.Setting;
import net.minecraft.client.settings.KeyBinding;

@Module.Define(name = "Sprint", category = Module.Category.Movement)
@Module.Info(description = "Makes you automatically sprint")
public class Sprint extends Module {
    public final Setting<Mode> mode = new Setting<>("Mode", Mode.Legit);

    @Override
    protected void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }

    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.Legit) {
            if (mc.thePlayer.getFoodStats().getFoodLevel() <= 6 || mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isSneaking()) {
                return;
            }
        }

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

    public enum Mode {
        Rage, Legit
    }
}
