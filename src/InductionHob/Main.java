package InductionHob;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import InductionHob.View.View;

public class Main extends Application {
    View view;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("resources/view.fxml"));
 //       view = fxmlloader.<View>getController;
 //       view.setController(controller);

        primaryStage.setTitle("Induction Hob");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
