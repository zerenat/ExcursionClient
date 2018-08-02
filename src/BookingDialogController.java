import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookingDialogController {
    @FXML private Stage mainWindow;
    @FXML private TextField numberOfSeatsField;
    @FXML private Button seatConfirmationButton;
    @FXML private Button closeDialogButton;

    public void initialize(){

    }

    public String getNumberOfSeatsField(){
        return numberOfSeatsField.getText();
    }

    public void setNumberOfSeatsField(String value){
        numberOfSeatsField.setText(value);
    }
}

