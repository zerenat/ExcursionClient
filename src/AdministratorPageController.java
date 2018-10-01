import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

//Class controlling the activity on Admin page
public class AdministratorPageController{
    private Stage mainWindow;
    private String userEmail;
    @FXML private ListView <ExcursionItem> excursionsList;
    @FXML private Button changeBookingButton;
    @FXML private Button cancelBookingButton;
    @FXML private Label bookedSeats;

    //Admin page controller singleton
    public static AdministratorPageController instance = new AdministratorPageController();

    //Get singleton
    public static AdministratorPageController getInstance(){
        return instance;
    }

    public void initialize(){
        changeBookingButton.setDisable(true);
        cancelBookingButton.setDisable(true);
        excursionsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExcursionItem>() {
            @Override

            public void changed(ObservableValue<? extends ExcursionItem> observable, ExcursionItem oldValue, ExcursionItem newValue) {
                //Optional<String> value = excursionsList.getSelectionModel().getSelectedItem().getBookedSeats().toString();
                bookedSeats.setText("Seats booked for selected: " + excursionsList.getSelectionModel().getSelectedItem().getBookedSeats());
                //changeBookingButton.setDisable(false);
            }
        });
    }

    public void toAdministratorPage()throws IOException{
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AdministratorPage.fxml"));
        mainWindow.setScene(new Scene(root, 500, 300));
        System.out.println("To Administrator page");
    }

    public void loadExcursions() {
        excursionsList.getItems().clear();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Excursions");
        dialog.setHeaderText("Enter a specific cruise ID or leave blank");
        Optional<String> result = dialog.showAndWait();

        if (result.get().equals("")) {
            System.out.println("Option1");
            excursionsList.getItems().clear();
            ExcursionItem.getInstance().resetExcursionList();
            ClientData.getInstance().setCruiseId("none");
            String message = "4" + "," + ClientData.getInstance().getCruiseId();
            Messenger.getInstance().clientOptions(message);
            excursionsList.setItems(ExcursionItem.getInstance().getExcursions());
        } else if(!result.get().equals("")) {
            System.out.println("Option2");
            excursionsList.getItems().clear();
            ExcursionItem.getInstance().resetExcursionList();
            ClientData.getInstance().setCruiseId(result.get());
            String message = "4" + "," + ClientData.getInstance().getCruiseId();
            Messenger.getInstance().clientOptions(message);
            excursionsList.setItems(ExcursionItem.getInstance().getExcursions());
        } else {
            System.out.println("Canceled");
        }
    }

    public void displayBookings(){
        String message = "";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update booking");
        dialog.setHeaderText("Enter customer E-mail address");
        Optional<String> result = dialog.showAndWait();
        if (result.get().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("There appears to be no e-mail address inserted. Please add an e-mail address to search for");
            alert.showAndWait();
            displayBookings();

        }else {
            //changeBookingButton.setDisable(false);
            //cancelBookingButton.setDisable(false);
            userEmail = result.get();
            message = "6" + "," + result.get();
            Messenger.getInstance().clientOptions(message);
            excursionsList.setItems(ExcursionItem.getExcursions());
        }
    }

    public void updateBooking()throws IOException {
        UpdateBookingsPageController.getInstance().changeBooking(userEmail, excursionsList.getSelectionModel().getSelectedItem().getExcursionId(),
                excursionsList.getSelectionModel().getSelectedItem().getBookedSeats(),
                excursionsList.getSelectionModel().getSelectedItem().getSeatsAvailable(), true);
        excursionsList.getSelectionModel().clearSelection();
        changeBookingButton.setDisable(true);
        bookedSeats.setText("");
        UpdateBookingsPageController.getInstance().updateValues(excursionsList);
    }

    public void logOut()throws IOException {
        String message = "3"+","+ClientData.getInstance().getLoggedUser();
        Messenger.getInstance().clientOptions(message);
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setScene(new Scene(root ,500, 300));
    }
}
