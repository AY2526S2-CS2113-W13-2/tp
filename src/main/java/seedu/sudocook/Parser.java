package seedu.sudocook;

import static seedu.sudocook.SudoCook.DELETE_R_PREFIX;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());
    private final Ui ui;

    public Parser(Ui ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
        Command c;
        if (input.startsWith("delete-r")) {
            logger.log(Level.INFO, "Received delete-r request");
            try {
                int index = Integer.parseInt(input.substring(DELETE_R_PREFIX).trim());
                assert index > 0 : "Parsed index must be positive";
                c = new DeleteCommand(index);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid index format for delete-r");
                ui.printError("Invalid index for delete-r. Use: delete-r INDEX");
                return new Command(false);
            }
        } else if (input.startsWith("list-r")) {
            logger.log(Level.INFO, "Received list-r request");
            c = new ListCommand();
        } else if (input.startsWith("add-i")) {
            try {
                logger.log(Level.FINE, "Parsing add-i command: " + input);

                String addIngredientInput = input.substring("add-i".length()).trim();
                Pattern addIngredientPattern = Pattern.compile("n/([^q/]+)\\s+q/([\\d.]+)\\s+u/(.+)");
                Matcher addIngredientMatcher = addIngredientPattern.matcher(addIngredientInput);

                if (!addIngredientMatcher.matches()) {
                    String errorMsg = "Invalid add-i format. Use: add-i n/NAME q/QUANTITY u/UNIT";
                    logger.log(Level.WARNING, "add-i format mismatch. Input: " + addIngredientInput);
                    ui.printError(errorMsg);
                    return new Command(false);
                }

                String name = addIngredientMatcher.group(1).trim();
                String quantityStr = addIngredientMatcher.group(2).trim();
                String unit = addIngredientMatcher.group(3).trim();

                logger.log(Level.FINE, "Extracted add-i components - name: " + name
                        + ", quantity: " + quantityStr + ", unit: " + unit);

                // Validate name doesn't contain special characters
                if (!name.matches("[a-zA-Z0-9\\s]+")) {
                    String errorMsg = "Ingredient name should not contain special characters.";
                    logger.log(Level.WARNING, "Invalid ingredient name: " + name);
                    ui.printError(errorMsg);
                    return new Command(false);
                }

                // Parse and validate quantity
                double quantity;
                try {
                    quantity = Double.parseDouble(quantityStr);
                    if (quantity <= 0) {
                        String errorMsg = "Quantity must be a positive number.";
                        logger.log(Level.WARNING, "Invalid quantity: " + quantity);
                        ui.printError(errorMsg);
                        return new Command(false);
                    }
                } catch (NumberFormatException e) {
                    String errorMsg = "Invalid quantity format: " + quantityStr;
                    logger.log(Level.WARNING, errorMsg, e);
                    ui.printError(errorMsg);
                    return new Command(false);
                }

                logger.log(Level.INFO, "Successfully parsed add-i command - ingredient: " + name
                        + " (" + quantity + " " + unit + ")");
                c = new AddIngredientCommand(name, quantity, unit, ui);

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error parsing add-i command", e);
                ui.printError("Error processing add-i command: " + e.getMessage());
                return new Command(false);
            }
        } else if (input.startsWith("add-r")) {
            ArrayList<String> ingredients = new ArrayList<>();
            ArrayList<String> steps = new ArrayList<>();
            String addRecipeInput = input.substring("add-r".length()).trim();
            Pattern addRecipePattern = Pattern.compile("^(.*?)\\s+i/(.+?)\\s+s/(.+)$");
            Matcher addRecipeMatcher = addRecipePattern.matcher(addRecipeInput);

            if (!addRecipeMatcher.matches()) {
                ui.printError("Invalid add-r format.");
                return new Command(false);
            }

            String name = stripOptionalBraces(addRecipeMatcher.group(1).trim());
            String ingredientInput = addRecipeMatcher.group(2).trim();
            String stepInput = addRecipeMatcher.group(3).trim();

            Pattern tokenPattern = Pattern.compile("\\{[^}]*\\}|\\S+");
            Matcher ingredientMatcher = tokenPattern.matcher(ingredientInput);
            ArrayList<String> ingredientTokens = new ArrayList<>();
            while (ingredientMatcher.find()) {
                ingredientTokens.add(stripOptionalBraces(ingredientMatcher.group()));
            }

            for (int i = 0; i + 2 < ingredientTokens.size(); i += 3) {
                ingredients.add(ingredientTokens.get(i) + " "
                        + ingredientTokens.get(i + 1) + " "
                        + ingredientTokens.get(i + 2));
            }

            Matcher stepMatcher = tokenPattern.matcher(stepInput);
            while (stepMatcher.find()) {
                steps.add(stripOptionalBraces(stepMatcher.group()));
            }

            c = new AddRecipeCommand(name, ingredients, steps);


        } else {
            c = new Command(false);
            ui.printError("I don't recognise that command!");
        }
        return c;

    }


    private String stripOptionalBraces(String token) {
        if (token.startsWith("{") && token.endsWith("}")) {
            return token.substring(1, token.length() - 1);
        }
        return token;
    }
}
