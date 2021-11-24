package cope.nova.client.manager.managers;

import cope.nova.client.events.entity.UpdateWalkingPlayerEvent;
import cope.nova.client.features.Wrapper;
import cope.nova.util.player.RotationUtil;
import cope.nova.util.timing.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Manages the rotations sent to the server. Used to send "fake" rotations to the server
 * @author aesthetical
 */
public class RotationManager implements Wrapper {
    private final RotationUtil.Rotation rotation = new RotationUtil.Rotation(-1.0f, -1.0f);
    private final Timer timer = new Timer();

    @SubscribeEvent
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (event.getEra() == UpdateWalkingPlayerEvent.Era.Pre) {
            if (this.timer.passed(375.0, Timer.Format.Milliseconds) && this.rotation.isValid()) {
                this.rotation.reset();
            } else {
                event.setYaw(this.rotation.getYaw());
                event.setPitch(this.rotation.getPitch());
                event.setCanceled(true);
            }
        }
    }

    public void rotate(Entity entity) {
        this.rotate(entity.getPositionEyes(0.0f));
    }

    public void rotate(BlockPos pos) {
        this.rotate(new Vec3(pos).addVector(0.5, 0.5, 0.5));
    }

    public void rotate(Vec3 vec) {
        RotationUtil.Rotation rotation = RotationUtil.getRotations(vec);
        this.rotate(rotation.getYaw(), rotation.getPitch());
    }

    public void rotate(float yaw, float pitch) {
        this.rotation.setYaw(yaw);
        this.rotation.setPitch(pitch);
        this.timer.reset();
    }

    public RotationUtil.Rotation getRotation() {
        return rotation;
    }
}
