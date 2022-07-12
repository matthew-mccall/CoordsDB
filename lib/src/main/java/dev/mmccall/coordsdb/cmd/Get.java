package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.GameEvent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.Coord;
import dev.mmccall.coordsdb.DB;

public class Get extends Command {

    public Get() {
        super("get");

        setDescription("Gets the coordinate location with the specified label");
        setUsage("/coords:get <label>");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        String username;
        String label;
        Coord location;

        username = sender.getName();

        if (args.length < 1) {
            sender.sendMessage("[CoordsDB] label not specified! Usage: " + getUsage());
            return false;
        }

        label = args[0];
        location = DB.getEntry(username, label);

        if (location == null) {
            sender.sendMessage(
                    MessageFormat.format("[CoordsDB] Coordinate labelled {0}:{1} does not exist!", username, label));
            return false;
        }

        sender.sendMessage(MessageFormat.format("{0}:{1} [{2,number}, {3,number}, {4,number}]",
                username, label, location.getX(), location.getY(), location.getZ()));

        return true;
    }

}
