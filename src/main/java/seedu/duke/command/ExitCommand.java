package seedu.duke.command;

import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

public class ExitCommand extends Command {
    @Override
    public void execute(Inventory inventory, UI ui) {
        // Exit handled by isExit() in main loop
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
