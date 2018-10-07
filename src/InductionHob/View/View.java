package InductionHob.View;

import InductionHob.Controller.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    //Variables of Hob
    int temp = 0;

    private ControllerInterface controller;

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

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void increaseTemp(){
        setTempDisplay();
        System.out.println("Increase Temp");
    }
    @Override
    public void decreaseTemp(){
        System.out.println("Decrease Temp");
        setTempDisplay();
    }
    @Override
    public void setTempDisplay() {
        tempDisplay.setText(String.valueOf(temp));
        System.out.println("Set Temp display");
    }
    @Override
    public void setTimerDisplay() {System.out.println("Set Timer display");}
    @Override
    public void powerHob(){
        if (!powerButton.isSelected()){
            System.out.println("Power off");
        }
        else {
            System.out.println("Power on");
        }
    }
    @Override
    public void lockHob(){
        if (lockButton.isSelected()) {
            System.out.println("Lock");
        }
        else{
            System.out.println("Unlock");
        }
    }
    @Override
    public void setTimer(){
        System.out.println("Set Timer");
    }
    @Override
    public void chooseCookingMode(){
        cookingMode.setOnAction(e -> setCookingMode(cookingMode.getValue().toString()));
    }
    public void setCookingMode(String cookingModeValue){
        System.out.println("Set cooking mode to " + cookingModeValue);
    }


}
