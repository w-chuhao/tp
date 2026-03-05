package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Fruit extends Item {
    String expiryDate;
    String size;
    boolean isRipe;

    public Fruit(String name, int quantity, String binLocation, String expiryDate, String size, boolean isRipe) {
        super(name, quantity, binLocation);
        this.expiryDate = expiryDate;
        this.size = size;
        this.isRipe = isRipe;
    }
}
