package dev.dungeonderps.cutscene.config;

import dev.dungeonderps.cutscene.CutScene;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigSetup {

    public static final Logger LOGGER = LogManager.getLogger();
    public static Path CUTSCENES;

    public static void configInit(){
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path cutScenesDataPath = Paths.get(configPath.toAbsolutePath().toString(), CutScene.MOD_ID, "data");
        CUTSCENES = cutScenesDataPath;

        try { Files.createDirectories(cutScenesDataPath);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) { LOGGER.error("failed to create cut scene config directory");}
    }
}
