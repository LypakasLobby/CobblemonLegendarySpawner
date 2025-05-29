package com.lypaka.cobblemonlegendaryspawner.Handlers;

import com.lypaka.cobblemonlegendaryspawner.CobblemonLegendarySpawner;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHandler {

    public static Map<String, List<String>> biomesToPokemonMap; // used to quickly grab all available Pokemon in the selected player's biome
    public static Map<String, Integer> indexMap; // used to grab the index needed for the ComplexConfigManager so we're grabbing the proper data for the selected Pokemon

    public static void createAndLoadFiles (boolean reload) {

        biomesToPokemonMap = new HashMap<>();
        indexMap = new HashMap<>();
        String[] times = new String[]{"Day", "Dawn", "Dusk", "Night"};
        String[] weathers = new String[]{"Clear", "Rain", "Storm"};

        List<String> files = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : ConfigGetters.bigOleMap.entrySet()) {

            String pokemonName = entry.getKey();
            files.add(pokemonName + ".conf");

        }
        if (!reload) {

            CobblemonLegendarySpawner.pokemonConfigManager = new ComplexConfigManager(files, "pokemon", "format.conf", ConfigUtils.checkDir(Paths.get("./config/cobblemonlegendaryspawner")), CobblemonLegendarySpawner.class, CobblemonLegendarySpawner.MOD_NAME, CobblemonLegendarySpawner.MOD_ID, CobblemonLegendarySpawner.logger);
            CobblemonLegendarySpawner.pokemonConfigManager.init();

        } else {

            CobblemonLegendarySpawner.pokemonConfigManager.setFileNames(files);
            CobblemonLegendarySpawner.pokemonConfigManager.load();

        }

        boolean needsSaving = false;
        for (int i = 0; i < files.size(); i++) {

            if (CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Data", "Level", "Max").isVirtual()) {

                needsSaving = true;
                CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Data", "Level", "Max").setValue(70);
                CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Data", "Level", "Min").setValue(60);
                CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Data", "Form").setValue("default");

            }
            String pokemon = files.get(i).replace(".conf", "");
            indexMap.put(pokemon, i);
            List<String> biomes = ConfigGetters.bigOleMap.get(pokemon);
            for (String b : biomes) {

                List<String> pokemonList = new ArrayList<>();
                if (biomesToPokemonMap.containsKey(b)) {

                    pokemonList = biomesToPokemonMap.get(b);

                }
                if (!pokemonList.contains(pokemon)) {

                    pokemonList.add(pokemon);

                }
                biomesToPokemonMap.put(b, pokemonList);
                for (String t : times) {

                    for (String w : weathers) {

                        if (CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Spawn", b, t, w).isVirtual()) {

                            if (!needsSaving) needsSaving = true;
                            CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(i, "Spawn", b, t, w, "Chance").setValue(0.01);

                        }

                    }

                }

            }

        }

        if (needsSaving) {

            CobblemonLegendarySpawner.pokemonConfigManager.save();

        }

        CobblemonLegendarySpawner.logger.info("Loaded Pokemon data.");

    }

}
