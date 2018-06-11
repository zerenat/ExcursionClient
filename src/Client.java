import javafx.collections.ObservableList;

public class Client {
    private String cruiseId;
    private String loggedUser;
    private ObservableList<String> excursionInfo;
    private static Client instance = new Client();

    public static Client getInstance(){
        return instance;
    }

    public String getCruiseId(){
        return cruiseId;
    }

    public void setCruiseId(String cruiseId) {
        this.cruiseId = cruiseId;
    }

    public String getLoggedUser(){
        return this.loggedUser;
    }

    public void setLoggedUser(String loggedUser){
        this.loggedUser = loggedUser;
    }

    public ObservableList<String> getExcursionInfo() {
        return excursionInfo;
    }

    public void setExcursionInfo(ObservableList<String> excursionInfo) {
        this.excursionInfo = excursionInfo;
    }
}
