package InductionHob.Model.Service;

import InductionHob.Constants;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

import java.beans.PropertyChangeSupport;

public class CookingControl {
    private final PropertyChangeSupport propertyChangeSupport;

    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMinimum = Constants.TEMP_MIN,
            allowedValueMaximum = Constants.TEMP_MAX
    )
    private int temp;

    @UpnpStateVariable(
            defaultValue = "Normal",
            allowedValues = {"Normal", "Fry", "Boil", "Steam"}
    )
    private String cookingMode;


    public CookingControl() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public int getTemp() {
        return temp;
    }

    @UpnpAction
    public void setTemp(@UpnpInputArgument(name = Constants.IN) int temp) {
        if (temp >= Constants.TEMP_MIN && temp <= Constants.TEMP_MAX) {
            this.temp = temp;
            getPropertyChangeSupport().firePropertyChange(Constants.TEMP, null, null);
        }
    }

    @UpnpAction
    public void increaseVolume() {
        if (temp + 10 <= Constants.TEMP_MAX) {
            temp += 10;
            getPropertyChangeSupport().firePropertyChange(Constants.TEMP, null, null);
        }
    }

    @UpnpAction
    public void decreaseVolume() {
        if (temp - 10 >= Constants.TEMP_MIN) {
            temp -= 10;
            getPropertyChangeSupport().firePropertyChange(Constants.TEMP, null, null);
        }
    }

    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public String getAudioMode() {
        return cookingMode;
    }

    @UpnpAction
    public void setAudioMode(@UpnpInputArgument(name = Constants.IN) String cookingMode) {
        if (this.cookingMode.equalsIgnoreCase(cookingMode)) {
            this.cookingMode = cookingMode;
            getPropertyChangeSupport().firePropertyChange(Constants.COOKING_MODE, null, null);
        }
    }
}
