package me.faln.chunkhopper.cache;

import lombok.Getter;
import me.faln.chunkhopper.ChunkHopper;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ConfigCache {

    private final ChunkHopper plugin;

    private List<String> blacklistedWorlds;
    private Set<Material> blacklistedMaterials;

    public ConfigCache(final ChunkHopper plugin) {
        this.plugin = plugin;
        this.cache();
    }

    public void cache() {
        final ConfigurationSection section = this.plugin.getFiles().getFile("config").section("");
        if (section == null) return;

        this.blacklistedWorlds = section.getStringList("blacklisted-worlds");
        this.blacklistedMaterials = section.getStringList("blacklisted-materials").stream().map(Material::getMaterial).collect(Collectors.toSet());
    }
}
