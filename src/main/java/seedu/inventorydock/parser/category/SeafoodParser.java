package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses seafood-specific fields from user input.
 */
public class SeafoodParser {
    private static final Logger logger = Logger.getLogger(SeafoodParser.class.getName());

    public final String seafoodType;
    public final String origin;

    public SeafoodParser(String seafoodType, String origin) {
        this.seafoodType = seafoodType;
        this.origin = origin;
    }

    public static SeafoodParser parse(String input) throws InventoryDockException {
        assert input != null : "SeafoodParser received null input.";
        logger.log(Level.INFO, "Processing Seafood special fields.");

        String seafoodType = FieldParser.extractField(input, "seafoodType/", "origin/");
        if (seafoodType == null || seafoodType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing seafoodType for seafood.");
            throw new MissingArgumentException("Missing seafoodType for seafood.");
        }

        String origin = FieldParser.extractField(input, "origin/", null);
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for seafood.");
            throw new MissingArgumentException("Missing origin for seafood.");
        }

        logger.log(Level.INFO, "End of processing seafood.");
        return new SeafoodParser(seafoodType, origin);
    }
}
