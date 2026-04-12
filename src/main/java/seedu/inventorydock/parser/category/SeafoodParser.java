package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
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
    public final boolean isFresh;

    public SeafoodParser(String seafoodType, String origin, boolean isFresh) {
        this.seafoodType = seafoodType;
        this.origin = origin;
        this.isFresh = isFresh;
    }

    public static SeafoodParser parse(String input) throws InventoryDockException {
        assert input != null : "SeafoodParser received null input.";
        logger.log(Level.INFO, "Processing Seafood special fields.");

        String seafoodType = FieldParser.extractField(input, "seafoodType/", "origin/");
        if (seafoodType == null || seafoodType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing seafoodType for seafood.");
            throw new MissingArgumentException("Missing seafoodType for seafood.");
        }

        String origin = FieldParser.extractField(input, "origin/", "isFresh/");
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for seafood.");
            throw new MissingArgumentException("Missing origin for seafood.");
        }

        String freshString = FieldParser.extractField(input, "isFresh/", null);
        if (freshString == null || freshString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing freshness field for seafood.");
            throw new MissingArgumentException("Missing freshness field for seafood.");
        }

        if (!(freshString.equalsIgnoreCase("true") || freshString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Freshness field must be true or false.");
            throw new InvalidCommandException("Freshness field must be true or false.");
        }
        boolean isFresh = Boolean.parseBoolean(freshString);

        logger.log(Level.INFO, "End of processing seafood.");
        return new SeafoodParser(seafoodType, origin, isFresh);
    }
}
