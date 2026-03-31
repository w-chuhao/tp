# User Guide

## Introduction

InventoryDock helps store managers keep track of inventory by category, quantity, expiry date, and bin location.
This guide covers the commands needed to add items, list all stored items, and search by category or bin.

## Quick Start

1. Ensure that you have Java 17 or above installed.
1. Download the latest version of the application jar.
1. Open a terminal in the project folder.
1. Run `java -jar duke.jar`.
1. Type a command and press Enter.

## Features 

Notes about the command format:

* Words in `UPPER_CASE` are placeholders you should replace with your own values.
* Command keywords are case-sensitive and should be typed exactly as shown.
* Item and category values are matched case-insensitively by the app.
* For `add`, fields must appear in the correct order.

### Adding an item: `add`
Adds a new item to an existing category.

Format:

`add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`

Common required fields:

* `category/` specifies the item category.
* `item/` specifies the item name.
* `bin/` specifies the bin location.
* `qty/` specifies the quantity as a positive integer.
* `expiryDate/` specifies the expiry date.

Supported categories and extra fields:

* `fruits`: `size/SIZE isRipe/BOOL`
* `snacks`: `brand/BRAND`
* `toiletries`: `brand/BRAND isLiquid/BOOL`
* `vegetables`: `isLeafy/BOOL`
* `drinks`: `brand/BRAND flavour/FLAVOUR`
* `icecream`: `flavour/FLAVOUR isDairyFree/BOOL`
* `sweets`: `brand/BRAND sweetnessLevel/LEVEL`
* `burger`: `isSpicy/BOOL pattyType/TYPE`
* `setmeal`: `mealType/TYPE foodSize/SIZE`
* `seafood`: `seafoodType/TYPE origin/ORIGIN`
* `meat`: `meatType/TYPE origin/ORIGIN`
* `petfood`: `petType/TYPE brand/BRAND`
* `accessories`: `type/TYPE material/MATERIAL`

Examples:

`add category/fruits item/apple bin/A-10 qty/40 expiryDate/2026-10-03 size/big isRipe/true`

`add category/toiletries item/shampoo bin/E-02 qty/15 expiryDate/2026-10-03 brand/Dove isLiquid/true`

Expected result:

* The item is added to the specified category.
* The app shows the item name, quantity, category, and bin location.

### Listing all items: `list`
Lists the entire inventory grouped by category.

Format: `list`

Example:

`list`

Expected result:

* All categories are shown in numbered order.
* Items under each category are listed with their details.
* If the inventory is empty, the app shows `Inventory is empty.`

### Finding items by category: `find category/...`
Shows all items stored in a specified category.

Format: `find category/CATEGORY`

Example:

`find category/fruits`

Expected result:

* If the category exists and contains items, the app lists all items in that category.
* If the category exists but has no items, the app shows `No items found in category: CATEGORY.`
* If the category does not exist, the app shows an error that the category was not found.

### Finding items by bin: `find bin/...`
Shows all items that match a bin search.

Format: `find bin/BIN_INPUT`

Valid `BIN_INPUT` values:

* Full bin location, for example `A-10`
* Bin letter only, for example `A`
* Bin number only, for example `10`

Examples:

`find bin/A-10`

`find bin/A`

`find bin/10`

Expected result:

* A full bin location matches only that exact bin.
* A bin letter matches all items in bins with that letter.
* A bin number matches all items in bins with that number.
* If no items match, the app shows `No items found in bin location: BIN_INPUT.`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Copy the application's data file to the same location on the other computer before starting the app there.

## Command Summary

* Add item:
  `add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`
* List all items:
  `list`
* Find items by category:
  `find category/CATEGORY`
* Find items by bin:
  `find bin/BIN_INPUT`
