package com.example.Controllers;

import Managers.fileManager;
import Managers.inventoryManager;
import Models.Items;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

//delete item method
public class deleteItems  {

    @FXML private TextField deleteItemField;

    //delete the item searched by item code
    @FXML
    private void deleteByCode() {
        String deleteCode = deleteItemField.getText().trim(); //get input from user

        //check if item code is empty
        if (deleteCode.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter an item code to delete!");
            return;
        }

        //search for the item code in the inventory
        Items itemToDelete = inventoryManager.getItemsList().stream()
                .filter(item -> item.getItemCode().equalsIgnoreCase(deleteCode))
                .findFirst()
                .orElse(null);

        //If no match is found, display error message
        if (itemToDelete == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No item found with code: " + deleteCode);
            return;
        }

        //confirm if the user really wish to delete the item
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Item");
        confirmAlert.setContentText("Are you sure you want to delete this item?\n\n" +
                "Code: " + itemToDelete.getItemCode());


        Optional<javafx.scene.control.ButtonType> result = confirmAlert.showAndWait();


        //if delete is confirmed, delete the item
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            boolean deleted = inventoryManager.deleteItem(itemToDelete);

            if (deleted) {
                fileManager.saveInventoryToFile(); //update the inventory
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item deleted successfully!");
                deleteItemField.clear();

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete item!");
            }
        }
    }

    //if cancel button is clicked, close the window
    @FXML
    private void cancelAndClose(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //alert pop-ups
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
