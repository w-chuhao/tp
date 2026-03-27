package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FindItemByBinCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByBinCommand.class.getName());
    private final String binInput;

    public FindItemByBinCommand(String binInput) {
        this.binInput = binInput;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "FindItemByBinCommand received null inventory.";
        assert ui != null : "FindItemByBinCommand received null UI.";
        assert binInput != null : "FindItemByBinCommand received null bin input.";

        List<Item> matches = new ArrayList<>();
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getBinLocation().toLowerCase().contains(binInput.toLowerCase())) {
                    matches.add(item);
                }
            }
        }

        if (matches.isEmpty()) {
            logger.log(Level.INFO, "No items found in bin location: " + binInput);
            ui.showMessage("No items found in bin location: " + binInput + ".");
            return;
        }

        logger.log(Level.INFO, "Found " + matches.size()
                + " item(s) in bin location '" + binInput + "'.");

        ui.showDivider();
        ui.showMessage("Items in bin location: " + binInput);
        for (int i = 0; i < matches.size(); i++) {
            ui.showMessage((i + 1) + ". " + matches.get(i));
        }
        ui.showDivider();
    }
}
