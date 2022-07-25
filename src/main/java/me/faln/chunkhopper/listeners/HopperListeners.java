package me.faln.chunkhopper.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import me.faln.chunkhopper.ChunkHopper;
import me.faln.chunkhopper.objects.CHopper;
import me.faln.chunkhopper.utils.Utils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HopperListeners implements Listener {

    private final ChunkHopper plugin;

    public HopperListeners(final ChunkHopper plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHopperPlace(final BlockPlaceEvent event) {

        if (event.isCancelled() || event.getBlockPlaced().getType() != Material.HOPPER) {
            return;
        }

        final Block block = event.getBlock();
        final Player player = event.getPlayer();

        if (block.getLocation().getWorld() == null) return;

        final String location = Utils.deserializeLocation(block.getLocation().getChunk());
        final NBTItem item = new NBTItem(event.getItemInHand());
        NBTTileEntity tileEntity = new NBTTileEntity(block.getState());

        if (!item.hasKey("ChunkHopper")) {
            return;
        }

        if (this.plugin.getHopperCache().contains(location)
                || this.plugin.getConfigCache().getBlacklistedWorlds().contains(block.getLocation().getWorld().getName())) {
            event.setCancelled(true);
            player.sendMessage(Utils.colorize("&cAlready a ChunkHopper in chunk: " + location));
            return;
        }

        tileEntity.setString("ChunkHopper", "true");
        final UUID id = UUID.randomUUID();
        this.plugin.getHopperCache().add(location, new CHopper(id));

        player.sendMessage(Utils.colorize("&aAdded new ChunkHopper with ID: " + id));

    }

    //There is many more block breaking/altering events but for simplicity’s sake, we only check BlockBreakEvent
    @EventHandler
    public void onHopperBreak(final BlockBreakEvent event) {

        final Block block = event.getBlock();

        if (event.isCancelled() || block.getType() != Material.HOPPER) {
            return;
        }

        final Chunk chunk = block.getLocation().getChunk();
        final String location = Utils.deserializeLocation(chunk);
        final NBTTileEntity tileEntity = new NBTTileEntity(block.getState());

        if (!tileEntity.hasKey("ChunkHopper") || !this.plugin.getHopperCache().contains(location)) {
            return;
        }

        this.plugin.getHopperCache().remove(location);
        event.getPlayer().sendMessage(Utils.colorize("&cBroken ChunkHopper at: " + location));
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {

        final String location = Utils.deserializeLocation(event.getEntity().getLocation().getChunk());

        if (!this.plugin.getHopperCache().contains(location)) {
            return;
        }

        List<ItemStack> drops = event.getDrops();

        if (drops.isEmpty()) return;

        final Set<Material> blacklistedMaterials = this.plugin.getConfigCache().getBlacklistedMaterials();

        for (final ItemStack item : drops) {
            if (blacklistedMaterials.contains(item.getType())) continue;
            this.plugin.getHopperCache().getHopper(location).addItem(item.getType(), BigInteger.valueOf(item.getAmount()));
            drops.remove(item);
        }
    }
}
