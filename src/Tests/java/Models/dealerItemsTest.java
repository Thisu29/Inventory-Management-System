package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dealerItemsTest {
    private dealerItems testItem;

    @BeforeEach
    void setUp() {
        //sample details
        testItem = new dealerItems("Choco Cookies", "Kitkat", 50, 120.00);
    }

    @Test
    void getItemName() {
        assertEquals("Choco Cookies", testItem.getItemName(), "Item name doesn't match");
    }

    @Test
    void getItemBrand() {
        assertEquals("Kitkat", testItem.getItemBrand(), "Item brand doesn't match");
    }

    @Test
    void getItemPrice() {
        assertEquals(120.00, testItem.getItemPrice(), 0.001, "Item price doesn't match");
    }

    @Test
    void getItmQuantity() {
        assertEquals(50, testItem.getItmQuantity(), "Item quantity doesn't match");
    }
}