package InductionHob.View;

import InductionHob.Controller.ControllerInterface;
import InductionHob.Model.CookingMode;
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

    private boolean on;

    private ControllerInterface controller;

    public void initialize(URL location, ResourceBundle resources) {
        // Add option to cooking mode
        cookingMode.getItems().addAll("Fry", "Steam", "Boil");
        cookingMode.setPromptText("Change Cooking mode");
    }

    public void increaseTemp(){
        controller.increaseTemp();
        System.out.println("Increase Temp");
    }

    public void decreaseTemp(){
        controller.decreaseTemp();
        System.out.println("Decrease Temp");
    }

    public void powerHob(){
        controller.power(true);
        System.out.println("Power off");
    }

    public void lockHob(){
        controller.lock(true);
        System.out.println("Lock");
    }

    public void setTimer(){
        controller.setTimer(1);
        System.out.println("Set Timer");
    }

    public void changeCookingMode() {
        controller.changeCookingMode(CookingMode.FRY);
        System.out.println("Change Cooking Mode");
    }

}
