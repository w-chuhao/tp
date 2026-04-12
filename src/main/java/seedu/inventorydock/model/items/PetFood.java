package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a pet food item in the inventory.
 */
public class PetFood extends Item {
    private String petType;
    private String brand;
    private boolean isDry;

    /**
     * Creates a pet food item with the given details.
     *
     * @param name Name of the pet food item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param petType Type of pet the food is for.
     * @param brand Brand of the pet food.
     * @param isDry Whether the pet food is dry.
     */
    public PetFood(String name, int quantity, String binLocation,
                   String expiryDate, String petType, String brand, boolean isDry) {
        super(name, quantity, binLocation, expiryDate);
        this.petType = petType;
        this.brand = brand;
        this.isDry = isDry;
    }

    /** @return Type of pet the food is for. */
    public String getPetType() {
        return petType;
    }

    /** @param petType New pet type. */
    public void setPetType(String petType) {
        this.petType = petType;
    }

    /** @return Brand of the pet food. */
    public String getBrand() {
        return brand;
    }

    /** @param brand New pet food brand. */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /** @return {@code true} if the pet food is dry. */
    public boolean isDry() {
        return isDry;
    }

    /** @param isDry New pet food dryness status. */
    public void setDry(boolean isDry) {
        this.isDry = isDry;
    }

    /**
     * Converts this pet food item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " petType/" + petType
                + " brand/" + brand
                + " isDry/" + isDry;
    }

    /**
     * Returns a string representation of this pet food item.
     *
     * @return Formatted pet food details.
     */
    @Override
    public String toString() {
        return "[PetFood] " + super.toString()
                + ", Pet Type: " + petType
                + ", Brand: " + brand
                + ", Dry: " + isDry;
    }
}
