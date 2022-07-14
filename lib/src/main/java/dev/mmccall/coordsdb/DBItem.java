package dev.mmccall.coordsdb;

public class DBItem {
    Entry entry;
    Coord coord;

    public DBItem(Entry entry, Coord coord) {
        this.entry = entry;
        this.coord = coord;
    }

    public Entry getEntry() {
        return entry;
    }

    public Coord getCoord() {
        return coord;
    }
}
