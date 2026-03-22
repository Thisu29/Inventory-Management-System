package com.example.Controllers;

import Managers.fileManager;
import Managers.inventoryManager;
import Models.Items;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import javafx.stage.FileChooser;
import java.io.File;

//Controller for adding an item
public class addItemDetails {

    //FXML for user inputs
    @FXML private TextField addBrand;
    @FXML private TextField addCategory;
    @FXML private TextField addCode;
    @FXML private DatePicker addDate;
    @FXML private TextField addName;
    @FXML private TextField addPrice;
    @FXML private TextField addThreshold;
    @FXML private TextField addQuantity;


    private String selectedImagePath = ""; //Image path

    @FXML
    private void itemDetails(){
        try{
            // Validate all fields are filled (|| OR)
            if (addCode.getText().isEmpty() ||
                    addName.getText().isEmpty() ||
                    addBrand.getText().isEmpty() ||
                    addCategory.getText().isEmpty() ||
                    addPrice.getText().isEmpty() ||
                    addQuantity.getText().isEmpty() ||
                    addThreshold.getText().isEmpty() ||
                    addDate.getValue() == null) {

                //if fields are not filled display error message
                showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields!");
                return;
            }

            //get inputs
            String itemCode = addCode.getText().trim();
            String itemName = addName.getText().trim();
            String itemBrand = addBrand.getText().trim();
            String itemCategory = addCategory.getText().trim();
            double price = Double.parseDouble(addPrice.getText().trim());
            LocalDate date = addDate.getValue();
            int quantity = Integer.parseInt(addQuantity.getText().trim());
            int itemThreshold = Integer.parseInt(addThreshold.getText().trim());



            //check if the item code is duplicated
            boolean duplicateCode;
            duplicateCode = inventoryManager.getItemsList().stream()
                    .anyMatch(existingCode -> existingCode.getItemCode().equalsIgnoreCase(itemCode));

            if (duplicateCode) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Item Code", "Item with code '" + itemCode + "' already exists!");
                return;
            }

            // Create new item with constructors
            Items item = new Items(itemCode, itemName, itemBrand, itemCategory, price, quantity, date, itemThreshold, selectedImagePath);
            inventoryManager.addItem(item); //add items to inventory

            //print that item added successfully
            showAlert(Alert.AlertType.INFORMATION, "Success", "Item added successfully!");
            clearForm();


        //error handling (If numbers are not integers)
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numbers for price, quantity, and threshold.");
            e.printStackTrace();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the item: " + e.getMessage());
            e.printStackTrace();
        }
    }



    //let the user select and image from the folder
    @FXML
    private void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File("Images")); //choose images from the Images folder
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                String fileName = selectedFile.getName();
                selectedImagePath = "images/" + fileName;  //save relative path

                showAlert(Alert.AlertType.INFORMATION, "Image Selected",
                        "Image saved : " + selectedImagePath);


            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to copy image: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Image Selected", "Please select an image to attach.");
        }
    }


    //save items to text file after 'save Items' button click
    @FXML
    private void saveToFile() {
        boolean success = fileManager.saveInventoryToFile();

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Item saved to text file successfully!");
        }else{
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save item to text file!");
        }
    }


    //clear all fields after adding the item
    private void clearForm(){
        addCode.clear();
        addName.clear();
        addBrand.clear();
        addCategory.clear();
        addPrice.clear();
        addQuantity.clear();
        addThreshold.clear();
        addDate.setValue(null);
    }


    //display pop-up alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


