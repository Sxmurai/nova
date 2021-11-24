package cope.nova.client.features;

import net.minecraft.client.Minecraft;

public interface Wrapper {
    Minecraft mc = Minecraft.getMinecraft();

    default boolean nullCheck() {
        return mc.thePlayer == null || mc.theWorld == null;
    }
}
