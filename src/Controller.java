import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller extends Main{
    private Scene mainMenuWindow;
    private Scene registerWindow;
    private Scene loginWindow;
    private Scene loggedInWindow;
    private Scene viewExcursionWindow;
    @FXML private TextField loginCabinNo;
    @FXML private PasswordField loginPassword;
    @FXML private TextField registerName;
    @FXML private TextField registerCabinNo;
    @FXML private TextField registerEmail;
    @FXML private TextField registerCruiseId;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField registerConfirmPassword;
    @FXML private ListView excursionList;

    @FXML
    public void toRegisterPage() throws IOException{
        Stage mainWindow = getMainWindow();
        registerWindow = new Scene(FXMLLoader.load(getClass().getResource("FXML/RegisterPage.fxml")),500,300);
        mainWindow.setScene(registerWindow);
        System.out.println("Register");
    }
    @FXML
    public void toLoginPage() throws IOException{
        Stage mainWindow = getMainWindow();
        loginWindow = new Scene(FXMLLoader.load(getClass().getResource("FXML/LoginPage.fxml")),500,300);
        mainWindow.setScene(loginWindow);
        System.out.println("Login");
    }
    @FXML
    public void toMainPage()throws IOException{
        Stage mainWindow = getMainWindow();
        mainMenuWindow = new Scene(FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml")),500,300);
        mainWindow.setScene(mainMenuWindow);
        System.out.println("Main");
    }
    @FXML
    public void toLoggedInPage()throws IOException{
        Stage mainWindow = getMainWindow();
        loggedInWindow = new Scene(FXMLLoader.load(getClass().getResource("FXML/LoggedInPage.fxml")),500,300);
        mainWindow.setScene(loggedInWindow);
        System.out.println("LoggedIn");
    }
    @FXML
    public void processRegisterDetails(){
        String name = registerName.getText();
        String cabinNo = registerCabinNo.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();
        String cruiseId = registerCruiseId.getText();
        String confirmPassword = registerConfirmPassword.getText();
        if(!cruiseId.isEmpty() && !cabinNo.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)){
            System.out.println("Passwords match");
            String message = "1"+","+name+","+email+","+cabinNo+","+password+","+cruiseId;
            System.out.println(message);
            Messenger.clientOptions(message);
        }
    }
    @FXML
    public void logIn(){
        String cabinNo = loginCabinNo.getText();
        String password = loginPassword.getText();
        String message = "2"+","+cabinNo +","+ password;
        System.out.println(message);
        Messenger.clientOptions(message);
    }
    @FXML
    public void logOut()throws IOException{
        toMainPage();
        Messenger.clientOptions("3");
    }
    @FXML
    public void viewExcursions()throws IOException{
        Messenger.clientOptions("4");excursionList.setItems(Client.getInstance().getExcursionInfo());
        Stage mainWindow = getMainWindow();
        viewExcursionWindow = new Scene(FXMLLoader.load(getClass().getResource("FXML/ViewExcursionsPage.fxml")),500,300);
        mainWindow.setScene(viewExcursionWindow);
        System.out.println("Available Excursions");
    }
}
