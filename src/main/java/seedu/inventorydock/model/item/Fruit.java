package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a fruit item in the inventory.
 */
public class Fruit extends Item {
    private boolean isRipe;

    /**
     * Creates a fruit item with the given details.
     *
     * @param name Name of the fruit.
     * @param quantity Quantity of the fruit.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isRipe Whether the fruit is ripe.
     */
    public Fruit(String name, int quantity, String binLocation,
                 String expiryDate, boolean isRipe) {
        super(name, quantity, binLocation, expiryDate);
        this.isRipe = isRipe;
    }

    /** @return {@code true} if the fruit is ripe. */
    public boolean isRipe() {
        return isRipe;
    }

    /** @param isRipe New ripe status. */
    public void setRipe(boolean isRipe) {
        this.isRipe = isRipe;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isRipe/" + isRipe;
    }

    @Override
    public String toString() {
        return "[Fruit] " + super.toString()
                + ", Ripe: " + isRipe;
    }
}
