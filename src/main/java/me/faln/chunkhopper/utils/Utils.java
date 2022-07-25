package me.faln.chunkhopper.utils;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static String colorize(final String message) {
        return message == null ? null : ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(final List<String> list) {
        return list.stream().map(Utils::colorize).collect(Collectors.toList());
    }

    public static void send(final Player player, final List<String> messageList) {
        messageList.forEach(s -> player.sendMessage(Utils.colorize(s)));
    }

    public static void send(final CommandSender sender, final List<String> messageList) {
        messageList.forEach(s -> sender.sendMessage(Utils.colorize(s)));
    }

    public static void send(final CommandSender sender, final Set<String> messageList) {
        messageList.forEach(s -> sender.sendMessage(Utils.colorize(s)));
    }

    public static String deserializeLocation(final Location location) {
        return location.getWorld() == null ? null : location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getWorld().getName();
    }

    public static String deserializeLocation(final Chunk chunk) {
        return chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ();
    }
}
