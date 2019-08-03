package com.github.jiizuz.bungeedescription.management;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

/**
 * A class to manage the descriptions.
 *
 * @author Jiizuz
 * @see com.github.jiizuz.bungeedescription.management.DescriptionManager
 * @since 2.0
 */
public class DescriptionManagement implements DescriptionManager {
    /**
     * The path where the raw descriptions {@code List} where going to find.
     */
    private static final String DESCRIPTIONS_PATH = "descriptions";

    private BaseComponent[] descriptions = new BaseComponent[0];
    private final SecureRandom random = new SecureRandom();
    private File descriptionsFile;
    private final Plugin plugin;

    /**
     * Constructs a {@code DescriptionManagement} wit the {@code Plugin} owner.
     *
     * @param plugin the plugin which going to use the {@code ConfigurationManager}
     * @see #loadConfiguration()
     */
    public DescriptionManagement(final Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * In this file the descriptions must be signed as
     * YAML format in a list.
     *
     * @return the {@code File} containing the raw descriptions.
     */
    @Override
    public File getDescriptionsFile() {
        return descriptionsFile;
    }

    /**
     * Takes from the stored descriptions a random of them
     * and returns it.
     *
     * @return a randomized stored description {@code BaseComponent}.
     */
    @Override
    public BaseComponent getRandomDescription() {
        return descriptions[random.nextInt(descriptions.length)];
    }

    /**
     * Tries to create the main data folder and the {@link #getDescriptionsFile()}
     * Additionally it saves on the {@code File} a default raw descriptions
     * if the file doesn't exists. It call {@link #reloadConfiguration()} too.
     *
     * @throws IOException if can't create a folder or
     *                     can't reach the {@link #getDescriptionsFile()} to load
     */
    @Override
    public void loadConfiguration() throws IOException {
        this.descriptionsFile = new File(plugin.getDataFolder(), "descriptions.yml");
        if (plugin.getDataFolder().mkdirs()) {
            plugin.getLogger().info("Main folder created.");
        }
        if (!getDescriptionsFile().exists() && getDescriptionsFile().createNewFile()) {
            final Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getDescriptionsFile());
            configuration.set(DESCRIPTIONS_PATH, Collections.singletonList("&eDefault description%n&6Another line!"));
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, getDescriptionsFile());
            plugin.getLogger().info(getDescriptionsFile().getName() + " created.");
        }

        this.reloadConfiguration();
    }

    /**
     * It reloads by parsing a {@code List} of strings which are designed
     * to be a description. It convert that {@code Strings} to {@code TextComponent}
     * to use in game.
     *
     * @throws IOException if the configuration can't be loaded
     * @see #parseDescription(String)
     */
    @Override
    public void reloadConfiguration() throws IOException {
        final Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getDescriptionsFile());
        final List<String> rawDescriptions = configuration.getStringList(DESCRIPTIONS_PATH);
        final int maxBuffer = rawDescriptions.size();

        final BaseComponent[] descriptions = new BaseComponent[maxBuffer];

        for (int i = 0; i < maxBuffer; i++)
            descriptions[i] = parseDescription(rawDescriptions.get(i));

        this.descriptions = descriptions;
        // Clear the List to help the GC
        rawDescriptions.clear();
    }

    /**
     * Parses an {@code String} by casting it to a {@code TextComponent}
     * for using on the ping event.
     * The {@param rawDescription} it's colorized according to the {@code ChatColor}'s.
     * Additionally it replaces the input '%n' by the {@link System#lineSeparator()} String.
     *
     * @param rawDescription the text to place on the {@code TextComponent}
     * @return a new {@code TextComponent} with the specified text on {@param rawDescription}
     * @see #colorize(String)
     */
    private TextComponent parseDescription(final String rawDescription) {
        return new TextComponent(colorize(rawDescription).replaceAll("%n", "\n"));
    }

    /**
     * Replaces the color code formats according to the colors
     * specified on the {@link net.md_5.bungee.api.ChatColor} class
     * by using a regex method.
     *
     * @see net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char, String)
     * @see String#replaceAll(String, String)
     */
    private String colorize(final String input) {
        return input.replaceAll("&([0-9a-fA-Fk-rK-R])", "§$1");
    }
}
