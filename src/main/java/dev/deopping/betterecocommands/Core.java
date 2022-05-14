package dev.deopping.betterecocommands;

import dev.deopping.betterecocommands.commands.BEC;
import dev.deopping.betterecocommands.commands.BetterEcoCommands;
import dev.deopping.betterecocommands.utilities.Debugger;
import dev.deopping.betterecocommands.utilities.Formatter;
import dev.deopping.betterecocommands.utilities.PermissionManager;
import dev.deopping.betterecocommands.utilities.UpdateCheck;
import dev.deopping.betterecocommands.utilities.configurations.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    @Override
    public void onLoad() {
        this.configYml = new ConfigManager(this, "config");
        configYml.saveDefaultConfig();
        configYml.updateConfig();

        this.debugger = new Debugger(this);
        debugger.debug("Saved and Updated config.yml!");

        this.messagesYml = new ConfigManager(this, "messages");
        messagesYml.saveDefaultConfig();
        messagesYml.updateConfig();
        debugger.debug("Saved and Updated messages.yml!");

        this.permissionsYml = new ConfigManager(this, "permissions");
        permissionsYml.saveDefaultConfig();
        permissionsYml.updateConfig();
        debugger.debug("Saved and Updated permissions.yml!");

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("This plugin requires Vault!");
            getLogger().info("Download it here: https://www.spigotmc.org/resources/vault.34315/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!this.setupEconomy()) {
            getLogger().info("No Vault economy provider found!");
            getLogger().info("Disabling BetterEcoCommands...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.formatter = new Formatter(this);
        this.permissionManager = new PermissionManager(this);
    }

    @Override
    public void onEnable() {
        this.bec = new BEC(this);
        this.betterEcoCommands = new BetterEcoCommands(this);
        debugger.debug("Registered BetterEcoCommands command!");

        getLogger().info("BetterEcoCommands enabled!");

        if (configYml.getConfig().getBoolean("update-check")) {
            new UpdateCheck(this).getVersion(version -> {
                if (!getDescription().getVersion().equals(version)) {
                    getLogger().info("There is a new update available! Version " + version);
                    getLogger().info("You are on version " + getDescription().getVersion());
                    getLogger().info("Download it here: https://www.spigotmc.org/resources/betterecocommands.101969/");
                }
            });
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterEcoCommands disabled!");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> service = getServer().getServicesManager().getRegistration(Economy.class);
        if (service == null) return false;

        economy = service.getProvider();
        return true;
    }

    private Economy economy;
    public Economy getEconomy() { return economy; }

    private ConfigManager configYml;
    public ConfigManager getConfigYml() { return configYml; }

    private ConfigManager messagesYml;
    public ConfigManager getMessagesYml() { return messagesYml; }

    private ConfigManager permissionsYml;
    public ConfigManager getPermissionsYml() { return permissionsYml; }

    private Debugger debugger;
    public Debugger getDebugger() { return debugger; }

    private Formatter formatter;
    public Formatter getFormatter() { return formatter; }

    private PermissionManager permissionManager;
    public PermissionManager getPermissionManager() { return permissionManager; }

    private BetterEcoCommands betterEcoCommands;
    public BetterEcoCommands getBetterEcoCommands() { return betterEcoCommands; }

    private BEC bec;
    public BEC getBEC() { return bec; }

}
