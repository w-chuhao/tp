package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses meat-specific fields from user input.
 */
public class MeatParser {
    private static final Logger logger = Logger.getLogger(MeatParser.class.getName());

    public final String meatType;
    public final boolean isFrozen;

    public MeatParser(String meatType, boolean isFrozen) {
        this.meatType = meatType;
        this.isFrozen = isFrozen;
    }

    public static MeatParser parse(String input) throws InventoryDockException {
        assert input != null : "MeatParser received null input.";
        logger.log(Level.INFO, "Processing Meat special fields.");

        String meatType = FieldParser.extractField(input, "meatType/", "isFrozen/");
        meatType = SpecificFieldValidator.parseStringOnlyField(meatType, "meatType", "meat");

        String frozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (frozenString == null || frozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing frozen field for meat.");
            throw new MissingArgumentException("Missing frozen field for meat.");
        }

        if (!(frozenString.equalsIgnoreCase("true") || frozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Frozen field must be true or false.");
            throw new InvalidCommandException("Frozen field must be true or false.");
        }
        boolean isFrozen = Boolean.parseBoolean(frozenString);

        logger.log(Level.INFO, "End of processing meat.");
        return new MeatParser(meatType, isFrozen);
    }
}
