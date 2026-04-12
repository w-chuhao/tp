package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses pet food-specific fields from user input.
 */
public class PetFoodParser {
    private static final Logger logger = Logger.getLogger(PetFoodParser.class.getName());

    public final String petType;
    public final String brand;
    public final boolean isDry;

    public PetFoodParser(String petType, String brand, boolean isDry) {
        this.petType = petType;
        this.brand = brand;
        this.isDry = isDry;
    }

    public static PetFoodParser parse(String input) throws InventoryDockException {
        assert input != null : "PetFoodParser received null input.";
        logger.log(Level.INFO, "Processing PetFood special fields.");

        String petType = FieldParser.extractField(input, "petType/", "brand/");
        if (petType == null || petType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing petType for pet food.");
            throw new MissingArgumentException("Missing petType for pet food.");
        }

        String brand = FieldParser.extractField(input, "brand/", "isDry/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for pet food.");
            throw new MissingArgumentException("Missing brand for pet food.");
        }

        String dryString = FieldParser.extractField(input, "isDry/", null);
        if (dryString == null || dryString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing dry field for pet food.");
            throw new MissingArgumentException("Missing dry field for pet food.");
        }

        if (!(dryString.equalsIgnoreCase("true") || dryString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Dry field must be true or false.");
            throw new InvalidCommandException("Dry field must be true or false.");
        }
        boolean isDry = Boolean.parseBoolean(dryString);

        logger.log(Level.INFO, "End of processing pet food.");
        return new PetFoodParser(petType, brand, isDry);
    }
}
