package seedu.duke.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Item> items;

    public Category(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
