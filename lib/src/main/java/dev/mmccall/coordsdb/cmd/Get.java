package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.Coord;
import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.Entry;

public class Get extends Command {

    public Get() {
        super("get");

        setDescription("Gets the coordinate location with the specified label");
        setUsage("/coords:get <label>");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Coord location;

        if (args.length < 1) {
            sender.sendMessage("[CoordsDB] label not specified! Usage: " + getUsage());
            return false;
        }

        Optional<Entry> optionalEntry = Entry.fromString(args[0]);
        Entry entry;

        if (optionalEntry.isPresent()) {
            entry = optionalEntry.get();
        } else if (Entry.isUsernameOrLabel(args[0])) {
            entry = new Entry(sender.getName(), args[0]);
        } else {
            sender.sendMessage("[CoordsDB] invalid label! Usage: " + getUsage());
            return false;
        }

        location = DB.getEntry(entry);

        if (location == null) {
            sender.sendMessage(MessageFormat.format("[CoordsDB] Coordinate labelled {0} does not exist!", entry));
            return false;
        }

        sender.sendMessage(MessageFormat.format("{0} [{1,number}, {2,number}, {3,number}]", entry, location.getX(),
                location.getY(), location.getZ()));

        return true;
    }

}
