package seedu.inventorydock.command;

import seedu.inventorydock.exception.CategoryNotFoundException;
import seedu.inventorydock.exception.DuplicateItemException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.ItemNotFoundException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.category.CommonFieldParser;
import seedu.inventorydock.ui.UI;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Updates an existing item in a category using fields provided by the user.
 * An <code>UpdateItemCommand</code> object locates the target item and applies
 * supported updates such as name, bin location, quantity, and expiry date.
 */
public class UpdateItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(UpdateItemCommand.class.getName());

    private final String categoryName;
    private final int itemIndex;
    private final Map<String, String> updates;

    /**
     * Creates an update command for the specified category, item index, and fields.
     *
     * @param categoryName Name of the category containing the item to update.
     * @param itemIndex One-based index of the item within the category.
     * @param updates Fields and values to apply to the item.
     */
    public UpdateItemCommand(String categoryName, int itemIndex,
                             Map<String, String> updates) {
        assert categoryName != null : "UpdateItemCommand received null category name.";
        assert updates != null : "UpdateItemCommand received null updates map.";
        this.categoryName = categoryName;
        this.itemIndex = itemIndex;
        this.updates = updates;
    }

    /**
     * Executes the update command on the specified inventory.
     * Finds the target category and item, applies the requested updates, and
     * reports the result through the user interface.
     *
     * @param inventory Inventory containing the item to update.
     * @param ui User interface used to display update results.
     * @throws InventoryDockException If the category, item index, or update values are invalid.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws InventoryDockException {
        assert inventory != null : "UpdateItemCommand received null inventory.";
        assert ui != null : "UpdateItemCommand received null UI.";

        Category category = inventory.findCategoryByName(categoryName);
        if (category == null) {
            logger.log(Level.WARNING, "Category not found while updating item: " + categoryName);
            throw new CategoryNotFoundException("Category not found: " + categoryName);
        }

        if (itemIndex < 1 || itemIndex > category.getItemCount()) {
            logger.log(Level.WARNING, "Invalid item index while updating item: " + itemIndex);
            throw new ItemNotFoundException(
                "Item at index " + itemIndex +
                " not found in category '" + categoryName + "'.");
        }

        Item item = category.getItem(itemIndex - 1);
        String originalName = item.getName();
        String originalBin = item.getBinLocation();
        int originalQuantity = item.getQuantity();
        String originalExpiryDate = item.getExpiryDate();

        applyUpdates(item);

        Item duplicateItem = findDuplicateItem(category, item);
        if (duplicateItem != null && duplicateItem != item) {
            String attemptedName = item.getName();
            restoreOriginalValues(item, originalName, originalBin, originalQuantity, originalExpiryDate);
            logger.log(Level.WARNING, "Duplicate item detected while updating category '"
                    + category.getName() + "' and name '" + attemptedName + "'.");
            throw new DuplicateItemException("Duplicate item found for category/" + category.getName()
                    + " item/" + attemptedName + ".");
        }

        logger.log(Level.INFO, "Updated item '" + originalName
                + "' in category '" + category.getName()
                + "' to '" + item.getName() + "'.");
        ui.showItemUpdated(originalName, item.getName(), category.getName());
    }

    /**
     * Applies all requested field updates to the specified item.
     * Only supported update prefixes are accepted.
     *
     * @param item Item to be updated.
     * @throws InventoryDockException If an update field is unsupported or contains an invalid value.
     */
    private void applyUpdates(Item item) throws InventoryDockException {
        for (Map.Entry<String, String> entry : updates.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            switch (field) {
            case "newItem":
                validateNonEmpty(value, "New item name cannot be empty.");
                item.setName(value.trim());
                break;
            case "bin":
                validateNonEmpty(value, "Bin location cannot be empty.");
                item.setBinLocation(value.trim());
                break;
            case "qty":
                item.setQuantity(CommonFieldParser.parseQuantity(value));
                break;
            case "expiryDate":
                CommonFieldParser.validateExpiryDate(value);
                item.setExpiryDate(value.trim());
                break;
            default:
                throw new InvalidCommandException("Only newItem/, bin/, qty/, and expiryDate/ can be updated.");
            }
        }
    }

    /**
     * Validates that the specified value is not empty.
     * Throws an exception if the value is null or blank.
     *
     * @param value Value to validate.
     * @param message Error message to use when validation fails.
     * @throws InventoryDockException If the value is null or blank.
     */
    private void validateNonEmpty(String value,
                                  String message) throws InventoryDockException {
        if (value == null || value.trim().isEmpty()) {
            throw new MissingArgumentException(message);
        }
    }

    /**
     * Restores original item values when update validation fails after mutation.
     *
     * @param item Item to restore.
     * @param name Original name.
     * @param bin Original bin.
     * @param quantity Original quantity.
     * @param expiryDate Original expiry date.
     */
    private void restoreOriginalValues(Item item, String name, String bin,
                                       int quantity, String expiryDate) {
        item.setName(name);
        item.setBinLocation(bin);
        item.setQuantity(quantity);
        item.setExpiryDate(expiryDate);
    }

    /**
     * Finds an existing item in the category that has the same duplicate identity as the candidate item.
     * The duplicate identity ignores qty and bin, and compares the remaining stored fields.
     *
     * @param category Category to scan.
     * @param candidate Item being updated.
     * @return Matching duplicate item, or {@code null} if no duplicate exists.
     */
    private Item findDuplicateItem(Category category, Item candidate) {
        assert category != null : "Category cannot be null while checking duplicates.";
        assert candidate != null : "Candidate item cannot be null while checking duplicates.";

        String candidateIdentity = buildBatchIdentityKey(category.getName(), candidate);
        for (Item existing : category.getItems()) {
            String existingIdentity = buildBatchIdentityKey(category.getName(), existing);
            if (existingIdentity.equals(candidateIdentity)) {
                return existing;
            }
        }
        return null;
    }

    /**
     * Builds a normalized identity key for duplicate checks.
     * The key intentionally ignores qty and bin fields so that only logical batch identity is compared.
     *
     * @param category Name of the category containing the item.
     * @param target Item whose identity key is built.
     * @return Normalized identity key.
     */
    private String buildBatchIdentityKey(String category, Item target) {
        assert category != null : "Category cannot be null when building duplicate identity key.";
        assert target != null : "Item cannot be null when building duplicate identity key.";

        String storageString = target.toStorageString(category);
        StringBuilder key = new StringBuilder();

        String[] tokens = storageString.split(" ");
        for (String token : tokens) {
            if (token.startsWith("qty/") || token.startsWith("bin/")) {
                continue;
            }
            key.append(token.toLowerCase()).append(" ");
        }

        return key.toString().trim();
    }
}


