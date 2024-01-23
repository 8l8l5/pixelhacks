package dev.tapwatero.pixelmod;

import com.pixelmonmod.pixelmon.Pixelmon;
import dev.tapwatero.pixelmod.listener.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Mod(pixelmonle.MOD_ID)
@Mod.EventBusSubscriber(modid = pixelmonle.MOD_ID)
public class pixelmonle {

    public static final String MOD_ID = "pixelmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static pixelmonle instance;

    public pixelmonle() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        instance = this;
        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new SendMessageEvent());
        MinecraftForge.EVENT_BUS.register(new PokemonTickEvent());
        MinecraftForge.EVENT_BUS.register(new RenderWorldEvent());
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
    }


    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        // Logic for when the server is starting here

        // Here is how you register a listener for Pixelmon events
        // Pixelmon has its own event bus for its events, as does TCG
        // So any event listener for those mods need to be registered to those specific event buses

    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        // Logic for once the server has started here
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        //Register command logic here
        // Commands don't have to be registered here
        // However, not registering them here can lead to some hybrids/server software not recognising the commands
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event) {
        // Logic for when the server is stopping
    }

    @SubscribeEvent
    public static void onServerStopped(FMLServerStoppedEvent event) {
        // Logic for when the server is stopped
    }

    public static pixelmonle getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
