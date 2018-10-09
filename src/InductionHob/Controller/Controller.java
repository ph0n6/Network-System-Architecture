package InductionHob.Controller;

import InductionHob.Constants;
import InductionHob.Model.CookingMode;
import InductionHob.Model.Service.CookingControl;
import InductionHob.Model.Service.SwitchPower;
import InductionHob.View.ViewInterface;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.UDADeviceTypeHeader;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller implements ControllerInterface{
    private ViewInterface view;

    private Device device;

    private ActionExecutor actionExecutor;

    private UpnpService upnpService;

    private RegistryListener registryListener = new DefaultRegistryListener() {
        @Override
        public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
            System.out.println("Remote device detected.");
            if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Induction hob detected.");
                device = remoteDevice;
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
                upnpService.getControlPoint().execute(createCookingControlSubscriptionCallBack(getServiceById(device, Constants.COOKING_CONTROL)));
//                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));k(getServiceBy
            }
        }

        @Override
        public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
            if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Induction hob removed.");
                device = null;
            }
        }

        @Override
        public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
            System.out.println("Local device detected.");
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Induction hob detected.");
                device = localDevice;
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
                upnpService.getControlPoint().execute(createCookingControlSubscriptionCallBack(getServiceById(device, Constants.COOKING_CONTROL)));
//                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        power(Constants.POWER_STATUS_DEFAULT);
                        setTemp(Constants.TEMP_DEFAULT);
//                        setPlayStatus(Constants.PLAY_STATUS_DEFAULT);
//                        setMode(AudioMode.NORMAL);
                    }
                }, 500, TimeUnit.MILLISECONDS);
            }
        }

        @Override
        public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constants.MODEL_DETAILS)) {
                System.out.println("Induction hob removed.");
                device = null;
            }
        }
    };


    public Controller(ViewInterface view) {
        this.view = view;
        init();
    }

    public void init() {
        actionExecutor = new ActionExecutor(this);
        upnpService = new UpnpServiceImpl();
        upnpService.getRegistry().addListener(registryListener);
        try {
            upnpService.getRegistry().addDevice(createDevice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UDADeviceTypeHeader header = new UDADeviceTypeHeader(new UDADeviceType(Constants.DEVICE_NAME));
        upnpService.getControlPoint().search(header);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });
    }

    public LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {

        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier(Constants.DEVICE_NAME));

        DeviceType type = new UDADeviceType(Constants.DEVICE_NAME, 1);

        DeviceDetails details = new DeviceDetails(Constants.DEVICE_NAME,
                new ManufacturerDetails(Constants.MANUFACTURER_DETAILS),
                new ModelDetails(Constants.MODEL_DETAILS, Constants.MODEL_DESCRIPTION, Constants.MODEL_NUMBER));

        Icon icon = new Icon("image/png", 48, 48, 8, getClass().getResource(Constants.INDUCTION_HOB_IMAGE));

        LocalService<SwitchPower> switchPowerService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
        switchPowerService.setManager(new DefaultServiceManager(switchPowerService, SwitchPower.class));

        LocalService<CookingControl> cookingControlLocalService = new AnnotationLocalServiceBinder().read(CookingControl.class);
        cookingControlLocalService.setManager(new DefaultServiceManager(cookingControlLocalService, CookingControl.class));
//
//        LocalService<PlayMusic> playMusicService = new AnnotationLocalServiceBinder().read(PlayMusic.class);
//        playMusicService.setManager(new DefaultServiceManager(playMusicService, PlayMusic.class));

        return new LocalDevice(
                identity, type, details, icon,
                new LocalService[]{
                        switchPowerService,
                        cookingControlLocalService,
//                        playMusicService
                }
        );
    }

    private Service getServiceById(Device device, String serviceId) {
        if (device == null) {
            return null;
        }
        return device.findService(new UDAServiceId(serviceId));
    }

    public boolean setTemp(int value) {
        Service service = getServiceById(device, Constants.COOKING_CONTROL);
        if (service != null) {
            actionExecutor.setTemp(upnpService, service, value);
        }
        return true;
    }

    @Override
    public boolean increaseTemp() {
        Service service = getServiceById(device, Constants.COOKING_CONTROL);
        if (service != null) {
            actionExecutor.increaseTemp(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean decreaseTemp() {
        Service service = getServiceById(device, Constants.COOKING_CONTROL);
        if (service != null) {
            actionExecutor.decreaseTemp(upnpService, service);
        }
        return true;
    }

    @Override
    public boolean power(boolean status) {
        Service service = getServiceById(device, Constants.SWITCH_POWER);
        if (service != null) {
            actionExecutor.setPowerStatus(upnpService, service, status);
//            if (!status) {
//                Service playMusicService = getServiceById(device, Constants.PLAY_MUSIC);
//                if (playMusicService != null) {
//                    actionExecutor.setPlayStatus(upnpService, playMusicService, false);
//                }
//            }
        }
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

    private SubscriptionCallback createPowerSwitchSubscriptionCallBack(Service service) {
        return new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Power switch subscription created.");
//                setPowerStatus(Constants.POWER_STATUS_DEFAULT);
            }

            @Override
            protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {

            }

            @Override
            public void eventReceived(GENASubscription sub) {
                System.out.println("Event: " + sub.getCurrentSequence().getValue());
                Map<String, StateVariableValue> values = sub.getCurrentValues();
                for (String key : values.keySet()) {
                    System.out.println(key + " changed.");
                }
                if (values.containsKey(Constants.STATUS)) {
                    boolean value = (boolean) values.get(Constants.STATUS).getValue();
                    view.onPowerStatusChange(value);
//                    if (!value) {
//                        view.onPlayStatusChange(false);
//                    }
//                    view.onPlayStatusChange(value);
                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }

    private SubscriptionCallback createCookingControlSubscriptionCallBack(Service service) {
        return new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Cooking control subscription created.");
                setTemp(Constants.TEMP_DEFAULT);
//                setMode(AudioMode.NORMAL);
            }

            @Override
            protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {

            }

            @Override
            public void eventReceived(GENASubscription sub) {
                System.out.println("Event: " + sub.getCurrentSequence().getValue());
                Map<String, StateVariableValue> values = sub.getCurrentValues();
                for (String key : values.keySet()) {
                    System.out.println(key + " changed.");
                }
                if (values.containsKey(Constants.TEMP)) {
                    int value = (int) values.get(Constants.TEMP).getValue();
                    view.onTempChange(value);
                    System.out.println("New value: " + value);
//                } else if (values.containsKey(Constants.AUDIO_MODE)) {
//                    String value = (String) values.get(Constants.AUDIO_MODE).getValue();
//                    view.onModeChange(AudioMode.valueOf(value));
//                    System.out.println("New value: " + value);
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
            }
        };
    }
}
