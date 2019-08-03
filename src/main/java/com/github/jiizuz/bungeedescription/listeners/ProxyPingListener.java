package com.github.jiizuz.bungeedescription.listeners;

import com.github.jiizuz.bungeedescription.management.DescriptionManager;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * A {@code Listener} which handles only the {@code ProxyPingEvent}
 * to change the {@link ServerPing} on the event to set a colorful
 * description on the Servers List.
 *
 * @author Jiizuz
 * @see net.md_5.bungee.api.plugin.Listener
 * @see ProxyPingEvent#setResponse(ServerPing)
 * @since 1.0
 */
public class ProxyPingListener implements Listener {

    private final DescriptionManager descriptionManager;

    /**
     * Constructs a {@code ProxyPingListener} with the {@code DescriptionManager}
     * to get the descriptions to set.
     *
     * @param descriptionManager the {@code DescriptionManager} to get the descriptions.
     */
    public ProxyPingListener(final DescriptionManager descriptionManager) {
        this.descriptionManager = descriptionManager;
    }

    /**
     * Only changes the description in a new instance of
     * {@code ServerPing} class and set on response.
     *
     * @param event the event provided by the {@code BungeeCord} API.
     * @see ProxyPingEvent#setResponse(ServerPing)
     * @see DescriptionManager#getRandomDescription()
     * @see ServerPing
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProxyPingEvent(final ProxyPingEvent event) {
        final ServerPing currentResponse = event.getResponse();

        final ServerPing.Protocol version = currentResponse.getVersion();
        final ServerPing.Players players = currentResponse.getPlayers();
        final BaseComponent description = descriptionManager.getRandomDescription();
        final Favicon favicon = currentResponse.getFaviconObject();

        final ServerPing newResponse = new ServerPing(version, players, description, favicon);

        event.setResponse(newResponse);
    }
}
