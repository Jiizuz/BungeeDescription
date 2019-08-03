package com.github.jiizuz.bungeedescription;

import com.github.jiizuz.bungeedescription.commands.ReloadCommand;
import com.github.jiizuz.bungeedescription.listeners.ProxyPingListener;
import com.github.jiizuz.bungeedescription.management.DescriptionManagement;
import com.github.jiizuz.bungeedescription.management.DescriptionManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

/**
 * BungeeDescription is a simple {@code Plugin} which sets
 * on the Minecraft Server's Description a colorful description
 * of the current server by using the event
 * {@link net.md_5.bungee.api.event.ProxyPingEvent}
 *
 * @author Jiizuz
 * @see net.md_5.bungee.api.event.ProxyPingEvent
 */
public class BungeeDescription extends Plugin {
    /**
     * The {@code DescriptionManager} to manage the descriptions.
     */
    private final DescriptionManager configurationManager = new DescriptionManagement(this);

    /**
     * Plugin startup logic.
     */
    @Override
    public void onEnable() {
        if (!canEnable()) return;

        registerListeners();
        registerCommands();

        getLogger().info("BungeeDescription v2.0.4 by Jiizuz enabled correctly.");
    }

    /**
     * @return {@code true} if there's no errors loading the configuration;
     * {@code false} if there's any error
     * @see DescriptionManager#loadConfiguration()
     */
    private boolean canEnable() {
        try {
            configurationManager.loadConfiguration();
            return true;
        } catch (IOException e) {
            getLogger().severe("There was an error initializing the configuration. The Plugin will not be longer enabled.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Register as needed {@code Listener}'s to handle different
     * {@code Event}'s
     */
    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new ProxyPingListener(configurationManager));
    }

    /**
     * Register the current used {@code Command}'s in the
     * {@code Plugin}
     */
    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(configurationManager));
    }
}
