package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a meat item in the inventory.
 */
public class Meat extends Item {
    private boolean isFrozen;

    /**
     * Creates a meat item with the given details.
     *
     * @param name Name of the meat item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isFrozen Whether the meat is frozen.
     */
    public Meat(String name, int quantity, String binLocation,
                String expiryDate, boolean isFrozen) {
        super(name, quantity, binLocation, expiryDate);
        this.isFrozen = isFrozen;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isFrozen/" + isFrozen;
    }

    @Override
    public String toString() {
        return "[Meat] " + super.toString()
                + ", Frozen: " + isFrozen;
    }
}
