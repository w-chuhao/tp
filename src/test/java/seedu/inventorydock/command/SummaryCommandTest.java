package seedu.inventorydock.command;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SummaryCommandTest {

    @Test
    public void execute_allSummary_callsUiWithBothSections() {
        Inventory inventory = createInventoryWithTies();
        SummaryCommand command = new SummaryCommand(SummaryCommand.SummaryType.ALL);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showInventorySummaryCalled);
        assertEquals(SummaryCommand.SummaryType.ALL, ui.summaryType);
        assertEquals(2, ui.summaries.size());
        assertEquals(2, ui.summaries.get(0).getLowestStockItems().size());
        assertEquals(2, ui.summaries.get(0).getEarliestExpiryItems().size());
    }

    @Test
    public void execute_stockSummary_callsUiWithStockOnly() {
        Inventory inventory = createInventoryWithTies();
        SummaryCommand command = new SummaryCommand(SummaryCommand.SummaryType.STOCK);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showInventorySummaryCalled);
        assertEquals(SummaryCommand.SummaryType.STOCK, ui.summaryType);
        assertEquals(2, ui.summaries.get(0).getLowestStockItems().size());
        assertTrue(ui.summaries.get(0).getEarliestExpiryItems().isEmpty());
    }

    @Test
    public void execute_expirySummary_callsUiWithExpiryOnly() {
        Inventory inventory = createInventoryWithTies();
        SummaryCommand command = new SummaryCommand(SummaryCommand.SummaryType.EXPIRYDATE);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showInventorySummaryCalled);
        assertEquals(SummaryCommand.SummaryType.EXPIRYDATE, ui.summaryType);
        assertTrue(ui.summaries.get(0).getLowestStockItems().isEmpty());
        assertEquals(2, ui.summaries.get(0).getEarliestExpiryItems().size());
    }

    private Inventory createInventoryWithTies() {
        Inventory inventory = new Inventory();

        Category fruits = new Category("fruits");
        fruits.addItem(new Item("Apple", 2, "A1", "2026-04-20"));
        fruits.addItem(new Item("Banana", 5, "A2", "2026-04-18"));
        fruits.addItem(new Item("Orange", 2, "A3", "2026-04-18"));

        Category drinks = new Category("drinks");

        inventory.addCategory(fruits);
        inventory.addCategory(drinks);
        return inventory;
    }

    private static class TestUI extends UI {
        private boolean showInventorySummaryCalled;
        private List<SummaryCommand.CategorySummary> summaries;
        private SummaryCommand.SummaryType summaryType;

        @Override
        public void showInventorySummary(List<SummaryCommand.CategorySummary> summaries,
                                         SummaryCommand.SummaryType summaryType) {
            this.showInventorySummaryCalled = true;
            this.summaries = summaries;
            this.summaryType = summaryType;
        }
    }
}
