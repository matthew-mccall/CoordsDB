package dev.mmccall.coordsdb;

import java.io.EOFException;
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
    private static HashTable<String, String, Coord> db;
    private static final String SAVEFILE = "coords.db";

    public static HashMap<String, Coord> getEntries(String username) {
        return db.getOrCreateCol(username);
    }

    public static Coord getEntry(Entry entry) {
        return db.get(entry.getUsername(), entry.getLabel());
    }

    public static boolean setEntry(Entry entry, Location location) {
        db.set(entry.getUsername(), entry.getLabel(), new Coord(location));
        return save();
    }

    public static boolean delEntry(Entry entry) {
        if (getEntries(entry.getUsername()).remove(entry.getLabel()) != null) {
            return save();
        }

        return false;
    }

    public static void load() {
        try {
            File descriptor = new File(SAVEFILE);

            if (!descriptor.createNewFile() || (descriptor.length() == 0)) {
                FileInputStream file = new FileInputStream(descriptor);
                ObjectInputStream in = new ObjectInputStream(file);

                db = (HashTable<String, String, Coord>) in.readObject();
                in.close();
            }

            if (db == null) {
                db = new HashTable<String, String, Coord>();
            }

        } catch (FileNotFoundException e) {
            db = new HashTable<String, String, Coord>();
            e.printStackTrace();
        } catch (IOException e) {
            db = new HashTable<String, String, Coord>();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            db = new HashTable<String, String, Coord>();
            e.printStackTrace();
        }
    }

    private static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream(SAVEFILE);
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