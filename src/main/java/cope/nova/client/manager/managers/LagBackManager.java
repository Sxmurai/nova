package cope.nova.client.manager.managers;

import cope.nova.client.events.network.PacketEvent;
import cope.nova.client.features.Wrapper;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Detects when the server lags you back
 * @author aesthetical
 */
public class LagBackManager implements Wrapper {
    private int packets = 0;
    private int tolerance = 2; // the amount of lagback packets until considering to stop shit

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        // i might have to make a smarter solution to this, but this is probably a good idea
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = event.getPacket();
            if (!packet.func_179834_f().isEmpty()) {
                ++this.packets;
                if (this.packets >= this.tolerance) {
                    this.packets = 0;
                    this.onLagBack();
                }
            }
        }
    }

    public void onLagBack() {
        // @todo this eventually will have shit with a LagBack module, but for now...
    }
}
