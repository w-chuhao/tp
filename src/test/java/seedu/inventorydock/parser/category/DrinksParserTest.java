package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinksParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/CocaCola isCarbonated/true";
        assertDoesNotThrow(() -> DrinksParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ isCarbonated/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing brand for drinks.", e.getMessage());
    }

    @Test
    public void parse_missingCarbonation_throwsException() {
        String input = "brand/PepsiCola isCarbonated/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing carbonation field for drinks.", e.getMessage());
    }

    @Test
    public void parse_invalidBrand_throwsException() {
        String input = "brand/CocaCola123 isCarbonated/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> DrinksParser.parse(input));
        assertEquals("brand for drinks must contain letters only.", e.getMessage());
    }
}
