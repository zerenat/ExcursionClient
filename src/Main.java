import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainWindow;
    //private static Main instance = new Main();

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setTitle("ClientData");
        mainWindow.setScene(new Scene(root, 500, 300));
        mainWindow.show();
    }

    public static Stage getMainWindow() {
        return mainWindow;
    }

    @Override
    public void stop() throws Exception {
        if(Messenger.connected) { //Check if connection has been established before sending any info towards server
            String message = "3" + "," + ClientData.getInstance().getLoggedUser();
            Messenger.getInstance().clientOptions(message);
            super.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
