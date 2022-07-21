package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.Entry;

public class Del implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage("[CoordsDB] label not specified!");
            return false;
        }

        Optional<Entry> optionalEntry = Entry.fromString(args[0]);
        Entry entry;

        if (optionalEntry.isPresent()) {
            entry = optionalEntry.get();
        } else if (Entry.isLabel(args[0])) {
            entry = new Entry(sender.getName(), args[0]);
        } else {
            sender.sendMessage("[CoordsDB] invalid label!");
            return false;
        }

        if (DB.delEntry(entry)) {
            sender.sendMessage(MessageFormat.format("[CoordsDB] Deleted {0}.", entry));
            return true;
        }

        sender.sendMessage(
                MessageFormat.format("[CoordsDB] Coordinate labelled {0} does not exist!", entry));

        return false;

    }
}
