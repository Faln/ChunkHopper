package me.faln.chunkhopper;

import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import me.faln.chunkhopper.cache.ConfigCache;
import me.faln.chunkhopper.cache.HopperCache;
import me.faln.chunkhopper.commands.HopperCmds;
import me.faln.chunkhopper.listeners.HopperListeners;
import me.faln.chunkhopper.managers.FilesManager;
import me.faln.chunkhopper.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ChunkHopper extends JavaPlugin {

    private FilesManager files;
    private ConfigCache configCache;
    private HopperCache hopperCache;
    private BukkitCommandManager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.reload();
    }

    public void reload() {
        this.files = new FilesManager(this);
        this.configCache = new ConfigCache(this);
        this.hopperCache = new HopperCache(this);

        new HopperListeners(this);

        this.manager = new BukkitCommandManager(this);
        this.manager.registerCommand(new HopperCmds(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void log(final String message) {
        this.getServer().getLogger().info(Utils.colorize(message));
    }

}
