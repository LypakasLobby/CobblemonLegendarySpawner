package com.lypaka.cobblemonlegendaryspawner;

import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import com.lypaka.shadow.google.common.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static String broadcast;
    public static boolean spawnGlowing;
    public static boolean ignoreCreative;
    public static boolean ignoreSpectator;
    public static int spawnIntervalMax;
    public static int spawnIntervalMin;
    public static double spawnAttemptChance;
    public static int spawnLocationXZ;
    public static String webhookAvatarURL;
    public static String webhookMessage;
    public static String webhookTitle;
    public static String webhookURL;
    public static String webhookUsername;

    public static Map<String, List<String>> bigOleMap;

    public static void load() throws ObjectMappingException {

        broadcast = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Broadcast").getString();
        spawnGlowing = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Glowing").getBoolean();
        ignoreCreative = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Ignore-Creative").getBoolean();
        ignoreSpectator = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Ignore-Spectator").getBoolean();
        spawnIntervalMax = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Interval-Max").getInt();
        spawnIntervalMin = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Interval-Min").getInt();
        spawnAttemptChance = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Spawn-Attempt-Chance").getDouble();
        spawnLocationXZ = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Spawn-Location-XZ").getInt();
        webhookAvatarURL = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Avatar-URL").getString();
        webhookMessage = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Message").getString();
        webhookTitle = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Title").getString();
        webhookURL = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "URL").getString();
        webhookUsername = CobblemonLegendarySpawner.configManager.getConfigNode(0, "Webhook", "Username").getString();

        bigOleMap = CobblemonLegendarySpawner.configManager.getConfigNode(1, "Legendary-Biome-Map").getValue(new TypeToken<Map<String, List<String>>>() {});

    }

}
