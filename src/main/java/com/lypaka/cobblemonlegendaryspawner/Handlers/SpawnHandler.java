package com.lypaka.cobblemonlegendaryspawner.Handlers;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lypaka.cobblemonlegendaryspawner.CobblemonLegendarySpawner;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.WorldTime;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.lypakautils.Handlers.RandomHandler;
import com.lypaka.lypakautils.Handlers.WorldHandlers;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.lypaka.shadow.google.common.reflect.TypeToken;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpawnHandler {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static LocalDateTime nextSpawnAttempt;

    public static void startTimer() {

        executor.scheduleAtFixedRate(() -> {

            LocalDateTime now = LocalDateTime.now();
            if (nextSpawnAttempt == null || now.isAfter(nextSpawnAttempt)) {

                if (ConfigGetters.spawnIntervalMax != ConfigGetters.spawnIntervalMin) {

                    nextSpawnAttempt = now.plusSeconds(RandomHandler.getRandomNumberBetween(ConfigGetters.spawnIntervalMin, ConfigGetters.spawnIntervalMax));

                } else {

                    nextSpawnAttempt = now.plusSeconds(ConfigGetters.spawnIntervalMax);

                }

                if (RandomHandler.getRandomChance(ConfigGetters.spawnAttemptChance)) {

                    List<ServerPlayerEntity> players = new ArrayList<>();
                    for (Map.Entry<UUID, ServerPlayerEntity> entry : LypakaUtils.playerMap.entrySet()) {

                        if (!ConfigGetters.playerBlacklist.contains(entry.getKey().toString())) {

                            String worldName = WorldHandlers.getWorldName(entry.getValue());
                            if (!ConfigGetters.worldBlacklist.contains(worldName)) {

                                if (!players.contains(entry.getValue())) {

                                    if (ConfigGetters.ignoreCreative) {

                                        if (entry.getValue().isCreative()) continue;

                                    }
                                    if (ConfigGetters.ignoreSpectator) {

                                        if (entry.getValue().isSpectator()) continue;

                                    }
                                    String biomeID = entry.getValue().getWorld().getBiome(entry.getValue().getBlockPos()).getIdAsString();
                                    if (DataHandler.biomesToPokemonMap.containsKey(biomeID)) {

                                        players.add(entry.getValue());

                                    }

                                }

                            }

                        }

                    }

                    if (!players.isEmpty()) {

                        ServerPlayerEntity target = RandomHandler.getRandomElementFromList(players);
                        String biomeID = target.getWorld().getBiome(target.getBlockPos()).getIdAsString();
                        try {

                            List<String> possibleSpawns = getPossibleSpawns(target, DataHandler.biomesToPokemonMap.get(biomeID), biomeID);
                            if (!possibleSpawns.isEmpty()) {

                                String randomSpawn = RandomHandler.getRandomElementFromList(possibleSpawns);
                                spawnPokemon(randomSpawn, target);

                            }

                        } catch (ObjectMappingException | IOException e) {

                            throw new RuntimeException(e);

                        }

                    }

                }

            }

        }, 0, 1, TimeUnit.SECONDS);

    }

    private static List<String> getPossibleSpawns (ServerPlayerEntity player, List<String> possibleSpawns, String biome) throws ObjectMappingException {

        List<String> times = WorldTime.getCurrentTimeValues(player.getWorld());
        String time = "Day";
        for (String t : times) {

            if (t.equalsIgnoreCase("Dawn")) {

                time = "Dawn";
                break;

            }
            if (t.equalsIgnoreCase("night") || t.equalsIgnoreCase("midnight")) {

                time = "Night";
                break;

            }
            if (t.equalsIgnoreCase("dusk")) {

                time = "Dusk";
                break;

            }

        }
        String weather = "clear";
        if (player.getWorld().isRaining()) weather = "rain";
        if (player.getWorld().isThundering()) weather = "storm";
        List<String> spawns = new ArrayList<>();
        for (String s : possibleSpawns) {

            int index = DataHandler.indexMap.get(s);
            Map<String, Map<String, Map<String, Double>>> map = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Spawn", biome).getValue(new TypeToken<Map<String, Map<String, Map<String, Double>>>>() {});
            for (Map.Entry<String, Map<String, Map<String, Double>>> entry : map.entrySet()) {

                if (entry.getKey().equalsIgnoreCase(time) || entry.getKey().equalsIgnoreCase("any")) {

                    Map<String, Map<String, Double>> map2 = entry.getValue();
                    for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                        if (entry2.getKey().equalsIgnoreCase(weather) || entry2.getKey().equalsIgnoreCase("any")) {

                            double chance = entry2.getValue().get("Chance");
                            if (RandomHandler.getRandomChance(chance)) spawns.add(s);

                        }

                    }

                }

            }

        }

        return spawns;

    }

    public static void spawnPokemon (String pokemonFile, ServerPlayerEntity player) throws IOException {

        int index = DataHandler.indexMap.get(pokemonFile);
        int maxLevel = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Level", "Max").getInt();
        int minLevel = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Level", "Min").getInt();
        int spawnLevel = RandomHandler.getRandomNumberBetween(minLevel, maxLevel);
        String form = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Form").getString("default");

        Pokemon pokemon = PokemonSpecies.INSTANCE.getByName(pokemonFile.toLowerCase()).create(spawnLevel);
        if (!form.equalsIgnoreCase("default")) {

            pokemon.setForm(pokemon.getSpecies().getFormByName(form));

        }
        if (RandomHandler.getRandomNumberBetween(1, Cobblemon.config.getShinyRate()) == 1) {

            pokemon.setShiny(true);

        }
        int playerX = player.getBlockX();
        int playerZ = player.getBlockZ();
        int rand = ConfigGetters.spawnLocationXZ;
        int spawnX;
        int spawnY = player.getBlockY();
        int spawnZ;
        if (RandomHandler.getRandomChance(50)) {

            spawnX = playerX - RandomHandler.getRandomNumberBetween(1, rand);

        } else {

            spawnX = playerX + RandomHandler.getRandomNumberBetween(1, rand);

        }
        if (RandomHandler.getRandomChance(50)) {

            spawnZ = playerZ - RandomHandler.getRandomNumberBetween(1, rand);

        } else {

            spawnZ = playerZ + RandomHandler.getRandomNumberBetween(1, rand);

        }
        PokemonEntity entity = new PokemonEntity(player.getWorld(), pokemon, CobblemonEntities.POKEMON);
        entity.setPosition(spawnX, spawnY, spawnZ);
        if (player.getWorld().spawnEntity(entity)) {

            if (ConfigGetters.spawnGlowing) {

                entity.setGlowing(true);

            }
            for (Map.Entry<UUID, ServerPlayerEntity> entry : LypakaUtils.playerMap.entrySet()) {

                entry.getValue().sendMessage(
                        FancyTextHandler.getFormattedText(
                                ConfigGetters.broadcast
                                        .replace("%player%", player.getName().getString())
                                        .replace("%pokemon%", pokemon.getSpecies().getName())
                                        .replace("%biome%", getPrettyBiomeName(player.getWorld().getBiome(player.getBlockPos()).getIdAsString()))
                        )
                );

            }

            WebhookHandler.ping(pokemon, player);

        }

    }

    public static String getPrettyBiomeName (String id) {

        String biome = id.split(":")[1];
        String[] words = biome.split("_");
        StringBuilder prettyName = new StringBuilder();

        for (String word : words) {

            if (!word.isEmpty()) {

                prettyName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");

            }

        }

        return prettyName.toString().trim();

    }

}
