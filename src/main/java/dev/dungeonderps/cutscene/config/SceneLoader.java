package dev.dungeonderps.cutscene.config;

import com.google.gson.Gson;
import dev.dungeonderps.cutscene.CutSceneInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SceneLoader {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void setupCutScenes() {
        CutSceneInfo.SCENE_INFO.clear();
        File[] files = ConfigSetup.CUTSCENES.toFile().listFiles();

        if (files != null && files.length > 0) {
            for (File f: files) {
                String s = f.getName();
                if (s.substring(s.indexOf('.')).equals(".json")) {
                    try {
                        parseCutScene(f);
                    } catch (IOException e) {
                        LOGGER.error("File not found when parsing cut scenes");
                    }
                }
            }
        }
    }

    private static void parseCutScene(File file) throws IOException {
        Gson gson = new Gson();
        Reader r = new FileReader(file);
        CutSceneData cutScene = gson.fromJson(r, CutSceneData.class);
        if (CutSceneInfo.validateScene(cutScene)){
            CutSceneInfo.SCENE_INFO.put(cutScene.getId().toLowerCase(), cutScene);
        }
        else {
            LOGGER.error(String.format("%s cutscene was not valid check your json.", cutScene.getId()));
        }
    }
}
