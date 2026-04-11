package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a vegetable item in the inventory.
 */
public class Vegetable extends Item {
    private boolean isLeafy;
    private String origin;

    public Vegetable(String name, int quantity, String binLocation,
                     String expiryDate, boolean isLeafy, String origin) {
        super(name, quantity, binLocation, expiryDate);
        this.isLeafy = isLeafy;
        this.origin = origin;
    }

    public boolean isLeafy() {
        return isLeafy;
    }

    public void setLeafy(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isLeafy/" + isLeafy
                + " origin/" + origin;
    }

    @Override
    public String toString() {
        return "[Vegetable] " + super.toString()
                + ", Leafy: " + isLeafy
                + ", Origin: " + origin;
    }
}
