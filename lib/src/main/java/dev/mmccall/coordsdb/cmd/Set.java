package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;

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

        username = sender.getName();

        if (args.length > 0) {
            label = args[0];
        } else {
            label = String.valueOf(DB.getEntries(username).size());
        }

        location = sender.getServer().getPlayer(username).getLocation();

        if (DB.setEntry(username, label, location)) {
            sender.sendMessage(MessageFormat.format(
                    "[CoordsDB] Successfully registered coordinate [{0,number}, {1,number}, {2,number}] with label {3}:{4}!",
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), username, label));
            return true;
        } else {
            sender.sendMessage(MessageFormat.format(
                    "[CoordsDB] Failed to register coordinate [{0,number}, {1,number}, {2,number}] with label {3}:{4}!",
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), username, label));
            return false;
        }
    }

}
