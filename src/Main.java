import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MainPage.fxml"));
        mainWindow.setTitle("Client");
        mainWindow.setScene(new Scene(root, 500, 300));
        mainWindow.show();
    }

    public static Stage getMainWindow() {
        return mainWindow;
    }

    @Override
    public void stop() throws Exception {
        Messenger.clientOptions("3");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
