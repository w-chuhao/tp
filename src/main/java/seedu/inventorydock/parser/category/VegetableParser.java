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

    public final String origin;
    public final boolean isLeafy;

    /**
     * Creates a {@code VegetableParser} object with the parsed vegetable details.
     *
     * @param origin Origin of the vegetable.
     * @param isLeafy Whether the vegetable is leafy.
     */
    public VegetableParser(String origin, boolean isLeafy) {
        this.origin = origin;
        this.isLeafy = isLeafy;
    }

    public static VegetableParser parse(String input) throws InventoryDockException {
        assert input != null : "VegetableParser received null inputs.";

        String origin = FieldParser.extractField(input, "origin/", "isLeafy/");
        origin = SpecificFieldValidator.parseStringOnlyField(origin, "origin", "vegetable");

        String leafyString = FieldParser.extractField(input, "isLeafy/", null);
        if (leafyString == null || leafyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing leafy field for vegetable.");
            throw new MissingArgumentException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true") || leafyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Leafy field must be true or false.");
            throw new InvalidCommandException("Leafy field must be true or false.");
        }
        boolean isLeafy = Boolean.parseBoolean(leafyString);

        return new VegetableParser(origin, isLeafy);
    }
}
