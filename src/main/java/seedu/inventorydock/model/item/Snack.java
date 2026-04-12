package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a snack item in the inventory.
 */
public class Snack extends Item {
    private boolean isCrunchy;

    /**
     * Creates a snack item with the given details.
     *
     * @param name Name of the snack.
     * @param quantity Quantity of the snack.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isCrunchy whether the snack is crunchy.
     */
    public Snack(String name, int quantity, String binLocation, String expiryDate,
                 boolean isCrunchy) {
        super(name, quantity, binLocation, expiryDate);
        this.isCrunchy = isCrunchy;
    }

    public boolean isCrunchy() {
        return isCrunchy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isCrunchy/" + isCrunchy;
    }

    @Override
    public String toString() {
        return "[Snack] " + super.toString()
                + ", Crunchy: " + isCrunchy;
    }
}
