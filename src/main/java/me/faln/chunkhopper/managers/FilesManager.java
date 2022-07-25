package me.faln.chunkhopper.managers;

import me.faln.chunkhopper.ChunkHopper;
import me.faln.chunkhopper.factory.YMLConfigFactory;
import me.faln.chunkhopper.objects.YMLConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesManager {

    private final Map<String, YMLConfig> filesMap = new HashMap<>();

    private final ChunkHopper main;

    public FilesManager(final ChunkHopper main) {
        this.main = main;
        this.createFiles();
    }

    private void createFiles() {
        final YMLConfigFactory configFactory = new YMLConfigFactory(main);
        for (String fileName : new String[] {"config", "data"}) {
            final YMLConfig ymlConfig = configFactory.createConfig(main.getDataFolder(), fileName);
            this.filesMap.put(fileName, ymlConfig);
        }
    }

    public YMLConfig getFile(final String fileName) {
        return this.filesMap.getOrDefault(fileName, null);
    }

    public void reloadAll(final List<String> files) {
        for (String file : files) {
            this.getFile(file).reload();
        }
    }
}
