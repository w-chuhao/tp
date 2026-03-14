package seedu.duke.command;

import seedu.duke.model.Inventory;

public abstract class Command {
    public abstract void execute(Inventory inventory);
}
