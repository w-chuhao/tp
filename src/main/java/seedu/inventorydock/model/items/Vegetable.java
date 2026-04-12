package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a vegetable item in the inventory.
 */
public class Vegetable extends Item {
    private boolean isLeafy;
    private String origin;

    /**
     * Creates a vegetable item with the given details.
     *
     * @param name Name of the vegetable.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param origin Origin of the vegetable.
     * @param isLeafy Whether the vegetable is leafy.
     */
    public Vegetable(String name, int quantity, String binLocation,
                     String expiryDate, String origin, boolean isLeafy) {
        super(name, quantity, binLocation, expiryDate);
        this.isLeafy = isLeafy;
        this.origin = origin;
    }

    /** @return {@code true} if the vegetable is leafy. */
    public boolean isLeafy() {
        return isLeafy;
    }

    /** @param isLeafy New leafy status. */
    public void setLeafy(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    /** @return Origin of the vegetable. */
    public String getOrigin() {
        return origin;
    }

    /** @param origin New vegetable origin. */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Converts this vegetable item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " origin/" + origin
                + " isLeafy/" + isLeafy;
    }

    /**
     * Returns a string representation of this vegetable item.
     *
     * @return Formatted vegetable details.
     */
    @Override
    public String toString() {
        return "[Vegetable] " + super.toString()
                + ", Leafy: " + isLeafy
                + ", Origin: " + origin;
    }
}
