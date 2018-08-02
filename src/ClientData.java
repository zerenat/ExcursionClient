public class ClientData {
    private String cruiseId;
    private String loggedUser;
    private static ClientData instance = new ClientData();

    public static ClientData getInstance(){
        return instance;
    }

    public String getCruiseId(){
        return this.cruiseId;
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
}
