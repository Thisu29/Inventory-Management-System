package Managers;

import Models.Items;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class inventoryManagerTest {

    private Items item1;
    private Items item2;

    @BeforeEach
    public void setup() {
        inventoryManager.getItemsList().clear();  // Clear before each test to avoid interference

        item1 = new Items("I001", "Butter", "Highland", "Dairy", 1100.0, 5, LocalDate.now(), 10, "img1.jpg");
        item2 = new Items("I002", "Book", "Promate", "Stationary", 280.0, 10, LocalDate.now(), 3, "img2.jpg");
    }

    //test to check if add items to the list correctly happen
    @Test
    void addItem() {
        inventoryManager.addItem(item1); //add an item
        assertEquals(1, inventoryManager.getItemsList().size());
        assertTrue(inventoryManager.getItemsList().contains(item1));
    }

    //test to check if deleting an item correctly happens
    @Test
    void deleteItem() {
        inventoryManager.addItem(item1); //add item first
        inventoryManager.deleteItem(item1); //then delete item
        assertEquals(0, inventoryManager.getItemsList().size()); //list must be 0
        assertFalse(inventoryManager.getItemsList().contains(item1));
    }

    //test to check if an item can be deleted if it's not in the list
    @Test
    public void testDeleteNonExistentItem() {
        inventoryManager.addItem(item1); //add item 1
        boolean result = inventoryManager.deleteItem(item2); //try to delete item 2
        assertTrue(result);
        assertEquals(1, inventoryManager.getItemsList().size());
    }
}