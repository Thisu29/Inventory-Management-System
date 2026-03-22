package Models;
import java.util.List;


public class Dealer {

    //declare fields for dealer properties
    private String dealerName;
    private String dealerLocation;
    private Integer dealerContact;
    private List<dealerItems> dealerItems ;

    // dealer constructors
    public Dealer(String dealerName, String dealerLocation, Integer dealerContact, List<dealerItems> dealerItems) {
        this.dealerName = dealerName;
        this.dealerLocation = dealerLocation;
        this.dealerContact = dealerContact;
        this.dealerItems = dealerItems;
    }

    //dealer getter methods
    public String getDealerName() {
        return dealerName;
    }
    public String getDealerLocation() {
    return dealerLocation;
    }
    public Integer getDealerContact() {
        return dealerContact;
    }
    public List<dealerItems> getDealerItems() {
        return dealerItems;
    }

    //convert to strings for displaying purposes
    @Override
    public String toString() {
        return   dealerName + " | " + dealerLocation + " | " + dealerContact;
    }

}

