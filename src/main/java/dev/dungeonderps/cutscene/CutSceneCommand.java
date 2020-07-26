package dev.dungeonderps.cutscene;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.dungeonderps.cutscene.config.SceneLoader;
import dev.dungeonderps.cutscene.network.CutSceneMessage;
import dev.dungeonderps.cutscene.network.NetPacketHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;

public class CutSceneCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("cutscene")
                .then(Commands.literal("play").requires((source) -> source.hasPermissionLevel(2))
                .then(Commands.argument("targets", EntityArgument.entities()).then(Commands.argument("name", StringArgumentType.string())
                .executes((context) -> playCutscene(EntityArgument.getEntities(context, "targets"), StringArgumentType.getString(context, "name"))))))

                .then(Commands.literal("view").requires((source) -> source.hasPermissionLevel(2))
                .then(Commands.argument("name", StringArgumentType.string())
                .executes((context) -> editCutscene(context.getSource().asPlayer(), StringArgumentType.getString(context, "name")))))
                .then(Commands.literal("reload").executes(context -> reloadScenes(context.getSource().asPlayer())))
        );


    }

    private static int playCutscene(Collection<? extends Entity> targets, String id) {
        for(Entity entity : targets) {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity)entity;
                NetPacketHandler.sendToSelectedPlayer(new CutSceneMessage(id, false), player);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int editCutscene(ServerPlayerEntity player, String id) {
        NetPacketHandler.sendToSelectedPlayer(new CutSceneMessage(id, true), player);
        return Command.SINGLE_SUCCESS;
    }

    private static int reloadScenes(ServerPlayerEntity player){
        SceneLoader.setupCutScenes();
        player.sendStatusMessage(new StringTextComponent("Cutscenes Reloading!"), false);
        return Command.SINGLE_SUCCESS;
    }
}
