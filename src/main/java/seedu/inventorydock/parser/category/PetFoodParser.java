package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
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

    public PetFoodParser(String petType, String brand) {
        this.petType = petType;
        this.brand = brand;
    }

    public static PetFoodParser parse(String input) throws InventoryDockException {
        assert input != null : "PetFoodParser received null input.";
        logger.log(Level.INFO, "Processing PetFood special fields.");

        String petType = FieldParser.extractField(input, "petType/", "brand/");
        if (petType == null || petType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing petType for pet food.");
            throw new MissingArgumentException("Missing petType for pet food.");
        }

        String brand = FieldParser.extractField(input, "brand/", null);
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for pet food.");
            throw new MissingArgumentException("Missing brand for pet food.");
        }

        logger.log(Level.INFO, "End of processing pet food.");
        return new PetFoodParser(petType, brand);
    }
}
