package com.github.jiizuz.bungeedescription.commands;

import com.github.jiizuz.bungeedescription.management.DescriptionManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

/**
 * A simply {@code Command} to reload or update the stored
 * configuration by using a {@link DescriptionManager} interface.
 * <p>
 * The command call {@link DescriptionManager#reloadConfiguration()} to reload a configuration.
 *
 * @see net.md_5.bungee.api.plugin.Command
 * @see DescriptionManager#reloadConfiguration()
 * @see #execute(CommandSender, String[])
 * @since 2.0
 */
public class ReloadCommand extends Command {
    /**
     * The command label to apply to this {@code Command}
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static final String COMMAND_NAME = "bungeedescription";
    /**
     * The permission required to execute this {@code Command}
     */
    private static final String COMMAND_PERMISSION = "description.command.reload";

    private final DescriptionManager descriptionManager;

    /**
     * Constructs a {@code ReloadCommand} with the {@code Updatable} to update
     * or reload the configuration.
     *
     * @param descriptionManager the {@code Updater} to call when the command is used
     */
    public ReloadCommand(final DescriptionManager descriptionManager) {
        super(COMMAND_NAME, COMMAND_PERMISSION);
        this.descriptionManager = descriptionManager;
    }

    /**
     * It tries to reload the configuration by calling {@link DescriptionManager#reloadConfiguration()}
     * and sends a message of confirmation to the {@param commandSender}.
     * If any error occurs updating the configuration, the {@code Throwable} will
     * be printed by {@link Throwable#printStackTrace()} and sends an error message
     * to the {@param commandSender}
     *
     * @param commandSender the command sender which executes the command
     * @param args          the arguments used in the command
     */
    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        try {
            descriptionManager.reloadConfiguration();
            commandSender.sendMessage(new TextComponent(ChatColor.GREEN + "[BungeeDescription] The descriptions have been successfully reloaded."));
        } catch (IOException e) {
            commandSender.sendMessage(new TextComponent(ChatColor.RED + "[BungeeDescription] There was an error reloading the descriptions."));
            e.printStackTrace();
        }
    }
}
