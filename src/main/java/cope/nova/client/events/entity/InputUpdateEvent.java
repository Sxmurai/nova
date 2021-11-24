package cope.nova.client.events.entity;

import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InputUpdateEvent extends Event {
    private final MovementInput movementInput;

    public InputUpdateEvent(MovementInput movementInput) {
        this.movementInput = movementInput;
    }

    public MovementInput getMovementInput() {
        return movementInput;
    }
}
