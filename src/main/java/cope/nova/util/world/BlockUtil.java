package cope.nova.util.world;

import cope.nova.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockUtil implements Util {
    public static EnumFacing getFacing(BlockPos pos) {
        for (EnumFacing direction : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(direction);
            if (mc.theWorld.isAirBlock(neighbor) || !BlockUtil.isClickable(neighbor)) {
                continue;
            }

            return direction;
        }

        return null;
    }

    public static boolean isClickable(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock().canPlaceBlockAt(mc.theWorld, pos) && mc.theWorld.getEntitiesWithinAABB(Entity.class, BlockUtil.toBoundingBox(pos), (entity) -> !entity.isDead && !(entity instanceof EntityItem)).isEmpty();
    }

    public static AxisAlignedBB toBoundingBox(BlockPos pos) {
        return new AxisAlignedBB(pos, pos.add(1.0, 1.0, 1.0));
    }
}
