package me.faln.chunkhopper.cache;

import lombok.Getter;
import me.faln.chunkhopper.ChunkHopper;
import me.faln.chunkhopper.objects.CHopper;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class HopperCache {

    private final Map<String, CHopper> locations = new HashMap<>();

    private final ChunkHopper plugin;

    public HopperCache(final ChunkHopper plugin) {
        this.plugin = plugin;
        this.cache();
    }

    private void cache() {

        this.locations.clear();

        final ConfigurationSection section = plugin.getFiles().getFile("data").section("hoppers");
        if (section == null) return;

        for (final String id : section.getKeys(false)) {
            final String location = section.getString(id + ".location");
            final ConfigurationSection itemSection = section.getConfigurationSection(id + ".items");

            CHopper hopper = new CHopper(UUID.fromString(id));

            if (itemSection != null) {
                Map<Material, BigInteger> items = new HashMap<>();
                for (final String material : itemSection.getKeys(false)) {
                    final BigInteger amount = BigInteger.valueOf(itemSection.getInt(material));
                    items.put(Material.getMaterial(material), amount);
                }
                hopper.setStoredItems(items);
            }

            this.locations.put(location, hopper);
        }
    }

    public boolean contains(final String location) {
        return this.locations.containsKey(location);
    }

    public void add(final String location, final CHopper hopper) {
        this.locations.put(location, hopper);
    }

    public void remove(final String location) {
        this.locations.remove(location);
    }

    public CHopper getHopper(final String location) {
        return this.locations.get(location);
    }

    public void save() {
        //TODO: save locations map data to data.yml async; call on onDisable
    }

}
