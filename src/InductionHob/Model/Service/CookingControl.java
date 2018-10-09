package InductionHob.Model.Service;

import InductionHob.Constants;
import org.fourthline.cling.binding.annotations.*;
import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constants.COOKING_CONTROL),
        serviceType = @UpnpServiceType(value = Constants.COOKING_CONTROL, version = 1)
)
public class CookingControl {

    private final PropertyChangeSupport propertyChangeSupport;

    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMinimum = Constants.TEMP_MIN,
            allowedValueMaximum = Constants.TEMP_MAX,
            allowedValueStep = Constants.TEMP_STEP
    )
    private int temp;

/*    @UpnpStateVariable(
            defaultValue = "Normal",
            allowedValues = {"Normal", "Fry", "Boil", "Steam"}
    )
    private String cookingMode;*/


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
    public void increaseTemp() {
        if (temp + Constants.TEMP_STEP <= Constants.TEMP_MAX) {
            temp += Constants.TEMP_STEP;
            getPropertyChangeSupport().firePropertyChange(Constants.TEMP, null, null);
        }
    }

    @UpnpAction
    public void decreaseTemp() {
        if (temp - Constants.TEMP_STEP >= Constants.TEMP_MIN) {
            temp -= Constants.TEMP_STEP;
            getPropertyChangeSupport().firePropertyChange(Constants.TEMP, null, null);
        }
    }

/*    @UpnpAction(out = @UpnpOutputArgument(name = Constants.OUT))
    public String getCookingMode() {
        return cookingMode;
    }

    @UpnpAction
    public void setCookingMode(@UpnpInputArgument(name = Constants.IN) String cookingMode) {
        if (this.cookingMode.equalsIgnoreCase(cookingMode)) {
            this.cookingMode = cookingMode;
            getPropertyChangeSupport().firePropertyChange(Constants.COOKING_MODE, null, null);
        }
    }*/
}
