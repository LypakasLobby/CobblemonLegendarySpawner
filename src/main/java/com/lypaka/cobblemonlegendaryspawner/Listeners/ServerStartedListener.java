package com.lypaka.cobblemonlegendaryspawner.Listeners;

import com.lypaka.cobblemonlegendaryspawner.Handlers.DataHandler;
import com.lypaka.cobblemonlegendaryspawner.Handlers.SpawnHandler;
import com.lypaka.cobblemonlegendaryspawner.Handlers.WebhookHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerStartedListener implements ServerLifecycleEvents.ServerStarted {

    @Override
    public void onServerStarted (MinecraftServer minecraftServer) {

        DataHandler.createAndLoadFiles(false);
        WebhookHandler.init();
        SpawnHandler.startTimer();

    }

}
