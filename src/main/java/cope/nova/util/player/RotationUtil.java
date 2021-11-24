package cope.nova.util.player;

import cope.nova.util.Util;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil implements Util {
    public static Rotation getRotations(Vec3 to) {
        Vec3 from = mc.thePlayer.getPositionEyes(0.0f);
        double diffX = to.xCoord - from.xCoord;
        double diffY = (to.yCoord - from.yCoord) * -1.0;
        double diffZ = to.zCoord - from.zCoord;

        float yaw = MathHelper.wrapAngleTo180_float((float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0));
        float pitch = MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))));

        return new Rotation(yaw, pitch);
    }

    public static class Rotation {
        private float yaw, pitch;

        public Rotation(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public void reset() {
            this.yaw = -1.0f;
            this.pitch = -1.0f;
        }

        public boolean isValid() {
            return this.yaw != -1.0f && this.pitch != -1.0f;
        }

        public float getYaw() {
            return yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public void setPitch(float pitch) {
            this.pitch = pitch;
        }
    }
}
