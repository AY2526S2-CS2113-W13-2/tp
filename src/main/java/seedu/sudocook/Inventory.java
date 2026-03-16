package seedu.sudocook;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages the inventory of ingredients.
 */
public class Inventory {
    private final ArrayList<Ingredient> ingredients;
    private static final Logger logger = Logger.getLogger(Inventory.class.getName());

    public Inventory() {
        this.ingredients = new ArrayList<>();
        logger.log(Level.FINE, "Inventory initialized");
    }

    /**
     * Adds an ingredient to the inventory.
     * If an ingredient with the same name and unit already exists, updates the quantity.
     *
     * @param ingredient The ingredient to add (must not be null)
     */
    public void addIngredient(Ingredient ingredient) {
        assert ingredient != null : "Ingredient to add cannot be null";
        assert !"".equals(ingredient.getName().trim()) : "Ingredient name cannot be empty";
        assert ingredient.getQuantity() > 0 : "Ingredient quantity must be positive";

        String ingredientKey = ingredient.getName().toLowerCase() + "_" + ingredient.getUnit().toLowerCase();

        for (Ingredient existing : ingredients) {
            if (existing.getName().equalsIgnoreCase(ingredient.getName()) 
                    && existing.getUnit().equalsIgnoreCase(ingredient.getUnit())) {
                double newQuantity = existing.getQuantity() + ingredient.getQuantity();
                existing.setQuantity(newQuantity);
                logger.log(Level.FINE, "Updated ingredient [" + ingredientKey + "] to quantity: " + newQuantity);
                return;
            }
        }

        ingredients.add(ingredient);
        logger.log(Level.FINE, "Added new ingredient [" + ingredientKey + "] with quantity: " + ingredient.getQuantity());
    }

    /**
     * Returns a copy of the ingredients list.
     *
     * @return ArrayList of ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * Returns the number of ingredients in the inventory.
     *
     * @return The number of ingredients
     */
    public int size() {
        return ingredients.size();
    }
}
