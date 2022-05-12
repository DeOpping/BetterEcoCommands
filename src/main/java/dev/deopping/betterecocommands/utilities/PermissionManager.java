package dev.paracausal.voidranks.utilities;

import dev.paracausal.voidranks.Core;
import dev.paracausal.voidranks.utilities.configurations.ConfigManager;
import org.bukkit.entity.Player;

public class PermissionManager {

    Core core;
    ConfigManager permissionsYml;

    public PermissionManager(Core core) {
        this.core = core;
        this.permissionsYml = core.getPermissionsYml();
    }

    public boolean hasPermission(Player player, String location) {
        String permission = permissionsYml.getConfig().getString(location);

        if (permission == null || permission.equalsIgnoreCase("")) {
            return true;
        }

        return player.hasPermission(permission);
    }

}
