package dev.deopping.betterecocommands.utilities;

import dev.deopping.betterecocommands.Core;
import dev.deopping.betterecocommands.utilities.configurations.ConfigManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class Formatter {

    Core core;
    ConfigManager messagesYml;

    public Formatter(Core core) {
        this.core = core;
        this.messagesYml = core.getMessagesYml();
    }

    public Component format(String string, Player player) {
        var miniMessage = MiniMessage.miniMessage();
        string = string.replace("{PREFIX}", messagesYml.getConfig().getString("prefix"));
        string = string.replace("{VERSION}", core.getDescription().getVersion());
        string = PlaceholderAPI.setPlaceholders(player, string);

        return miniMessage.deserialize(string);
    }

    public void sendMessage(Player player, String location) {
        Object value = messagesYml.getConfig().get(location);
        Audience audience = (Audience) player;

        if (value instanceof String) {
            audience.sendMessage(this.format(value.toString(), player));
            return;
        }

        for (String message : messagesYml.getConfig().getStringList(location)) {
            audience.sendMessage(this.format(message, player));
        }
    }

    public void sendMessage(Player player, String location, String replace1, String replacement1, String replace2, String replacement2) {
        Object value = messagesYml.getConfig().get(location);
        Audience audience = (Audience) player;

        if (value instanceof String) {
            String message = value.toString().replace(replace1, replacement1);
            message = message.replace(replace2, replacement2);
            audience.sendMessage(this.format(message, player));
            return;
        }

        for (String message : messagesYml.getConfig().getStringList(location)) {
            message = message.replace(replace1, replacement1);
            message = message.replace(replace2, replacement2);
            audience.sendMessage(this.format(message, player));
        }
    }

}
