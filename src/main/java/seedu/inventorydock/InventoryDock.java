package seedu.inventorydock;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.logging.LoggerConfig;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.parser.Parser;
import seedu.inventorydock.storage.Storage;
import seedu.inventorydock.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the main entry point and application loop for InventoryDock.
 */
public class InventoryDock {
    private static final Logger logger = Logger.getLogger(InventoryDock.class.getName());
    private final Inventory inventory;
    private final UI ui;
    private final Parser parser;
    private final Storage storage;

    public InventoryDock() throws InventoryDockException {
        ui = new UI();
        inventory = new Inventory();
        parser = new Parser();
        storage = new Storage("./data/inventory.txt");
        logger.log(Level.INFO, "Initializing InventoryDoke application.");

        String[] categoryNames = {
            "fruits",
            "vegetables",
            "toiletries",
            "snacks",
            "drinks",
            "meat",
            "accessories"
        };

        for (String categoryName : categoryNames) {
            inventory.addCategory(new Category(categoryName));
        }

        storage.load(inventory, ui);
        logger.log(Level.INFO, "Loaded inventory with " + inventory.getCategoryCount() + " categories.");
    }

    public static void main(String[] args) throws InventoryDockException {
        LoggerConfig logger = new LoggerConfig("./logs/logger.txt");
        logger.setup();
        new InventoryDock().run();
    }

    public void run() {
        logger.log(Level.INFO, "Starting InventoryDock command loop.");
        ui.showWelcome();

        String input;
        while ((input = ui.readCommand()) != null) {
            try {
                Command command = parser.parse(input);

                if (command == null) {
                    continue;
                }

                if (command.isExit()) {
                    logger.log(Level.INFO, "Exit command received. Saving inventory and shutting down.");
                    command.execute(inventory, ui);
                    storage.save(inventory);
                    break;
                }

                logger.log(Level.INFO, "Executing command: " + command.getClass().getSimpleName());
                command.execute(inventory, ui);
                storage.save(inventory);
            } catch (InventoryDockException e) {
                logger.log(Level.WARNING, "Command processing failed: " + e.getMessage());
                ui.showError(e);
            }
        }

        logger.log(Level.INFO, "Closing InventoryDock application.");
        ui.close();
    }
}
