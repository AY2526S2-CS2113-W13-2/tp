package seedu.sudocook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AddIngredientCommand.
 * Tests the functionality of adding ingredients to the inventory with and without expiry dates.
 */
public class AddIngredientCommandTest {
    private Inventory inventory;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void addIngredientCommand_withoutExpiry_addsIngredientSuccessfully() {
        AddIngredientCommand cmd = new AddIngredientCommand("Milk", 2, "liters");
        cmd.execute(inventory);

        assertEquals(1, inventory.size());
        assertTrue(getOutput().contains("Added:"));
        assertTrue(getOutput().contains("Milk"));
    }

    @Test
    public void addIngredientCommand_withExpiry_addsIngredientWithExpiryDate() {
        LocalDate expiryDate = LocalDate.of(2026, 4, 15);
        AddIngredientCommand cmd = new AddIngredientCommand("Chicken", 1.5, "kg", expiryDate);
        cmd.execute(inventory);

        assertEquals(1, inventory.size());
        assertTrue(getOutput().contains("Added:"));
        assertTrue(getOutput().contains("Chicken"));
        assertTrue(getOutput().contains("2026-04-15"));
    }

    @Test
    public void addIngredientCommand_multipleIngredients_addsAllSuccessfully() {
        AddIngredientCommand cmd1 = new AddIngredientCommand("Milk", 2, "liters");
        AddIngredientCommand cmd2 = new AddIngredientCommand("Eggs", 12, "pieces", LocalDate.of(2026, 4, 20));
        AddIngredientCommand cmd3 = new AddIngredientCommand("Flour", 5, "kg");

        cmd1.execute(inventory);
        cmd2.execute(inventory);
        cmd3.execute(inventory);

        assertEquals(3, inventory.size());
    }

    @Test
    public void addIngredientCommand_withDecimalQuantity_addsIngredientWithDecimalAmount() {
        AddIngredientCommand cmd = new AddIngredientCommand("Sugar", 2.5, "cups");
        cmd.execute(inventory);

        assertEquals(1, inventory.size());
        assertTrue(getOutput().contains("Added:"));
        assertTrue(getOutput().contains("Sugar"));
    }

    @Test
    public void addIngredientCommand_validExpiryFormat_addsWithCorrectDate() {
        LocalDate expiryDate = LocalDate.of(2026, 3, 10);
        AddIngredientCommand cmd = new AddIngredientCommand("Yogurt", 1, "carton", expiryDate);
        cmd.execute(inventory);

        assertEquals(1, inventory.size());
        assertTrue(getOutput().contains("Yogurt"));
    }

    private String getOutput() {
        return new String(output.toByteArray(), StandardCharsets.UTF_8);
    }
}
