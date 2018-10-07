package InductionHob.Controller;

import InductionHob.Model.CookingMode;

public interface ControllerInterface {
    boolean increaseTemp();
    boolean decreaseTemp();
    boolean power(boolean status);
    boolean lock(boolean status);
    boolean setTimer(int minute);
    boolean changeCookingMode(CookingMode cookingMode);
}
