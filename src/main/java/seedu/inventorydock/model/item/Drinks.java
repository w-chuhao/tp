package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a drink item in the inventory.
 */
public class Drinks extends Item {
    private boolean isCarbonated;

    /**
     * Creates a drink item with the given details.
     *
     * @param name Name of the drink.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isCarbonated Whether the drink is carbonated.
     */
    public Drinks(String name, int quantity, String binLocation, String expiryDate,
                  boolean isCarbonated) {
        super(name, quantity, binLocation, expiryDate);
        this.isCarbonated = isCarbonated;
    }

    public boolean isCarbonated() {
        return isCarbonated;
    }

    public void setCarbonated(boolean isCarbonated) {
        this.isCarbonated = isCarbonated;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isCarbonated/" + isCarbonated;
    }

    @Override
    public String toString() {
        return "[Drinks] " + super.toString()
                + ", Carbonated: " + isCarbonated;
    }
}
