package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a toiletries item in the inventory.
 */
public class Toiletries extends Item {
    private boolean isLiquid;

    /**
     * Creates a toiletries item with the given details.
     *
     * @param name Name of the toiletries item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isLiquid Whether the item is liquid.
     */
    public Toiletries(String name, int quantity, String binLocation, String expiryDate,
                      boolean isLiquid) {
        super(name, quantity, binLocation, expiryDate);
        this.isLiquid = isLiquid;
    }

    /** @return {@code true} if the item is liquid. */
    public boolean isLiquid() {
        return isLiquid;
    }

    /** @param isLiquid New liquid status. */
    public void setLiquid(boolean isLiquid) {
        this.isLiquid = isLiquid;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isLiquid/" + isLiquid;
    }

    @Override
    public String toString() {
        return "[Toiletries] " + super.toString()
                + ", Liquid: " + isLiquid;
    }
}
