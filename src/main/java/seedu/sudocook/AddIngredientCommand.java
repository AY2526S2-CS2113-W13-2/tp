package seedu.sudocook;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Command to add an ingredient to the inventory.
 */
public class AddIngredientCommand extends Command {
    private final String name;
    private final double quantity;
    private final String unit;
    private final Ui ui;
    private static final Logger logger = Logger.getLogger(AddIngredientCommand.class.getName());

    /**
     * Constructs an AddIngredientCommand with the specified ingredient details.
     *
     * @param name The name of the ingredient (must not be null or empty)
     * @param quantity The quantity of the ingredient (must be positive)
     * @param unit The unit of measurement (must not be null or empty)
     * @param ui The UI instance for printing messages (must not be null)
     */
    public AddIngredientCommand(String name, double quantity, String unit, Ui ui) {
        super(false);
        // Defensive assertions: validate pre-conditions
        assert name != null : "Ingredient name cannot be null";
        assert !name.trim().isEmpty() : "Ingredient name cannot be empty";
        assert quantity > 0 : "Ingredient quantity must be positive";
        assert unit != null : "Unit cannot be null";
        assert !unit.trim().isEmpty() : "Unit cannot be empty";
        assert ui != null : "UI instance cannot be null";

        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.ui = ui;

        logger.log(Level.FINE, "AddIngredientCommand created: " + name + " (" + quantity + " " + unit + ")");
    }

    @Override
    public void execute(RecipeBook recipeBook) {
        // This method is not used for inventory operations
    }

    /**
     * Executes the command by adding the ingredient to the inventory.
     *
     * @param inventory The inventory to add the ingredient to (must not be null)
     * @throws IllegalArgumentException if inventory is null
     */
    public void execute(Inventory inventory) {
        assert inventory != null : "Inventory cannot be null during execution";

        try {
            // Validate ingredient parameters one more time before creating
            if (name == null || name.trim().isEmpty()) {
                throw new InvalidIngredientException("Ingredient name cannot be empty");
            }
            if (quantity <= 0) {
                throw new InvalidIngredientException("Ingredient quantity must be positive, got: " + quantity);
            }
            if (unit == null || unit.trim().isEmpty()) {
                throw new InvalidIngredientException("Unit cannot be empty");
            }

            Ingredient ingredient = new Ingredient(name, quantity, unit);
            inventory.addIngredient(ingredient);
            logger.log(Level.INFO, "Successfully executed AddIngredientCommand for: " + ingredient);
            ui.printMessage("Added: " + ingredient);

        } catch (InvalidIngredientException e) {
            logger.log(Level.WARNING, "Invalid ingredient data in AddIngredientCommand: " + e.getMessage(), e);
            ui.printError("Error adding ingredient: " + e.getMessage());
        } catch (AssertionError e) {
            logger.log(Level.SEVERE, "Assertion failed in AddIngredientCommand: " + e.getMessage(), e);
            ui.printError("System error: Ingredient validation failed");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in AddIngredientCommand execution", e);
            ui.printError("Unexpected error: " + e.getMessage());
        }
    }
}
