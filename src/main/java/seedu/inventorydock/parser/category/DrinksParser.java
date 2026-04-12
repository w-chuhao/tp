package seedu.inventorydock.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses drink-specific fields from user input.
 */
public class DrinksParser {
    private static final Logger logger = Logger.getLogger(DrinksParser.class.getName());

    public final String brand;
    public final boolean isCarbonated;

    public DrinksParser(String brand, boolean isCarbonated) {
        this.brand = brand;
        this.isCarbonated = isCarbonated;
    }

    public static DrinksParser parse(String input) throws InventoryDockException {
        assert input != null : "DrinksParser received null input.";
        logger.log(Level.INFO, "Processing Drinks special fields.");

        String brand = FieldParser.extractField(input, "brand/", "isCarbonated/");
        brand = SpecificFieldValidator.parseStringOnlyField(brand, "brand", "drinks");

        String carbonatedString = FieldParser.extractField(input, "isCarbonated/", null);
        if (carbonatedString == null || carbonatedString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing carbonation field for drinks.");
            throw new MissingArgumentException("Missing carbonation field for drinks.");
        }

        if (!(carbonatedString.equalsIgnoreCase("true") || carbonatedString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Carbonation field must be true or false.");
            throw new InvalidCommandException("Carbonation field must be true or false.");
        }
        boolean isCarbonated = Boolean.parseBoolean(carbonatedString);

        logger.log(Level.INFO, "End of processing drinks.");
        return new DrinksParser(brand, isCarbonated);
    }
}
