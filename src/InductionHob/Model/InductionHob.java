package InductionHob.Model;

public class InductionHob {
    private int temp = 0;
    private boolean on = false;
    private boolean lock = false;
    private String cookingMode = CookingMode.NORMAL;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getCookingMode() {
        return cookingMode;
    }

    public void setCookingMode(String cookingMode) {
        this.cookingMode = cookingMode;
    }

}
