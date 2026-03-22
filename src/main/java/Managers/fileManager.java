package Managers;

import Models.Items;
import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;

public class fileManager {
    //save data in file
    private static final String FILE_NAME = "inventory_data.txt";

    // Save all current items from memory to text file
    public static boolean saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Items item : inventoryManager.getItemsList()) {
                writer.write(formatItems(item));
                writer.newLine();
            }
            return true; //save items

            //display error message if needed
        } catch (IOException e) {
            showAlert("Error saving inventory to file: " + e.getMessage());
            return false; //save failed
        }
    }


    // Load items from text file to memory list
    public static void loadInventoryFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        //read tet file line by line and convert to item object
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Items item = parseItemFromLine(line);
                if (item != null) {
                    inventoryManager.addItem(item);
                }
            }
        //display error message
        } catch (IOException e) {
            showAlert("Error loading inventory: " + e.getMessage());
        }
    }

    // convert item object to formatted string
    private static String formatItems(Items item) {
        return String.join("|",
                item.getItemCode(),
                item.getItemName(),
                item.getItemBrand(),
                item.getItemCategory(),
                String.valueOf(item.getItemPrice()),
                String.valueOf(item.getItemQuantity()),
                item.getPurchasedDate().toString(),
                String.valueOf(item.getThresholdValue()),
                item.getItemImage()
        );
    }

    // convert a formatted string from file into an object
    private static Items parseItemFromLine(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            return new Items(
                    parts[0],
                    parts[1],
                    parts[2],
                    parts[3],
                    Double.parseDouble(parts[4]),
                    Integer.parseInt(parts[5]),
                    LocalDate.parse(parts[6]),
                    Integer.parseInt(parts[7]),
                    parts[8]
            );
        } catch (Exception e) {
            return null;
        }
    }

    //display alerts
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
