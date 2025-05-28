package com.lypaka.cobblemonlegendaryspawner.Commands;

import com.lypaka.cobblemonlegendaryspawner.GUIs.CheckSpawnsGUI;
import com.lypaka.cobblemonlegendaryspawner.Handlers.DataHandler;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CheckSpawnsCommand {

    public CheckSpawnsCommand (CommandDispatcher<ServerCommandSource> dispatcher) {

        for (String a : CobblemonLegendarySpawnerCommand.ALIASES) {

            dispatcher.register(
                    CommandManager.literal(a)
                            .then(
                                    CommandManager.literal("checkspawns")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity player) {

                                                    String biomeID = player.getWorld().getBiome(player.getBlockPos()).getIdAsString();
                                                    if (DataHandler.biomesToPokemonMap.containsKey(biomeID)) {

                                                        try {

                                                            CheckSpawnsGUI.open(player, biomeID);

                                                        } catch (ObjectMappingException e) {

                                                            throw new RuntimeException(e);

                                                        }

                                                    } else {

                                                        player.sendMessage(FancyTextHandler.getFormattedText("&cNo legendaries here!"));

                                                    }

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
