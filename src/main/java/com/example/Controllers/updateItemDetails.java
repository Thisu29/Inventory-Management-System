package com.example.Controllers;

import Managers.fileManager;
import Managers.inventoryManager;
import Models.Items;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import java.io.File;


public class updateItemDetails implements Initializable {

    public Button updateImageButton;

    // Search section
    @FXML private TextField searchCodeField;


    // Update form fields
    @FXML private TextField updateCode;
    @FXML private TextField updateName;
    @FXML private TextField updateBrand;
    @FXML private TextField updateCategory;
    @FXML private TextField updatePrice;
    @FXML private TextField updateQuantity;
    @FXML private TextField updateThreshold;
    @FXML private DatePicker updateDate;

    // Buttons
    @FXML private Button updateButton;
    @FXML private Button clearButton;


    private Items currentItem = null;
    private String selectedImagePath = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateButton.setDisable(true); // Initially disable update button
    }

    //main method to search the item
    @FXML
    private void searchItem() {
        String searchCode = searchCodeField.getText().trim();

        //check if the search field is empty, if Yes display error
        if (searchCode.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter an item code to search!");
            return;
        }

        // Find item by code in the inventory
        currentItem = inventoryManager.getItemsList().stream()
                .filter(item -> item.getItemCode().equalsIgnoreCase(searchCode))
                .findFirst()
                .orElse(null);

        //if item is not found, display error
        if (currentItem == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No item found with code: " + searchCode);
            setFormEnabled(false);
            updateButton.setDisable(true);
            clearForm();
            return;
        }

        // if item is found - fill the form with current details
        populateForm(currentItem);
        setFormEnabled(true);
        updateButton.setDisable(false);
    }


    //method of updating item details
    @FXML
    private void updateItem() {

        try {
            // Validate all fields and check if all are filled
            if (updateCode.getText().isEmpty() ||
                    updateName.getText().isEmpty() ||
                    updateBrand.getText().isEmpty() ||
                    updateCategory.getText().isEmpty() ||
                    updatePrice.getText().isEmpty() ||
                    updateQuantity.getText().isEmpty() ||
                    updateThreshold.getText().isEmpty() ||
                    updateDate.getValue() == null) {

                showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all the fields!");
                return;
            }

            // Get updated values
            String newCode = updateCode.getText().trim();
            String newName = updateName.getText().trim();
            String newBrand = updateBrand.getText().trim();
            String newCategory = updateCategory.getText().trim();
            double newPrice = Double.parseDouble(updatePrice.getText().trim());
            int newQuantity = Integer.parseInt(updateQuantity.getText().trim());
            int newThreshold = Integer.parseInt(updateThreshold.getText().trim());
            LocalDate newDate = updateDate.getValue();

            // Check if item code is  changed and if new code already exists display error
            if (!newCode.equals(currentItem.getItemCode())) {
                boolean codeExists = inventoryManager.getItemsList().stream()
                        .anyMatch(item -> item.getItemCode().equalsIgnoreCase(newCode) && !item.equals(currentItem));

                if (codeExists) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Item code '" + newCode + "' already exists!");
                    return;
                }
            }

            // Confirm whether user really wants to update
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Update");
            confirmAlert.setHeaderText("Update Item");
            confirmAlert.setContentText("Are you sure you want to update this item?\n\n" +
                    "Current: " + currentItem.getItemCode());

            Optional<ButtonType> result = confirmAlert.showAndWait();

            //If yes then
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Update the item details
                currentItem.setItemCode(newCode);
                currentItem.setItemName(newName);
                currentItem.setItemBrand(newBrand);
                currentItem.setItemCategory(newCategory);
                currentItem.setItemPrice(newPrice);
                currentItem.setItemQuantity(newQuantity);
                currentItem.setThresholdValue(newThreshold);
                currentItem.setPurchasedDate(newDate);

                // If user selected a new image, update the item's image
                if (selectedImagePath != null && !selectedImagePath.trim().isEmpty()) {
                    currentItem.setItemImage(selectedImagePath);
                }


                fileManager.saveInventoryToFile(); //update the inventory details

                showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated successfully!"); //display confirm message

                // Update search field if code changed
                if (!newCode.equals(searchCodeField.getText().trim())) {
                    searchCodeField.setText(newCode);
                }
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numbers for price, quantity, and threshold.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the item: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //method to update the image
    @FXML
    private void updateImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select New Image");
        fileChooser.setInitialDirectory(new File("Images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            showAlert(Alert.AlertType.INFORMATION, "Image Selected", "Selected image: " + selectedImagePath);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Image Selected", "Image remains unchanged.");
        }
    }

    //clear form after updating
    @FXML
    private void clearForm() {
        updateCode.clear();
        updateName.clear();
        updateBrand.clear();
        updateCategory.clear();
        updatePrice.clear();
        updateQuantity.clear();
        updateThreshold.clear();
        updateDate.setValue(null);
        currentItem = null;
        updateButton.setDisable(true);

    }

    //when cancel button is clicked, close the window
    @FXML private void cancelAndClose(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //show all the details of the current details in the fields (before updating)
    private void populateForm(Items item) {
        updateCode.setText(item.getItemCode());
        updateName.setText(item.getItemName());
        updateBrand.setText(item.getItemBrand());
        updateCategory.setText(item.getItemCategory());
        updatePrice.setText(String.valueOf(item.getItemPrice()));
        updateQuantity.setText(String.valueOf(item.getItemQuantity()));
        updateThreshold.setText(String.valueOf(item.getThresholdValue()));
        updateDate.setValue(item.getPurchasedDate());
        selectedImagePath = item.getItemImage();
    }

    //enable the fields
    private void setFormEnabled(boolean enabled) {
        updateCode.setDisable(!enabled);
        updateName.setDisable(!enabled);
        updateBrand.setDisable(!enabled);
        updateCategory.setDisable(!enabled);
        updatePrice.setDisable(!enabled);
        updateQuantity.setDisable(!enabled);
        updateThreshold.setDisable(!enabled);
        updateDate.setDisable(!enabled);
        clearButton.setDisable(!enabled);
    }


    //alert pop - ups
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
