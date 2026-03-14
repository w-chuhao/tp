package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Inventory;

public abstract class Command {
    public abstract void execute(Inventory inventory) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
