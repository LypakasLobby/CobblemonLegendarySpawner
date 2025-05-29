package com.lypaka.cobblemonlegendaryspawner.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lypaka.cobblemonlegendaryspawner.CobblemonLegendarySpawner;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.Handlers.DataHandler;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.lypakautils.Handlers.ItemStackHandler;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.lypaka.shadow.google.common.reflect.TypeToken;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckSpawnsGUI {

    public static void open (ServerPlayerEntity player, String biomeID) throws ObjectMappingException {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.checkSpawnsRows).build();
        GooeyPage page = GooeyPage.builder().template(template).title(FancyTextHandler.getFormattedString(ConfigGetters.checkSpawnsTitle)).build();

        List<String> species = DataHandler.biomesToPokemonMap.get(biomeID);
        int maxRows = 9 * ConfigGetters.checkSpawnsRows;
        List<Integer> allSlots = new ArrayList<>();
        for (int i = 0; i < maxRows; i++) {

            allSlots.add(i);

        }
        if (!ConfigGetters.checkSpawnsBorderSlots.isEmpty()) {

            for (String s : ConfigGetters.checkSpawnsBorderSlots.split(", ")) {

                int slot = Integer.parseInt(s);
                ItemStack border = ItemStackHandler.buildFromStringID(ConfigGetters.checkSpawnsBorderID);
                border.set(DataComponentTypes.CUSTOM_NAME, FancyTextHandler.getFormattedText(""));
                page.getTemplate().getSlot(slot).setButton(GooeyButton.builder().display(border).build());
                allSlots.removeIf(e -> e == slot);

            }

        }

        for (int i = 0; i < species.size(); i++) {

            try {

                int slot = allSlots.get(i);
                String pokemonSpecies = species.get(i);
                int index = DataHandler.indexMap.get(pokemonSpecies);

                int maxLevel = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Level", "Max").getInt();
                int minLevel = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Level", "Min").getInt();
                String form = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Data", "Form").getString("default");
                double dawnClearChance = 0.00;
                double dawnRainChance = 0.00;
                double dawnStormChance = 0.00;
                double dayClearChance = 0.00;
                double dayRainChance = 0.00;
                double dayStormChance = 0.00;
                double duskClearChance = 0.00;
                double duskRainChance = 0.00;
                double duskStormChance = 0.00;
                double nightClearChance = 0.00;
                double nightRainChance = 0.00;
                double nightStormChance = 0.00;
                Map<String, Map<String, Map<String, Double>>> data = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Spawn", biomeID).getValue(new TypeToken<Map<String, Map<String, Map<String, Double>>>>() {});
                for (Map.Entry<String, Map<String, Map<String, Double>>> entry : data.entrySet()) {

                    if (entry.getKey().equalsIgnoreCase("dawn")) {

                        Map<String, Map<String, Double>> map2 = entry.getValue();
                        for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                            if (entry2.getKey().equalsIgnoreCase("clear")) {

                                dawnClearChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("rain")) {

                                dawnRainChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("storm")) {

                                dawnStormChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("any")) {

                                dawnClearChance = entry2.getValue().get("Chance");
                                dawnRainChance = entry2.getValue().get("Chance");
                                dawnStormChance = entry2.getValue().get("Chance");

                            }

                        }

                    } else if (entry.getKey().equalsIgnoreCase("day")) {

                        Map<String, Map<String, Double>> map2 = entry.getValue();
                        for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                            if (entry2.getKey().equalsIgnoreCase("clear")) {

                                dayClearChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("rain")) {

                                dayRainChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("storm")) {

                                dayStormChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("any")) {

                                dayClearChance = entry2.getValue().get("Chance");
                                dayRainChance = entry2.getValue().get("Chance");
                                dayStormChance = entry2.getValue().get("Chance");

                            }

                        }

                    } else if (entry.getKey().equalsIgnoreCase("dusk")) {

                        Map<String, Map<String, Double>> map2 = entry.getValue();
                        for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                            if (entry2.getKey().equalsIgnoreCase("clear")) {

                                duskClearChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("rain")) {

                                duskRainChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("storm")) {

                                duskStormChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("any")) {

                                duskClearChance = entry2.getValue().get("Chance");
                                duskRainChance = entry2.getValue().get("Chance");
                                duskStormChance = entry2.getValue().get("Chance");

                            }

                        }

                    } else if (entry.getKey().equalsIgnoreCase("night")) {

                        Map<String, Map<String, Double>> map2 = entry.getValue();
                        for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                            if (entry2.getKey().equalsIgnoreCase("clear")) {

                                nightClearChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("rain")) {

                                nightRainChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("storm")) {

                                nightStormChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("any")) {

                                nightClearChance = entry2.getValue().get("Chance");
                                nightRainChance = entry2.getValue().get("Chance");
                                nightStormChance = entry2.getValue().get("Chance");

                            }

                        }

                    } else if (entry.getKey().equalsIgnoreCase("any")) {

                        Map<String, Map<String, Double>> map2 = entry.getValue();
                        for (Map.Entry<String, Map<String, Double>> entry2 : map2.entrySet()) {

                            if (entry2.getKey().equalsIgnoreCase("clear")) {

                                dawnClearChance = entry2.getValue().get("Chance");
                                dayClearChance = entry2.getValue().get("Chance");
                                duskClearChance = entry2.getValue().get("Chance");
                                nightClearChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("rain")) {

                                dawnRainChance = entry2.getValue().get("Chance");
                                dayRainChance = entry2.getValue().get("Chance");
                                duskRainChance = entry2.getValue().get("Chance");
                                nightRainChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("storm")) {

                                dawnStormChance = entry2.getValue().get("Chance");
                                dayStormChance = entry2.getValue().get("Chance");
                                duskStormChance = entry2.getValue().get("Chance");
                                nightStormChance = entry2.getValue().get("Chance");

                            } else if (entry2.getKey().equalsIgnoreCase("any")) {

                                dawnClearChance = entry2.getValue().get("Chance");
                                dawnRainChance = entry2.getValue().get("Chance");
                                dawnStormChance = entry2.getValue().get("Chance");
                                dayClearChance = entry2.getValue().get("Chance");
                                dayRainChance = entry2.getValue().get("Chance");
                                dayStormChance = entry2.getValue().get("Chance");
                                duskClearChance = entry2.getValue().get("Chance");
                                duskRainChance = entry2.getValue().get("Chance");
                                duskStormChance = entry2.getValue().get("Chance");
                                nightClearChance = entry2.getValue().get("Chance");
                                nightRainChance = entry2.getValue().get("Chance");
                                nightStormChance = entry2.getValue().get("Chance");

                            }

                        }

                    }

                }

                Pokemon pokemon = PokemonSpecies.INSTANCE.getByName(pokemonSpecies.toLowerCase()).create(1); // I hate that I have to put a level there
                if (!form.equalsIgnoreCase("default")) {

                    pokemon.setForm(pokemon.getSpecies().getFormByName(form));

                }
                ItemStack sprite = PokemonItem.from(pokemon);
                sprite.set(DataComponentTypes.CUSTOM_NAME, FancyTextHandler.getFormattedText(ConfigGetters.checkSpawnsPokemonDisplayName.replace("%pokemon%", pokemon.getSpecies().getName())));
                List<Text> lore = new ArrayList<>();
                for (String s : ConfigGetters.checkSpawnsPokemonLore) {

                    lore.add(FancyTextHandler.getFormattedText(
                            s
                                    .replace("%minLevel%", String.valueOf(minLevel))
                                    .replace("%maxLevel%", String.valueOf(maxLevel))
                                    .replace("%form%", form)
                                    .replace("%dawnClearChance%", convertDecimal(dawnClearChance))
                                    .replace("%dawnRainChance%", convertDecimal(dawnRainChance))
                                    .replace("%dawnStormChance%", convertDecimal(dawnStormChance))
                                    .replace("%dayClearChance%", convertDecimal(dayClearChance))
                                    .replace("%dayRainChance%", convertDecimal(dayRainChance))
                                    .replace("%dayStormChance%", convertDecimal(dayStormChance))
                                    .replace("%duskClearChance%", convertDecimal(duskClearChance))
                                    .replace("%duskRainChance%", convertDecimal(duskRainChance))
                                    .replace("%duskStormChance%", convertDecimal(duskStormChance))
                                    .replace("%nightClearChance%", convertDecimal(nightClearChance))
                                    .replace("%nightRainChance%", convertDecimal(nightRainChance))
                                    .replace("%nightStormChance%", convertDecimal(nightStormChance))
                    ));

                }
                sprite.set(DataComponentTypes.LORE, new LoreComponent(lore));
                page.getTemplate().getSlot(slot).setButton(GooeyButton.builder().display(sprite).build());

            } catch (IndexOutOfBoundsException e) {

                break;

            }

        }

        UIManager.openUIForcefully(player, page);

    }

    private static String convertDecimal (double decimal) {

        DecimalFormat df = new DecimalFormat("#.##");
        double value = decimal * 100;
        return df.format(value) + "%";

    }

}
