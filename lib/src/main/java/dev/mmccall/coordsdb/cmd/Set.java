package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.Entry;

public class Set extends Command {

    public Set() {
        super("set");

        setDescription("Sets and stores your current coordinate location with an optional label");
        setUsage("/coords:set <label>");

        ArrayList<String> aliases = new ArrayList<String>();
        aliases.add("add");

        setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        String username;
        String label;
        Location location;
        Entry entry;

        username = sender.getName();

        if (args.length > 0) {
            label = args[0];
        } else {
            label = String.valueOf(DB.getEntries(username).size());
        }

        entry = new Entry(username, label);
        location = sender.getServer().getPlayer(username).getLocation();

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
