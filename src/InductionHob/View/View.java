package InductionHob.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    /* View Button */
    @FXML
    TextField tempDisplay;
    @FXML
    TextField timerDisplay;
    @FXML
    Button decreaseTempButton;
    @FXML
    Button increaseTempButton;
    @FXML
    ToggleButton powerButton;
    @FXML
    ToggleButton lockButton;
    @FXML
    Button timerButton;
    @FXML
    ComboBox cookingMode;

    public void initialize(URL location, ResourceBundle resources) {
        // Add option to cooking mode
        cookingMode.getItems().addAll("Fry", "Steam", "Boil");
        cookingMode.setPromptText("Change Cooking mode");
    }

    public void increaseTemp(){
        System.out.println("Increase Temp");
    }

    public void decreaseTemp(){
        System.out.println("Decrease Temp");
    }

    public void powerHob(){
        System.out.println("Power off");
    }

    public void lockHob(){
        System.out.println("Lock");
    }

    public void setTimer(){
        System.out.println("Set Timer");
    }


}
