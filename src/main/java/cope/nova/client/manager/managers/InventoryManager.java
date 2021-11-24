package cope.nova.client.manager.managers;

import cope.nova.client.events.network.PacketEvent;
import cope.nova.client.features.Wrapper;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Manages and syncs the inventory with the server
 * @author aesthetical
 */
public class InventoryManager implements Wrapper {
    private int serverSlot = -1;

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof C09PacketHeldItemChange) {
            this.serverSlot = ((C09PacketHeldItemChange) event.getPacket()).getSlotId();
        }
    }

    public void swap(int slot, Swap swap) {
        if (swap == Swap.None) {
            return;
        }

        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
        if (swap == Swap.Legit) {
            mc.thePlayer.inventory.currentItem = slot;
        }

        mc.playerController.updateController();
    }

    public void click(int slot) {
        // the mode? i have 0 clue. i'll have to packet log to see. i just looked through mc code and mode 1 seemed to be used
        mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
    }

    public boolean isSlotSynced() {
        return this.serverSlot == mc.thePlayer.inventory.currentItem;
    }

    public void sync() {
        mc.thePlayer.inventory.currentItem = this.serverSlot;
    }

    public int getServerSlot() {
        return serverSlot;
    }

    public enum Swap {
        None, Legit, Silent
    }
}
