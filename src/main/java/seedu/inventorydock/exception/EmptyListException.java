package seedu.inventorydock.exception;

/**
 * Represents an error when an operation requires items but the target list is empty.
 */
public class EmptyListException extends InventoryDockException {

    /**
     * Constructs an EmptyListException specifying which list is empty.
     *
     * @param listType A description of the empty list (e.g., "inventory list" or "category list").
     */
    public EmptyListException(String listType) {
        super("The " + listType + " is empty. Please add items first.");
    }
}
