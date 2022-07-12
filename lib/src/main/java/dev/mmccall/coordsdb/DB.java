package dev.mmccall.coordsdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.bukkit.Location;

public class DB {
    private static HashMap<String, HashMap<String, Coord>> db;

    public static HashMap<String, Coord> getEntries(String username) {
        HashMap<String, Coord> userTable = db.get(username);

        if (userTable == null) {
            db.put(username, new HashMap<String, Coord>());
        }

        return db.get(username);
    }

    public static Coord getEntry(String username, String label) {
        return getEntries(username).get(label);
    }

    public static boolean setEntry(String username, String label, Location location) {
        getEntries(username).put(label, new Coord(location));
        return save();
    }

    public static boolean delEntry(String username, String label) {
        if (getEntries(username).remove(label) != null) {
            return save();
        }

        return false;
    }

    public static void load() {
        try {
            File descriptor = new File("coords.db");
            descriptor.createNewFile();

            FileInputStream file = new FileInputStream(descriptor);
            ObjectInputStream in = new ObjectInputStream(file);

            db = (HashMap<String, HashMap<String, Coord>>) in.readObject();

            if (db == null) {
                db = new HashMap<String, HashMap<String, Coord>>();
            }
        } catch (FileNotFoundException e) {
            db = new HashMap<String, HashMap<String, Coord>>();
            e.printStackTrace();
        } catch (IOException e) {
            db = new HashMap<String, HashMap<String, Coord>>();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            db = new HashMap<String, HashMap<String, Coord>>();
            e.printStackTrace();
        }
    }

    private static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("coords.db");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(db);
            out.close();
            file.close();

            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    private DB() {

    }
}
