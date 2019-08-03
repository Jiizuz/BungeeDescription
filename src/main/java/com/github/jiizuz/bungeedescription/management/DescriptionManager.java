package com.github.jiizuz.bungeedescription.management;

import net.md_5.bungee.api.chat.BaseComponent;

import java.io.File;
import java.io.IOException;

/**
 * A interface implementable to use the default
 * protocols to manage descriptions around the {@code Plugin}
 *
 * @author Jiizuz
 * @since 2.0
 */
public interface DescriptionManager {

    /**
     * In this file the descriptions must be signed as
     * YAML format in a list.
     *
     * @return the {@code File} containing the raw descriptions.
     */
    File getDescriptionsFile();

    /**
     * Takes from the stored descriptions a random of them
     * and returns it.
     *
     * @return a randomized stored description {@code BaseComponent}.
     */
    BaseComponent getRandomDescription();

    /**
     * Tries to create the main data folder and the {@link #getDescriptionsFile()}
     * Additionally it saves on the {@code File} a default raw descriptions
     * if the file doesn't exists. It call {@link #reloadConfiguration()} too.
     *
     * @throws IOException if can't create a folder or
     *                     can't reach the {@link #getDescriptionsFile()} to load
     */
    void loadConfiguration() throws IOException;

    /**
     * It reloads by parsing a {@code List} of strings which are designed
     * to be a description. It convert that {@code Strings} to {@code TextComponent}
     * to use in game.
     *
     * @throws IOException if the configuration can't be loaded
     */
    void reloadConfiguration() throws IOException;
}
