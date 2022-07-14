package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.Entry;

public class Del extends Command {

    public Del() {
        super("del");

        setDescription("Deletes the coordinate location with the specified label");
        setUsage("/coords:del <label>");

        ArrayList<String> aliases = new ArrayList<String>();
        aliases.add("rm");

        setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("[CoordsDB] label not specified! Usage: " + getUsage());
            return false;
        }

        Optional<Entry> optionalEntry = Entry.fromString(args[0]);
        Entry entry;

        if (optionalEntry.isPresent()) {
            entry = optionalEntry.get();
        } else if (Entry.isLabel(args[0])) {
            entry = new Entry(sender.getName(), args[0]);
        } else {
            sender.sendMessage("[CoordsDB] invalid label! Usage: " + getUsage());
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
