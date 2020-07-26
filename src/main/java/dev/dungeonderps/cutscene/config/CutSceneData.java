package dev.dungeonderps.cutscene.config;

public class CutSceneData {
    private int duration;
    private double fov;
    private float rotationSpeed;
    private String id, resourcePath, fileNames, sound, text, textLocation, color;
    private transient String name;

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public float getRotationSpeed() { return rotationSpeed; }
    public void setRotationSpeed(float rotationSpeed) { this.rotationSpeed = rotationSpeed; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFileNames() { return fileNames; }
    public void setFileNames(String fileNames) { this.fileNames = fileNames; }

    public String getSound() { return sound; }
    public void setSound(String sound) { this.sound = sound; }

    public double getFov() { return fov; }
    public void setFov(double fov) { this.fov = fov; }

    public String getResourcePath() { return resourcePath; }
    public void setResourcePath(String resourcePath) { this.resourcePath = resourcePath; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTextLocation() { return textLocation; }
    public void setTextLocation(String textLocation) { this.textLocation = textLocation; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
