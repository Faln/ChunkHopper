package me.faln.chunkhopper.factory;

import me.faln.chunkhopper.ChunkHopper;
import me.faln.chunkhopper.objects.YMLConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class YMLConfigFactory {

    private final ChunkHopper plugin;

    public YMLConfigFactory(final ChunkHopper plugin) {
        this.plugin = plugin;
    }

    public YMLConfig createConfig(final File folder, final String name) {
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                plugin.log("Created folder for " + name + ".yml");
            }
        }

        final File ymlFile = new File(folder, name + ".yml");

        if (!ymlFile.exists()) {
            try {
                plugin.saveResource(ymlFile.getName(), false);
                if (ymlFile.createNewFile()) {
                    plugin.log("Created file: " + ymlFile.getName());
                }
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }

        final FileConfiguration config = YamlConfiguration.loadConfiguration(ymlFile);

        return new YMLConfig(ymlFile, config);
    }
}
