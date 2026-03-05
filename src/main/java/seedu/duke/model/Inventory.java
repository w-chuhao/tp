package seedu.duke.model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Category> inventory;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addCategories(Category category) {
        inventory.add(category);
    }

    // print inventory
}
