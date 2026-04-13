package seedu.inventorydock.command;

import seedu.inventorydock.exception.InvalidDateException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.DateParser;
import seedu.inventorydock.ui.UI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Displays a category-based summary of the inventory.
 */
public class SummaryCommand extends Command {
    private static final Logger logger = Logger.getLogger(SummaryCommand.class.getName());

    /**
     * Represents the supported summary display modes.
     */
    public enum SummaryType {
        ALL,
        STOCK,
        EXPIRYDATE
    }

    private final SummaryType summaryType;

    /**
     * Creates a summary command with the specified display mode.
     *
     * @param summaryType Type of summary to display.
     */
    public SummaryCommand(SummaryType summaryType) {
        this.summaryType = summaryType;
    }

    /**
     * Executes the summary command on the specified inventory. Displays a category-based summary through the UI.
     *
     * @param inventory Inventory to summarise.
     * @param ui User interface used to display the summary.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "SummaryCommand received null inventory.";
        assert ui != null : "SummaryCommand received null UI.";
        assert summaryType != null : "SummaryCommand received null summary type.";

        logger.log(Level.INFO, "Displaying inventory summary: " + summaryType);
        ui.showInventorySummary(buildCategorySummaries(inventory), summaryType);
    }

    /**
     * Builds summary data for all categories in the inventory.
     *
     * @param inventory Inventory containing the categories to summarise.
     * @return List of category summaries in inventory order.
     */
    private List<CategorySummary> buildCategorySummaries(Inventory inventory) {
        List<CategorySummary> summaries = new ArrayList<>();

        for (Category category : inventory.getCategories()) {
            List<IndexedItem> lowestStockItems = summaryType == SummaryType.EXPIRYDATE
                    ? new ArrayList<>()
                    : getLowestStockItems(category);

            List<IndexedItem> earliestExpiryItems = summaryType == SummaryType.STOCK
                    ? new ArrayList<>()
                    : getEarliestExpiryItems(category);

            summaries.add(new CategorySummary(
                    category.getName(),
                    category.getItemCount(),
                    lowestStockItems,
                    earliestExpiryItems
            ));
        }

        return summaries;
    }

    /**
     * Returns all items in the category tied for the lowest quantity.
     *
     * @param category Category to inspect.
     * @return List of indexed items with the lowest quantity.
     */
    private List<IndexedItem> getLowestStockItems(Category category) {
        List<IndexedItem> lowestStockItems = new ArrayList<>();
        List<Item> items = category.getItems();

        if (items.isEmpty()) {
            return lowestStockItems;
        }

        int lowestQuantity = Integer.MAX_VALUE;
        for (Item item : items) {
            lowestQuantity = Math.min(lowestQuantity, item.getQuantity());
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getQuantity() == lowestQuantity) {
                lowestStockItems.add(new IndexedItem(i + 1, item));
            }
        }

        return lowestStockItems;
    }

    /**
     * Returns all items in the category tied for the earliest valid expiry date.
     *
     * @param category Category to inspect.
     * @return List of indexed items with the earliest expiry date.
     */
    private List<IndexedItem> getEarliestExpiryItems(Category category) {
        List<IndexedItem> earliestExpiryItems = new ArrayList<>();
        List<Item> items = category.getItems();

        LocalDate earliestDate = null;
        for (Item item : items) {
            LocalDate parsedDate = parseExpiryDate(item.getExpiryDate());
            if (parsedDate == null) {
                continue;
            }

            if (earliestDate == null || parsedDate.isBefore(earliestDate)) {
                earliestDate = parsedDate;
            }
        }

        if (earliestDate == null) {
            return earliestExpiryItems;
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            LocalDate parsedDate = parseExpiryDate(item.getExpiryDate());
            if (earliestDate.equals(parsedDate)) {
                earliestExpiryItems.add(new IndexedItem(i + 1, item));
            }
        }

        return earliestExpiryItems;
    }

    /**
     * Parses the specified expiry date string.
     * Returns {@code null} if the expiry date is invalid or missing.
     *
     * @param expiryDate Expiry date string to parse.
     * @return Parsed expiry date, or {@code null} if parsing fails.
     */
    private LocalDate parseExpiryDate(String expiryDate) {
        try {
            return DateParser.parseDate(expiryDate);
        } catch (InvalidDateException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Represents an item together with its one-based index within a category.
     */
    public static class IndexedItem {
        private final int index;
        private final Item item;

        public IndexedItem(int index, Item item) {
            this.index = index;
            this.item = item;
        }

        public int getIndex() {
            return index;
        }

        public Item getItem() {
            return item;
        }
    }

    /**
     * Represents the summary information for a single category.
     */
    public static class CategorySummary {
        private final String categoryName;
        private final int itemCount;
        private final List<IndexedItem> lowestStockItems;
        private final List<IndexedItem> earliestExpiryItems;

        public CategorySummary(String categoryName, int itemCount,
                               List<IndexedItem> lowestStockItems,
                               List<IndexedItem> earliestExpiryItems) {
            this.categoryName = categoryName;
            this.itemCount = itemCount;
            this.lowestStockItems = lowestStockItems;
            this.earliestExpiryItems = earliestExpiryItems;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public int getItemCount() {
            return itemCount;
        }

        public List<IndexedItem> getLowestStockItems() {
            return lowestStockItems;
        }

        public List<IndexedItem> getEarliestExpiryItems() {
            return earliestExpiryItems;
        }
    }

    /**
     * Returns the summary display mode of this command.
     *
     * @return Summary type for this command.
     */
    public SummaryType getSummaryType() {
        return summaryType;
    }
}
