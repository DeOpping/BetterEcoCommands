package dev.deopping.betterecocommands.utilities;

import dev.deopping.betterecocommands.Core;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateCheck {

    Core core;
    int id = 101969;

    public UpdateCheck(Core core) {
        this.core = core;
    }

   public void getVersion(Consumer<String> consumer) {
       Bukkit.getScheduler().runTaskAsynchronously(core, () -> {
          try {
              InputStream stream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id).openStream();
              Scanner scanner = new Scanner(stream);

              if (scanner.hasNext())
                  consumer.accept(scanner.next());

          } catch (IOException exception) {
              core.getLogger().info("Unable to check for updates: " + exception.getMessage());
          }
       });
   }

}
