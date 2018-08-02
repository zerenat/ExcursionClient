import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    private Stage mainWindow;
    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;
    private static LoginPageController instance = new LoginPageController();

    public void initialize(){

    }

    public static LoginPageController getInstance(){
        return instance;
    }

    @FXML
    public void logIn(){
        String email = loginEmail.getText().trim();
        String password = loginPassword.getText().trim();
        if(!email.isEmpty() && !password.isEmpty()){
            String message = "2"+","+email+","+ password;
            Messenger.getInstance().clientOptions(message);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty fields detected. Please fill in the information");
            alert.showAndWait();
        }
    }

    @FXML
    public void toLoggedInPage()throws IOException {
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/LoggedInPage.fxml"));
        mainWindow.setScene(new Scene(root, 500,300));
        System.out.println("LoggedIn");
    }

    public void backToMainPage()throws IOException{
        mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setScene(new Scene(root,500,300));
        System.out.println("LoggedIn");
    }
}
