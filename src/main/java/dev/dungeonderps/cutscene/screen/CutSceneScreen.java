package dev.dungeonderps.cutscene.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.dungeonderps.cutscene.CutScene;
import dev.dungeonderps.cutscene.PanoramaRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

public class CutSceneScreen extends Screen {
    public static PanoramaRenderer PANORAMA_RESOURCES = new PanoramaRenderer(new ResourceLocation(CutScene.MOD_ID, "textures/panorama/panorama"));
    private static boolean closing;
    private Double fov;
    private float rotationSpeed;
    private String text, textLocation;
    private int color, duration;
    private long startTime;
    public CutSceneScreen(Double fov, ResourceLocation panorama, float rotateSpeed, String text, String location, int textColor, int duration) {
        super(new StringTextComponent("cutscene"));

        PANORAMA_RESOURCES = new PanoramaRenderer(panorama);
        this.fov = fov;
        this.rotationSpeed = rotateSpeed;
        this.text = text;
        this.textLocation = location;
        this.color = textColor;
        this.startTime = System.currentTimeMillis() / 1000;
        this.duration = duration;
        System.out.println(startTime);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        if (System.currentTimeMillis() / 1000 >= (startTime + duration)) closeScreen();
        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = mc.fontRenderer;
        int screeWidth = mc.getMainWindow().getScaledWidth();
        int screeHeight = mc.getMainWindow().getScaledHeight();

        this.PANORAMA_RESOURCES.render(mc,  MathHelper.clamp(Util.milliTime() * 0.1F, 0.0F, 1.0F),Util.milliTime() * rotationSpeed, 1, fov);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1);
        switch (textLocation){
            case "top-center":
                    this.drawCenteredString(matrixStack, font, text, screeWidth /2, mc.fontRenderer.FONT_HEIGHT, color);
                break;
            case "bottom-center":
                    this.drawCenteredString(matrixStack, font, text, screeWidth /2, screeHeight - mc.fontRenderer.FONT_HEIGHT, color);
                break;
            case "top-right":
                    this.drawCenteredString(matrixStack, font, text, screeWidth - (font.getStringWidth(text) / 2), mc.fontRenderer.FONT_HEIGHT, color);
                break;
            case "middle-right":
                    this.drawCenteredString(matrixStack, font, text, screeWidth - (font.getStringWidth(text)/ 2), screeHeight / 2, color);
                break;
            case "bottom-right":
                    this.drawCenteredString(matrixStack, font, text, screeWidth - (font.getStringWidth(text) / 2), screeHeight - mc.fontRenderer.FONT_HEIGHT, color);
                break;
            case "top-left":
                    this.drawString(matrixStack, font, text, 0, mc.fontRenderer.FONT_HEIGHT, color);
                break;
            case "middle-left":
                    this.drawString(matrixStack, font, text, 0, screeHeight / 2, color);
                break;
            case "bottom-left":
                    this.drawString(matrixStack, font, text, 0, screeHeight - mc.fontRenderer.FONT_HEIGHT, color);
                break;
            default:
                    this.drawCenteredString(matrixStack, font, text, screeWidth /2, screeHeight / 2, color);
                break;
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void closeScreen(){
        Minecraft.getInstance().player.closeScreen();
    }
}
