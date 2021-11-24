package cope.nova.client.features.module.modules.movement;

import cope.nova.client.Nova;
import cope.nova.client.events.entity.InputUpdateEvent;
import cope.nova.client.events.network.PacketEvent;
import cope.nova.client.features.module.Module;
import cope.nova.client.manager.managers.InventoryManager;
import cope.nova.client.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Module.Define(name = "NoSlow", category = Module.Category.Movement)
@Module.Info(description = "Stops you from being slowed down", bind = Keyboard.KEY_R)
public class NoSlow extends Module {
    public final Setting<Mode> mode = new Setting<>("Mode", Mode.NCP);
    public final Setting<Float> multiplier = new Setting<>("Multiplier", 5.0f, 0.2f, 1.0f);

    @Override
    public void onUpdate() {
        if (mc.thePlayer.isUsingItem() && !mc.thePlayer.isRiding()) {
            if (this.mode.getValue() == Mode.Swap) {
                Nova.inventoryManager.swap(mc.thePlayer.inventory.currentItem, InventoryManager.Swap.Silent); // no clue if this even works, but if it does thats a plus
            }
        }
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        if (mc.thePlayer.isUsingItem() && !mc.thePlayer.isRiding()) {
            event.getMovementInput().moveForward *= this.multiplier.getValue();
            event.getMovementInput().moveStrafe *= this.multiplier.getValue();
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof C03PacketPlayer && this.mode.getValue() == Mode.NCP) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, mc.thePlayer.getPosition(), EnumFacing.DOWN));
        }
    }

    public enum Mode {
        NCP, Swap
    }
}
