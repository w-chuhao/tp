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
