package InductionHob.Controller;

import InductionHob.Model.CookingMode;

public class Controller implements ControllerInterface{


    public Controller(ViewInterface view) {
        this.view = view;
        init();
    }

    @Override
    public boolean increaseTemp() {
        return true;
    }

    @Override
    public boolean decreaseTemp() {
        return true;
    }

    @Override
    public boolean power(boolean status) {
        return true;
    }

    @Override
    public boolean lock(boolean status) {
        return true;
    }

    @Override
    public boolean setTimer(int minute) {
        return true;
    }

    @Override
    public boolean changeCookingMode(CookingMode cookingMode) {
        return true;
    }
}
