import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExcursionItem {
    private String name;
    private String seatsAvailable;
    private String excursionId; //Used to store cruiseId @ viewExcursions & booked seats @ updateBookings
    private String bookedSeats;
    private static ObservableList<ExcursionItem> excursions = FXCollections.observableArrayList();
    public static ExcursionItem instance = new ExcursionItem();

    public ExcursionItem(){}

    public static ExcursionItem getInstance (){
        return instance;
    }

    public ExcursionItem(String name, String seatsAvailable, String bookedSeats, String excursionId){
        this.name = name;
        this.seatsAvailable = seatsAvailable;
        this.excursionId = excursionId;
        this.bookedSeats = bookedSeats;
    }

    public String getName() {
        return name;
    }

    public String getExcursionId(){
        return excursionId;
    }

    public String getSeatsAvailable(){
        return seatsAvailable;
    }

    public String getBookedSeats(){
        return bookedSeats;
    }

    public static ObservableList<ExcursionItem> getExcursions(){
        return excursions;
    }

    public void addToExcursionList(ExcursionItem item){
        excursions.add(item);
    }

    //Retrieved data (input) is in format "ExcursionName_BookedSeats,"
    //Once data is retrieved it is split into individual String and then once again split to
    //create excursion information items that are stored in an Arraylist for later use
    public void storeExcursionItems(String input){
        excursions.clear();
        String [] excursionInfo = input.split(",");
        for(int i = 0; i < excursionInfo.length; i++) {
            String[] processedInfo = excursionInfo[i].split("_");
            //String name, String seatsAvailable, String excursionId, String bookedSeats
            ExcursionItem item = new ExcursionItem(processedInfo[0], processedInfo[1], processedInfo[2], processedInfo[3]);
            item.addToExcursionList(item);
        }
        excursions.sorted();
    }

    public void resetExcursionList(){
        excursions.clear();
    }

    @Override
    public String toString() {
        return name;
    }
}
