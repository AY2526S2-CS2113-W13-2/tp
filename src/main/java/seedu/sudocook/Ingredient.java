package seedu.sudocook;

/**
 * Represents an ingredient with a name, quantity, and unit.
 */
public class Ingredient {
    private final String name;
    private double quantity;
    private final String unit;

    /**
     * Constructs an Ingredient with the specified name, quantity, and unit.
     *
     * @param name The name of the ingredient (must not be null or empty)
     * @param quantity The quantity of the ingredient (must be positive)
     * @param unit The unit of measurement (must not be null or empty)
     */
    public Ingredient(String name, double quantity, String unit) {
        // Defensive assertions: validate pre-conditions
        assert name != null : "Ingredient name cannot be null";
        assert !name.trim().isEmpty() : "Ingredient name cannot be empty";
        assert quantity > 0 : "Ingredient quantity must be positive";
        assert unit != null : "Ingredient unit cannot be null";
        assert !unit.trim().isEmpty() : "Ingredient unit cannot be empty";

        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setQuantity(double quantity) {
        assert quantity > 0 : "New quantity must be positive";
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}
