package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents an accessory item in the inventory.
 */
public class Accessories extends Item {
    private String type;
    private String material;
    private boolean isFragile;

    /**
     * Creates an accessories item with the given details.
     *
     * @param name Name of the accessory item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param type Type of accessory.
     * @param material Material of the accessory.
     * @param isFragile Whether the accessory is fragile.
     */
    public Accessories(String name, int quantity, String binLocation,
                       String expiryDate, String type, String material, boolean isFragile) {
        super(name, quantity, binLocation, expiryDate);
        this.type = type;
        this.material = material;
        this.isFragile = isFragile;
    }

    /** @return Type of the accessory. */
    public String getType() {
        return type;
    }

    /** @param type New accessory type. */
    public void setType(String type) {
        this.type = type;
    }

    /** @return Material of the accessory. */
    public String getMaterial() {
        return material;
    }

    /** @param material New accessory material. */
    public void setMaterial(String material) {
        this.material = material;
    }

    /** @return {@code true} if the accessory is fragile. */
    public boolean isFragile() {
        return isFragile;
    }

    /** @param isFragile New accessory fragility status. */
    public void setFragile(boolean isFragile) {
        this.isFragile = isFragile;
    }

    /**
     * Converts this accessory item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " type/" + type
                + " material/" + material
                + " isFragile/" + isFragile;
    }

    /**
     * Returns a string representation of this accessory item.
     *
     * @return Formatted accessory details.
     */
    @Override
    public String toString() {
        return "[Accessories] " + super.toString()
                + ", Type: " + type
                + ", Material: " + material
                + ", Fragile: " + isFragile;
    }
}
