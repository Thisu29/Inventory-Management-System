package Models;

public class dealerItems {
    private String itemName;
    private String itemBrand;
    private int itmQuantity;
    private double itemPrice;


    public dealerItems(String itemName, String itemBrand, int itemQuantity, double itemPrice) {
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.itmQuantity = itemQuantity;
        this.itemPrice = itemPrice;

    }

    public String getItemName() {
        return itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItmQuantity() {
    return itmQuantity;}
}
