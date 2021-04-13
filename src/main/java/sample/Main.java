package sample;

import bp.BreakPointAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    private BreakPointAlgorithm algorithm;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.addObserver(new DataFileObserver());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    // Solution to obtain primary stage from here:
    // https://stackoverflow.com/questions/15805881/how-can-i-obtain-the-primary-stage-in-a-javafx-application
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setPrimaryStage(Stage newPrimaryStage) {
        primaryStage = newPrimaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
