package InductionHob.Controller;

import InductionHob.Constants;
import InductionHob.Model.CookingMode;
import InductionHob.View.ViewInterface;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.message.header.UDADeviceTypeHeader;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.io.IOException;
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
//                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
//                upnpService.getControlPoint().execute(createAudioControlSubscriptionCallBack(getServiceById(device, Constants.AUDIO_CONTROL)));
//                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));
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
//                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constants.SWITCH_POWER)));
//                upnpService.getControlPoint().execute(createAudioControlSubscriptionCallBack(getServiceById(device, Constants.AUDIO_CONTROL)));
//                upnpService.getControlPoint().execute(createPlayMusicSubscriptionCallBack(getServiceById(device, Constants.PLAY_MUSIC)));
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
//                        setPowerStatus(Constants.POWER_STATUS_DEFAULT);
//                        setVolume(Constants.VOLUME_DEFAULT);
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

//        LocalService<SwitchPower> switchPowerService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
//        switchPowerService.setManager(new DefaultServiceManager(switchPowerService, SwitchPower.class));
//        LocalService<AudioControl> audioControlService = new AnnotationLocalServiceBinder().read(AudioControl.class);
//        audioControlService.setManager(new DefaultServiceManager(audioControlService, AudioControl.class));
//        LocalService<PlayMusic> playMusicService = new AnnotationLocalServiceBinder().read(PlayMusic.class);
//        playMusicService.setManager(new DefaultServiceManager(playMusicService, PlayMusic.class));

        return new LocalDevice(
                identity, type, details, icon,
                new LocalService[]{
//                        switchPowerService,
//                        audioControlService,
//                        playMusicService
                }
        );
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
