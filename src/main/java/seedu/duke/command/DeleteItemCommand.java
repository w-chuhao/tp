package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.List;

public class DeleteItemCommand extends Command {
    private final String itemName;

    public DeleteItemCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(Inventory inventory, UI ui) {
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            Item item = category.findItemByName(itemName);
            if (item != null) {
                category.getItems().remove(item);
                ui.showItemDeleted(itemName,
                        category.getName());
                return;
            }
        }

        ui.showItemNotFound(itemName);
    }
}
