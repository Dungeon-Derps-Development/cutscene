package dev.dungeonderps.cutscene;

import dev.dungeonderps.cutscene.config.ConfigSetup;
import dev.dungeonderps.cutscene.config.SceneLoader;
import dev.dungeonderps.cutscene.network.NetPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cutscene")
public class CutScene
{
    public static final String MOD_ID = "cutscene";
    public static final Logger LOGGER = LogManager.getLogger();

    public CutScene() {
        ConfigSetup.configInit();
        SceneLoader.setupCutScenes();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.LOW, this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        //MinecraftForge.EVENT_BUS.addListener(this::onClientTick);

        MinecraftForge.EVENT_BUS.register(this);
    }


    private void setup(final FMLCommonSetupEvent event){
        NetPacketHandler.init();
    }

    public void registerCommands(RegisterCommandsEvent event) {
        CutSceneCommand.register(event.getDispatcher());
    }

//    public void onClientTick(TickEvent event){
//        PlayerEntity player = Minecraft.getInstance().player;
//        if (player != null && player.isCrouching()){
//            Minecraft.getInstance().displayGuiScreen(new CutSceneScreen(
//                    85D,
//                    new ResourceLocation(
//                        CutScene.MOD_ID,
//                        "textures/panorama/panorama"
//                    ),
//                    0.01F,
//                    "test",
//                    "top-center",
//                    0xff0000)
//            );
//        }
//    }
}
