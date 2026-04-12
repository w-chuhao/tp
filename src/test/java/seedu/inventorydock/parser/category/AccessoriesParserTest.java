package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccessoriesParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "type/Clip isFragile/true";
        assertDoesNotThrow(() -> AccessoriesParser.parse(input));
    }

    @Test
    public void parse_missingType_throwsException() {
        String input = "type/ isFragile/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> AccessoriesParser.parse(input));
        assertEquals("Missing type for accessories.", e.getMessage());
    }

    @Test
    public void parse_missingFragility_throwsException() {
        String input = "type/Clip isFragile/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> AccessoriesParser.parse(input));
        assertEquals("Missing fragility field for accessories.", e.getMessage());
    }

    @Test
    public void parse_invalidType_throwsException() {
        String input = "type/Clip123 isFragile/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> AccessoriesParser.parse(input));
        assertEquals("type for accessories must contain letters only.", e.getMessage());
    }
}
