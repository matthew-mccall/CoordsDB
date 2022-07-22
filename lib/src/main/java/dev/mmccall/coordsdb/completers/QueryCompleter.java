package dev.mmccall.coordsdb.completers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.mmccall.coordsdb.DB;
import dev.mmccall.coordsdb.DBItem;
import dev.mmccall.coordsdb.Entry;

public class QueryCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return null;
        }

        if (args.length < 1) {
            return null;
        }

        ArrayList<String> completions = new ArrayList<String>();
        Optional<Entry> optionalEntry = Entry.fromString(args[0]);
        Entry entry;

        if (optionalEntry.isPresent()) {
            entry = optionalEntry.get();
        } else if (Entry.isLabel(args[0])) {
            entry = new Entry(sender.getName(), args[0]);
        } else {
            return null;
        }

        if (entry.getLabel().indexOf("*") >= 0) {
            return null;
        }

        entry.getLabel().concat("*");
        ArrayList<DBItem> results = DB.queryEntries(entry);

        if ((results == null) || results.isEmpty()) {
            return null;
        }

        results.forEach(result -> {
            completions.add(result.getEntry().toString());
        });

        completions.add((new Entry(sender.getName(), args[0] + "*")).toString());

        return completions;
    }

}
