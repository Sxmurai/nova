package cope.nova.client;

import cope.nova.client.manager.managers.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Nova.MOD_ID, name = Nova.MOD_NAME, version = Nova.MOD_VER, clientSideOnly = true)
public class Nova {
    public static final String MOD_ID = "nova_client";
    public static final String MOD_NAME = "Nova";
    public static final String MOD_VER = "1.0-beta";

    @Mod.Instance
    public static Nova INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger(Nova.class);

    // client shit
    public static ModuleManager moduleManager;

    // server shit
    public static RotationManager rotationManager;
    public static InteractionManager interactionManager;
    public static InventoryManager inventoryManager;
    public static LagBackManager lagBackManager;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LOGGER.info("oh hi there");
        LOGGER.info("another fatherless child in my fucking logs fuck you");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        LOGGER.info("Loading Nova v{}", MOD_VER);
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        // client
        moduleManager = new ModuleManager();

        MinecraftForge.EVENT_BUS.register(moduleManager);

        // server
        rotationManager = new RotationManager();
        interactionManager = new InteractionManager();
        inventoryManager = new InventoryManager();
        lagBackManager = new LagBackManager();

        MinecraftForge.EVENT_BUS.register(rotationManager);
        MinecraftForge.EVENT_BUS.register(inventoryManager);
        MinecraftForge.EVENT_BUS.register(lagBackManager);

        MinecraftForge.EVENT_BUS.register(new EventManager());

        stopwatch.stop();
        LOGGER.info("Loaded Nova v{} in {}ms", MOD_VER, stopwatch.getTime());
    }
}
