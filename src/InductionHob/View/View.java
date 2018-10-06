package InductionHob.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    /* View Button */
    @FXML
    Text tempDisplay;
    @FXML
    Text timerDisplay;
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
        cookingMode.getItems().addAll("Fry", "Steam", "Boil", "Custom");
        //cookingMode.setPromptText("Use Cooking preset");
    }
    @Override
    public void increaseTemp(){
        System.out.println("Increase Temp");
    }
    @Override
    public void decreaseTemp(){
        System.out.println("Decrease Temp");
    }
    @Override
    public void setTempDisplay() {System.out.println("Set Temp display");}
    @Override
    public void setTimerDisplay() {System.out.println("Set Timer display");}
    @Override
    public void powerHob(){
        System.out.println("Power off");
    }
    @Override
    public void lockHob(){
        System.out.println("Lock");
    }
    @Override
    public void setTimer(){
        System.out.println("Set Timer");
    }
    @Override
    public void chooseCookingMode(){
        cookingMode.setOnAction(e -> setCookingMode());
    }
    public void setCookingMode(){
        System.out.println("Set cooking mode to " + cookingMode.getValue());
    }
}
