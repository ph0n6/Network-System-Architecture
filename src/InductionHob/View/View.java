package InductionHob.View;

import InductionHob.Controller.ControllerInterface;
import InductionHob.Model.CookingMode;
import InductionHob.Model.InductionHob;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable, ViewInterface {
    private ControllerInterface controller;
    private InductionHob inductionHob;

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
        inductionHob = new InductionHob();

        // Add option to cooking mode
        cookingMode.getItems().addAll("Fry", "Steam", "Boil", "Normal");
        powerButton.setSelected(inductionHob.isOn());
        lockButton.setSelected(inductionHob.isLock());
        tempDisplay.setText(String.valueOf(inductionHob.getTemp()));

        disableAllPower(true);
        //cookingMode.setPromptText("Use Cooking preset");
    }

    public void disableAllPower(boolean status) {
        cookingMode.setDisable(status);
        lockButton.setDisable(status);
        tempDisplay.setDisable(status);
        timerButton.setDisable(status);
        increaseTempButton.setDisable(status);
        decreaseTempButton.setDisable(status);
    }

    public void disableAllLock(boolean status) {
        cookingMode.setDisable(status);
        powerButton.setDisable(status);
        tempDisplay.setDisable(status);
        timerButton.setDisable(status);
        increaseTempButton.setDisable(status);
        decreaseTempButton.setDisable(status);
    }

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void increaseTemp(){
        if (!inductionHob.isOn()) {
            return;
        }
        if (inductionHob.isLock()) {
            System.out.println("System Locked");
            return;
        }
        if (inductionHob.getTemp() < 2200) {
            inductionHob.setTemp(inductionHob.getTemp() + 200);;
            System.out.println("Increase Temp");
        } else {
            System.out.println("Already Max Temp");
        }
        setTempDisplay();
    }

    @Override
    public void decreaseTemp(){
        if (!inductionHob.isOn()) {
            return;
        }
        if (inductionHob.isLock()) {
            System.out.println("System Locked");
            return;
        }
        if (inductionHob.getTemp() > 0) {
            inductionHob.setTemp(inductionHob.getTemp() - 200);
            System.out.println("Decrease Temp");
        } else {
            System.out.println("Already Min Temp");
        }
        setTempDisplay();
    }
    @Override
    public void setTempDisplay() {
        tempDisplay.setText(String.valueOf(inductionHob.getTemp()));
//        System.out.println("Set Temp display");
    }

    @Override
    public void setTimerDisplay() {
        System.out.println("Set Timer display");
    }

    @Override
    public void powerHob(){
        if (inductionHob.isLock()) {
            System.out.println("System Locked");
            return;
        }
        if (inductionHob.isOn()){
            inductionHob.setOn(!inductionHob.isOn());
            System.out.println("Power off");
            powerButton.setSelected(false);
            disableAllPower(true);
            controller.power(true);

            inductionHob = new InductionHob();

            // Add option to cooking mode
            cookingMode.getItems().addAll("Fry", "Steam", "Boil", "Normal");
            powerButton.setSelected(inductionHob.isOn());
            lockButton.setSelected(inductionHob.isLock());
            tempDisplay.setText(String.valueOf(inductionHob.getTemp()));
        }
        else {
            inductionHob.setOn(!inductionHob.isOn());
            System.out.println("Power on");
            powerButton.setSelected(true);
            disableAllPower(false);

            controller.power(true);
        }
    }

    @Override
    public void lockHob(){
        if (!inductionHob.isOn()) {
            lockButton.setSelected(false);
            return;
        }
        if (inductionHob.isLock()) {
            inductionHob.setLock(!inductionHob.isLock());
            System.out.println("Unlock");
            lockButton.setSelected(false);
            disableAllLock(false);
        }
        else {
            inductionHob.setLock(!inductionHob.isLock());
            System.out.println("Lock");
            lockButton.setSelected(true);
            cookingMode.setDisable(true);
            disableAllLock(true);
        }
    }

    @Override
    public void setTimer(){
        if (!inductionHob.isOn()) {
            return;
        }
        if (inductionHob.isLock()) {
            System.out.println("System Locked");
            return;
        }
        System.out.println("Set Timer");
    }

    @Override
    public void chooseCookingMode(){
        cookingMode.setOnAction(e -> setCookingMode(cookingMode.getValue().toString()));
    }

    public void setCookingMode(String cookingModeValue){


        if (!inductionHob.isOn()) {
            cookingMode.setValue(CookingMode.NORMAL);
            return;
        }
        if (inductionHob.isLock()) {
            System.out.println("System Locked");
            return;
        }
        inductionHob.setCookingMode(cookingModeValue);
        System.out.println("Set cooking mode to " + cookingModeValue);
    }

    @Override
    public void onPowerStatusChange(boolean status) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (status) {
                    inductionHob.setOn(true);
                    powerButton.setSelected(true);
                    disableAllPower(false);

//                    controller.power(true);
                } else {
                    inductionHob.setOn(false);
                    powerButton.setSelected(false);
                    disableAllPower(true);
//                    controller.power(true);

                    inductionHob = new InductionHob();

                    // Add option to cooking mode
                    cookingMode.getItems().addAll("Fry", "Steam", "Boil", "Normal");
                    powerButton.setSelected(inductionHob.isOn());
                    lockButton.setSelected(inductionHob.isLock());
                    tempDisplay.setText(String.valueOf(inductionHob.getTemp()));
                }
            }
        });
    }

}
