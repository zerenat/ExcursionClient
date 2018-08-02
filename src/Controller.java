import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller{
    private Stage mainWindow;

    @FXML
    public void toRegisterPage() throws IOException{
        Stage mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/RegisterPage.fxml"));
        mainWindow.setScene(new Scene(root, 500,300));
        System.out.println("Register");
    }
    @FXML
    public void toLoginPage() throws IOException{
        Stage mainWindow = Main.getMainWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/LoginPage.fxml"));
        mainWindow.setScene(new Scene(root, 500, 300));
        System.out.println("Login");
    }
    @FXML
    public void toMainPage()throws IOException{
        Stage mainWindow = Main.getMainWindow();
        mainWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml")),500,300));
        System.out.println("Main");
    }
}
