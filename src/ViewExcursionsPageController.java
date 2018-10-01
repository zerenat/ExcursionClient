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

public class ViewExcursionsPageController extends LoggedInPageController{
    private String selectedExcursionName;
    private String requestedSeats;
    private String availableSeats;
    private String selectedExcursionsId;
    private Stage mainWindow;
    @FXML private ListView<ExcursionItem> excursionList;
    @FXML private Label bookingsDisplayArea;
    @FXML private Button bookingButton;

    public static ViewExcursionsPageController instance = new ViewExcursionsPageController();

    public static ViewExcursionsPageController getInstance() {
        return instance;
    }

    @FXML
    public void initialize(){
        bookingButton.setDisable(true);
        excursionList.setItems(ExcursionItem.getInstance().getExcursions());
        excursionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExcursionItem>() {
            @Override
            public void changed(ObservableValue<? extends ExcursionItem> observable, ExcursionItem oldValue, ExcursionItem newValue) {
                try {
                    bookingsDisplayArea.setText("Seats available for booking: "
                            + excursionList.getSelectionModel().getSelectedItem().getSeatsAvailable());
                    bookingButton.setDisable(false);
                    availableSeats = excursionList.getSelectionModel().getSelectedItem().getSeatsAvailable();
                    System.out.println("Seats avail : " + availableSeats);
                    selectedExcursionsId = excursionList.getSelectionModel().getSelectedItem().getExcursionId();

                } catch (NullPointerException e) {
                    //DEAL WITH ME
                }
            }
        });
    }

    @FXML
    public void backToLoggedInPage()throws IOException{
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/LoggedInPage.fxml"));
        mainWindow.setScene(new Scene(root,500,300));
        ExcursionItem.getInstance().resetExcursionList();
        System.out.println("LoggedIn");
    }

    //Book seats dialog. takes request from a user which is then checked and sent to the server if valid
    @FXML
    public void bookSeats() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Book seats");
        dialog.setContentText("Enter the amount of seats you would like to book");
        Optional<String> result = dialog.showAndWait();
        requestedSeats = result.get();

        if (Integer.parseInt(requestedSeats) <= Integer.parseInt(availableSeats) && Integer.parseInt(requestedSeats) >= 1) {
                    String message = "5" + "," + excursionList.getSelectionModel().getSelectedItem().getExcursionId()+","+
                            ClientData.getInstance().getLoggedUser() + "," + requestedSeats;
                    Messenger.getInstance().clientOptions(message);

                    excursionList.getSelectionModel().clearSelection();
                    bookingButton.setDisable(true);
                    System.out.println(message);
                } else {
                    System.out.println(result.get());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid entry. Please make sure the amount of requested seats won't exceed the available seats");
                    alert.showAndWait();
                }
    }
        //selectedExcursionName = excursionList.getSelectionModel().getSelectedItem().getName();
//        Dialog<String> dialog = new Dialog<>();
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("FXML/BookingDialog.fxml"));
//        try {
//            dialog.getDialogPane().setContent(fxmlLoader.load());
//        }catch (IOException e) {
//            System.out.println("Couldn't load the dialog");
//            e.printStackTrace();
//            return;
//        }
//        BookingDialogController controller = fxmlLoader.getController();
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
//        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
//            requestedSeats = controller.getNumberOfSeatsField();
//            try {
//                if (Integer.parseInt(requestedSeats) <= Integer.parseInt(availableSeats) && Integer.parseInt(requestedSeats) >= 1) {
//                    String message = "5" + "," + excursionList.getSelectionModel().getSelectedItem().getExcursionId()+","+
//                            ClientData.getInstance().getLoggedUser() + "," + requestedSeats;
//                    Messenger.getInstance().clientOptions(message);
//
//                    excursionList.getSelectionModel().clearSelection();
//                    bookingButton.setDisable(true);
//                    System.out.println(message);
//                } else {
//                    System.out.println(controller.getNumberOfSeatsField());
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("Invalid entry. Please make sure the amount of requested seats won't exceed the available seats");
//                    alert.showAndWait();
//                    event.consume();
//                }
//            }catch(NumberFormatException e){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Invalid entry. Please use numbers only");
//                alert.showAndWait();
//                event.consume();
//                controller.setNumberOfSeatsField("");
//            }
//        });
//        dialog.showAndWait();
//    }

    //Refresh information that is displayed
    @FXML
    public void updateValues() {
        try {
            ExcursionItem.getInstance().resetExcursionList();
            String message = "4"+","+ClientData.getInstance().getCruiseId();
            Messenger.getInstance().clientOptions(message);
            excursionList.setItems(ExcursionItem.getInstance().getExcursions());
    }catch(NullPointerException e) {

        }
    }
}




