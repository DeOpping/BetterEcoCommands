package dev.paracausal.voidranks.utilities;

import dev.paracausal.voidranks.Core;
import dev.paracausal.voidranks.utilities.configurations.ConfigManager;

public class Debugger {

    Core core;
    ConfigManager configYml;

    public Debugger(Core core) {
        this.core = core;
        this.configYml = core.getConfigYml();
    }

    public void debug(String string) {
        if (configYml.getConfig().getBoolean("debug-mode"))
            core.getServer().getLogger().info("[VoidRanks] " + string);
    }

}
