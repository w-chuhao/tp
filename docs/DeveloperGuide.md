# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Find Item By Expiry Date Feature

One enhancement added to the product is the ability to find items by expiry date using the command
`find expiryDate/DATE`.

This feature was introduced because the inventory is centered around storing physical goods, many of
which are perishable or time-sensitive. In this context, searching by name alone is not sufficient.
A user may know that some products are expiring soon, but may not remember the exact names or which
category they were placed under. The expiry-date search solves this by letting the user enter a cutoff
date and retrieve every item expiring on or before that date.

For example, if the user enters `find expiryDate/2026-3-25`, the system returns all items with expiry
dates earlier than `2026-3-25` as well as items expiring exactly on `2026-3-25`.

#### High-level design

At a high level, this enhancement reuses the existing command-based architecture of the application.
The feature fits naturally into the existing flow:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix after `find`.
3. If the prefix is `expiryDate/`, the parser creates a `FindItemByExpiryDateCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command scans the inventory and prints the matching results.

This design was chosen because it follows the same separation of concerns already used in the project:

- Parsers are responsible for interpreting user input.
- Command classes are responsible for application behaviour.
- Model classes store inventory data.
- `UI` is responsible for displaying the result to the user.

As a result, the new enhancement could be added without changing the overall architecture of the system.
It is an extension of the existing command pipeline rather than a separate subsystem.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `FindItemByExpiryDateCommand`
- `DateParser`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to perform a search based on expiry date.
- `FindItemByExpiryDateCommand` performs the actual search logic.
- `DateParser` ensures that the provided date is valid and converts it into a `LocalDate`.
- `Inventory` exposes the list of categories currently stored.
- `Category` exposes the list of items belonging to that category.
- `Item` provides each stored item's expiry date.

The parser logic is intentionally simple. After splitting the input around the first `/`, it checks the
left-hand side to determine what type of find command the user requested. If the type is `expirydate`,
it returns a new `FindItemByExpiryDateCommand`.

This means the parser does not perform the date comparison itself. Instead, it only constructs the
correct command object. This keeps parsing logic lightweight and pushes business logic into the command
layer, where it belongs.

#### Command execution flow

When `FindItemByExpiryDateCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `expiryDateInput` are not `null`.
2. Convert the user-supplied date into a `LocalDate` by calling `DateParser.parseDate(expiryDateInput)`.
3. Create an empty `List<String>` named `matches` to store formatted search results.
4. Retrieve all categories from the `Inventory`.
5. Iterate through each `Category`.
6. Within each category, iterate through each `Item`.
7. Read the item's expiry date using `item.getExpiryDate()`.
8. Skip that item if the expiry date is `null`.
9. Parse the item's expiry date into a `LocalDate`.
10. Compare the item's expiry date with the user-provided cutoff date.
11. If the item date is not after the cutoff date, add a formatted result string to `matches`.
12. After the scan is complete, either:
13. Display a "no items found" message if `matches` is empty, or
14. Print the list of matching items with numbering and dividers.

The key comparison is:

```java
if (!itemDate.isAfter(cutoffDate)) {
    matches.add(category.getName() + ": " + item);
}
```

This logic means the search is inclusive. In other words, if an item expires on the exact cutoff date,
it is still considered a match. This is the intended behaviour because users searching for items expiring
"by" a certain date usually expect items on that date to be included.

#### Why the feature is implemented this way

The most important design choice in this enhancement is that the command performs a full scan of the
inventory instead of relying on a precomputed data structure such as a sorted list, map, or expiry-date
index.

This was chosen for three reasons.

First, it keeps the implementation small and easy to reason about. The inventory is already organized by
category, and each category already stores its own list of items. Reusing that structure avoids adding
new state that must be updated every time an item is added, deleted, or edited.

Second, it reduces the risk of inconsistency. If an additional expiry-date index were introduced, the
application would need to ensure that every mutation to the inventory also updates the index correctly.
For a student project, the simpler design is more robust because there are fewer moving parts that can
fall out of sync.

Third, the expected inventory size is modest. A linear scan is acceptable for the current scale of the
application. The extra complexity of a specialised indexing structure is not justified unless the number
of items becomes large enough for search performance to become a real bottleneck.

Another deliberate design decision is the use of `LocalDate` instead of comparing raw strings.
Even though the input format looks sortable, relying on proper date parsing is safer and more maintainable.
It guarantees that invalid dates are rejected early and ensures that comparisons remain logically correct.

The implementation also ignores items whose expiry date is `null`. This was done to make the command
resilient when scanning heterogeneous inventory data. Some items may not have expiry dates populated, and
those items should not cause the search to fail. Instead, they are excluded from the result set because
they do not carry the attribute being searched on.

#### Error handling and validation

Input validation is handled mainly by `DateParser`.

If the user provides a missing or blank expiry date, `DateParser` throws a `DukeException` with the
message `Missing expiry date`.

If the user provides an invalid format such as `2026/3/25`, `DateParser` throws a `DukeException` with
the message `Invalid date. Please use yyyy-M-d.`

This design centralises date validation in one place instead of duplicating date checks across multiple
commands. That improves consistency and makes future maintenance easier. If the date format requirement
changes later, only `DateParser` needs to be updated.

The parser also handles malformed `find` commands before the command object is created. For example, if
the user enters `find expiryDate/` with no value after the slash, `FindItemParser` identifies the missing
argument and reports invalid input to the UI.

#### Alternatives considered

Several alternatives were considered when implementing this enhancement.

Alternative 1: Compare expiry dates as strings.

This approach would have been shorter to implement because it avoids converting strings into `LocalDate`
objects. However, it was rejected because string comparison is more fragile and tightly coupled to the
exact formatting of the stored dates. Using `LocalDate` gives stronger correctness guarantees and clearer
intent.

Alternative 2: Store items in a separate expiry-date index.

This approach could improve search performance by making lookups faster than a full scan, especially for
larger inventories. It was rejected for now because it adds extra complexity to the data model. Every
operation that modifies the inventory would also need to update the index, which increases implementation
effort and introduces more opportunities for bugs.

Alternative 3: Restrict expiry-date search to a single category at a time.

This approach could reduce the scope of each search and align with the current category-based inventory
organisation. It was rejected because it weakens the usefulness of the feature. Users usually care about
which items are expiring soon across the entire inventory, not within one specific category.

Alternative 4: Return only items expiring exactly on the given date.

This would have made the search condition simpler and more literal. It was rejected because the use case
is broader than exact-date matching. A cutoff-based search is more practical for inventory review and
waste prevention because it supports questions like "Which items expire by the end of this week?"

#### Current limitations

The current implementation has some limitations.

- It performs a full scan of the inventory each time the command is run.
- It returns formatted strings rather than a structured result object.
- It depends on expiry dates being stored in a valid format.
- It does not sort the output by earliest expiry date; results follow the current category and item order.

These limitations are acceptable for the current scope, but they identify possible directions for future
enhancement.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Sort matching items by expiry date before displaying them.
- Highlight items that are already expired separately from items that are merely approaching expiry.
- Support date ranges such as `find expiryDate/2026-3-01 to/2026-3-25`.
- Add category filters so users can combine expiry search with category search.
- Introduce an internal index if inventory size grows enough to justify optimisation.

### Add Item Feature

Another core feature of the product is the ability to add an item into an existing category using
the `add` command.

This feature is necessary because the application is fundamentally an inventory manager. Users need
to record newly stocked products together with shared fields such as name, quantity, bin location,
and expiry date, while also capturing category-specific attributes such as fruit size or drink
volume. The add-item flow solves this by routing the same high-level command through specialised
parsers based on the category provided by the user.

For example, if the user enters
`add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`,
the system validates the common and category-specific fields, constructs the correct `Item`
subclass, and adds it into the matching category.

#### High-level design

At a high level, this enhancement also fits into the existing command-based architecture of the
application. The feature follows this flow:

1. The user enters an `add` command.
2. `Parser` recognises the `add` command word and delegates the remaining input to `AddCommandParser`.
3. `AddCommandParser` validates the required shared fields and determines the target category.
4. `AddItemCommandParser` dispatches to the category-specific parsing method such as `FruitParser` and constructs the
   correct `Item` subtype.
5. An `AddItemCommand` is created and executed with access to the current `Inventory` and `UI`.
6. The command finds the target category, inserts the item, and shows a confirmation message.

The main interaction for this flow is illustrated in [AddItemCommandMainFlow.puml](AddItemCommandMainFlow.puml).

This design was chosen because it preserves the same separation of responsibilities used elsewhere
in the codebase:

- `Parser` and parser helpers interpret user input.
- `AddItemCommand` performs the inventory mutation.
- Model classes such as `Inventory`, `Category`, and `Item` hold the application state.
- `UI` presents confirmation messages to the user.

As a result, adding a new item subtype does not require redesigning the command pipeline. The parser
layer can be extended category by category while the execution model remains unchanged.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `AddCommandParser`
- `AddItemCommandParser`
- Category-specific parsers such as `FruitParser`
- `AddItemCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `Parser` identifies that the user wants to perform an add operation.
- `AddCommandParser` validates shared required fields and chooses the correct parsing branch based on
  `category/`.
- `AddItemCommandParser` coordinates common-field parsing and category-specific parsing.
- Category-specific parsers construct the extra fields required by each concrete `Item` subtype.
- `AddItemCommand` performs the actual insertion into the inventory.
- `Inventory` finds the matching category by name.
- `Category` stores the added item.
- `Item` and its subclasses represent the domain object being created.

This design intentionally separates shared parsing from category-specific parsing. Common fields such
as `item/`, `bin/`, `qty/`, and `expiryDate/` can be handled consistently, while subtype-specific
fields remain encapsulated in the relevant parser and model class.

#### Command execution flow

When the user enters an add command, the implementation performs the following sequence:

1. `Parser.parse()` splits the command word from the arguments.
2. `Parser` calls `AddCommandParser.parse(arguments)`.
3. `AddCommandParser` checks that mandatory fields such as `item/` and `category/` are present.
4. `AddCommandParser` extracts the category and dispatches to the corresponding method in
   `AddItemCommandParser`.
5. `AddItemCommandParser` validates the input, parses common fields, and invokes the category-specific
   parser.
6. `AddItemCommandParser` creates an `Item` subtype and wraps it in an `AddItemCommand`.
7. `Duke` executes `AddItemCommand.execute(inventory, ui)`.
8. `AddItemCommand` calls `inventory.findCategoryByName(categoryName)`.
9. If the category exists, `AddItemCommand` calls `category.addItem(item)`.
10. `UI.showItemAdded(...)` displays the confirmation to the user.

The execution logic in `AddItemCommand` is intentionally small:

```java
Category category = inventory.findCategoryByName(categoryName);
category.addItem(item);
ui.showItemAdded(item.getName(), item.getQuantity(),
        category.getName(), item.getBinLocation());
```

This keeps construction concerns in the parser layer and mutation concerns in the command layer.

#### Why the feature is implemented this way

The main design choice is the use of category-based dispatch in `AddCommandParser` and
`AddItemCommandParser` instead of one very large parser or category-agnostic item builder.

This was chosen for three reasons.

First, different item types do not share the same attributes. Separating parsers by category keeps
validation rules close to the subtype that needs them.

Second, it improves maintainability. Adding support for a new category mostly requires introducing a
new parser branch and item subtype rather than modifying one monolithic parsing method with many
special cases.

Third, it keeps command execution simple. By the time `AddItemCommand` runs, all parsing and object
construction work has already been completed. The command only needs to find the category and append
the item.

Another deliberate design choice is that the command adds only into an existing category rather than
creating a missing category automatically. This keeps category creation rules explicit and avoids
silently introducing unintended categories due to typing errors.

#### Error handling and validation

Validation is split across the parser layer.

`AddCommandParser` rejects missing shared fields such as `item/` and `category/` before dispatching
to a category-specific parser.

`AddItemCommandParser` and the specialised parsers validate category-specific input. If required
fields are missing or malformed, they throw `DukeException` before an `AddItemCommand` is created.

`AddItemCommand` also performs execution-time checks. If `inventory.findCategoryByName(categoryName)`
returns `null`, the command throws a `DukeException` with the message
`Category not found: <categoryName>`. If the parsed item is unexpectedly `null`, it throws
`Item cannot be null.`

This layered approach ensures invalid input is rejected as early as possible, while still protecting
the command layer from invalid state.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Use a single generic item parser for every category.

This would reduce the number of parser classes, but it was rejected because different categories have
different required fields. A single parser would become difficult to understand and maintain.

Alternative 2: Let `AddItemCommand` parse the raw command string itself.

This was rejected because it mixes input interpretation with business logic. The current design keeps
commands focused on behaviour and leaves parsing to the parser layer.

Alternative 3: Create missing categories automatically during item addition.

This was rejected because it can hide user mistakes. Requiring the target category to exist makes the
inventory structure more predictable and prevents accidental category creation due to typos.

#### Current limitations

The current implementation has some limitations.

- Category dispatch in `AddCommandParser` is hard-coded using a `switch` statement.
- Supporting a new category requires updates in multiple places, including parser dispatch and the
  corresponding model subtype.
- Error messages depend on the specific parser branch and are not fully standardised across all item
  types.

These limitations are acceptable for the current project scope, but they may become more noticeable if
the number of item categories continues to grow.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Replace hard-coded category dispatch with a registry-based parser lookup.
- Standardise validation error messages across all category-specific parsers.
- Support optional default values for selected fields where domain rules permit them.
- Separate category definitions from parser code so new item types can be added with less wiring.

### List Feature

The product also supports displaying the current inventory using the `list` command.

This feature is important because users need a quick way to inspect the complete inventory after
adding, updating, deleting, or loading items from storage. Unlike targeted search commands, the list
operation provides a full snapshot of the current inventory state grouped by category.

For example, after a sequence of inventory changes, the user can enter `list` to review all categories
and their stored items in one output.

#### High-level design

At a high level, the feature is intentionally minimal and fits directly into the existing command
architecture:

1. The user enters a `list` command.
2. `Parser` recognises the command word and constructs a `ListCommand`.
3. `Duke` executes the command with the current `Inventory` and `UI`.
4. `ListCommand` delegates rendering to `UI.showInventory(inventory)`.
5. `UI` iterates through the inventory and prints the formatted listing to the user.

The main interaction for this flow is illustrated in [ListCommandMainFlow.puml](ListCommandMainFlow.puml).

This design was chosen because listing inventory does not require separate parsing logic beyond
recognising the command word. The command object acts mainly as a bridge between the parser and the UI.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `ListCommand`
- `Inventory`
- `Category`
- `UI`

The responsibilities of these classes are as follows:

- `Parser` detects the `list` command and returns a new `ListCommand`.
- `ListCommand` represents the list operation and triggers the display behaviour.
- `Inventory` provides access to the stored categories.
- `Category` provides the items and summary information for each category.
- `UI` formats and prints the inventory contents.

This design keeps the command itself lightweight. Since listing is a read-only operation, most of the
formatting logic appropriately lives in the UI layer instead of the command layer.

#### Command execution flow

When `ListCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory` and `ui` are not `null`.
2. Log that the inventory listing is being requested.
3. Call `ui.showInventory(inventory)`.
4. Inside the UI layer, retrieve all categories from the `Inventory`.
5. Iterate through each `Category`.
6. For each category, display its name, item count, and items.
7. Print the combined inventory listing to the user.

The command logic is intentionally short:

```java
logger.log(Level.INFO, "Listing inventory.");
ui.showInventory(inventory);
```

This reflects the design decision that `ListCommand` should trigger the operation, while formatting and
presentation remain the responsibility of the UI.

#### Why the feature is implemented this way

The most important design choice here is that `ListCommand` delegates almost all work to the UI layer
instead of assembling formatted output by itself.

This was chosen for two reasons.

First, it preserves separation of concerns. The command layer decides what action should happen, while
the UI layer decides how the result should be shown.

Second, it keeps the read-only command easy to maintain. Since `list` does not modify state, there is
no need for extra model logic or intermediate data transformation in the command itself.

This also makes the command consistent with other parts of the application where `UI` is responsible
for user-facing output.

#### Error handling and validation

The `list` command has minimal input validation because it takes no arguments.

`Parser` handles recognition of the command word. Once a `ListCommand` is created, the main runtime
checks are the assertions in `ListCommand.execute()` that ensure `inventory` and `ui` are not `null`.

Because the command is read-only and does not parse additional user arguments, there are fewer failure
modes compared with commands such as `add` or `find`.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Let `Parser` call `UI.showInventory(inventory)` directly without creating a command
object.

This was rejected because it breaks the existing command architecture. Keeping `ListCommand` preserves
a consistent parse-then-execute pipeline across user actions.

Alternative 2: Let `ListCommand` build a formatted string instead of delegating to `UI`.

This was rejected because presentation logic belongs more naturally in the UI layer. Mixing display
formatting into the command would weaken separation of concerns.

Alternative 3: Add filtering arguments directly to `list`.

This was rejected for now because filtered retrieval is already covered by specialised `find`
commands. Keeping `list` simple makes its behaviour predictable.

#### Current limitations

The current implementation has some limitations.

- The command always lists the full inventory and does not support optional filters or sorting.
- Output ordering depends on the current order of categories and items stored in memory.
- Formatting is tied to the current console UI implementation.

These limitations are acceptable for the current scope because the command is intended to provide a
simple full-inventory view.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support optional sorted views such as by category name or expiry date.
- Add pagination or condensed summaries for larger inventories.
- Reuse the same command object for alternate UI front ends if the presentation layer expands.
### Storage feature

This product includes a storage component that is responsible for persisting inventory data
between application runs.

This component is necessary because the inventory should not be lost when the program exits, as
users expect their items to remain available the next time they launch the application.

The storage feature solves this by writing the current inventory to a file and reconstructing it
when the application starts again.

#### Implementation

The storage mechanism is facilitated by `Storage`, which is responsible for saving the current `Inventory`
to file and loading it back into memory.

It supports two main operations:

- `save(Inventory inventory)` which writes the current inventory to the storage file.
- `load(Inventory inventory, UI ui)` which reads the storage file and reconstructs the inventory state.

The save format is text-based. Each item is stored on a separate line together with its common fields and any
additional fields required by its specific category. This allows all supported item types to be saved in a 
single format while preserving the extra data needed for each subtype.

#### Saving execution flow

When the application saves, `Storage` performs the following sequence:
1. Open the data file for writing.
2. Retrieve all categories from the inventory. 
3. Iterate through each `Category`.
4. Within each category, iterate through each `Item`.
5. Convert each item into a formatted text line.
6. Write the formatted line into the file.
7. Repeat until all items have been written.
8. Close the file.

The formatting logic is delegated to the `Item` class via polymorphism, instead of being centralized 
in the `Storage` class.

A simplified example of the base formatting logic is:
```java
public String toStorageString(String categoryName) {
    return "category/" + categoryName
            + " item/" + name
            + " bin/" + binLocation
            + " qty/" + quantity
            + " expiryDate/" + expiryDate;
}
```

Subclasses extend this behaviour by appending their own fields. For example, the `Fruit` class 
adds additional attributes:
```java
public String toStorageString(String categoryName) {
    return super.toStorageString(categoryName)
            + " size/" + size
            + " isRipe/" + isRipe;
}
```

This design ensures that each subclass is responsible for serializing its own data,
while the `Storage` class remains independent of specific item types.

#### Loading execution flow

When the application loads data from file, `Storage` performs the following sequence:
1. Ensure the storage file exists. If not, it is created automatically.
2. Read the file line by line. 
3. Extract the category from the line.
4. Use the category to determine the appropriate parsing method.
5. Convert the line into a `Command` using `AddItemCommandParser`.
6. Execute the command to reconstruct the item in the inventory.
7. Skip malformed lines where appropriate.
8. Continue until the entire file has been processed.

This design allows the application to reconstruct the same logical inventory state from the 
saved text data.

The loading process depends on the file format remaining consistent with the save format.
Since both directions are controlled by `Storage`, the implementation can ensure that the data
written is also the data that can be read back correctly.

This approach reuses existing parsing logic, ensuring consistency between user input handling and 
stored data reconstruction.

#### Error handling and validation

The storage component also handles cases where the save file is missing, or contains invalid data.

If the file does not exist, the application can start with an empty inventory instead of
crashing. This is because the absence of a save file may simply mean that the program is being
run for the first time.

If a line is malformed, the exception is caught and the line is skipped. A warning is 
logged and the user is informed via the UI, detailing the line that was skipped and the 
reason for skipping. 

#### Why the storage component is implemented this way

The simple text-based format is chosen instead of a more complex format such as JSON or database
for several reasons.

First, this keeps the implementation lightweight. The project does not require any external 
libraries or database setup, which makes the application easier to develop and test.

Second, the saved data is readable which is useful during debugging because we can inspect the 
contents of the file directly and verify whether items are being written correctly.

Third, the amount of data in the application is relatively small. Hence, a plain text file is 
sufficient and avoids unnecessary complexity.

#### Alternatives considered

Alternative 1: Store each category in a separate file.

This could improve file organization, especially if categories become large. It was rejected because
it would make file management more complicated and require the application to coordinate multiple 
save files instead of just one.

Alternative 2: Use JSON format.

This would make the file structure more standardized. However, it was rejected because it would
introduce additional complexity which is unnecessary for our project scope.

#### Current limitations

The current storage implementation has several limitations. 

1. The storage system relies on a fixed text format with prefixes such as `category/`, `item/`.
If the format is modified or corrupted, the parser may fail to reconstruct the item correctly.
2. During loading, each stored line is converted into a Command using `AddItemCommandParser` and executed.
While this ensures consistency with user input handling, it introduces coupling between storage logic and 
command parsing logic. Any changes in parsing behaviour may affect the loading process.
3. Malformed or corrupted lines are skipped during loading. While this prevents crashes, it may result
in data loss and incomplete reconstruction of the inventory.

For the current version of the application, these limitations are acceptable, but they may become
relevant if the system grows more complex.

#### Possible future improvements

The following enhancements can be considered to improve the storage component.

1. Introduce structured storage format, such as JSON. This will help to improve robustness and 
make format easier to extend.
2. The system could directly construct `Item` objects instead of converting stored lines into 
commands. This would reduce coupling and improve clarity of storage logic.
3. Instead of skipping malformed lines completely, the system could attempt partial recovery and
provide more detailed diagnostics to the user. This would reduce potential data loss.

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

### Testing add item

1. Ensure the target category already exists in the inventory, for example `fruits`.
2. Run `add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
3. Verify that the application shows a confirmation message for the added item.
4. Run `list`.
5. Verify that `apple` appears under the `fruits` category with the entered values.
6. Run `add category/unknown item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
7. Verify that the application shows `Category not found: unknown` or the corresponding category error.
8. Run an add command with a missing required field, for example `add category/fruits bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
9. Verify that the application shows the appropriate validation error for the missing field.

### Testing list command

1. Start the application with at least one category containing items.
2. Run `list`.
3. Verify that the application displays the full inventory grouped by category.
4. Start the application with an empty inventory.
5. Run `list`.
6. Verify that the application still handles the command successfully and shows the inventory view for the empty state.

### Testing find by expiry date

1. Add items with different expiry dates.
2. Run `find expiryDate/2026-3-25`.
3. Verify that only items expiring on or before `2026-3-25` are shown.
4. Run `find expiryDate/2026-3-01`.
5. Verify that the application shows `No items found expiring by 2026-3-01.` when there are no matches.
6. Run `find expiryDate/2026/3/25`.
7. Verify that the application shows the invalid date format error.
8. Run `find expiryDate/`.
9. Verify that the application shows the missing expiry date error.

### Testing storage

1. Add several items to the inventory.
2. Exit the application using the `bye` command.
3. Reopen the application.
4. Run `list`.
5. Verify that all items are restored with their correct category specific fields.
6. Exit the application using the `bye` command.
7. Modify the storage file so that a line is missing the `category/` field.
8. Reopen the application.
9. Verify that the application skips the malformed line and displays the appropriate error message.
10. Run `list`.
11. Verify that the remaining valid items are loaded correctly.
12. Exit the application using the `bye` command.
13. Delete the storage file before launching the application.
14. Verify that the application recreates the file automatically and starts without crashing.



