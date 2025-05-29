package com.lypaka.cobblemonlegendaryspawner.Commands;

import com.lypaka.cobblemonlegendaryspawner.CobblemonLegendarySpawner;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.Handlers.DataHandler;
import com.lypaka.cobblemonlegendaryspawner.Handlers.WebhookHandler;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.lypakautils.Handlers.PermissionHandler;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<ServerCommandSource> dispatcher) {

        for (String a : CobblemonLegendarySpawnerCommand.ALIASES) {

            dispatcher.register(
                    CommandManager.literal(a)
                            .then(
                                    CommandManager.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "cobblemonlegendaryspawner.command.admin")) {

                                                        player.sendMessage(FancyTextHandler.getFormattedText("&cYou don't have permission to use this command!"));
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    CobblemonLegendarySpawner.configManager.load();
                                                    ConfigGetters.load();
                                                    DataHandler.createAndLoadFiles(true);
                                                    WebhookHandler.init();
                                                    c.getSource().sendMessage(FancyTextHandler.getFormattedText("&aSuccessfully reloaded."));

                                                } catch (ObjectMappingException e) {

                                                    throw new RuntimeException(e);

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
