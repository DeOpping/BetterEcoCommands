package dev.deopping.betterecocommands.utilities;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.configurations.ConfigManager;

public class Debugger {

    Core core;
    ConfigManager configYml;

    public Debugger(Core core) {
        this.core = core;
        this.configYml = core.getConfigYml();
    }

    public void debug(String string) {
        if (configYml.getConfig().getBoolean("debug-mode"))
            core.getServer().getLogger().info("[BetterEcoCommands] " + string);
    }

}
