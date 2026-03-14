package seedu.duke.command;

import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

public class HelpCommand extends Command {
    @Override
    public void execute(Inventory inventory, UI ui) {
        ui.showHelp();
    }
}
