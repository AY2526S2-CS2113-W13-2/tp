
# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features

### Recommending recipes: `recommend-r`

The `recommend-r` command has two modes:

- **Ingredient-based** — finds recipes that use a specific ingredient you already have enough of.
- **Inventory-based** — finds every recipe that can be fully made using your current inventory.

<br>

#### Ingredient-based recommendation

Shows all recipes that contain a specific ingredient, provided your inventory holds at least the required quantity.

Format: `recommend-r n/INGREDIENT_NAME`

* `INGREDIENT_NAME` is case-insensitive (`egg`, `Egg`, and `EGG` all work).
* The ingredient must exist in your inventory; otherwise an error is shown.
* Only recipes whose required quantity of the ingredient is **≤** the amount you have are listed.

Examples:

`recommend-r n/egg`

`recommend-r n/Sugar`

Expected output (ingredient found, matching recipes exist):
```
Recipes containing egg:
1. Omelette
2. Fried Rice
```

Expected output (ingredient not in inventory):
```
Oops! Ingredient "beef" does not exist in inventory.
```

Expected output (ingredient in inventory but no recipe uses enough of it):
```
No recipes meet the requirement
```

<br>

#### Inventory-based recommendation

Shows all recipes that can be fully made with your current inventory — every required ingredient must be present in sufficient quantity.

Format: `recommend-r`

* A recipe is only listed if **all** of its ingredients are available in the inventory with enough quantity.
* Ingredient name matching is case-insensitive.

Example:

`recommend-r`

Expected output (some recipes are makeable):
```
Recipes you can make with your inventory:
1. Omelette
2. Mixue
```

Expected output (no recipe can be fully made):
```
No recipes can be made with the current inventory.
```

---

### Deleting a recipe: `delete-r`

Removes a recipe from the recipe book by its index.

Format: `delete-r INDEX`

* `INDEX` must be a positive integer corresponding to the recipe's position in the recipe list.
* Use `list-r` first to confirm the index of the recipe you want to delete.
* The deletion cannot be undone.

Examples:

`delete-r 1`

`delete-r 3`

Expected output (successful deletion):
```
Recipe 1 deleted successfully.
```

Expected output (index out of range):
```
Invalid index: Index 5 is out of range. Valid range: 1 to 3
```

Expected output (non-numeric index):
```
Oops! Invalid index for delete-r. Use: delete-r INDEX
```

---

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
