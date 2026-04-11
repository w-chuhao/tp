package seedu.inventorydock.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses drink-specific fields from user input.
 */
public class DrinksParser {
    private static final Logger logger = Logger.getLogger(DrinksParser.class.getName());

    public final String brand;
    public final String flavour;

    public DrinksParser(String brand, String flavour) {
        this.brand = brand;
        this.flavour = flavour;
    }

    public static DrinksParser parse(String input) throws InventoryDockException {
        assert input != null : "DrinksParser received null input.";
        logger.log(Level.INFO, "Processing Drinks special fields.");

        String brand = FieldParser.extractField(input, "brand/", "flavour/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for drinks.");
            throw new MissingArgumentException("Missing brand for drinks.");
        }

        String flavour = FieldParser.extractField(input, "flavour/", null);
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for drinks.");
            throw new MissingArgumentException("Missing flavour for drinks.");
        }

        logger.log(Level.INFO, "End of processing drinks.");
        return new DrinksParser(brand, flavour);
    }
}
