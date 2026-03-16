package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;
import java.util.logging.Logger;

public abstract class Command {

    public abstract void execute(Inventory inventory, UI ui) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
