package com.lypaka.cobblemonlegendaryspawner.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.cobblemonlegendaryspawner.CobblemonLegendarySpawner;
import com.lypaka.cobblemonlegendaryspawner.ConfigGetters;
import com.lypaka.cobblemonlegendaryspawner.Handlers.DataHandler;
import com.lypaka.cobblemonlegendaryspawner.Handlers.SpawnHandler;
import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.lypakautils.Handlers.ItemStackHandler;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.lypaka.shadow.google.common.reflect.TypeToken;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WhereGUI {

    public static void open (ServerPlayerEntity player, String pokemonFile) throws ObjectMappingException {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.whereRows).build();
        GooeyPage page = GooeyPage.builder().template(template).title(FancyTextHandler.getFormattedString(
                ConfigGetters.whereTitle
                        .replace("%pokemon%", pokemonFile)
        )).build();

        int maxRows = 9 * ConfigGetters.whereRows;
        List<Integer> allSlots = new ArrayList<>();
        for (int i = 0; i < maxRows; i++) {

            allSlots.add(i);

        }
        if (!ConfigGetters.whereBorderSlots.isEmpty()) {

            for (String s : ConfigGetters.whereBorderSlots.split(", ")) {

                int slot = Integer.parseInt(s);
                ItemStack border = ItemStackHandler.buildFromStringID(ConfigGetters.whereBorderID);
                border.set(DataComponentTypes.CUSTOM_NAME, FancyTextHandler.getFormattedText(""));
                page.getTemplate().getSlot(slot).setButton(GooeyButton.builder().display(border).build());
                allSlots.removeIf(e -> e == slot);

            }

        }

        int index = DataHandler.indexMap.get(pokemonFile);
        List<String> biomes = new ArrayList<>();
        Map<String, Map<String, Map<String, Map<String, Double>>>> bigMap = CobblemonLegendarySpawner.pokemonConfigManager.getConfigNode(index, "Spawn").getValue(new TypeToken<Map<String, Map<String, Map<String, Map<String, Double>>>>>() {});
        for (Map.Entry<String, Map<String, Map<String, Map<String, Double>>>> entry : bigMap.entrySet()) {

            String biomeID = entry.getKey();
            biomes.add(biomeID);

        }

        for (int i = 0; i < biomes.size(); i++) {

            try {

                int slot = allSlots.get(i);
                String biomeID = biomes.get(i);
                Map<String, Map<String, Map<String, Double>>> data = bigMap.get(biomeID);
                ItemStack displayItem = ItemStackHandler.buildFromStringID(ConfigGetters.biomeDisplayID);
                displayItem.set(DataComponentTypes.CUSTOM_NAME, FancyTextHandler.getFormattedText(
                        ConfigGetters.biomeDisplayName.replace("%biomeID%", biomeID)
                                .replace("%biomeName%", SpawnHandler.getPrettyBiomeName(biomeID))
                ));
                List<String> times = new ArrayList<>();
                List<String> w = new ArrayList<>();
                for (Map.Entry<String, Map<String, Map<String, Double>>> e : data.entrySet()) {

                    String time = e.getKey();
                    times.add(time);
                    Map<String, Map<String, Double>> data2 = e.getValue();
                    for (Map.Entry<String, Map<String, Double>> e2 : data2.entrySet()) {

                        if (!w.contains(e2.getKey())) {

                            w.add(e2.getKey());

                        }

                    }

                }
                String time = String.join(", ", times);
                String weather = String.join(", ", w);
                List<Text> lore = new ArrayList<>();
                for (String s : ConfigGetters.biomeLore) {

                    lore.add(FancyTextHandler.getFormattedText(s
                            .replace("%time%", time)
                            .replace("%weather%", weather)
                    ));

                }

                displayItem.set(DataComponentTypes.LORE, new LoreComponent(lore));
                page.getTemplate().getSlot(slot).setButton(GooeyButton.builder().display(displayItem).build());

            } catch (IndexOutOfBoundsException e) {

                break;

            }

            UIManager.openUIForcefully(player, page);

        }

    }

}
