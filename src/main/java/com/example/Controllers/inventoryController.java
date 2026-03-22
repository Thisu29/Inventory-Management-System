package com.example.Controllers;

import Managers.inventoryManager;
import Models.Items;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//controller for inventory table
public class inventoryController implements Initializable {

    @FXML private TableView<Items> itemsTable;
    @FXML private TableColumn<Items, LocalDate> DatePurchased;
    @FXML private TableColumn<Items, String> ItemCode;
    @FXML private TableColumn<Items, Double> Price;
    @FXML private TableColumn<Items, String> itemBrand;
    @FXML private TableColumn<Items, String> itemCategory;
    @FXML private TableColumn<Items, String> itemImage;
    @FXML private TableColumn<Items, Integer> itemQuantity;
    @FXML private TableColumn<Items, String> itemName;

    @FXML private Label totalCountLabel;
    @FXML private Label totalSumLabel;


    private final ObservableList<Items> itemsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle ignored) {
        // Initialize and assign table columns
        ItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        itemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        Price.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        DatePurchased.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        itemImage.setCellValueFactory(new PropertyValueFactory<>("itemImage"));


        // Configure image column to display image thumbnails
        itemImage.setCellValueFactory(new PropertyValueFactory<>("itemImage"));
        itemImage.setCellFactory(col -> new TableCell<Items, String>() {
                    private final ImageView imageView = new ImageView();

                    {
                        // Image properties
                        imageView.setFitWidth(70);
                        imageView.setFitHeight(60);
                        imageView.setPreserveRatio(true);
                        imageView.setSmooth(true);
                    }


                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);

                        if (empty || imagePath == null || imagePath.trim().isEmpty()) {
                            setGraphic(null);
                        } else {
                            try {
                                File imageFile = new File(imagePath);
                                if (imageFile.exists()) {
                                    Image image = new Image(imageFile.toURI().toString());
                                    imageView.setImage(image);
                                    setGraphic(imageView);
                                } else {
                                    // Show placeholder or "No Image" text if file doesn't exist
                                    setGraphic(null);
                                    setText("No Image");
                                }
                            } catch (Exception e) {
                                setGraphic(null);
                                setText("Invalid Image");
                                System.err.println("Error loading image: " + e.getMessage());
                            }
                        }
                    }
        });


        // Load data
        loadTableData();
    }

    //load data from inventory manager
    private void loadTableData() {
        itemsList.clear();
        itemsList.addAll(inventoryManager.getItemsList());
        sortByCategoryAndCode(); //sort items by category function
        itemsTable.setItems(itemsList);
        SummaryLabels(); //display total item count and sum function
    }


    //sort the items by category and then by item code
    private void sortByCategoryAndCode() {
        for (int i = 0; i < itemsList.size() - 1; i++) {
            for (int j = 0; j < itemsList.size() - i - 1; j++) {
                Items current = itemsList.get(j);
                Items next = itemsList.get(j + 1);

                // Compare categories
                int categoryCompare = current.getItemCategory().compareToIgnoreCase(next.getItemCategory());

                if (categoryCompare > 0 ||
                        //sort by Item code
                        (categoryCompare == 0 && current.getItemCode().compareToIgnoreCase(next.getItemCode()) > 0)) {
                    itemsList.set(j, next);
                    itemsList.set(j + 1, current);
                }
            }
        }
    }



   //display the summary
    private void SummaryLabels() {
        int totalItems = 0;
        double totalVal = 0.0;

        for (Items item : itemsList) {
            totalItems += item.getItemQuantity();
            totalVal += item.getItemPrice() * item.getItemQuantity();

        }

        //display summary
        totalCountLabel.setText("Total Item Count: " + totalItems);
        totalSumLabel.setText("Total Inventory Value: " + totalVal);
    }
}

