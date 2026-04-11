package seedu.inventorydock.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.command.AddItemCommand;
import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.model.items.Accessories;
import seedu.inventorydock.model.items.Drinks;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.model.items.Meat;
import seedu.inventorydock.model.items.PetFood;
import seedu.inventorydock.model.items.Seafood;
import seedu.inventorydock.model.items.Snack;
import seedu.inventorydock.model.items.Toiletries;
import seedu.inventorydock.model.items.Vegetable;
import seedu.inventorydock.parser.category.AccessoriesParser;
import seedu.inventorydock.parser.category.CommonFieldParser;
import seedu.inventorydock.parser.category.DrinksParser;
import seedu.inventorydock.parser.category.FruitParser;
import seedu.inventorydock.parser.category.InputValidator;
import seedu.inventorydock.parser.category.MeatParser;
import seedu.inventorydock.parser.category.PetFoodParser;
import seedu.inventorydock.parser.category.SeafoodParser;
import seedu.inventorydock.parser.category.SnackParser;
import seedu.inventorydock.parser.category.ToiletriesParser;
import seedu.inventorydock.parser.category.VegetableParser;

/**
 * Converts validated add-item arguments into category-specific {@link Item}
 * instances wrapped in {@link AddItemCommand} objects.
 */
public class AddItemCommandParser {
    private static final Logger logger = Logger.getLogger(AddItemCommandParser.class.getName());

    public Command handleFruit(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null fruit input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "size/");
        FruitParser fruitFields = FruitParser.parse(input);
        Item item = new Fruit(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, fruitFields.size, fruitFields.isRipe);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSnack(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null snack input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "isCrunchy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SnackParser snackFields = SnackParser.parse(input);
        Item item = new Snack(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, snackFields.brand, snackFields.isCrunchy);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleToiletries(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null toiletries input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "isLiquid/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        Item item = new Toiletries(commonFields.itemName, commonFields.quantity, commonFields.bin,
                toiletriesFields.brand, toiletriesFields.isLiquid, commonFields.expiryDate);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleVegetables(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null vegetable input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "isLeafy/", "origin/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "isLeafy/");
        VegetableParser vegetableFields = VegetableParser.parse(input);
        Item item = new Vegetable(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, vegetableFields.isLeafy, vegetableFields.origin);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleDrinks(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null drinks input.";
        logger.log(Level.INFO, "Parsing add-item command for drinks.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "flavour/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        DrinksParser drinksFields = DrinksParser.parse(input);
        Item item = new Drinks(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, drinksFields.brand, drinksFields.flavour);

        logger.log(Level.INFO, "Created drinks item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSeafood(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null seafood input.";
        logger.log(Level.INFO, "Parsing add-item command for seafood.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "seafoodType/", "origin/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "seafoodType/");
        SeafoodParser seafoodFields = SeafoodParser.parse(input);
        Item item = new Seafood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, seafoodFields.seafoodType, seafoodFields.origin);

        logger.log(Level.INFO, "Created seafood item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleMeat(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null meat input.";
        logger.log(Level.INFO, "Parsing add-item command for meat.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "meatType/", "origin/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "meatType/");
        MeatParser meatFields = MeatParser.parse(input);
        Item item = new Meat(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, meatFields.meatType, meatFields.origin);

        logger.log(Level.INFO, "Created meat item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handlePetFood(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null pet food input.";
        logger.log(Level.INFO, "Parsing add-item command for pet food.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "petType/", "brand/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "petType/");
        PetFoodParser petFoodFields = PetFoodParser.parse(input);
        Item item = new PetFood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, petFoodFields.petType, petFoodFields.brand);

        logger.log(Level.INFO, "Created pet food item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleAccessories(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null accessories input.";
        logger.log(Level.INFO, "Parsing add-item command for accessories.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "type/", "material/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "type/");
        AccessoriesParser accessoriesFields = AccessoriesParser.parse(input);
        Item item = new Accessories(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, accessoriesFields.type, accessoriesFields.material);

        logger.log(Level.INFO, "Created accessories item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }
}
