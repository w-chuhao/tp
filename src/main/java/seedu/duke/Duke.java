package seedu.duke;

import java.util.Scanner;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.items.Fruit;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
//        System.out.println("What is your name?");
//
//        Scanner in = new Scanner(System.in);
//        System.out.println("Hello " + in.nextLine());
        Inventory inventory = new Inventory();

        // Create categories
        Category fruitsCategory = new Category("Fruits");
        // Category toiletriesCategory = new Category("Toiletries");

        // Create items
        fruitsCategory.addItem(new Fruit("apple",40,"A-10","10-03-2026","big",true));
        fruitsCategory.addItem(new Fruit("banana",30,"B-10","9-03-2026","small",true));

        // Add into inventory
        inventory.addCategories((fruitsCategory));

//        tasks.addAll(storage.load());
//
//        System.out.println("Hello! Welcome to InventoryDock!");
//        System.out.println("What can I do for you?");
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            try {
//                Input input = getUserInput(scanner); // process user input
//
//                ErrorChecker.checkErrors(input.command(), input.description(), tasks);
//
//                CommandHandler.execute(input.command(), input.description(), tasks, storage);
//
//            } catch (SixSevenException e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }
//    private static Result getUserInput(Scanner scanner) {
//        String line = scanner.nextLine();
//
//        String[] input = line.split(" ", 2);
//        String command = input[0];
//        String description = input.length > 1 ? input[1] : "";
//        return new Result(command, description);
//    }
//    private record Input(String command, String description) {}
}
