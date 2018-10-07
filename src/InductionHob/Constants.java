package InductionHob;

import InductionHob.Model.CookingMode;

public class Constants {
    // device
    public static final String DEVICE_NAME = "InductionHob";
    public static final String MANUFACTURER_DETAILS = "ICT";
    public static final String MODEL_DETAILS = "NSA20181";
    public static final String MODEL_DESCRIPTION = "Induction Hob System";
    public static final String MODEL_NUMBER = "v69";

    // switch power service
    public static final String SWITCH_POWER = "SwitchPower";
    public static final String STATUS = "Status";
    public static final String GET_STATUS = "GetStatus";
    public static final String TARGET = "Target";
    public static final String SET_TARGET = "SetTarget";
    public static final String GET_TARGET = "GetTarget";
    public static final String NEW_TARGET_VALUE = "NewTargetValue";
    public static final String RET_TARGET_VALUE = "RetTargetValue";
    public static final String RESULT_STATUS = "ResultStatus";

    // audio control service
    public static final String COOKING_CONTROL = "Cooking Control";
    public static final String TEMP = "Temp";
    public static final String GET_TEMP = "GetTemp";
    public static final String SET_TEMP = "SetTemp";
    public static final String INCREASE_TEMP = "IncreaseTemp";
    public static final String DECREASE_TEMP = "DecreaseTemp";
    public static final String COOKING_MODE = "CookingMode";
    public static final String GET_COOKING_MODE = "GetCookingMode";
    public static final String SET_COOKING_MODE = "SetCookingMode";

    // play music service
    public static final String PLAY_MUSIC = "PlayMusic";
    public static final String PLAY_STATUS = "PlayStatus";
    public static final String GET_PLAY_STATUS = "GetPlayStatus";
    public static final String SET_PLAY_STATUS = "SetPlayStatus";
    public static final String TRACK_NO = "TrackNo";
    public static final String GET_TRACK_NO = "GetTrackNo";
    public static final String SET_TRACK_NO = "SetTrackNo";
    public static final String NEXT_TRACK = "NextTrack";
    public static final String PREV_TRACK = "PrevTrack";
    public static final String TIMER_VALUE = "TimerValue";
    public static final String GET_TIMER_VALUE = "GetTimerValue";
    public static final String SET_TIMER_VALUE = "SetTimerValue";
    public static final String TIMER_STATUS = "TimerStatus";
    public static final String GET_TIMER_STATUS = "GetTimerStatus";
    public static final String SET_TIMER_STATUS = "SetTimerStatus";

    // input argument
    public static final String IN = "In";
    public static final String OUT = "Out";

    // resources
    public static final String INDUCTION_HOB_IMAGE = "../resources/induction_hob.png";

    // min-max values
    public static final int TEMP_DEFAULT = 0;
    public static final int TEMP_MAX = 2200;
    public static final int TEMP_MIN = 0;
    public static final int TIMER_MIN = 0;
    public static final int TIMER_MAX = Integer.MAX_VALUE;
    public static final boolean TIMER_STATUS_DEFAULT = false;
    public static final boolean POWER_STATUS_DEFAULT = false;
    public static final String COOKING_MODE_DEFAULT = CookingMode.NORMAL;
    public static final int DEFAULT_TIMER_VALUE = 10;
}

