package dev.deopping.betterecocommands.commands;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.Debugger;
import dev.deopping.betterecocommands.utilities.Formatter;
import dev.deopping.betterecocommands.utilities.PermissionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BetterEcoCommands implements CommandExecutor {

    Core core;
    Formatter formatter;
    PermissionManager permissionManager;
    Debugger debugger;
    BEC bec;

    public BetterEcoCommands(Core core) {
        this.core = core;
        this.formatter = core.getFormatter();
        this.permissionManager = core.getPermissionManager();
        this.debugger = core.getDebugger();
        this.bec = core.getBEC();

        core.getServer().getPluginCommand("betterecocommands").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    this.reload();
                    return true;
                }

                if (args[0].equalsIgnoreCase("set")) {
                    bec.set(sender, args);
                    return true;
                }

                if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) {
                    bec.give(sender, args);
                    return true;
                }

                if (args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("remove")) {
                    bec.take(sender, args);
                    return true;
                }

                this.help();
                return true;
            }

            this.info();
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                this.reload(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {
                bec.set(sender, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) {
                bec.give(sender, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("remove")) {
                bec.take(sender, args);
                return true;
            }

            this.help(player);
            return true;
        }

        this.info(player);
        return true;
    }

    public void info() {
        core.getLogger().info("BetterEcoCommands");
        core.getLogger().info("Developed by Mantice");
        core.getLogger().info("Version: " + core.getDescription().getVersion());
        core.getLogger().info("Need help? Join my Discord:");
        core.getLogger().info("https://discord.deopping.dev");
    }

    public void info(Player player) {
        if (!permissionManager.hasPermission(player, "info")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        formatter.sendMessage(player, "info");
    }

    public void help() {
        core.getLogger().info("Help:");
        core.getLogger().info(" | bec - Shows plugin information");
        core.getLogger().info(" | bec help - Shows this information");
        core.getLogger().info(" | bec reload - Reloads config files");
        core.getLogger().info(" | bec <set/give/take> <player> <amount> (-s)");
        core.getLogger().info("If you need any additional help, feel free to join my Discord:");
        core.getLogger().info("https://discord.deopping.dev");
    }

    public void help(Player player) {
        if (!permissionManager.hasPermission(player, "help")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        formatter.sendMessage(player, "help");
    }

    public void reload() {
        debugger.debug("Reloading configuration files...");
        core.getConfigYml().saveDefaultConfig();
        core.getConfigYml().reloadConfig();
        debugger.debug("Reloaded config.yml!");
        core.getMessagesYml().saveDefaultConfig();
        core.getMessagesYml().reloadConfig();
        debugger.debug("Reloaded messages.yml!");
        core.getPermissionsYml().saveDefaultConfig();
        core.getPermissionsYml().reloadConfig();
        debugger.debug("Reloaded permissions.yml!");
        core.getLogger().info("Reload complete!");
    }

    public void reload(Player player) {
        if (!permissionManager.hasPermission(player, "reload")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        this.reload();
        formatter.sendMessage(player, "reload");
    }

}
