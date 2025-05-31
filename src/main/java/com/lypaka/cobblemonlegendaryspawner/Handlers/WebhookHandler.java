package com.lypaka.cobblemonlegendaryspawner.Handlers;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.DiscordWebhook;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;

public class WebhookHandler {

    public static DiscordWebhook webhook;

    public static void init() {

        if (!ConfigGetters.webhookURL.isEmpty()) {

            webhook = new DiscordWebhook(ConfigGetters.webhookURL);
            if (!ConfigGetters.webhookAvatarURL.isEmpty()) {

                webhook.setAvatarUrl(ConfigGetters.webhookAvatarURL);

            }
            if (!ConfigGetters.webhookUsername.isEmpty()) {

                webhook.setUsername(ConfigGetters.webhookUsername);

            }

        }

    }

    public static void ping (Pokemon pokemon, ServerPlayerEntity player) throws IOException {

        if (webhook != null) {

            DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
            webhook.addEmbed(
                    embedObject
                            .setTitle(ConfigGetters.webhookTitle)
                            .setThumbnail(ConfigGetters.webhookThumbnail.isEmpty() ? null :
                                    ConfigGetters.webhookThumbnail
                                            .replace("%species%", pokemon.getSpecies().getName().toLowerCase())
                                            .replace("%texture%", pokemon.getShiny() ? "shiny" : "normal")
                            )
                            .setDescription(
                                    FancyTextHandler.getFormattedString(
                                            ConfigGetters.webhookMessage
                                                    .replace("%player%", player.getName().getString())
                                                    .replace("%pokemon%", pokemon.getSpecies().getName())
                                                    .replace("%biome%", SpawnHandler.getPrettyBiomeName(player.getWorld().getBiome(player.getBlockPos()).getIdAsString()))
                                    )
                            )
            );
            webhook.execute();

        }

    }

}
