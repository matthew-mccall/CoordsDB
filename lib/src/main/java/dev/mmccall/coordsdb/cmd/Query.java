package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.Coord;
import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.DBItem;
import dev.mmccall.coordsdb.Entry;

public class Query implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        Entry entry;

        if (args.length > 0) {
            Optional<Entry> optionalEntry = Entry.fromString(args[0]);
            if (optionalEntry.isPresent()) {
                entry = optionalEntry.get();
            } else if (Entry.isLabel(args[0])) {
                entry = new Entry(sender.getName(), args[0]);
            } else {
                sender.sendMessage("[CoordsDB] Bad label or entry specified!");
                return false;
            }
        } else {
            entry = new Entry(sender.getName(), "*");
        }

        ArrayList<DBItem> results = DB.queryEntries(entry);

        if ((results == null) || results.isEmpty()) {
            sender.sendMessage(MessageFormat.format("[CoordsDB] Coordinates matching {0} does not exist!", entry));
            return false;
        }

        results.forEach(result -> {
            Coord coord = result.getCoord();

            sender.sendMessage(
                    MessageFormat.format("{0} [{1,number}, {2,number}, {3,number}]", result.getEntry(), coord.getX(),
                            coord.getY(), coord.getZ()));
        });

        return true;

    }

}