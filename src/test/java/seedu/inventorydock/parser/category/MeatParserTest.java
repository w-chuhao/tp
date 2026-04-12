package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeatParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "meatType/Beef isFrozen/true";
        assertDoesNotThrow(() -> MeatParser.parse(input));
    }

    @Test
    public void parse_missingMeatType_throwsException() {
        String input = "meatType/ isFrozen/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing meatType for meat.", e.getMessage());
    }

    @Test
    public void parse_missingFrozen_throwsException() {
        String input = "meatType/Beef isFrozen/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing frozen field for meat.", e.getMessage());
    }

    @Test
    public void parse_invalidMeatType_throwsException() {
        String input = "meatType/beef123 isFrozen/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> MeatParser.parse(input));
        assertEquals("meatType for meat must contain letters only.", e.getMessage());
    }
}
