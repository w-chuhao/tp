package seedu.inventorydock.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;

/**
 * Parses top-level {@code add} commands and routes them to the category-specific
 * parser that constructs the corresponding add-item command.
 */
public class AddCommandParser {
    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());

    public Command parse(String input) throws InventoryDockException {
        assert input != null : "AddCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Add command input is empty.");
            throw new MissingArgumentException("Input is empty.");
        }

        String trimmedInput = input.trim();
        validateRequiredFields(trimmedInput);

        String category = extractCategory(trimmedInput);
        logger.log(Level.INFO, "Processing add command for category: " + category);
        return parseByCategory(trimmedInput, category);
    }

    private void validateRequiredFields(String input) throws InventoryDockException {
        String category = extractFieldValue(input, "category/");
        if (category == null || category.isEmpty()) {
            logger.log(Level.WARNING, "Missing category in add command.");
            throw new MissingArgumentException("Missing category.");
        }
    }

    private String extractCategory(String input) {
        return extractFieldValue(input, "category/").toLowerCase();
    }

    private String extractFieldValue(String input, String prefix) {
        String[] tokens = input.split(" ");
        for (String token : tokens) {
            if (token.startsWith(prefix)) {
                return token.substring(prefix.length()).trim();
            }
        }
        return null;
    }

    private Command parseByCategory(String input, String category) throws InventoryDockException {
        AddItemCommandParser parser = new AddItemCommandParser();

        switch (category) {
        case "fruits":
            return parser.handleFruit(input);
        case "snacks":
            return parser.handleSnack(input);
        case "toiletries":
            return parser.handleToiletries(input);
        case "vegetables":
            return parser.handleVegetables(input);
        case "drinks":
            return parser.handleDrinks(input);
        case "seafood":
            return parser.handleSeafood(input);
        case "meat":
            return parser.handleMeat(input);
        case "petfood":
            return parser.handlePetFood(input);
        case "accessories":
            return parser.handleAccessories(input);
        default:
            logger.log(Level.WARNING, "Unknown add command category: " + category);
            throw new InvalidCommandException("Unknown category: " + category);
        }
    }
}
