package Managers;

import Models.Dealer;
import Models.dealerItems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class dealerManager {

    //call text file with dealer data
    private static final String FILE_NAME = "dealers_data.txt";
    private static List<Dealer> allDealers = new ArrayList<>(); //assign all dealers to an array List
    private static List<Dealer> selectedDealers = new ArrayList<>();


    // read dealers details from text file
    public static void readDealersFromFile() {
        allDealers.clear();

        //read the dealer file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                //split line using |
                String[] parts = line.split("\\|");

                //make sure there are 4 parts in one line
                if (parts.length < 4) {
                    System.out.println("Invalid dealer line: " + line);
                    continue;
                }

                //assign dealer details
                String dealerName = parts[0].trim();
                String dealerLocation = parts[1].trim();
                Integer dealerContact = Integer.parseInt(parts[2].trim());

                //extract dealer item details which is seperated by ;
                String[] itemsInfo = parts[3].split(";");
                List<dealerItems> itemsList = new ArrayList<>(); //create list

                for (String itemStr : itemsInfo) {
                    String[] itemDetails = itemStr.split(",");

                    //check if item has 4 fields, if not display error message
                    if (itemDetails.length < 4) {
                        System.out.println("Invalid item details for dealer: " + dealerName);
                        continue;
                    }

                    //extract item fields
                    String itemName = itemDetails[0].trim();
                    String itemBrand = itemDetails[1].trim();
                    int itemQuantity = Integer.parseInt(itemDetails[2].trim());
                    double itemPrice = Double.parseDouble(itemDetails[3].trim());

                    dealerItems item = new dealerItems(itemName, itemBrand, itemQuantity, itemPrice);
                    itemsList.add(item); //create object and add to items lisy
                }

                //create the dealer object including item list
                Dealer dealer = new Dealer(dealerName, dealerLocation, dealerContact, itemsList);
                allDealers.add(dealer);
            }

        } catch (IOException e) {
            System.out.println("Error reading dealers.txt file: " + e.getMessage());
        }
    }


    // method to Randomly select 4 dealers
    public static List<Dealer> selectRandomDealers() {
        selectedDealers.clear();
        readDealersFromFile();

        List<Dealer> tempList = new ArrayList<>(allDealers);
        Collections.shuffle(tempList); //shuffle dealers

        //select 4 dealers
        int numberToSelect = Math.min(4, tempList.size());
        selectedDealers.addAll(tempList.subList(0, numberToSelect));

        return selectedDealers;
    }

    // Return selected dealer list
    public static List<Dealer> getSelectedDealers() {
        return selectedDealers;
    }
}
