package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a meat item in the inventory.
 */
public class Meat extends Item {
    private String meatType;
    private boolean isFrozen;

    /**
     * Creates a meat item with the given details.
     *
     * @param name Name of the meat item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param meatType Type of meat.
     * @param isFrozen Whether the meat is frozen.
     */
    public Meat(String name, int quantity, String binLocation,
                String expiryDate, String meatType, boolean isFrozen) {
        super(name, quantity, binLocation, expiryDate);
        this.meatType = meatType;
        this.isFrozen = isFrozen;
    }

    /** @return Type of the meat. */
    public String getMeatType() {
        return meatType;
    }

    /** @param meatType New meat type. */
    public void setMeatType(String meatType) {
        this.meatType = meatType;
    }

    /** @return {@code true} if the meat is frozen. */
    public boolean isFrozen() {
        return isFrozen;
    }

    /** @param isFrozen New frozen status. */
    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * Converts this meat item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " meatType/" + meatType
                + " isFrozen/" + isFrozen;
    }

    /**
     * Returns a string representation of this meat item.
     *
     * @return Formatted meat details.
     */
    @Override
    public String toString() {
        return "[Meat] " + super.toString()
                + ", Meat Type: " + meatType
                + ", Frozen: " + isFrozen;
    }
}
