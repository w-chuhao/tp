package seedu.inventorydock.parser;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InvalidIndexException;
import seedu.inventorydock.exception.MissingArgumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses user input for the update command into an executable command object.
 * An <code>UpdateCommandParser</code> object validates the category, item index,
 * and update fields before creating an update command.
 */
public class UpdateCommandParser {
    /**
     * Parses the specified update command arguments into an update command.
     * The input must contain <code>category/</code>, <code>index/</code>, and at least
     * one supported update field.
     *
     * @param input Update command arguments supplied by the user.
     * @return Command representing the requested item update.
     * @throws InventoryDockException If the category, index, or update fields are invalid.
     */
    public Command parse(String input) throws InventoryDockException {
        assert input != null : "UpdateCommandParser received null input.";

        if (input.trim().isEmpty()) {
            throw new MissingArgumentException("specify the update details. Use: update category/CATEGORY index/INDEX "
                    + "[newItem/NAME] [bin/BIN] [qty/QTY] [expiryDate/DATE] ...");
        }

        String[] tokens = input.trim().split("\\s+");
        Map<String, String> fields = new HashMap<>();

        for (String token : tokens) {
            int separatorIndex = token.indexOf('/');
            if (separatorIndex <= 0 || separatorIndex == token.length() - 1) {
                throw new InvalidCommandException("update token '" + token + "' is invalid.");
            }

            String key = token.substring(0, separatorIndex);
            String value = token.substring(separatorIndex + 1);
            if (fields.containsKey(key)) {
                throw new InvalidCommandException("Duplicate update field: " + key + "/.");
            }
            fields.put(key, value);
        }

        String categoryName = fields.remove("category");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new MissingArgumentException("category is required.");
        }

        String itemIndexString = fields.remove("index");
        if (itemIndexString == null || itemIndexString.trim().isEmpty()) {
            throw new MissingArgumentException("item index is required.");
        }

        int itemIndex;
        try {
            itemIndex = Integer.parseInt(itemIndexString.trim());
        } catch (NumberFormatException e) {
            throw new InvalidIndexException("Item index must be an integer.", e);
        }

        if (itemIndex <= 0) {
            throw new InvalidIndexException("Item index must be a positive integer.");
        }

        if (fields.isEmpty()) {
            throw new MissingArgumentException("at least one field to update is required.");
        }

        if (fields.containsKey("bin")) {
            fields.put("bin", BinLocationParser.parseExactInput(fields.get("bin")));
        }

        return new UpdateItemCommand(categoryName.trim(),
                itemIndex, fields);
    }
}
