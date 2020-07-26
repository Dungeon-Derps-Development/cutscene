package dev.dungeonderps.cutscene.network;

import dev.dungeonderps.cutscene.CutSceneInfo;
import dev.dungeonderps.cutscene.config.CutSceneData;
import dev.dungeonderps.cutscene.screen.CutSceneEditingScreen;
import dev.dungeonderps.cutscene.screen.CutSceneScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CutSceneMessage {

    private final String CSid;
    private final boolean isEditing;

    public CutSceneMessage(String cutSceneId, boolean editing){
        this.CSid = cutSceneId;
        this.isEditing = editing;
    }

    public static void encode(CutSceneMessage message, PacketBuffer buffer){ buffer.writeString(message.CSid); buffer.writeBoolean(message.isEditing); }

    public static CutSceneMessage decode(PacketBuffer buffer){ return new CutSceneMessage(buffer.readString(), buffer.readBoolean()); }

    public static void handle(CutSceneMessage message, Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                if (CutSceneInfo.SCENE_INFO.containsKey(message.CSid)) {
                    CutSceneData data = CutSceneInfo.SCENE_INFO.get(message.CSid);
                    if (!message.isEditing) {
                        Minecraft.getInstance().displayGuiScreen(
                                new CutSceneScreen(
                                        data.getFov(),
                                        new ResourceLocation(data.getResourcePath() + "/" + data.getFileNames().replace("${number}", "") + ".jpg"),
                                        data.getRotationSpeed(),
                                        data.getText(),
                                        data.getTextLocation(),
                                        Integer.decode(data.getColor()),
                                        data.getDuration()
                                )
                        );
                        if (ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(data.getSound())) != null)
                            Minecraft.getInstance().player.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(data.getSound())), 100, 0);
                    }
                    else {
                        Minecraft.getInstance().displayGuiScreen(
                                new CutSceneEditingScreen(
                                        data.getFov(),
                                        new ResourceLocation(data.getResourcePath() + "/" + data.getFileNames().replace("${number}", "")),
                                        data.getRotationSpeed(),
                                        data.getText(),
                                        data.getTextLocation(),
                                        Integer.decode(data.getColor())
                                )
                        );
                    }
                }
                else {
                    Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent("No valid cutscene with that id, check your logs to see if your cutscene was not valid."), false);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
