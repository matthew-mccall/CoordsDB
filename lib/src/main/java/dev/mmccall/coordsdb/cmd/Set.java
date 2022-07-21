package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.Entry;

public class Set implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel,
            @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        String username;
        String label;
        Location location;
        Entry entry;

        username = player.getName();

        if (args.length > 0) {
            if (!Entry.isLabel(args[0])) {
                sender.sendMessage("[CoordsDB] Invalid label!");
                return false;
            }
            label = args[0];
        } else {
            label = String.valueOf(DB.getEntries(username).size());
        }

        entry = new Entry(username, label);
        location = player.getLocation();

        if (DB.setEntry(new Entry(username, label), location)) {
            sender.sendMessage(MessageFormat.format(
                    "[CoordsDB] Successfully registered coordinate [{0,number}, {1,number}, {2,number}] with label {3}!",
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), entry));
            return true;
        } else {
            sender.sendMessage(MessageFormat.format(
                    "[CoordsDB] Failed to register coordinate [{0,number}, {1,number}, {2,number}] with label {3}!",
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), entry));
            return false;
        }

    }

}
