package Managers;

import Models.Items;

import java.util.ArrayList;
import java.util.List;


public class inventoryManager {
    //array list which stores inventory items in memory
    private static final List<Items> itemsList = new ArrayList<>();

    //method to add item to inventory list
    public static void addItem(Items item) {
        itemsList.add(item);
    }

    //method to delete an item from inventory list
    public static boolean deleteItem(Items item) {
        return itemsList.remove(item);
    }

    //return current list of inventory items
    public static List<Items> getItemsList() {
        return itemsList;
    }

}


