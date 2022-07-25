package me.faln.chunkhopper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.faln.chunkhopper.ChunkHopper;
import me.faln.chunkhopper.objects.CHopper;
import me.faln.chunkhopper.objects.Item;
import me.faln.chunkhopper.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;

@CommandAlias("chunkhopper|chopper")
public class HopperCmds extends BaseCommand {

    private final ChunkHopper plugin;

    public HopperCmds(final ChunkHopper plugin) {
        this.plugin = plugin;
    }

    @Subcommand("cache")
    @Syntax("cache")
    public void cacheCommand(final CommandSender sender) {
        if (this.plugin.getHopperCache().getLocations().isEmpty()) {
            Utils.send(sender, Collections.singleton("&cNo Cached Hoppers"));
        }

        for (final CHopper hopper : this.plugin.getHopperCache().getLocations().values()) {
            for (Map.Entry<Material, BigInteger> entry : hopper.getStoredItems().entrySet()) {
                Utils.send(sender, Collections.singleton("Material: " + entry.getKey().toString()+ " Amount: " + entry.getValue().intValue()));
            }
        }

    }

    //lazy item creation
    @Subcommand("get")
    @Syntax("get")
    public void getCommand(final Player player) {
        NBTItem nbtItem = new NBTItem(new Item(Material.HOPPER).setName("&aChunkHopper").build());
        nbtItem.setString("ChunkHopper", "true");
        player.getInventory().addItem(nbtItem.getItem());
    }
}
