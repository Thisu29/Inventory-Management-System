package com.example.Controllers;
import Managers.dealerManager;
import Models.Dealer;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.List;


//controller class for displaying dealer
public class dealerController {

    @FXML private TextArea displayArea;

    // Select and Display Names of 4 Random Dealers
    @FXML
    private void displaySelectedDealers() {
        List<Dealer> dealers = dealerManager.selectRandomDealers();
        displayArea.clear();
        displayArea.appendText("4 Random Dealers Selected:\n\n");

        for (Dealer dealer : dealers) {
            displayArea.appendText("- " + dealer.getDealerName() + "\n");
        }
    }

    // display dealer details and sort them
    @FXML
    private void displayDealerDetails() {
        List<Dealer> dealers = dealerManager.getSelectedDealers();

        // sort dealers according to their location using bubble sort
        for (int i = 0; i < dealers.size() - 1; i++) {
            for (int j = 0; j < dealers.size() - i - 1; j++) {
                if (dealers.get(j).getDealerLocation()
                        .compareToIgnoreCase(dealers.get(j + 1).getDealerLocation()) > 0) {
                    Dealer temp = dealers.get(j);
                    dealers.set(j, dealers.get(j + 1));
                    dealers.set(j + 1, temp);
                }
            }
        }
        displayArea.clear();


        //display dealer details
        for (Dealer dealer : dealers) {
            displayArea.appendText("Dealer Name: " + dealer.getDealerName() + "\n");
            displayArea.appendText("Contact No: " + dealer.getDealerContact() + "\n");
            displayArea.appendText("Location: " + dealer.getDealerLocation() + "\n");
            displayArea.appendText("-------------------------------\n");
        }
    }

    //open dealer items window upon user click
    @FXML
    private void displayDealerItems() throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("dealer-items-table.fxml"));
        javafx.scene.Parent root = loader.load();
        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.setTitle("Dealer Items");
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }
}

