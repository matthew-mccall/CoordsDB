package dev.mmccall.coordsdb.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.mmccall.coordsdb.DB;

public class List extends Command {

    public List() {
        super("list");

        setDescription("Lists all your current coordinate locations");
        setUsage("/coords:list");

        ArrayList<String> aliases = new ArrayList<String>();
        aliases.add("show");

        setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        String username = sender.getName();

        DB.getEntries(username).forEach((label, coord) -> {
            sender.sendMessage(MessageFormat.format(
                    "{0}:{1} [{2,number}, {3,number}, {4,number}]",
                    username, label, coord.getX(), coord.getY(), coord.getZ()));
        });

        return true;
    }

}
