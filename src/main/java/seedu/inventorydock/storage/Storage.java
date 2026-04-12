package seedu.inventorydock.storage;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.StorageException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.AddItemCommandParser;
import seedu.inventorydock.ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles reading from and writing to the storage file.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final File dataFile;

    public Storage(String filePath) {
        assert filePath != null : "Storage received null file path.";
        this.dataFile = new File(filePath);
        logger.log(Level.INFO, "Storage initialized with file path: " + filePath);
    }

    private void createFile() throws IOException {
        if (dataFile.exists()) {
            return;
        }
        File parent = dataFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        dataFile.createNewFile();
        logger.log(Level.INFO, "Created storage file: " + dataFile.getPath());
    }

    public void load(Inventory inventory, UI ui) throws InventoryDockException {
        assert inventory != null : "Storage.load received null inventory.";
        assert ui != null : "Storage.load received null ui.";
        logger.log(Level.INFO, "Starting inventory load from file: " + dataFile.getPath());
        try {
            createFile();
            List<String> lines = Files.readAllLines(dataFile.toPath());
            AddItemCommandParser addItemCommandParser = new AddItemCommandParser();

            for (String line : lines) {
                try {
                    logger.log(Level.INFO, "Parsing stored line: " + line);
                    Command command = parseStoredLine(line, addItemCommandParser);
                    command.execute(inventory, null);
                } catch (InventoryDockException e) {
                    logger.log(Level.WARNING, "Skipped corrupted line: " + line
                            + ", Reason: " + e.getMessage());
                    ui.showSkippedLine(line, e.getMessage());
                }
            }
            logger.log(Level.INFO, "Finished loading inventory.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to read storage file.");
            throw new StorageException("Unable to read storage file.", e);
        }
    }

    private Command parseStoredLine(String line, AddItemCommandParser parser) throws InventoryDockException {
        assert line != null : "Storage.parseStoredLine received null line.";
        assert parser != null : "Storage.parseStoredLine received null parser.";
        String category = extractCategory(line);

        switch (category) {
        case "fruits":
            return parser.handleFruit(line);
        case "snacks":
            return parser.handleSnack(line);
        case "toiletries":
            return parser.handleToiletries(line);
        case "vegetables":
            return parser.handleVegetables(line);
        case "drinks":
            return parser.handleDrinks(line);
        case "meat":
            return parser.handleMeat(line);
        case "accessories":
            return parser.handleAccessories(line);
        default:
            logger.log(Level.WARNING, "Unknown category in storage.");
            throw new StorageException("Unknown category in storage.");
        }
    }

    private String extractCategory(String line) throws InventoryDockException {
        assert line != null : "Storage.extractCategory received null line.";
        String[] tokens = line.trim().split(" ");

        for (String token : tokens) {
            if (token.startsWith("category/")) {
                return token.substring("category/".length()).trim().toLowerCase();
            }
        }

        throw new StorageException("Missing category in storage line: " + line);
    }

    public void save(Inventory inventory) throws InventoryDockException {
        assert inventory != null : "Storage.save received null inventory.";
        List<String> lines = new ArrayList<>();
        logger.log(Level.INFO, "Starting inventory save to file: " + dataFile.getPath());

        for (Category category : inventory.getCategories()) {
            for (Item item : category.getItems()) {
                lines.add(formatItem(item, category.getName()));
            }
        }

        try {
            createFile();
            Files.write(dataFile.toPath(), lines);
            logger.log(Level.INFO, "Saved inventory to storage file.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to save inventory.");
            throw new StorageException("Unable to save inventory.", e);
        }
    }

    private String formatItem(Item item, String categoryName) {
        assert item != null : "Storage.formatItem received null item.";
        assert categoryName != null : "Storage.formatItem received null categoryName.";
        return item.toStorageString(categoryName);
    }
}
