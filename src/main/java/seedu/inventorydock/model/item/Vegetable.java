package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a vegetable item in the inventory.
 */
public class Vegetable extends Item {
    private boolean isLeafy;

    /**
     * Creates a vegetable item with the given details.
     *
     * @param name Name of the vegetable.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isLeafy Whether the vegetable is leafy.
     */
    public Vegetable(String name, int quantity, String binLocation,
                     String expiryDate, boolean isLeafy) {
        super(name, quantity, binLocation, expiryDate);
        this.isLeafy = isLeafy;
    }

    /** @return {@code true} if the vegetable is leafy. */
    public boolean isLeafy() {
        return isLeafy;
    }

    /** @param isLeafy New leafy status. */
    public void setLeafy(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isLeafy/" + isLeafy;
    }

    @Override
    public String toString() {
        return "[Vegetable] " + super.toString()
                + ", Leafy: " + isLeafy;
    }
}
