import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPageController{
    private Stage mainWindow;
    @FXML private TextField registerName;
    @FXML private TextField registerCabinNo;
    @FXML private TextField registerEmail;
    @FXML private TextField registerCruiseId;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField registerConfirmPassword;

    public void initialize(){

    }

    @FXML
    public void processRegisterDetails() {
        String name = registerName.getText().trim();
        String cabinNo = registerCabinNo.getText().trim();
        String email = registerEmail.getText().trim();
        String password = registerPassword.getText().trim();
        String cruiseId = registerCruiseId.getText().trim();
        String confirmPassword = registerConfirmPassword.getText().trim();
        if (!cruiseId.isEmpty() && !cabinNo.isEmpty() && !email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !confirmPassword.isEmpty()) {
            if(password.equals(confirmPassword)) {
                String message = "1" + "," + name + "," + email + "," + cabinNo + "," + password + "," + cruiseId;
                Messenger.getInstance().    clientOptions(message);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password mismatch. Please try again");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty fields detected. Please fill in the information");
            alert.showAndWait();
        }
    }

    public void backToMainPage()throws IOException {
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setScene(new Scene(root,500,300));
        System.out.println("Main Page");
    }
}
