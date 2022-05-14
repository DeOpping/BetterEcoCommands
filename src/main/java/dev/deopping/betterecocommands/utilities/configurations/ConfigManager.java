package dev.deopping.betterecocommands.utilities.configurations;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.configurations.updater.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    Core core;
    private File configFile;
    private FileConfiguration dataConfig;
    private final String location;

    public ConfigManager(Core core, String name) {
        this.core = core;
        this.location = name + ".yml";

        saveDefaultConfig();
        dataConfig = YamlConfiguration.loadConfiguration(getFile());
    }

    private File getFile() {
        return new File(core.getDataFolder(), location);
    }

    public FileConfiguration getConfig() {
        return dataConfig;
    }

    public void saveConfig() throws IOException {
        if (!((this.dataConfig == null) || (this.configFile == null))) {
            this.getConfig().save(this.configFile);
        }
    }

    public void updateConfig() {
        try {
            ConfigUpdater.update(core, location, getFile(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
    }

    public void reloadConfig() {
        dataConfig = YamlConfiguration.loadConfiguration(getFile());
    }

    public void saveDefaultConfig() {
        if (configFile == null) configFile = getFile();
        if (configFile.exists()) return;

        core.saveResource(location, false);
    }

}
