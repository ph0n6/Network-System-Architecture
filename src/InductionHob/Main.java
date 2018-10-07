package InductionHob;

import InductionHob.Controller.Controller;
import InductionHob.Controller.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import InductionHob.View.View;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    View view;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/InductionHob/resources/view.fxml").toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        view = fxmlLoader.<View>getController();
        ControllerInterface controller = new Controller(view);
        view.setController(controller);

        primaryStage.setTitle("Induction Hob");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
