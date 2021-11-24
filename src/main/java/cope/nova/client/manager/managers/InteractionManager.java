package cope.nova.client.manager.managers;

import cope.nova.client.Nova;
import cope.nova.client.features.Wrapper;
import cope.nova.util.math.Vec3Util;
import cope.nova.util.world.BlockUtil;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

/**
 * Handles world interactions. (eg block placements, entity attacking, etc)
 * @author aesthetical
 */
public class InteractionManager implements Wrapper {
    public void place(BlockPos pos, Placement placement, boolean sneak, boolean rotate, boolean swing) {
        EnumFacing facing = BlockUtil.getFacing(pos);
        if (facing == null) {
            return;
        }

        EnumFacing opposite = facing.getOpposite();
        BlockPos neighbor = pos.offset(facing);

        if (sneak) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }

        if (rotate) {
            Nova.rotationManager.rotate(pos);
        }

        Vec3 hitVec = new Vec3(pos).addVector(0.5, 0.5, 0.5).add(Vec3Util.scale(new Vec3(opposite.getDirectionVec()), 0.5));

        if (placement == Placement.Packet) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(neighbor, opposite.getIndex(), mc.thePlayer.getHeldItem(), (float) (hitVec.xCoord - pos.getX()), (float) (hitVec.yCoord - pos.getY()), (float) (hitVec.zCoord - pos.getZ())));
        } else if (placement == Placement.Vanilla) {
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), neighbor, opposite, hitVec);
        }

        if (swing) {
            mc.thePlayer.swingItem();
        }

        if (sneak) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    public void block() {
        if (!mc.thePlayer.isBlocking() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        }
    }

    public void attack(Entity entity, boolean block, boolean rotate, boolean swing) {
        if (entity == null || entity == mc.thePlayer) { // dont hit invalid entities nor ourselves
            return;
        }

        if (rotate) {
            Nova.rotationManager.rotate(entity);
        }

        if (block) {
            this.block();
        }

        mc.playerController.attackEntity(mc.thePlayer, entity);

        if (swing) {
            mc.thePlayer.swingItem();
        }
    }

    public enum Placement {
        Vanilla, Packet
    }
}
