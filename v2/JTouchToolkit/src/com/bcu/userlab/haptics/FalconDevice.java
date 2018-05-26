/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics;

import com.bcu.userlab.haptics.HDAL.HDAL;
import com.bcu.userlab.haptics.HDAL.HDLCallback;
import com.bcu.userlab.haptics.HDAL.HDLException;
import com.bcu.userlab.haptics.HDAL.HDLU;
import com.bcu.userlab.haptics.HDAL.HDLVersionInfo;
import com.uce.userlab.haptics.DeviceException;
import java.util.HashMap;

/**
 *
 * @author id108336
 */
public final class FalconDevice extends HapticDevice implements HDLCallback {
    private int hHandler;
    private int m_servoOp;
    
    private boolean lastButtonState;
    private boolean currentButtonState;
    private int lastButtons;
    private int currentButtons;
    
    private double[] deviceWorkspace;
    private double[] sceneWorkspace;
    private double[] transWorkspace;
    
    private String deviceModel;
    
    private HDLVersionInfo vsHDAL;
    private HDLVersionInfo vsDEVICE;
    private HDLVersionInfo vsSDK;
    private HDLVersionInfo vsCOMMS;
    private HDLVersionInfo vsOS;
    private HDLVersionInfo vsGRIP;
    
    private double[] tmpPos;
    
    private double[] tmpForce;
    private boolean forceRamping;
    private double rampingRate;
    
    private FalconDevice(int index) throws DeviceException {
        try {
            tmpForce = new double[] {0, 0, 0};
            rampingRate = FalconDevice.DEFAULT_FORCE_RAMPING_RATE;
            forceRamping = false;
            hHandler = HDAL.hdlInitIndexedDevice(index);
            HDAL.hdlStart();
            this.makeCurrent();
            this.m_servoOp = HDAL.hdlCreateServoOp(this, new Object[0], false);
            
            this.makeCurrent();
            this.updateProperties();
            this.updateStates();
            
            //Reset state while loop starts up
            this.setButtonPressed(false);
            this.setButtonsPressed(FalconDevice.NO_BUTTONS);
        } catch (HDLException ex) {
            throw new DeviceException(this, ex.getMessage(), 
                    ex.getErrorCode(), ex.getErrorCode());
        }
    }

    @Override
    protected void fireUpdateEvent(String message, int eventCode) {
        super.listenerSupport.fireDeviceUpdate(message, eventCode, 
                this.getCurrentPosition(), this.getButtonsPressed());
    }
    @Override
    protected void fireInputEvent(String message, int eventCode) {
        super.listenerSupport.fireDeviceInput(message, eventCode, 
                this.getCurrentPosition(), this.getButtonsPressed());
    }
    @Override
    protected void fireDeviceManagementError(String message, int eventCode, 
            DeviceException exc) {
        super.listenerSupport.fireDeviceManagementError(message, eventCode, 
                this.getCurrentPosition(), this.getButtonsPressed(), exc);
    }

    @Override
    public String getVendor() {
        return "Novint";
    }
    @Override
    public String getVersion() {
        return (this.getDeviceVersion() != null) ? 
            "v" + this.getDeviceVersion().getMajorVersion() + "." + this.getDeviceVersion().getMinorVersion() : 
            FalconDevice.UNKNOWN_VALUE;
    }
    @Override
    public String getDevice() {
        return (deviceModel != null && deviceModel.compareTo("") != 0) ? 
            deviceModel : FalconDevice.UNKNOWN_VALUE;
    }
    @Override
    public String getDeviceID() {
        return FalconDevice.UNKNOWN_VALUE;
    }

    public HDLVersionInfo getHDALVersion() {
        return (vsHDAL != null) ? vsHDAL : FalconDevice.UNAVAILABLE_VERSION;
    }
    public HDLVersionInfo getDeviceVersion() {
        return (vsDEVICE != null) ? vsDEVICE : FalconDevice.UNAVAILABLE_VERSION;
    }
    public HDLVersionInfo getSDKVersion() {
        return (vsSDK != null) ? vsSDK : FalconDevice.UNAVAILABLE_VERSION;
    }
    public HDLVersionInfo getCommsVersion() {
        return (vsCOMMS != null) ? vsCOMMS : FalconDevice.UNAVAILABLE_VERSION;
    }
    public HDLVersionInfo getOSVersion() {
        return (vsOS != null) ? vsOS : FalconDevice.UNAVAILABLE_VERSION;
    }
    public HDLVersionInfo getGripVersion() {
        return (vsGRIP != null) ? vsGRIP : FalconDevice.UNAVAILABLE_VERSION;
    }
    
    public double getForceRampingRate() {
        return rampingRate;
    }
    public void setForceRampingRate(double rate) {
        rampingRate = rate;
    }
    public void setForceRamping(boolean ramp) {
        forceRamping = ramp;
    }
    public boolean isForceRamping() {
        return forceRamping;
    }
    
    @Override
    protected void setPosition(double[] pos) {
        if(pos.length == 3)
        {
            double[] p = new double[]{0,0,0};
            p[0] = pos[0] * this.transWorkspace[0] + 
                   pos[1] * this.transWorkspace[4] + 
                   pos[2] * this.transWorkspace[8] + this.transWorkspace[12];
            p[1] = pos[0] * this.transWorkspace[1] + 
                   pos[1] * this.transWorkspace[5] + 
                   pos[2] * this.transWorkspace[9] + this.transWorkspace[13];
            p[2] = pos[0] * this.transWorkspace[2] + 
                   pos[1] * this.transWorkspace[6] + 
                   pos[2] * this.transWorkspace[10] + this.transWorkspace[14];
            pos = p;
        }

        super.setPosition(pos);
    }
    
    private void setButtonPressed(boolean state) {
        this.lastButtonState = this.isButtonsPressed();
        this.currentButtonState = state;
    }
    public boolean isButtonsPressed() {
        return currentButtonState;
    }
    private void setButtonsPressed(int buttons) {
        this.lastButtons = this.getButtonsPressed();
        this.currentButtons = buttons;
    }
    public int getButtonsPressed() {
        return currentButtons;
    }
    public int getLastButtonsPressed() {
        return lastButtons;
    }
    
    public double[] getWorkspace() {
        return (this.sceneWorkspace != null && this.sceneWorkspace.length == 6) ?
            new double[] {sceneWorkspace[0], sceneWorkspace[1], sceneWorkspace[2], 
                            sceneWorkspace[3], sceneWorkspace[4], sceneWorkspace[5]} :
            FalconDevice.DEFAULT_WORKSPACE;
    }
    public void setWorkspace(double[] workspace) {
        this.sceneWorkspace = (workspace != null && workspace.length == 6) ? 
            new double[] {workspace[0], workspace[1], workspace[2], 
                            workspace[3], workspace[4], workspace[5]} :
            FalconDevice.DEFAULT_WORKSPACE;
        this.updateWorkspace();
        this.fireUpdateEvent("Workspace altered", FalconDevice.EVENT_UPDATE);
    }
    
    public void makeCurrent() throws DeviceException {
        try {
            HDAL.hdlMakeCurrent(hHandler);
        } catch (HDLException ex) {
            throw new DeviceException(this, ex.getMessage(), 
                    ex.getErrorCode(), ex.getErrorCode());
        }
    }
    
    public int HDLServoOp(Object[] obj) {
        try {
            if(super.isEnabled()) {
                try {
                    this.updateStates();
                    this.fireInputEvents();

                    super.listenerSupport.fireDeviceCallback("Servo Callback", 0, 
                            this.getCurrentPosition(), this.getButtonsPressed());

                    this.makeCurrent();
                    if(this.forceRamping) {
                        this.tmpForce = new double[] {
                            CLAMP_FORCE(this.rampingRate, tmpForce[0], this.getForce()[0]),
                            CLAMP_FORCE(this.rampingRate, tmpForce[1], this.getForce()[1]),
                            CLAMP_FORCE(this.rampingRate, tmpForce[2], this.getForce()[2])};
                    } else {
                        tmpForce = this.getForce();
                    }
                    
                    HDAL.hdlSetToolForce(tmpForce);//this.getForce());
                } catch (HDLException ex) {
                    throw new DeviceException(this, ex.getMessage(), 
                        ex.getErrorCode(), ex.getErrorCode());
                } 
            }
        } catch (DeviceException exc) {
            this.fireDeviceManagementError(
                    "Haptic Device Management Exception within Servo Loop", 
                    FalconDevice.EVENT_EXCEPTION, exc);
            return HDAL.HDL_SERVOOP_EXIT;
        }
        return HDAL.HDL_SERVOOP_CONTINURE;
    }
    
    private void updateProperties() throws DeviceException, HDLException {
        this.makeCurrent();
        
        this.deviceWorkspace = HDAL.hdlDeviceWorkspace();
        
        vsHDAL = HDAL.hdlGetVersion(HDAL.HDL_HDAL);
        vsDEVICE = HDAL.hdlGetVersion(HDAL.HDL_DEVICE);
        vsSDK = HDAL.hdlGetVersion(HDAL.HDL_DEVICE_SDK);
        vsCOMMS = HDAL.hdlGetVersion(HDAL.HDL_DEVICE_COMMS);
        vsOS = HDAL.hdlGetVersion(HDAL.HDL_DEVICE_OS);
        vsGRIP = HDAL.hdlGetVersion(HDAL.HDL_GRIP);
        
        this.updateWorkspace();
    }
    private void updateWorkspace() {
        if(this.deviceWorkspace == null || this.deviceWorkspace.length != 6) {
            this.deviceWorkspace = 
                    new double[FalconDevice.DEFAULT_WORKSPACE.length];
            for(int i=0; i<FalconDevice.DEFAULT_WORKSPACE.length; i++) {
                this.deviceWorkspace[i] = FalconDevice.DEFAULT_WORKSPACE[i];
            }
        }
        if(this.sceneWorkspace == null || this.sceneWorkspace.length != 6) {
            this.sceneWorkspace = 
                    new double[FalconDevice.DEFAULT_WORKSPACE.length];
            for(int i=0; i<FalconDevice.DEFAULT_WORKSPACE.length; i++) {
                this.sceneWorkspace[i] = FalconDevice.DEFAULT_WORKSPACE[i];
            }
        }
        
        this.transWorkspace = 
                HDLU.hdluGenerateHapticToAppWorkspaceTransform(
                    deviceWorkspace, sceneWorkspace, false);
    }
    private void updateStates() throws DeviceException {
        this.makeCurrent();
        this.setPosition(HDAL.hdlToolPosition());
        this.setButtonPressed(HDAL.hdlToolButton());
        this.setButtonsPressed(HDAL.hdlToolButtons());
    }
    
    private void fireInputEvents() {
        if(tmpPos == null) {
            tmpPos = HDAL.hdlToolPosition();
        } else {
            double[] pos = HDAL.hdlToolPosition();
            if((Math.abs(tmpPos[0] - pos[0]) > threshold) || 
                    (Math.abs(tmpPos[1] - pos[1]) > threshold) || 
                    (Math.abs(tmpPos[2] - pos[2]) > threshold)) {
                this.fireInputEvent("Device moved", FalconDevice.EVENT_MOTION);
            }
            tmpPos = pos;
        }
        
        if(this.getButtonsPressed() != this.getLastButtonsPressed()) {
            this.fireInputEvent("Device button state changed", FalconDevice.EVENT_BUTTON);
        }
    }
    
    
    
    public static FalconDevice newFalconDevice(int index) throws DeviceException {
        FalconDevice falcon = devices.get(index);
        
        if(falcon == null) {
            falcon = new FalconDevice(index);
            devices.put(index, falcon);
        }
        return falcon;
    }
    public static int countDevices() {
        return HDAL.hdlCountDevices();
    }
    
    protected static double CLAMP_FORCE(double forceRamp, double lastForce, double newForce) {
        if(newForce > 0) {
            if(lastForce > 0) {
                if(newForce > lastForce) {
                    return (newForce-lastForce > forceRamp) ? lastForce+forceRamp : newForce;
                } else {
                    return newForce;
                }
            } else {
                return (newForce > forceRamp) ? forceRamp : newForce;
            }
        } else if(newForce < 0) {
            if(lastForce < 0) {
                return (newForce-lastForce < (-forceRamp)) ? lastForce+(-forceRamp) : newForce;
            } else {
                return (newForce < (-forceRamp)) ? -forceRamp : newForce;
            }
        } else {
            return newForce;
        }
    }
    
    private static HashMap<Integer, FalconDevice> devices = 
            new HashMap<Integer, FalconDevice>();
    private static final String UNKNOWN_VALUE = "Unknown";
    private static final double[] DEFAULT_WORKSPACE = 
            new double[] {-0.06, -0.06, -0.06, 0.06, 0.06, 0.06};
    private static final HDLVersionInfo UNAVAILABLE_VERSION = 
            new HDLVersionInfo(HDAL.HDL_VERSION_UNAVAILABLE, 
                                HDAL.HDL_VERSION_UNAVAILABLE, 
                                 HDAL.HDL_VERSION_UNAVAILABLE);
    private double threshold = 0.00005;
    
    public static final int NO_BUTTONS = 0;
    public static final int BUTTON_1 = HDAL.HDL_BUTTON_1;
    public static final int BUTTON_2 = HDAL.HDL_BUTTON_2;
    public static final int BUTTON_3 = HDAL.HDL_BUTTON_3;
    public static final int BUTTON_4 = HDAL.HDL_BUTTON_4;
    public static final int ANY_BUTTON = HDAL.HDL_BUTTON_ANY;
    
    public static final int EVENT_MOTION = 0x111;
    public static final int EVENT_BUTTON = 0x112;
    public static final int EVENT_CALLBACK = 0x113;
    public static final int EVENT_EXCEPTION = 0x114;
    
    public static final double DEFAULT_FORCE_RAMPING_RATE = 0.1;
}
