package dev.dungeonderps.cutscene;

import dev.dungeonderps.cutscene.config.CutSceneData;

import java.util.LinkedHashMap;

public class CutSceneInfo {
    public static final LinkedHashMap<String, CutSceneData> SCENE_INFO = new LinkedHashMap<>();

    public static boolean validateScene(CutSceneData data){
        if (data.getFov() < 1) return false;
        if (!data.getSound().contains(":")) return false;
        if (data.getResourcePath().isEmpty()) return false;
        if (data.getFileNames().isEmpty()) return false;
        switch (data.getTextLocation()) {
            case "top-center":
            case "bottom-center":
            case "top-right":
            case "middle-right":
            case "bottom-right":
            case "top-left":
            case "middle-left":
            case "bottom-left":
            case "middle-center":
                break;
            default: return false;
        }
        return data.getDuration() < 60;
    }
}
