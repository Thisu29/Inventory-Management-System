package com.example.Controllers;

import Models.Dealer;
import Managers.dealerManager;
import Models.dealerItems;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.*;



//controller for dealer items
public class dealerItemController {
    //FXML components
    @FXML private ComboBox<String> dealerComboBox;
    @FXML private TableView<dealerItems> dealerItemsTable;
    @FXML private TableColumn<dealerItems, String> nameCol;
    @FXML private TableColumn<dealerItems, String> brandCol;
    @FXML private TableColumn<dealerItems, Double> priceCol;
    @FXML private TableColumn<dealerItems, Integer> quantityCol;


    @FXML
    public void initialize() {
        // Set up table columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("itmQuantity"));

        // Load dealer names into combo box
        loadDealerNamesToCombo();

        // Set up combo box selection
        dealerComboBox.setOnAction(event -> displaySelectedDealerItems());
    }

    //load dealer names to the comboBox
    private void loadDealerNamesToCombo() {
        List<Dealer> selectedDealers = dealerManager.getSelectedDealers();
        ObservableList<String> dealerNames = FXCollections.observableArrayList();

        //iterate through selected dealers
        for (Dealer dealer : selectedDealers) {
            dealerNames.add(dealer.getDealerName());
        }
        dealerComboBox.setItems(dealerNames);

        // display texts
        if (dealerNames.isEmpty()) {
            dealerComboBox.setPromptText("No dealers selected");
        } else {
            dealerComboBox.setPromptText("Select a dealer");
        }
    }

    //display the user selected dealer items
    @FXML
    private void displaySelectedDealerItems() {
        String selectedDealerName = dealerComboBox.getValue();

        //check if user selected a dealer
        if (selectedDealerName == null) {
            dealerItemsTable.getItems().clear();
            return;
        }

        // Find the selected dealer from the list
        List<Dealer> selectedDealers = dealerManager.getSelectedDealers();
        Dealer selectedDealer = null;

        for (Dealer dealer : selectedDealers) {
            if (dealer.getDealerName().equals(selectedDealerName)) {
                selectedDealer = dealer;
                break;
            }
        }

        if (selectedDealer != null) {
            // Display items for selected dealer only
            ObservableList<dealerItems> itemList = FXCollections.observableArrayList();
            itemList.addAll(selectedDealer.getDealerItems());
            dealerItemsTable.setItems(itemList);

        } else {
            dealerItemsTable.getItems().clear();
        }
    }
}