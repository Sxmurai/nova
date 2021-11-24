package cope.nova.client.manager.managers;

import cope.nova.client.Nova;
import cope.nova.client.features.Wrapper;
import cope.nova.client.features.module.Module;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventManager implements Wrapper {
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving == mc.thePlayer && nullCheck()) {
            for (Module module : Nova.moduleManager.getElements()) {
                if (module.isToggled()) {
                    module.onUpdate();
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (nullCheck()) {
            for (Module module : Nova.moduleManager.getElements()) {
                if (module.isToggled()) {
                    module.onTick();
                }
            }
        }
    }
}
