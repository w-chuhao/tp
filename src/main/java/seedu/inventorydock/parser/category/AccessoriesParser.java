package seedu.inventorydock.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses accessory-specific fields from user input.
 */
public class AccessoriesParser {
    private static final Logger logger = Logger.getLogger(AccessoriesParser.class.getName());

    public final String type;
    public final boolean isFragile;

    public AccessoriesParser(String type, boolean isFragile) {
        this.type = type;
        this.isFragile = isFragile;
    }

    public static AccessoriesParser parse(String input) throws InventoryDockException {
        assert input != null : "AccessoriesParser received null input.";
        logger.log(Level.INFO, "Processing Accessories special fields.");

        String type = FieldParser.extractField(input, "type/", "isFragile/");
        type = SpecificFieldValidator.parseStringOnlyField(type, "type", "accessories");

        String fragileString = FieldParser.extractField(input, "isFragile/", null);
        if (fragileString == null || fragileString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing fragility field for accessories.");
            throw new MissingArgumentException("Missing fragility field for accessories.");
        }

        if (!(fragileString.equalsIgnoreCase("true") || fragileString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Fragility field must be true or false.");
            throw new InvalidCommandException("Fragility field must be true or false.");
        }
        boolean isFragile = Boolean.parseBoolean(fragileString);

        logger.log(Level.INFO, "End of processing accessories.");
        return new AccessoriesParser(type, isFragile);
    }
}
