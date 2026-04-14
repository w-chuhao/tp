# Yeo Si Zhao's Project Portfolio Page

## Project: InventoryDock

InventoryDock is a CLI based inventory management application that helps users manage categorized inventory items with details such as quantity, bin location, and expiry date.

### Code contributed 

[RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=YeoSiZhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

**Feature 1: Update existing items**

- **Parsing and command handling:** Implemented parsing for optional update fields and integrated them into the existing command pipeline.
- **Model update logic:** Designed the flow so the target item is identified by category and index while unchanged fields are preserved.
- **Validation reuse:** Reused shared validation for quantity and expiry date so `update` stays aligned with `add`.
- **Feature completeness:** Supported multiple field updates in one command rather than forcing item recreation.

**Feature 2: Find items by expiry date**

- **Command extension:** Extended the existing `find` flow to support date-based search instead of only text matching.
- **Date parsing and comparison:** Implemented proper date parsing and comparison so results are based on actual dates rather than string comparison.
- **Inventory-wide scanning:** Designed the feature to scan the full inventory against a user-supplied cutoff date.
- **Feature completeness:** Accepted a clear cutoff date and rejected invalid input reliably.

**Feature 3: Sort items within each category**

- **Sorting logic:** Implemented comparator-based sorting by name, expiry date, and quantity in `SortCommand`.
- **Design choice:** Kept sorting as a generated view instead of mutating stored inventory order.
- **Feature completeness:** Added three practical sort modes for inventory review.

**Feature 4: Exception hierarchy and error handling**

- **Custom exception design:** Created the project's exception hierarchy around `InventoryDockException`, including specific types such as `MissingArgumentException`, `InvalidCommandException`, `InvalidFilterException`, `InvalidDateException`, `InvalidIndexException`, `CategoryNotFoundException`, `ItemNotFoundException`, `DuplicateItemException`, and `StorageException`.
- **Error-handling alignment:** Updated parsers, commands, and storage paths to throw specific exceptions instead of overly generic ones.
- **Codebase consistency:** Cleaned up stale exception usage and aligned documented behaviour with the actual exception subtypes used in code.

### Contributions to team-based tasks

- Led the design of the project's initial architecture so teammates could build on a consistent command-driven structure.
- Helped define the responsibilities and interactions of key classes such as `Parser`, `Command`, `Inventory`, `Category`, `Item`, `Storage`, and `UI`.
- Established an extensible model design that relied on polymorphism in the `Item` and `InventoryDockException` hierarchy, so shared behaviour could be handled through common abstractions while specific item types could still define their own fields and constraints.
- Reviewed Java logging API documentation and helped standardise how loggers are declared across classes, as well as which logging levels should be used for different situations to ensure consistency.

### Contributions beyond the project team

- Reported 19 bugs during the PE dry run for another team's product.

### Review/mentoring contributions

- Reviewed [PR #17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17), with feedback on parser simplification, reducing duplicate error handling, and refactoring item creation toward a clearer factory-style design pattern.
- Reviewed [PR #10](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/10), with feedback on aligning model code with coding standards, including clearer boolean naming, consistency in state design, and basic defensive checks.
- Reviewed [PR #37](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/37), with feedback on simplifying validation logic by removing redundant checks after required-field validation.
- Reviewed [PR #77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77), with feedback on logging levels, separation of parsing and command logic, and import organisation.

### Contributions to the UG

- Documented the `update` feature.
- Documented the `find expiryDate` feature.
- Documented the `sort` feature.
- Documented exception-related features.

These updates covered command formats, examples, expected outcomes, usage notes, and error cases for the features I implemented.

### Contributions to the DG

- Wrote implementation details for the `update item`, `find by expiry date`, `sort`, and exception hierarchy features.
- Drew the following PlantUML diagrams:
- Class diagrams: [UpdateItemCommandClassDiagram](../diagrams/class/UpdateItemCommandClassDiagram.png), [FindItemByExpiryDateCommandClassDiagram](../diagrams/class/FindItemByExpiryDateCommandClassDiagram.png), [ExceptionHierarchyParserClassDiagram](../diagrams/class/ExceptionHierarchyParserClassDiagram.png), [ExceptionHierarchyStorageClassDiagram](../diagrams/class/ExceptionHierarchyStorageClassDiagram.png), and [ExceptionHierarchyInventoryClassDiagram](../diagrams/class/ExceptionHierarchyInventoryClassDiagram.png).
- Object diagrams: [UpdateItemCommandObjectDiagram](../diagrams/object/UpdateItemCommandObjectDiagram.png) and [FindItemByExpiryDateCommandObjectDiagram](../diagrams/object/FindItemByExpiryDateCommandObjectDiagram.png).
- Sequence diagrams: [UpdateItemCommandParseFlow](../diagrams/sequence/UpdateItemCommandParseFlow.png), [UpdateItemCommandApplyUpdatesFlow](../diagrams/sequence/UpdateItemCommandApplyUpdatesFlow.png), [UpdateItemCommandDuplicateCheckFlow](../diagrams/sequence/UpdateItemCommandDuplicateCheckFlow.png), [FindItemByExpiryDateCommandMatchingFlow](../diagrams/sequence/FindItemByExpiryDateCommandMatchingFlow.png), [FindItemByExpiryDateCommandParseFlow](../diagrams/sequence/FindItemByExpiryDateCommandParseFlow.png), and [FindItemByExpiryDateCommandDisplayFlow](../diagrams/sequence/FindItemByExpiryDateCommandDisplayFlow.png).
