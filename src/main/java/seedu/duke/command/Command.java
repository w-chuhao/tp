package seedu.duke.command;

import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

public abstract class Command {

    public abstract void execute(Inventory inventory, UI ui);

    public boolean isExit() {
        return false;
    }
}
