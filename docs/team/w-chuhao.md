# Wang Chuhao's Project Portfolio Page
## Project: InventoryDock
InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location through keyboard-driven workflows.

Given below are my contributions to the project.
- **New Feature**: Added the ability to add inventory items into an existing category using a structured command format.
  - What it does: Supports the `add` command with common-field parsing, category-based dispatch, subtype creation, duplicate-batch detection, and exception-aware validation.
  - Justification: `add` is a core write operation and required coordination across validation, item construction, category lookup, duplicate checks, and invalid input error handling.
  - Highlights: Added `AddItemCommand`; extended `Parser`, `AddCommandParser`, and `AddItemCommandParser`; preserved separation between parsing and execution; added exception handling and JUnit coverage.
  - Representative PRs: [#17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17), [#22](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/22), [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to list the full inventory grouped by category.
  - What it does: Supports the `list` command to show the full inventory after add, update, delete, or load operations.
  - Justification: This is a foundational read operation that helps users review current inventory state before other actions.
  - Highlights: Added `ListCommand`, integrated it into the parser flow, added tests, and aligned it with the application's exception-based handling.
  - Representative PR: [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to find items by category.
  - What it does: Supports `find category/CATEGORY` to retrieve all items in a given category.
  - Justification: This improves retrieval efficiency by using the category-based data model directly.
  - Highlights: Added `FindItemByCategoryCommand`, extended `FindItemParser`, handled missing versus empty categories, and added JUnit tests and validation paths.
  - Representative PRs: [#51](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/51), [#68](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/68)

- **New Feature**: Added the ability to find items by bin location.
  - What it does: Supports `find bin/BIN_INPUT` for exact bin, bin letter, or bin number matching.
  - Justification: This is practical for inventory retrieval because users often remember storage location before item name.
  - Highlights: Added `FindItemByBinCommand`, `BinLocationParser`, matching logic for three bin modes, and JUnit tests with exception handling for invalid formats.
  - Representative PR: [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77)

- **New Feature**: Added the ability to find items by quantity threshold.
  - What it does: Supports `find qty/QUANTITY` to retrieve items with quantity less than or equal to a threshold.
  - Justification: This supports low-stock review workflows more effectively than exact-quantity search.
  - Highlights: Added `FindItemByQtyCommand`, extended `FindItemParser`, reused quantity validation, implemented inclusive `<=` matching, and added JUnit tests and explicit exception handling.
  - Representative PRs: [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120), [#128](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/128)

- **Documentation**:
  - User Guide: Wrote or substantially updated the sections for `add`, `list`, `find category`, `find bin`, and `find qty`, covering command formats, examples, expected outcomes, and usage notes.
  - Developer Guide: Wrote or substantially updated the sections for Add Item, List Command, Find Item By Category, Find Item By Bin, and Find Item By Quantity, including feature design, implementation flow, validation, and manual testing.
  - UML diagrams: Added and updated the sequence, class, and object diagrams that document these features.

- **Code contributed**: [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=w09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=w-chuhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- **Project management**:
  - Helped set up the team's GitHub repository and collaboration workflow.

- **Enhancements to existing features**:
  - Added exception-based error handling across my inventory creation and retrieval features.
  - Helped maintain separation between parsing logic and command execution logic.
  - Reorganised test files to match the source package structure.
  - Helped fix Gradle and style-check issues during integration.

- **Community**:
  - PRs reviewed (with non-trivial review comments): [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28), [#32](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/32), [#39](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/39), [#50](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/50), [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55), [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57), [#63](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/63), [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86)
  - Reported bugs and suggestions for other teams in the class: helped team `CS2113-T11-2` identify bugs and improvement areas.

### Contributions to the User Guide (Extracts)

- [Adding an item](../UserGuide.md#adding-an-item-add): Documented the add command format, supported category-specific fields, examples, and duplicate-batch behaviour.
- [Listing all items](../UserGuide.md#listing-all-items-list): Documented the list command format and expected inventory display behaviour.
- [Finding items by category](../UserGuide.md#finding-items-by-category-find-category): Documented category-based lookup behaviour and expected results.
- [Finding items by bin](../UserGuide.md#finding-items-by-bin-find-bin): Documented supported bin input forms and matching behaviour.
- [Finding items by quantity](../UserGuide.md#finding-items-by-quantity-find-qty): Documented threshold-based quantity matching and expected results.

### Contributions to the Developer Guide (Extracts)

- [AddItemCommandParseRoutingFlow](../diagrams/sequence/AddItemCommandParseRoutingFlow.png): Sequence diagram showing add-command parse routing with the fruit example.
- [AddItemCommandExecutionDisplayFlow](../diagrams/sequence/AddItemCommandExecutionDisplayFlow.png): Sequence diagram showing add-command execution, duplicate checking, and UI reporting.
- [ListCommandMainFlow](../diagrams/sequence/ListCommandMainFlow.png): Sequence diagram showing the main list-command execution flow.
- [FindItemByCategoryCommandParseFlow](../diagrams/sequence/FindItemByCategoryCommandParseFlow.png): Sequence diagram showing parsing for category search.
- [FindItemByCategoryCommandMatchingFlow](../diagrams/sequence/FindItemByCategoryCommandMatchingFlow.png): Sequence diagram showing category lookup and matching.
- [FindItemByCategoryCommandDisplayFlow](../diagrams/sequence/FindItemByCategoryCommandDisplayFlow.png): Sequence diagram showing result display for category search.
- [FindItemByBinCommandParseFlow](../diagrams/sequence/FindItemByBinCommandParseFlow.png): Sequence diagram showing parsing for bin search.
- [FindItemByBinCommandMatchingFlow](../diagrams/sequence/FindItemByBinCommandMatchingFlow.png): Sequence diagram showing bin matching across supported input modes.
- [FindItemByBinCommandDisplayFlow](../diagrams/sequence/FindItemByBinCommandDisplayFlow.png): Sequence diagram showing result display for bin search.
- [FindItemByQtyCommandParseFlow](../diagrams/sequence/FindItemByQtyCommandParseFlow.png): Sequence diagram showing parsing for quantity search.
- [FindItemByQtyCommandMatchingFlow](../diagrams/sequence/FindItemByQtyCommandMatchingFlow.png): Sequence diagram showing inclusive `<=` quantity matching.
- [FindItemByQtyCommandDisplayFlow](../diagrams/sequence/FindItemByQtyCommandDisplayFlow.png): Sequence diagram showing result display for quantity search.
- [AddItemCommandClassDiagram](../diagrams/class/AddItemCommandClassDiagram.png): Class diagram for the add-item feature structure.
- [ListCommandClassDiagram](../diagrams/class/ListCommandClassDiagram.png): Class diagram for the list feature structure.
- [FindItemByCategoryCommandClassDiagram](../diagrams/class/FindItemByCategoryCommandClassDiagram.png): Class diagram for category-based search.
- [FindItemByBinCommandClassDiagram](../diagrams/class/FindItemByBinCommandClassDiagram.png): Class diagram for bin-based search.
- [FindItemByQtyCommandClassDiagram](../diagrams/class/FindItemByQtyCommandClassDiagram.png): Class diagram for quantity-threshold search.
- [AddItemCommandObjectDiagram](../diagrams/object/AddItemCommandObjectDiagram.png): Object snapshot for the add-item flow.
- [ListCommandObjectDiagram](../diagrams/object/ListCommandObjectDiagram.png): Object snapshot for the list feature.
- [FindItemByCategoryCommandObjectDiagram](../diagrams/object/FindItemByCategoryCommandObjectDiagram.png): Object snapshot for category search.
- [FindItemByBinCommandObjectDiagram](../diagrams/object/FindItemByBinCommandObjectDiagram.png): Object snapshot for bin search.
- [FindItemByQtyCommandObjectDiagram](../diagrams/object/FindItemByQtyCommandObjectDiagram.png): Object snapshot for quantity search.
