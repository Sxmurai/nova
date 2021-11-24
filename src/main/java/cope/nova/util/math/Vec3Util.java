package cope.nova.util.math;

import net.minecraft.util.Vec3;

public class Vec3Util {
    public static Vec3 scale(Vec3 vec, double factor) {
        return new Vec3(vec.xCoord * factor, vec.yCoord * factor, vec.zCoord * factor);
    }
}
