package InductionHob.View;

public interface ViewInterface {
    public void increaseTemp();
    public void decreaseTemp();
    public void setTempDisplay();
    public void setTimerDisplay();
    public void powerHob();
    public void lockHob();
    public void setTimer();
    public void chooseCookingMode();

    public void onPowerStatusChange(boolean status);
    public void onTempChange(int newValue);
}


