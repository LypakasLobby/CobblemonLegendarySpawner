package com.lypaka.cobblemonlegendaryspawner;

/** TODO
 * World Blacklist
 * Player Blacklist
 */
import com.lypaka.cobblemonlegendaryspawner.Commands.CobblemonLegendarySpawnerCommand;
import com.lypaka.cobblemonlegendaryspawner.Listeners.ServerStartedListener;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.shadow.configurate.objectmapping.ObjectMappingException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CobblemonLegendarySpawner implements ModInitializer {

    public static final String MOD_ID = "cobblemonlegendaryspawner";
    public static final String MOD_NAME = "CobblemonLegendarySpawner";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static ComplexConfigManager pokemonConfigManager;

    @Override
    public void onInitialize() {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/cobblemonlegendaryspawner"));
        String[] files = new String[]{"cobblemonlegendaryspawner.conf", "legendaryList.conf", "checkSpawnsGUI.conf", "whereGUI.conf"};
        configManager = new BasicConfigManager(files, dir, CobblemonLegendarySpawner.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        try {

            ConfigGetters.load();

        } catch (ObjectMappingException e) {

            throw new RuntimeException(e);

        }

        CobblemonLegendarySpawnerCommand.register();

        ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedListener());

    }

}
