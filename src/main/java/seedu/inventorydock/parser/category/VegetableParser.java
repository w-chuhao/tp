package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses vegetable-specific fields from user input.
 */
public class VegetableParser {
    private static final Logger logger = Logger.getLogger(VegetableParser.class.getName());

    public final boolean isLeafy;
    public final String origin;

    public VegetableParser(boolean isLeafy, String origin) {
        this.isLeafy = isLeafy;
        this.origin = origin;
    }

    public static VegetableParser parse(String input) throws InventoryDockException {
        assert input != null : "VegetableParser received null inputs.";

        String leafyString = FieldParser.extractField(input, "isLeafy/", "origin/");
        if (leafyString == null || leafyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing leafy field for vegetable.");
            throw new MissingArgumentException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true") || leafyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Leafy field must be true or false.");
            throw new InvalidCommandException("Leafy field must be true or false.");
        }
        boolean isLeafy = Boolean.parseBoolean(leafyString);

        String origin = FieldParser.extractField(input, "origin/", null);
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for vegetable.");
            throw new MissingArgumentException("Missing origin for vegetable.");
        }

        return new VegetableParser(isLeafy, origin);
    }
}
