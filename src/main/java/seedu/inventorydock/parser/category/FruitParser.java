package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses fruit-specific fields from user input.
 */
public class FruitParser {
    private static final Logger logger = Logger.getLogger(FruitParser.class.getName());

    public final String size;
    public final boolean isRipe;

    /**
     * Creates a {@code FruitParser} object with the parsed fruit details.
     *
     * @param size Size of the fruit.
     * @param isRipe Whether the fruit is ripe.
     */
    public FruitParser(String size, boolean isRipe) {
        this.size = size;
        this.isRipe = isRipe;
    }

    /**
     * Parses the fruit-related fields from the given input string.
     *
     * @param input User input containing fruit fields.
     * @return A {@code FruitParser} containing the parsed values.
     * @throws InventoryDockException If any required field is missing or invalid.
     */
    public static FruitParser parse(String input) throws InventoryDockException {
        assert input != null : "FruitParser received null input.";

        String size = FieldParser.extractField(input, "size/", "isRipe/");
        size = parseFruitSize(size);

        String ripeString = FieldParser.extractField(input, "isRipe/", null);
        if (ripeString == null || ripeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing ripeness for fruit.");
            throw new MissingArgumentException("Missing ripeness for fruit.");
        }

        if (!(ripeString.equalsIgnoreCase("true") || ripeString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Ripeness must be true or false");
            throw new InvalidCommandException("Ripeness must be true or false");
        }
        boolean isRipe = Boolean.parseBoolean(ripeString);

        return new FruitParser(size, isRipe);
    }

    private static String parseFruitSize(String size) throws InventoryDockException {
        String parsedSize = SpecificFieldValidator.parseStringOnlyField(size, "size", "fruit");
        if (!(parsedSize.equalsIgnoreCase("small")
                || parsedSize.equalsIgnoreCase("medium")
                || parsedSize.equalsIgnoreCase("large"))) {
            logger.log(Level.WARNING, "Size for fruit must be small, medium, or large.");
            throw new InvalidCommandException("Size for fruit must be small, medium, or large.");
        }
        return parsedSize.toLowerCase();
    }
}
