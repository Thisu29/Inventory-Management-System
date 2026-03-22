package com.example.Controllers;

import Managers.fileManager;
import Managers.inventoryManager;
import Models.Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


//main controller that handles UI and low-stock table
public class main implements Initializable {

    //UI components in the low-stock table
    @FXML private TableView<Items> itemsTable;
    @FXML private TableColumn<Items, String> viewBrand;
    @FXML private TableColumn<Items, String> viewCategory;
    @FXML private TableColumn<Items, LocalDate> viewDatePurchased;
    @FXML private TableColumn<Items, String> viewItemCode;
    @FXML private TableColumn<Items, String> viewItemName;
    @FXML private TableColumn<Items, Double> viewPrice;
    @FXML private TableColumn<Items, Integer> viewQuantity;
    @FXML private TableColumn<Items, Integer> viewThreshold;

    //show items in tableView
    private final ObservableList<Items> itemsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize and link table columns
        viewItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        viewItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        viewBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        viewCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        viewPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        viewQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        viewDatePurchased.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        viewThreshold.setCellValueFactory(new PropertyValueFactory<>("thresholdValue"));


        // Load data from text file to the table
        fileManager.loadInventoryFromFile(); //load from text file
        loadTableData();
    }

    //make sure that only items with quantity less than threshold values display in table
    private void loadTableData() {
        itemsList.clear();
        for (Items item : inventoryManager.getItemsList()) {
            if (item.getItemQuantity() < item.getThresholdValue()) {
                itemsList.add(item);
            }
        }
        itemsTable.setItems(itemsList);
    }


    //open add item window upon user click
    @FXML
    public void addItem(javafx.event.ActionEvent event) throws IOException {
        Stage addStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("add-items.fxml"));
        Scene scene = new Scene(root);
        addStage.setTitle("Add New Item");
        addStage.setScene(scene);
        addStage.setResizable(false);
        addStage.initModality(Modality.APPLICATION_MODAL); //block main window
        addStage.showAndWait();
        loadTableData(); //refresh table
    }


    //open delete item window upon user click
    public void deleteItem(javafx.event.ActionEvent event) throws IOException{
        Stage deleteStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("delete-items.fxml"));
        Scene scene = new Scene(root);
        deleteStage.setTitle("Delete Item");
        deleteStage.setScene(scene);
        deleteStage.setResizable(false);
        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.showAndWait();
        loadTableData(); //refresh table
    }


    //open update items upon user click
    public void updateItemDetails(javafx.event.ActionEvent event) throws IOException{
        Stage updateStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("update-items.fxml"));
        Scene scene = new Scene(root);
        updateStage.setTitle("Update Item");
        updateStage.setScene(scene);
        updateStage.setResizable(false);
        updateStage.initModality(Modality.APPLICATION_MODAL);
        updateStage.showAndWait();
        loadTableData();
    }


    //open Inventory window
    public void viewInventory(javafx.event.ActionEvent event) throws IOException {
        Stage viewStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        Scene scene = new Scene(root);
        viewStage.setTitle("View Inventory");
        viewStage.setScene(scene);
        viewStage.setResizable(false);
        viewStage.initModality(Modality.APPLICATION_MODAL);
        viewStage.showAndWait();
    }


    //open dealer section window
    @FXML
    private void dealerWindow() throws IOException {
        Stage dealerStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dealers-selected.fxml"));
        Parent root = loader.load();
        dealerStage.setTitle("Dealer Management");
        dealerStage.setScene(new Scene(root));
        dealerStage.initModality(Modality.APPLICATION_MODAL);
        dealerStage.showAndWait();
    }

    //Exit the program
    @FXML
    private void exitProgram() {
        System.exit(0);
    }
}
