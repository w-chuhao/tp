package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents an accessory item in the inventory.
 */
public class Accessories extends Item {
    private boolean isFragile;

    /**
     * Creates an accessories item with the given details.
     *
     * @param name Name of the accessory item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isFragile Whether the accessory is fragile.
     */
    public Accessories(String name, int quantity, String binLocation,
                       String expiryDate, boolean isFragile) {
        super(name, quantity, binLocation, expiryDate);
        this.isFragile = isFragile;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean isFragile) {
        this.isFragile = isFragile;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isFragile/" + isFragile;
    }

    @Override
    public String toString() {
        return "[Accessories] " + super.toString()
                + ", Fragile: " + isFragile;
    }
}
