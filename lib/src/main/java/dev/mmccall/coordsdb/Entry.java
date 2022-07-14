package dev.mmccall.coordsdb;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entry {

    public Entry(String username, String label) {
        this.username = username;
        this.label = label;
    }

    public static Optional<Entry> fromString(String entry) {
        Pattern usernamePattern = Pattern.compile("[(\\w{3,16})]|[\\w{1,16}\\*?]:(\\w{0,16})\\*?");
        Matcher usernameMatch = usernamePattern.matcher(entry);

        if (usernameMatch.matches()) {
            return Optional.of(new Entry(usernameMatch.group(1), usernameMatch.group(2)));
        }

        return Optional.empty();
    }

    public static boolean isUsername(String entry) {
        return Pattern.compile("\\w{3,16}\\*?").matcher(entry).matches();
    }

    public static boolean isLabel(String entry) {
        return Pattern.compile("\\w{0,16}\\*?").matcher(entry).matches();
    }

    @Override
    public java.lang.String toString() {
        return username + ":" + label;
    }

    public String getUsername() {
        return username;
    }

    public String getLabel() {
        return label;
    }

    String username;
    String label;
}
