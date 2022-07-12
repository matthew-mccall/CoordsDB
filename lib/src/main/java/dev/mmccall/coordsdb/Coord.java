package dev.mmccall.coordsdb;

import java.io.Serializable;

import org.bukkit.Location;

public class Coord implements Serializable {

    public Coord(Location location) {
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    int x, y, z;
}
