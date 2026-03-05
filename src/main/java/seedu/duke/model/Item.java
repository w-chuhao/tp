package seedu.duke.model;

public class Item {
    private String name;
    private int quantity;
    private String binLocation;

    protected Item(String name, int quantity, String binLocation) {
        this.name = name;
        this.quantity = quantity;
        this.binLocation = binLocation;
    }
    // getters and setters
}
