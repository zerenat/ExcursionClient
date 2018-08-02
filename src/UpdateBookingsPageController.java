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

public class UpdateBookingsPageController {
    private String excursionId;
    private String bookedSeats;
    private String requestedSeats;
    private String availableSeats;
    private String newAvailableSeats;
    private String userEmail;
    private Stage mainWindow;
    @FXML private ListView<ExcursionItem> viewBookings;
    @FXML private Label bookingInfo;
    @FXML private Label availableSeatsLabel;
    @FXML private Button changeBookingButton;
    @FXML private Button cancelBookingButton;

    public static UpdateBookingsPageController instance = new UpdateBookingsPageController();

    public static UpdateBookingsPageController getInstance(){
        return instance;
    }

    public void initialize(){
        userEmail = ClientData.getInstance().getLoggedUser();
        changeBookingButton.setDisable(true);
        cancelBookingButton.setDisable(true);
        viewBookings.setItems(ExcursionItem.getInstance().getExcursions());

        viewBookings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExcursionItem>() {
            @Override
            public void changed(ObservableValue<? extends ExcursionItem> observable, ExcursionItem oldValue, ExcursionItem newValue)throws NullPointerException {

                    bookingInfo.setText("Seats booked: " + viewBookings.getSelectionModel().getSelectedItem().getBookedSeats());
                    availableSeatsLabel.setText("Seats currently available: " + viewBookings.getSelectionModel().getSelectedItem().getSeatsAvailable());
                    changeBookingButton.setDisable(false);
                    cancelBookingButton.setDisable(false);
            }
        });
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    @FXML
    public void backToLoggedInPage()throws IOException {
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/LoggedInPage.fxml"));
        mainWindow.setScene(new Scene(root,500,300));
        ExcursionItem.getInstance().resetExcursionList();
        System.out.println("LoggedIn");
    }

    @FXML
    public void changeBooking(String userEmail, String excursionId, String bookedSeats, String availableSeats, boolean admin)throws IOException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter required amount of seats");
        Optional<String> result = dialog.showAndWait();

        this.userEmail = userEmail;
        this.excursionId = excursionId;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
        requestedSeats = result.get();
        newAvailableSeats = Integer.toString(Integer.parseInt(this.availableSeats) + Integer.parseInt(this.bookedSeats));

        try {
            if (Integer.parseInt(requestedSeats) >= 1 && Integer.parseInt(requestedSeats) <= Integer.parseInt(newAvailableSeats)) {
                //Selection code - ExcursionID - Customer email - Requested seats - Modified requested seats
                String message = "7" + "," + excursionId + "," + userEmail + "," + requestedSeats + "," + bookedSeats;
                Messenger.getInstance().clientOptions(message);
                //Update method is accessed from user login and admin login. when user updates a booking, boolean given to
                //the method is false while the admin side will be true
                if(!admin){
                    viewBookings.getSelectionModel().clearSelection();
                    changeBookingButton.setDisable(true);
                    cancelBookingButton.setDisable(true);
                    bookingInfo.setText("");
                    availableSeatsLabel.setText("");
                    updateValues(viewBookings);
                }
            } else {
                System.out.println(result.get());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid entry. Please make sure the amount of requested seats won't exceed the available seats");
                alert.showAndWait();
                //event.consume();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid entry. Please use numbers only");
            alert.showAndWait();
        }
    }

    @FXML
    public void callChangeBooking() throws IOException{
        setUserEmail(ClientData.getInstance().getLoggedUser());
        changeBooking(userEmail, viewBookings.getSelectionModel().getSelectedItem().getExcursionId(),
                viewBookings.getSelectionModel().getSelectedItem().getBookedSeats(),
                viewBookings.getSelectionModel().getSelectedItem().getSeatsAvailable(), false);
    }

    public void cancelBooking(ListView<ExcursionItem> listView){
        String message = "8"+","+listView.getSelectionModel().getSelectedItem().getExcursionId()+","
                +listView.getSelectionModel().getSelectedItem().getBookedSeats()+","
                +listView.getSelectionModel().getSelectedItem().getSeatsAvailable();
        Messenger.getInstance().clientOptions(message);
        updateValues(listView);
        changeBookingButton.setDisable(true);
        cancelBookingButton.setDisable(true);
    }

    public void callCancelBooking(){
        cancelBooking(viewBookings);
    }

    public void updateValues(ListView<ExcursionItem> listView) {
        try {
            ExcursionItem.getInstance().resetExcursionList();
            String message = "6"+","+userEmail;
            Messenger.getInstance().clientOptions(message);
            listView.setItems(ExcursionItem.getInstance().getExcursions());
        }catch(NullPointerException e) {

        }
    }
}
