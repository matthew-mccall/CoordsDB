package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.Coord;
import dev.mmccall.coordsdb.DB;

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
        String username;
        String label;

        username = sender.getName();

        if (args.length < 1) {
            sender.sendMessage("[CoordsDB] label not specified! Usage: " + getUsage());
            return false;
        }

        label = args[0];

        if (DB.delEntry(username, label)) {
            sender.sendMessage(MessageFormat.format("[CoordsDB] Deleted {0}:{1}.", username, label));
        } else {
            sender.sendMessage(
                    MessageFormat.format("[CoordsDB] Coordinate labelled {0}:{1} does not exist!", username, label));
        }

        return true;
    }

}
