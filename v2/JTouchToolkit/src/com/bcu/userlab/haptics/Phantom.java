/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics;

import com.uce.userlab.haptics.DeviceException;
import com.uce.userlab.haptics.HD.HDAPI;
import com.uce.userlab.haptics.HD.HDCallback;
import com.uce.userlab.haptics.HD.HDException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author id108336
 */
public abstract class Phantom 
        extends HapticDevice implements HDCallback {
    protected double resolution;
    protected String deviceName;
    
    protected int deviceID;
    
    protected String devVendor;
    protected String devVersion;
    protected String devDevice;
    protected String devDeviceSerial;
    
    protected double[] workspaceMax;
    protected double[] workspaceUsable;
    
    protected int currentButtons;
    protected int lastButtons;
    
    protected int calibrationStyle; 
    protected double forceRampingRate;
    protected int inputDOF;
    protected int outputDOF;
    protected int instantaneousUpdateRate;
    protected double nominalMaxContinuousForce;
    protected double nominalMaxDamping;
    protected double nominalMaxForce;
    protected double nominalMaxStiffness;
    protected float tableTopOffset;
    protected double[] motorTemperature;
    protected int calibrationState;
    
    protected double[] currentGimbalAngles;
    protected double[] lastGimbalAngles;
    
    protected double[] currentJointAngles;
    protected double[] lastJointAngles;
    
    protected boolean currentSafetySwitch;
    protected boolean lastSafetySwitch;
    
    protected double[] currentTransform;
    protected double[] lastTransform;
    
    protected double[] currentVelocity;
    protected double[] lastVelocity;
    
    protected double[] currentTorque;
    protected double[] lastTorque;
            
    private double[] tmpPos;
    
    protected Phantom(String deviceName, int deviceID, double resolution) 
            throws DeviceException {
        this.deviceName = deviceName;
        this.resolution = resolution;
        this.deviceID = deviceID;
        
        try {
            this.makeCurrent();
            this.setDefaultProperties();
            HDAPI.hdScheduleAsynchronous(this, new Object[0], 
                                        HDAPI.HD_DEFAULT_SCHEDULER_PRIORITY);
            
            this.callibrate();
            
            this.updateProperties();
        } catch (HDException e) {
            throw new DeviceException(this, 
                "Unable to init the device correctly: " + deviceName, 
                e.getErrorCode(), e.getInternalErrorCode());
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
    protected void fireDeviceManagementError(String message, 
            int eventCode, DeviceException exc) {
        super.listenerSupport.fireDeviceManagementError(message, eventCode, 
                this.getCurrentPosition(), this.getButtonsPressed(), exc);
    }

    @Override
    public String getVendor() {return devVendor;}

    @Override
    public String getVersion() {return devVersion;}

    @Override
    public String getDevice() {return devDevice;}

    @Override
    public String getDeviceID() {return devDeviceSerial;}
    
    public void makeCurrent() throws DeviceException {
        try {
            HDAPI.hdMakeCurrentDevice(deviceID);
        } catch (HDException ex) {
            throw new DeviceException(this, ex.getMessage(), 
                    ex.getErrorCode(), ex.getErrorCode());
        }
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
    
    public double[] getMaxWorkspace() {
        return workspaceMax;
    }
    public double[] getUsableWorkspace() {
        return workspaceUsable;
    }
    
    public void enableForceOutput(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_FORCE_OUTPUT);
                this.fireUpdateEvent("Force Output enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_FORCE_OUTPUT);
                this.fireUpdateEvent("Force Output disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public void enableForceRamping(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_FORCE_RAMPING);
                this.fireUpdateEvent("Force Ramping enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_FORCE_RAMPING);
                this.fireUpdateEvent("Force Ramping disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public void enableMaxForceClamping(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_MAX_FORCE_CLAMPING);
                this.fireUpdateEvent("Max Force Clamping enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_MAX_FORCE_CLAMPING);
                this.fireUpdateEvent("Max Force Clamping disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public void enableSoftwareForceImpulseLimit(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
                this.fireUpdateEvent("Software Force Impulse Limit enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
                this.fireUpdateEvent("Software Force Impulse Limit disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public void enableSoftwareForceLimit(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
                this.fireUpdateEvent("Software Force Limit enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
                this.fireUpdateEvent("Software Force Limit disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public void enableSoftwareVelocityLimit(boolean enable) 
            throws DeviceException {
        try {
            if(enable) {
                HDAPI.hdEnable(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
                this.fireUpdateEvent("Software Velocity Limit enabled", Phantom.EVENT_UPDATE);
            } else {
                HDAPI.hdDisable(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
                this.fireUpdateEvent("Software Velocity Limit disabled", Phantom.EVENT_UPDATE);
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }

    public boolean isForceOutput(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_FORCE_OUTPUT);
        } catch (HDException ex) {
            throw new DeviceException(null, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public boolean isForceRamping(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_FORCE_RAMPING);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public boolean isMaxForceClamping(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_MAX_FORCE_CLAMPING);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public boolean isSoftwareForceImpulseLimit(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public boolean isSoftwareForceLimit(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    public boolean isSoftwareVelocityLimit(boolean enable) 
            throws DeviceException {
        try {
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
        } catch (HDException ex) {
            throw new DeviceException(null, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    
    public double[] getCurrentTorque() {
        return currentTorque;
    }
    public void setTorque(double[] currentTorque) {
        this.lastTorque = this.currentTorque;
        this.currentTorque = currentTorque;
    }
    public double[] getLastTorque() {
        return lastTorque;
    }
    
    public double getForceRampingRate() {
        return forceRampingRate;
    }
    public void setForceRampingRate(double forceRampingRate) {
        this.forceRampingRate = forceRampingRate;
    }
    
    public int getCalibrationStyle() {
        return calibrationStyle;
    }
    public int getInputDOF() {
        return inputDOF;
    }
    public int getOutputDOF() {
        return outputDOF;
    }
    public int getInstantaneousUpdateRate() {
        return instantaneousUpdateRate;
    }
    public double getNominalMaxContinuousForce() {
        return nominalMaxContinuousForce;
    }
    public double getNominalMaxDamping() {
        return nominalMaxDamping;
    }
    public double getNominalMaxForce() {
        return nominalMaxForce;
    }
    public double getNominalMaxStiffness() {
        return nominalMaxStiffness;
    }
    public float getTableTopOffset() {
        return tableTopOffset;
    }
    
    private void setVelocity(double[] velocity) {
        lastVelocity = currentVelocity;
        currentVelocity = velocity;
    }
    public double[] getVelocity() {
        return currentVelocity;
    }
    public double[] getLastVelocity() {
        return lastVelocity;
    }
    
    private void setGimbalAngles(double[] gimbalAngles) {
        lastGimbalAngles = currentGimbalAngles;
        currentGimbalAngles = gimbalAngles;
    }
    public double[] getGimbalAngles() {
        return currentGimbalAngles;
    }
    public double[] getLastGimbalAngles() {
        return lastGimbalAngles;
    }
    
    private void setJointAngles(double[] jointAngles) {
        lastJointAngles = currentJointAngles;
        currentJointAngles = jointAngles;
    }
    public double[] getJointAngles() {
        return currentJointAngles;
    }
    public double[] getLastJointAngles() {
        return lastJointAngles;
    }
    
    private void setSafetySwitch(boolean safetySwitch) {
        lastSafetySwitch = currentSafetySwitch;
        currentSafetySwitch = safetySwitch;
    }
    public boolean isSafetySwitch() {
        return currentSafetySwitch;
    }
    public boolean isLastSafetySwitch() {
        return lastSafetySwitch;
    }
    
    public int getCalibrationState() {
        return calibrationState;
    }
    
    protected void callibrate() throws DeviceException {
        try {
            int supportedCalibrationStyles = HDAPI.hdGetIntegerv(HDAPI.HD_CALIBRATION_STYLE)[0];
            
            if(supportedCalibrationStyles == HDAPI.HD_CALIBRATION_ENCODER_RESET) {
                calibrationStyle = HDAPI.HD_CALIBRATION_ENCODER_RESET;
            }
            if(supportedCalibrationStyles == HDAPI.HD_CALIBRATION_INKWELL) {
                calibrationStyle = HDAPI.HD_CALIBRATION_INKWELL;
            }
            if(supportedCalibrationStyles == HDAPI.HD_CALIBRATION_AUTO) {
                calibrationStyle = HDAPI.HD_CALIBRATION_AUTO;
            }
            
            if(calibrationStyle == HDAPI.HD_CALIBRATION_ENCODER_RESET) {
                System.out.println("Please prepare for manual calibration by placing the device");
                System.out.println("at its reset position\n");
                System.out.println("Press any enter/return to continue...\n");
                
                try {
                    System.in.read();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                HDAPI.hdUpdateCalibration(calibrationStyle);
                if(HDAPI.hdCheckCalibration() == HDAPI.HD_CALIBRATION_OK) {
                    System.out.println("Calibration complete.\n\n");
                } else {
                    System.out.println("Error reseting encoder, calibration failed");
                }
            }
            
            HDAPI.hdStartScheduler();
            
            if(calibrationStyle == HDAPI.HD_CALIBRATION_INKWELL) {
                if(this.getCalibrationState() == HDAPI.HD_CALIBRATION_NEEDS_MANUAL_INPUT) {
                    System.out.println("Please place the device into the inkwell ");
                    System.out.println("for calibration.\n\n");
                }
            }
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    protected boolean updateCalibration() throws HDException, DeviceException {
        int status = this.getCalibrationState();
        
        if(status == HDAPI.HD_CALIBRATION_OK) {
            return true;
        } else if(status == HDAPI.HD_CALIBRATION_NEEDS_MANUAL_INPUT) {
            return false;
        } else if(status == HDAPI.HD_CALIBRATION_NEEDS_UPDATE) {
            if(HDAPI.hdCheckCalibration() == HDAPI.HD_CALIBRATION_NEEDS_UPDATE) {
                    HDAPI.hdUpdateCalibration(this.getCalibrationStyle());
            }
            
            this.updateProperties();
            return true;
        } else {
            return false;
        }
    }
    
    public int HD_Callback(Object[] obj) {
        try {
            try {
                this.makeCurrent();
                HDAPI.hdBeginFrame(deviceID);
                this.updateState();
                this.updateCalibration();
                //System.out.println(this.getCalibrationState() == HDAPI.HD_CALIBRATION_OK);
                this.fireInputEvents();

                super.listenerSupport.fireDeviceCallback("", -1, this.getCurrentPosition(), this.getButtonsPressed());

                this.makeCurrent();
                HDAPI.hdSetDoublev(HDAPI.HD_CURRENT_FORCE, this.getForce());
            } catch (HDException ex) {
                throw new DeviceException(this, 
                    "Haptic Device Management Exception within Servo Loop", 
                    ex.getErrorCode(), ex.getInternalErrorCode());
            }
        } catch (DeviceException ex) {
                this.fireDeviceManagementError(ex.getMessage(), 
                        Phantom.EVENT_EXCEPTION, ex);
        } finally {
            try {
                HDAPI.hdEndFrame(deviceID);
            } catch(HDException e) {
                this.fireDeviceManagementError("", Phantom.EVENT_EXCEPTION, 
                        new DeviceException(this, "Unable close device frame", 
                        e.getErrorCode(), e.getInternalErrorCode()));
            }
        }
        return HDAPI.HD_CALLBACK_CONTINUE;
    }
    
    protected void setDefaultProperties() throws DeviceException {
        this.makeCurrent();
        try {
            HDAPI.hdEnable(HDAPI.HD_FORCE_OUTPUT);
            HDAPI.hdEnable(HDAPI.HD_FORCE_RAMPING);
            HDAPI.hdEnable(HDAPI.HD_MAX_FORCE_CLAMPING);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    private void updateProperties() throws DeviceException {
        try {
            calibrationStyle = HDAPI.hdGetIntegerv(HDAPI.HD_CALIBRATION_STYLE)[0];
            inputDOF = HDAPI.hdGetIntegerv(HDAPI.HD_INPUT_DOF)[0];
            outputDOF = HDAPI.hdGetIntegerv(HDAPI.HD_OUTPUT_DOF)[0];
            
            instantaneousUpdateRate = HDAPI.hdGetIntegerv(HDAPI.HD_INSTANTANEOUS_UPDATE_RATE)[0];
            nominalMaxContinuousForce = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_CONTINUOUS_FORCE)[0];
            nominalMaxDamping = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_DAMPING)[0];
            nominalMaxForce = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_FORCE)[0];
            nominalMaxStiffness = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_STIFFNESS)[0];
            
            this.tableTopOffset = HDAPI.hdGetFloatv(HDAPI.HD_TABLETOP_OFFSET)[0];
            
            devVendor = HDAPI.hdGetString(HDAPI.HD_DEVICE_VENDOR);
            if(devVendor == null) {
                devVendor = Phantom.UNKNOWN_VALUE;
            }
            devVersion = HDAPI.hdGetString(HDAPI.HD_DEVICE_DRIVER_VERSION);
            if(devVersion == null) {
                devVersion = Phantom.UNKNOWN_VALUE;
            }
            devDevice = HDAPI.hdGetString(HDAPI.HD_DEVICE_MODEL_TYPE);
            if(devDevice == null) {
                devDevice = Phantom.UNKNOWN_VALUE;
            }
            devDeviceSerial = HDAPI.hdGetString(HDAPI.HD_DEVICE_SERIAL_NUMBER);
            if(devDeviceSerial == null) {
                devDeviceSerial = Phantom.UNKNOWN_VALUE;
            }
            
            workspaceMax = HDAPI.hdGetDoublev(HDAPI.HD_MAX_WORKSPACE_DIMENSIONS);
            workspaceUsable = HDAPI.hdGetDoublev(HDAPI.HD_USABLE_WORKSPACE_DIMENSIONS);
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    private void updateState() throws DeviceException {
        try {
            calibrationState = HDAPI.hdCheckCalibration();
            motorTemperature = HDAPI.hdGetDoublev(HDAPI.HD_MOTOR_TEMPERATURE);
            
            this.setButtonsPressed(HDAPI.hdGetIntegerv(HDAPI.HD_CURRENT_BUTTONS)[0]);
            this.setGimbalAngles(HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_GIMBAL_ANGLES));
            this.setJointAngles(HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_JOINT_ANGLES));
            this.setPosition(HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_POSITION));
            this.setSafetySwitch(HDAPI.hdGetBooleanv(HDAPI.HD_CURRENT_SAFETY_SWITCH)[0]);
            this.setVelocity(HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_VELOCITY));
            
        } catch (HDException ex) {
            throw new DeviceException(this, 
                ex.getMessage(), ex.getErrorCode(), ex.getInternalErrorCode());
        }
    }
    
    private void fireInputEvents() {
        if(tmpPos == null) {
                tmpPos = this.getCurrentPosition();
        } else {
            double[] pos = this.getCurrentPosition();
            if((Math.abs(tmpPos[0] - pos[0]) > resolution) || 
                    (Math.abs(tmpPos[1] - pos[1]) > resolution) || 
                    (Math.abs(tmpPos[2] - pos[2]) > resolution)) {
                this.fireInputEvent("Device moved", Phantom.EVENT_MOTION);
            }
            tmpPos = pos;
        }
        
        if(this.getButtonsPressed() != this.getLastButtonsPressed()) {
            this.fireInputEvent("Device button state changed", Phantom.EVENT_BUTTON);
        }
    }
    
    public static Phantom newPhantomDevice(String deviceName, double res) 
            throws DeviceException {
        Phantom phantom = devices.get(deviceName);
        
        if(phantom == null) {
            try {
                int deviceID = HDAPI.hdInitDevice(deviceName);
                HDAPI.hdMakeCurrentDevice(deviceID);
                String model = HDAPI.hdGetString(HDAPI.HD_DEVICE_MODEL_TYPE);
                if(model == null) {
                    model = "";
                } else {
                    model = model.toLowerCase();
                }
                
                if(model.compareTo("phantom omni") == 0) {
                    phantom = new PhantomDesktop(deviceName, deviceID);
                    devices.put(deviceName, phantom);
                } else if(model.compareTo("phantom desktop") == 0) {
                    phantom = new PhantomDesktop(deviceName, deviceID);
                    devices.put(deviceName, phantom);
                } else {
                    phantom = new PhantomUnknown(deviceName, deviceID, res);
                    devices.put(deviceName, phantom);
                }
            }catch(HDException e) {
                throw new DeviceException(null, 
                    "Unable to init the device correctly: " + deviceName, 
                    e.getErrorCode(), e.getInternalErrorCode());
            }
        }
        return phantom;
    }
    
    private static HashMap<String, Phantom> devices = 
            new HashMap<String, Phantom>();
    public static final String DEFAULT_PHANTOM = "Default PHANToM";
    protected static final String UNKNOWN_VALUE = "Unavailable";
    
    public static final int EVENT_MOTION = 0x111;
    public static final int EVENT_BUTTON = 0x112;
    public static final int EVENT_CALLBACK = 0x113;
    public static final int EVENT_EXCEPTION = 0x114;
    
    public static final int CALIBRATION_OK = HDAPI.HD_CALIBRATION_OK;
    public static final int CALIBRATION_NEEDS_UPDATE = HDAPI.HD_CALIBRATION_NEEDS_UPDATE;
    public static final int CALIBRATION_NEEDS_MANUAL_INPUT = HDAPI.HD_CALIBRATION_NEEDS_MANUAL_INPUT;
    
    public static final int CALIBRATION_METHOD_AUTO = HDAPI.HD_CALIBRATION_AUTO;
    public static final int CALIBRATION_METHOD_ENCODER_RESET = HDAPI.HD_CALIBRATION_ENCODER_RESET;
    public static final int CALIBRATION_METHOD_INKWELL = HDAPI.HD_CALIBRATION_INKWELL;
    
    public static final int BUTTON_1 = HDAPI.HD_DEVICE_BUTTON_1;
    public static final int BUTTON_2 = HDAPI.HD_DEVICE_BUTTON_2;
    public static final int BUTTON_3 = HDAPI.HD_DEVICE_BUTTON_3;
    public static final int BUTTON_4 = HDAPI.HD_DEVICE_BUTTON_4;
    
    public static final int BUTTON_1_AND_2 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_2;
    public static final int BUTTON_1_AND_3 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_3;
    public static final int BUTTON_1_AND_4 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_4;
    public static final int BUTTON_2_AND_3 = HDAPI.HD_DEVICE_BUTTON_2 * HDAPI.HD_DEVICE_BUTTON_3;
    public static final int BUTTON_2_AND_4 = HDAPI.HD_DEVICE_BUTTON_2 * HDAPI.HD_DEVICE_BUTTON_4;
    public static final int BUTTON_3_AND_4 = HDAPI.HD_DEVICE_BUTTON_3 * HDAPI.HD_DEVICE_BUTTON_4;
}
