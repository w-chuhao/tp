package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validates the category-specific string field shared across item parsers.
 */
public final class SpecificFieldValidator {
    private static final Logger logger = Logger.getLogger(SpecificFieldValidator.class.getName());
    private static final String STRING_ONLY_PATTERN = "[A-Za-z ]+";

    private SpecificFieldValidator() {
    }

    /**
     * Validates a required category-specific string field.
     *
     * @param value Raw field value.
     * @param fieldName Field label used in messages.
     * @param categoryName Category label used in messages.
     * @return Trimmed validated value.
     * @throws MissingArgumentException If the field is missing.
     * @throws InvalidCommandException If the field contains non-letter characters.
     */
    public static String parseStringOnlyField(String value, String fieldName, String categoryName)
            throws MissingArgumentException, InvalidCommandException {
        if (value == null || value.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing " + fieldName + " for " + categoryName + ".");
            throw new MissingArgumentException("Missing " + fieldName + " for " + categoryName + ".");
        }

        String trimmedValue = value.trim();
        if (!trimmedValue.matches(STRING_ONLY_PATTERN)) {
            logger.log(Level.WARNING, fieldName + " for " + categoryName + " must contain letters only.");
            throw new InvalidCommandException(fieldName + " for " + categoryName + " must contain letters only.");
        }
        return trimmedValue;
    }
}
