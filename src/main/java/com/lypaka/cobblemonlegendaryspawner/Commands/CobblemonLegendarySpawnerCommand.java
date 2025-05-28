package com.lypaka.cobblemonlegendaryspawner.Commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import java.util.Arrays;
import java.util.List;

public class CobblemonLegendarySpawnerCommand {

    public static List<String> ALIASES = Arrays.asList("cobblemonlegendaryspawner", "cls", "legendaries", "lspawner");

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            new ReloadCommand(dispatcher);

        });

    }

}
