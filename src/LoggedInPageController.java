import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggedInPageController {
    private Stage mainWindow;

    @FXML
    public void viewExcursions(){
        try {
            if(Messenger.connected == true) {
                String message = "4" + "," + ClientData.getInstance().getCruiseId();
                Messenger.getInstance().clientOptions(message);
                Parent root = FXMLLoader.load(getClass().getResource("FXML/ViewExcursionsPage.fxml"));
                mainWindow = Main.getMainWindow();
                mainWindow.setScene(new Scene(root, 500, 300));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void updateBookings(){
        try{
            String message = "6"+","+ClientData.getInstance().getLoggedUser();
            Messenger.getInstance().clientOptions(message);
            mainWindow = Main.getMainWindow();
            mainWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/UpdateBookingsPage.fxml")), 500, 300));
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void logOut()throws IOException {
        String message = "3"+","+ClientData.getInstance().getLoggedUser();
        Messenger.getInstance().clientOptions(message);
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setScene(new Scene(root ,500, 300));
    }
}
