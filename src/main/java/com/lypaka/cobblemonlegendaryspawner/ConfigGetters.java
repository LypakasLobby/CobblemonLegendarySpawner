package com.lypaka.cobblemonlegendaryspawner;

import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.lypaka.shadow.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static String broadcast;
    public static boolean spawnGlowing;
    public static boolean ignoreCreative;
    public static boolean ignoreSpectator;
    public static int spawnIntervalMax;
    public static int spawnIntervalMin;
    public static List<String> playerBlacklist;
    public static double spawnAttemptChance;
    public static int spawnLocationXZ;
    public static String webhookAvatarURL;
    public static String webhookMessage;
    public static String webhookTitle;
    public static String webhookURL;
    public static String webhookUsername;
    public static List<String> worldBlacklist;

    public static Map<String, List<String>> bigOleMap;

    public static int checkSpawnsRows;
    public static String checkSpawnsTitle;
    public static String checkSpawnsBorderID;
    public static String checkSpawnsBorderSlots;
    public static String checkSpawnsPokemonDisplayName;
    public static List<String> checkSpawnsPokemonLore;

    public static int whereRows;
    public static String whereTitle;
    public static String whereBorderID;
    public static String whereBorderSlots;
    public static String biomeDisplayID;
    public static String biomeDisplayName;
    public static List<String> biomeLore;

    public static void load() throws ObjectMappingException {

        boolean needsSaving = false;
        broadcast = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Broadcast").getString();
        spawnGlowing = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Glowing").getBoolean();
        ignoreCreative = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Ignore-Creative").getBoolean();
        ignoreSpectator = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Ignore-Spectator").getBoolean();
        spawnIntervalMax = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Interval-Max").getInt();
        spawnIntervalMin = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Interval-Min").getInt();
        playerBlacklist = new ArrayList<>();
        if (CobblemonLegendarySpawner.configManager.getConfigNode(0, "Player-Blacklist").isVirtual()) {

            needsSaving = true;
            CobblemonLegendarySpawner.configManager.getConfigNode(0, "Player-Blacklist").setValue(playerBlacklist);

        } else {

            playerBlacklist = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Player-Blacklist").getList(TypeToken.of(String.class));

        }
        spawnAttemptChance = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Spawn-Attempt-Chance").getDouble();
        spawnLocationXZ = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Spawn-Location-XZ").getInt();
        webhookAvatarURL = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Avatar-URL").getString();
        webhookMessage = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Message").getString();
        webhookTitle = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Title").getString();
        webhookURL = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "URL").getString();
        webhookUsername = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Username").getString();
        worldBlacklist = new ArrayList<>();
        if (CobblemonLegendarySpawner.configManager.getConfigNode(0, "World-Blacklist").isVirtual()) {

            if (!needsSaving) needsSaving = true;
            CobblemonLegendarySpawner.configManager.getConfigNode(0, "World-Blacklist").setValue(worldBlacklist);

        } else {

            worldBlacklist = CobblemonLegendarySpawner.configManager.getConfigNode(0, "World-Blacklist").getList(TypeToken.of(String.class));

        }

        bigOleMap = CobblemonLegendarySpawner.configManager.getConfigNode(1, "Legendary-Biome-Map").getValue(new TypeToken<Map<String, List<String>>>() {});

        checkSpawnsRows = CobblemonLegendarySpawner.configManager.getConfigNode(2, "General", "Rows").getInt();
        checkSpawnsTitle = CobblemonLegendarySpawner.configManager.getConfigNode(2, "General", "Title").getString();
        checkSpawnsBorderID = CobblemonLegendarySpawner.configManager.getConfigNode(2, "Slots", "Border", "ID").getString();
        checkSpawnsBorderSlots = CobblemonLegendarySpawner.configManager.getConfigNode(2, "Slots", "Border", "Slots").getString();
        checkSpawnsPokemonDisplayName = CobblemonLegendarySpawner.configManager.getConfigNode(2, "Slots", "Pokemon", "Display-Name").getString();
        checkSpawnsPokemonLore = CobblemonLegendarySpawner.configManager.getConfigNode(2, "Slots", "Pokemon", "Lore").getList(TypeToken.of(String.class));

        whereRows = CobblemonLegendarySpawner.configManager.getConfigNode(3, "General", "Rows").getInt();
        whereTitle = CobblemonLegendarySpawner.configManager.getConfigNode(3, "General", "Title").getString();
        whereBorderID = CobblemonLegendarySpawner.configManager.getConfigNode(3, "Slots", "Border", "ID").getString();
        whereBorderSlots = CobblemonLegendarySpawner.configManager.getConfigNode(3, "Slots", "Border", "Slots").getString();
        biomeDisplayID = CobblemonLegendarySpawner.configManager.getConfigNode(3, "Slots", "Biome-Info", "Display-ID").getString();
        biomeDisplayName = CobblemonLegendarySpawner.configManager.getConfigNode(3, "Slots", "Biome-Info", "Display-Name").getString();
        biomeLore = CobblemonLegendarySpawner.configManager.getConfigNode(3, "Slots", "Biome-Info", "Lore").getList(TypeToken.of(String.class));

        if (needsSaving) {

            CobblemonLegendarySpawner.configManager.save();

        }

    }

}
