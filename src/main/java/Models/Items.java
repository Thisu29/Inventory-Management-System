package Models;

import java.time.LocalDate;

public class Items {
    // assign fields to store item details
    private String itemCode;
    private String itemName;
    private String itemBrand;
    private double itemPrice;
    private int itemQuantity;
    private String itemCategory;
    private LocalDate purchasedDate;
    private int thresholdValue;
    private String itemImage;



    // item Constructors
    public Items(String itemCode, String itemName, String itemBrand, String itemCategory, double itemPrice, int itemQuantity, LocalDate purchasedDate, int thresholdValue, String itemImage) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.purchasedDate = purchasedDate;
        this.thresholdValue = thresholdValue;
        this.itemImage = itemImage;
    }


    //getter and setter methods for each field
    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }
    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemCategory() {
        return itemCategory;
    }
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }
    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public int getThresholdValue() {
        return thresholdValue;
    }
    public void setThresholdValue(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getItemImage() {
        return itemImage;
    }
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }



    //converted to strings when displaying item info
    @Override
    public String toString() {
        return "Items{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemBrand='" + itemBrand + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                ", purchasedDate=" + purchasedDate +
                ", thresholdValue=" + thresholdValue +
                ", itemImage='" + itemImage + '\'' +
                '}';
    }
}









