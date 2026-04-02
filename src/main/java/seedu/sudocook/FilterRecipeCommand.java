package seedu.sudocook;

public class FilterRecipeCommand extends Command {
    private final Integer maxTime;
    private final Integer maxCalories;

    public FilterRecipeCommand(Integer maxTime, Integer maxCalories) {
        super(false);
        this.maxTime = maxTime;
        this.maxCalories = maxCalories;
    }

    @Override
    public void execute(RecipeBook recipes) {
        recipes.filterRecipes(maxTime, maxCalories);
    }
}
