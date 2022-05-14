package dev.deopping.betterecocommands.commands;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.Formatter;
import dev.deopping.betterecocommands.utilities.PermissionManager;
import dev.deopping.betterecocommands.utilities.configurations.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BEC {

    Core core;
    Formatter formatter;
    PermissionManager permissionManager;
    Economy economy;
    ConfigManager messagesYml;

    public BEC(Core core) {
        this.core = core;
        this.formatter = core.getFormatter();
        this.permissionManager = core.getPermissionManager();
        this.economy = core.getEconomy();
        this.messagesYml = core.getMessagesYml();
    }

    public void set(CommandSender sender, String[] args) {
        if (sender instanceof Player player && !permissionManager.hasPermission(player, "set")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        OfflinePlayer target;
        double amount;

        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select a player!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-player");
            return;
        }

        target = Bukkit.getOfflinePlayer(args[1]);

        if (!target.hasPlayedBefore()) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid player!");
                return;
            }

            formatter.sendMessage(player, "invalid-player");
            return;
        }

        if (args.length == 2) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select an amount!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-amount");
            return;
        }

        try {
            amount = Double.parseDouble(args[2]);
        } catch (IllegalArgumentException exception) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid amount!");
                return;
            }
            formatter.sendMessage(player, "invalid-amount");
            return;
        }

        boolean silent = args.length >= 4 && args[3].equalsIgnoreCase("-s");

        economy.withdrawPlayer(target, economy.getBalance(target));
        economy.depositPlayer(target, amount);

        if (!silent && target.isOnline())
            formatter.sendMessage(target.getPlayer(), "notifications.set", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", sender.getName());

        if (!(sender instanceof Player player)) {
            core.getLogger().info("Set " + target.getName() + "'s balance to " + amount + "!");
            return;
        }

        formatter.sendMessage(player, "set", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", target.getName());
    }

    public void give(CommandSender sender, String[] args) {
        if (sender instanceof Player player && !permissionManager.hasPermission(player, "give")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        OfflinePlayer target;
        double amount;

        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select a player!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-player");
            return;
        }

        target = Bukkit.getOfflinePlayer(args[1]);

        if (!target.hasPlayedBefore()) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid player!");
                return;
            }

            formatter.sendMessage(player, "invalid-player");
            return;
        }

        if (args.length == 2) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select an amount!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-amount");
            return;
        }

        try {
            amount = Double.parseDouble(args[2]);
        } catch (IllegalArgumentException exception) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid amount!");
                return;
            }
            formatter.sendMessage(player, "invalid-amount");
            return;
        }

        boolean silent = args.length >= 4 && args[3].equalsIgnoreCase("-s");

        economy.depositPlayer(target, amount);

        if (!silent && target.isOnline())
            formatter.sendMessage(target.getPlayer(), "notifications.given", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", sender.getName());

        if (!(sender instanceof Player player)) {
            core.getLogger().info("Gave " + target.getName() + " $" + amount + "!");
            return;
        }

        formatter.sendMessage(player, "give", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", target.getName());
    }

    public void take(CommandSender sender, String[] args) {
        if (sender instanceof Player player && !permissionManager.hasPermission(player, "take")) {
            formatter.sendMessage(player, "no-permission");
            return;
        }

        OfflinePlayer target;
        double amount;

        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select a player!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-player");
            return;
        }

        target = Bukkit.getOfflinePlayer(args[1]);

        if (!target.hasPlayedBefore()) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid player!");
                return;
            }

            formatter.sendMessage(player, "invalid-player");
            return;
        }

        if (args.length == 2) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Please select an amount!");
                core.getLogger().info("Do '/bec help' if you need help!");
                return;
            }

            formatter.sendMessage(player, "syntax.select-amount");
            return;
        }

        try {
            amount = Double.parseDouble(args[2]);
        } catch (IllegalArgumentException exception) {
            if (!(sender instanceof Player player)) {
                core.getLogger().info("Invalid amount!");
                return;
            }
            formatter.sendMessage(player, "invalid-amount");
            return;
        }

        boolean silent = args.length >= 4 && args[3].equalsIgnoreCase("-s");

        economy.withdrawPlayer(target, amount);

        if (!silent && target.isOnline())
            formatter.sendMessage(target.getPlayer(), "notifications.taken", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", sender.getName());

        if (!(sender instanceof Player player)) {
            core.getLogger().info("Took $" + amount + " from " + target.getName() + "!");
            return;
        }

        formatter.sendMessage(player, "take", "{AMOUNT}", String.valueOf(amount), "{PLAYER}", target.getName());
    }

}