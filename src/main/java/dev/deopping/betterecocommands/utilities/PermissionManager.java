package dev.deopping.betterecocommands.utilities;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.configurations.ConfigManager;
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
