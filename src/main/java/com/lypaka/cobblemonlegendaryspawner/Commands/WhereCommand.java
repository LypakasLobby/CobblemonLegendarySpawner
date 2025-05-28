package com.lypaka.cobblemonlegendaryspawner.Commands;

import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.GUIs.WhereGUI;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class WhereCommand {

    public WhereCommand (CommandDispatcher<ServerCommandSource> dispatcher) {

        for (String a : CobblemonLegendarySpawnerCommand.ALIASES) {

            dispatcher.register(
                    CommandManager.literal(a)
                            .then(
                                    CommandManager.literal("where")
                                            .then(
                                                    CommandManager.argument("pokemon", StringArgumentType.string())
                                                            .suggests(
                                                                    (context, builder) -> CommandSource.suggestMatching(ConfigGetters.bigOleMap.keySet(), builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity player) {

                                                                    String pokemon = StringArgumentType.getString(c, "pokemon");
                                                                    try {

                                                                        WhereGUI.open(player, pokemon);

                                                                    } catch (ObjectMappingException e) {

                                                                        throw new RuntimeException(e);

                                                                    }

                                                                }

                                                                return 0;

                                                            })
                                            )
                            )

            );

        }

    }

}
