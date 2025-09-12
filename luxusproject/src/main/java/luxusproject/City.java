package luxusproject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class City {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;

    private int width;
    private int height;
    private List<Item> items;

    public City(int width, int height) {
        if (width < 1) {
            throw new IllegalArgumentException(
                    "Width must be positive: "
                    + width);
        }
        if (height < 1) {
            throw new IllegalArgumentException(
                    "Height must be positive: "
                    + height);
        }
        this.width = width;
        this.height = height;
        items = new LinkedList<Item>();
    }

    public City() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public List<Item> getItems() {
    return Collections.unmodifiableList(items);
}

    public void addItem(Item item) {
        if (items.contains(item)) {
            throw new IllegalArgumentException(
                    item + " already recorded in the city.");
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        if (!items.remove(item)) {
            throw new IllegalArgumentException(
                    item + " is not in the city.");
        }
    }

    public String toString() {
        return "City size " + width + " by " + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
