package dev.dungeonderps.cutscene.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.dungeonderps.cutscene.CutScene;
import dev.dungeonderps.cutscene.PanoramaRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

import java.text.DecimalFormat;

public class CutSceneEditingScreen extends Screen {
    public static PanoramaRenderer PANORAMA_RESOURCES = new PanoramaRenderer(new ResourceLocation(CutScene.MOD_ID, "textures/panorama/panorama"));
    private Double fov;
    private float rotationSpeed;
    private String text, textLocation;
    private int color;
    private boolean showInfo;
    public CutSceneEditingScreen(Double fov, ResourceLocation panorama, float rotateSpeed, String text, String location, int textColor) {
        super(new StringTextComponent("cutscene"));

        PANORAMA_RESOURCES = new PanoramaRenderer(panorama);
        this.fov = fov;
        this.rotationSpeed = rotateSpeed;
        this.text = text;
        this.textLocation = location;
        this.color = textColor;
        this.showInfo = false;
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        this.addButton(new Button(5, 5, 60, 20, new StringTextComponent("+1 FOV"), (onPress) -> fov++));
        this.addButton(new Button(5, 30, 60, 20, new StringTextComponent("-1 FOV"), (onPress) -> fov--));
        this.addButton(new Button(5, 55, 60, 20, new StringTextComponent("+0.01 SPD"), (onPress) -> rotationSpeed+=0.01));
        this.addButton(new Button(5, 80, 60, 20, new StringTextComponent("-0.01 SPD"), (onPress) -> rotationSpeed-=0.01));
        this.addButton(new ImageButton(5, 105, 20, 20, 40, 0, 20, new ResourceLocation(CutScene.MOD_ID, "textures/buttons.png"), 256, 256, (onPress) -> showInfo = !showInfo));
        super.init(minecraft, width, height);
    }

    @Override
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
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
        if (showInfo)
            this.drawCenteredString(matrixStack, font, String.format("Fov: %s, Rotation Speed: %s", fov, new DecimalFormat("#.##").format(rotationSpeed)), screeWidth /2, mc.fontRenderer.FONT_HEIGHT * 3, color);

        super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
