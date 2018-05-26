package com.uce.userlab.haptics;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.HD.*;
import com.uce.userlab.haptics.event.*;

/**
 * This Class encapsulates the functionality of a Phantom device, it builds on top
 * of the HDAPI to give a more object based approach to managing a haptic device.
 * This device implements the Device interface to allow it to take advantage of
 * other non-device persificate control and management classes.
 */
public class PhantomDevice implements Device, HDCallback
{
    /**
     * The Haptic device listener, aids in notifying listeners
     */
    private HapticListenerSupport support;
    /**
     * double array variables for update within servo loop callback method
     */
    private double[] currentPosition, currentVelocity, currentTransform,
                        currentAngularVelocity, currentJointAngles, currentGimbalAngles, 
                        deviceTotalForce, deviceTotalTorque, 
                        lastPosition, lastVelocity, lastTransform,
                        lastAngularVelocity, lastJointAngles, lastGimbalAngles, motorTemperature;
    /**
     * int variables for update within servo loop callback method
     */
    private int currentButtons, lastButtons, calibrationState;
    /**
     * long array variables for update within servo loop callback method
     */
    private long[] currentEncoderValue, currentMotorDAC, lastEncoderValue;
    /**
     * boolean variables for update within servo loop callback method
     */
    private boolean currentSafetySwitch, lastSafetySwitch;
    /**
     * double variable for update within servo loop callback method
     */
    private double schedulerTimeStamp;
    
    /**
     * double array variables for update on property change
     */
    private double[] maxWorkspaceDimensions, usableWorkspaceDimensions;
    /**
     * float variable for update on property change
     */
    private float tabletopOffset;
    /**
     * int variables for update on property change
     */
    private int inputDegreesOfFreedom, outputDegreesOfFreedom, calibrationStyle,
                        updateRate, instantaneousUpdateRate;
    /**
     * double variables for update on property change
     */
    private double nominalMaxStiffness, nominalMaxDamping, nominalMaxForce,
                        nominalMaxContinuousForce, forceRampingRate;
    /**
     * string variables for update on property change
     */
    private String vendor, version, device, deviceSerial;
    
    /**
     * identifiers for the device and scheduler within the HDAPI implimentation
     */
    private int deviceID = -1, schedulerID = -1;
    /**
     * boolean variable to indicate when a haptic frame is open
     */
    private boolean frameOpen = false;
    /**
     * boolean variable to indicate weather the device connection has been destroyed
     */
    private boolean destroyed = false;
    /**
     * boolean variable to indicate that the property change variables need update
     */
    private boolean updated = false;
    /**
     * A Object that is set by the user to indicate the state of the device
     */
    private Object stateStore;
    /**
     * The force that has been set to send to the device within the servo loop callback
     */
    private double[] force = new double[3];
    /**
     * The torque that has been set to send to the device within the servo loop callback
     */
    private double[] torque = new double[3];
    
    /**
     * The maximum and minimum force that is allowed to be sent to device
     */
    private double maxForce = 10, minForce = -10;
            
    /**
     * The constructor, creates a new instance of the Phantom device for the device specified
     * @param deviceName The Name of the device you wish to initialise and encapsulate within Object
     * @throws DeviceException If the device is unable to be initialised
     */
    public PhantomDevice(String deviceName) throws DeviceException
    {
        support = new HapticListenerSupport(this);
        
        try{
            this.deviceID = HDAPI.hdInitDevice(deviceName);
            this.enableForceOutput();
            this.scheduleCallback();
            this.updateCallbackVar();
            this.updateStateChangeVar();
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to init the device correctly: " + deviceName, e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Adds a haptic listener to the device, to be notified of events
     * @param listener The HapticListener Object to adds to the notify list
     */
    public void addHapticListener(HapticListener listener){support.addHapticListener(listener);}
    /**
     * Removes a haptic listener from the device, so the listener will not be notified of events
     * @param listener The HapticListener Object to remove from the notify list
     */
    public void removeHapticListener(HapticListener listener){support.removeHapticListener(listener);}
    
    /**
     * Returns the current position the device is in at last servo loop update
     * @return A double array containing to coOrdinates for x,y,z axis of the device
     */
    public double[] getCurrentPosition(){return this.currentPosition;}
    
    /**
     * Sets the Maximum force that is allowed to be sent to the device
     * @param max A double value representing the maximum force that is allowed to be sent to the device
     */
    public void setMaxForce(double max)
    {
        if(max >= this.minForce)
            this.maxForce = max;
    }
    /**
     * Sets the Minimum force that is allowed to be sent to the device
     * @param min A double value representing the minimum force that is allowed to be sent to the device (usually in negative force)
     */
    public void setMinForce(double min)
    {
        if(min <= this.maxForce)
            this.minForce = min;
    }
    
    /**
     * Returns the present maximum force boundary that has been set
     * @return A double value represent the maximum force that is allowed to be sent to the device
     */
    public double getMaxForce(){return this.maxForce;}
    /**
     * Returns the present minimum force boundary that has been set
     * @return A double value represent the minimum force that is allowed to be sent to the device
     */
    public double getMinForce(){return this.minForce;}
    
    /**
     * Returns the current state object that has been set by <code>updateCurrentState</code>
     * @return A Object that represents the current state of the device or its related process(es)
     */
    public Object getCurrentState(){return stateStore;}
    /**
     * Sets the current state Object that the device is storing
     * @param state The state Object to store
     */
    public void updateCurrentState(Object state){stateStore = state;}
    
    /**
     * Returns the device vendor name
     * @return The device Vendor name
     */
    public String getVendor() {return this.vendor;}
    /**
     * Returns the device version
     * @return The device Version
     */
    public String getVersion() {return this.version;}
    /**
     * Returns the device name
     * @return The Device name
     */
    public String getDevice() {return this.device;}
    /**
     * Returns the device serial number
     * @return The device Serial number
     */
    public String getDeviceID() {return this.deviceSerial;}
    
    /**
     * Enables the device, allows force output to be made with the device <I>Default: enabled</I>
     * @throws DeviceException if the device is unabled to allow force output to be enabled
     */
    public void enable() throws DeviceException{this.enableForceOutput();}
    /**
     * Disables the device, prevents force output by the device
     * @throws DeviceException if the device is unabled to allow force output to be disabled
     */
    public void disable() throws DeviceException{this.disableForceOutput();}
    /**
     * Returns weather the device is enabled or not
     * @return A boolean value, true if the Device is enabled, and false if not
     * @throws DeviceException if the device state cannot be determined
     */
    public boolean isEnabled() throws DeviceException{return this.isForceOutputEnabled();}
    /**
     * Returns weather the device is disabled or not
     * @return A boolean value, true if the Device is disabled, and false if not
     * @throws DeviceException if the device state cannot be determined
     */
    public boolean isDiabled() throws DeviceException{return !this.isForceOutputEnabled();}
    
    /**
     * Adds new force output to the present force output
     * @param force A double array of the force for the x,y,z axis
     */
    public void sendForce(double[] force)
    {
        this.force = new double[] 
            {force[0]+this.force[0], 
             force[1]+this.force[1], 
             force[2]+this.force[2]};
    }
    /**
     * Clears the current force values set for the axises
     */
    public void clearForce() {this.force = new double[3];}
    /**
     * Returns the current force that has been set or calculated for the device
     * @return A double array of the current force output of the device for x,y,z axis
     */
    public double[] getForce() {return this.force;}
    
    /**
     * HD_Callback method for the HD toolkit to call, (don't call event outside servo loop)
     * @param obj The Object array that was set at scheduling
     * @return A int value represent the servo loop next step. e.g 'Continue'
     */
    public int HD_Callback(Object[] obj)
    {
        try{
            this.makeCurrent();
            this.openFrame();
            
            try{
                this.updateCallbackVar();
                if(this.updated == true)
                {
                    this.updateStateChangeVar();
                    this.updated = false;
                }
            }catch(HDException exc)
            {
                support.fireDeviceManagementError("There has occured an error while manageing device information",
                        this.MANAGEMENT_ERROR_EVENT,this.currentPosition,this.currentButtons,
                        new DeviceException(this,"Unable to refresh device information",exc.getErrorCode(),exc.getInternalErrorCode()));
            }
            
            this.fireInputEvents();
            support.fireDeviceCallback("Callback event for: " + this.getID(), 1234, this.currentPosition, this.currentButtons);
                
            try{
                this.checkForce();
                HDAPI.hdSetDoublev(HDAPI.HD_CURRENT_TORQUE, this.torque);
                this.setCurrentForce(this.force);
            }catch(HDException exc)
            {
                support.fireDeviceManagementError("There has occured an error while sending force to the device",
                        this.MANAGEMENT_ERROR_EVENT,this.currentPosition,this.currentButtons,
                        new DeviceException(this,"Unable to send force to device",exc.getErrorCode(),exc.getInternalErrorCode()));
            }
        }catch(Exception e)
        {
            DeviceException error;
            if(e instanceof DeviceException)
                error = (DeviceException)e;
            else if(e instanceof HDException)
                error = new DeviceException(this,"There has occured an error within the callback loop",((HDException)e).getErrorCode(),((HDException)e).getInternalErrorCode());
            else
                error = new DeviceException(this,"There has occured an error within the callback loop",this.DEVICE_ERROR_UNKNOWN,0);
            
            support.fireDeviceManagementError("Error occured within the device management loop, you may experience some lose of control or force",
                    this.MANAGEMENT_ERROR_EVENT,this.currentPosition,this.currentButtons,
                    error);
        }
        finally
        {
            try{
                this.closeFrame();
            }catch(HDException exce)
            {
                support.fireDeviceManagementError("There has occured an error while closing the haptic frame within the management loop",
                    this.MANAGEMENT_ERROR_EVENT,this.currentPosition,this.currentButtons,
                    new DeviceException(this,"Unable to close the haptic frame",exce.getErrorCode(),exce.getInternalErrorCode()));
            }
        }
        return HDAPI.HD_CALLBACK_CONTINUE;
    }
    
    /**
     * Fires and input event if one has been made since last call of the HD_Callback method
     * @throws HDException if obtaining the device input values course an error
     */
    private void fireInputEvents() throws HDException
    {
        //Fire Button events if needed
        if(this.getCurrentButtons() != 0 && this.getLastButtons() == 0)
            support.fireDeviceInput("Button(s) Down", this.EVENT_BUTTONS_DOWN, this.currentPosition, this.currentButtons);
        if(this.getLastButtons() != 0 && this.currentButtons == 0)
            support.fireDeviceInput("Button(s) Up", this.EVENT_BUTTONS_UP, this.currentPosition, this.currentButtons);
        
        //Fire Gimbal motion events
        double twistV = Math.abs(this.getCurrentGimbalAngels()[2]-this.getLastGimbalAngles()[2]);
        double tiltV = Math.abs(this.getCurrentGimbalAngels()[1]-this.getLastGimbalAngles()[1]);
        double turnV = Math.abs(this.getCurrentGimbalAngels()[0]-this.getLastGimbalAngles()[0]);
        
        if(turnV > 0.00128)
            support.fireDeviceInput("Gimbal Joint 1 motion detected [turn]",this.EVENT_GIMBAL_JOINT_1_MOTION,this.currentPosition, this.currentButtons);
                
        if(tiltV > 0.00128)
            support.fireDeviceInput("Gimbal Joint 2 motion detected [tilt]",this.EVENT_GIMBAL_JOINT_2_MOTION,this.currentPosition,this.currentButtons);
            
        if(twistV > 0.00128)
            support.fireDeviceInput("Gimbal Joint 3 motion detected [twist]",this.EVENT_GIMBAL_JOINT_3_MOTION,this.currentPosition,this.currentButtons);
        
        //Fire Motion events if needed
        double disX = Math.abs(this.getLastPosition()[0]-this.getCurrentPosition()[0]);
        double disY = Math.abs(this.getLastPosition()[1]-this.getCurrentPosition()[1]);
        double disZ = Math.abs(this.getLastPosition()[2]-this.getCurrentPosition()[2]);
        double disTrav = Math.sqrt(disX + disY + disZ);
        
        if(disTrav > 0.266)
            support.fireDeviceInput("Motion detected",this.EVENT_MOTION,this.currentPosition,this.currentButtons);
    }
    
    /**
     * Checks the current force set for sending to the device, if over allowed limits it is cropped
     */
    private void checkForce()
    {
        for(int i=0; i<3; i++)
        {
            if(this.force[i] > this.maxForce)
                this.force[i] = this.maxForce;
            else if(this.force[i] < this.minForce)
                this.force[i] = this.minForce;
        }
    }
    
    /**
     * Updates the Device variables that are set for update from within the servo loop of the device
     * @throws HDException If an error occures when trying to obtain the system device variables
     */
    private void updateCallbackVar() throws HDException
    {
        //double
        schedulerTimeStamp = HDAPI.hdGetSchedulerTimeStamp();
        
        //double[]
        motorTemperature = HDAPI.hdGetDoublev(HDAPI.HD_MOTOR_TEMPERATURE);
        currentPosition = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_POSITION);
        currentVelocity = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_VELOCITY);
        currentTransform = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_TRANSFORM);
        currentAngularVelocity = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_ANGULAR_VELOCITY);
        currentJointAngles = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_JOINT_ANGLES);
        currentGimbalAngles = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_GIMBAL_ANGLES);
        deviceTotalForce = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_FORCE);
        deviceTotalTorque = HDAPI.hdGetDoublev(HDAPI.HD_CURRENT_TORQUE);
        lastPosition = HDAPI.hdGetDoublev(HDAPI.HD_LAST_POSITION);
        lastVelocity = HDAPI.hdGetDoublev(HDAPI.HD_LAST_VELOCITY);
        lastTransform = HDAPI.hdGetDoublev(HDAPI.HD_LAST_TRANSFORM);
        lastAngularVelocity = HDAPI.hdGetDoublev(HDAPI.HD_LAST_ANGULAR_VELOCITY);
        lastJointAngles = HDAPI.hdGetDoublev(HDAPI.HD_LAST_JOINT_ANGLES);
        lastGimbalAngles = HDAPI.hdGetDoublev(HDAPI.HD_LAST_GIMBAL_ANGLES);
        
        //int
        currentButtons = HDAPI.hdGetIntegerv(HDAPI.HD_CURRENT_BUTTONS)[0];
        lastButtons = HDAPI.hdGetIntegerv(HDAPI.HD_LAST_BUTTONS)[0];
        calibrationState = HDAPI.hdCheckCalibration();
        
        //long[]
        currentEncoderValue = HDAPI.hdGetLongv(HDAPI.HD_CURRENT_ENCODER_VALUES);
        currentMotorDAC = HDAPI.hdGetLongv(HDAPI.HD_CURRENT_MOTOR_DAC_VALUES);
        lastEncoderValue = HDAPI.hdGetLongv(HDAPI.HD_LAST_ENCODER_VALUES);
        
        //boolean
        currentSafetySwitch = HDAPI.hdGetBooleanv(HDAPI.HD_CURRENT_SAFETY_SWITCH)[0];
        lastSafetySwitch = HDAPI.hdGetBooleanv(HDAPI.HD_LAST_SAFETY_SWITCH)[0];
    }
    /**
     * Updates the Device variables that are set for update when properties change
     * @throws HDException If an error occures when trying to obtain the system device variables
     */
    private void updateStateChangeVar() throws HDException
    {
        //double[]
        maxWorkspaceDimensions = HDAPI.hdGetDoublev(HDAPI.HD_MAX_WORKSPACE_DIMENSIONS);
        usableWorkspaceDimensions = HDAPI.hdGetDoublev(HDAPI.HD_USABLE_WORKSPACE_DIMENSIONS);
        
        //float
        tabletopOffset = HDAPI.hdGetFloatv(HDAPI.HD_TABLETOP_OFFSET)[0];
        
        //int
        inputDegreesOfFreedom = HDAPI.hdGetIntegerv(HDAPI.HD_INPUT_DOF)[0];
        outputDegreesOfFreedom = HDAPI.hdGetIntegerv(HDAPI.HD_OUTPUT_DOF)[0];
        calibrationStyle = HDAPI.hdGetIntegerv(HDAPI.HD_CALIBRATION_STYLE)[0];
        updateRate = HDAPI.hdGetIntegerv(HDAPI.HD_UPDATE_RATE)[0];
        instantaneousUpdateRate = HDAPI.hdGetIntegerv(HDAPI.HD_INSTANTANEOUS_UPDATE_RATE)[0];

        //double
        nominalMaxStiffness = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_STIFFNESS)[0];
        nominalMaxDamping = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_DAMPING)[0];
        nominalMaxForce = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_FORCE)[0];
        nominalMaxContinuousForce = HDAPI.hdGetDoublev(HDAPI.HD_NOMINAL_MAX_CONTINUOUS_FORCE)[0];
        forceRampingRate = HDAPI.hdGetDoublev(HDAPI.HD_FORCE_RAMPING_RATE)[0];
        
        //string
        vendor = HDAPI.hdGetString(HDAPI.HD_DEVICE_VENDOR);
        version = HDAPI.hdGetString(HDAPI.HD_DEVICE_DRIVER_VERSION);
        device = HDAPI.hdGetString(HDAPI.HD_DEVICE_MODEL_TYPE);
        deviceSerial = HDAPI.hdGetString(HDAPI.HD_DEVICE_SERIAL_NUMBER);
    }
    /**
     * Opens a frame within the haptic renderer ready to retrieve and set variables
     * @throws HDException If an error occures when trying to open the frame
     */
    private void openFrame() throws HDException
    {
        if(!frameOpen)
        {
            frameOpen = true;
            HDAPI.hdBeginFrame(this.deviceID);
        }
    }
    /**
     * Closes a frame within the haptic renderer after retrieval and set of variables is complete
     * @throws HDExcetpion If an error occures when trying to close the frame
     */
    private void closeFrame() throws HDException
    {
        if(frameOpen)
        {
            frameOpen = false;
            HDAPI.hdEndFrame(this.deviceID);
        }
    }
    /**
     * Schedules callback from device servo loop to this object ready for update
     * @throws HDException If an error occures when trying to schedule callback
     */
    private void scheduleCallback() throws HDException
    {
        this.schedulerID = HDAPI.hdScheduleAsynchronous(this, new Object[0], HDAPI.HD_DEFAULT_SCHEDULER_PRIORITY);
        HDAPI.hdStartScheduler();
    }
    /**
     * Set the current force of the device
     * @param force A double array containning force values for x,y,z
     * @throws HDException If an error occures when trying to set the device force
     */
    private void setCurrentForce(double[] force) throws HDException
    {
        HDAPI.hdSetDoublev(HDAPI.HD_CURRENT_FORCE, force);
    }
    /**
     * Destroys the connection of the object to the device, this will release the device read to be used by others
     * @throws DeviceException If an error occures when trying to destroy connection
     */
    public void destroy() throws DeviceException
    {
        try{
            HDAPI.hdStopScheduler();
            HDAPI.hdUnschedule(this.schedulerID);
            HDAPI.hdDisableDevice(this.deviceID);
            this.destroyed = true;
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to destroy/remove device connection", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the device connection has been destroyed
     * @return A boolean value, true if device is destroyed, false if not
     */
    public boolean isDestroyed() throws HDException
    {
        return this.destroyed;
    }
    
    
    /**
     * Returns the Max Workspace Dimensions for the device
     * @return A double array of the Max Workspace Dimensions
     */
    public double[] getMaxWorkspaceDimensions(){return this.maxWorkspaceDimensions;}
    /**
     * Returns the Usable Workspace Dimensions for the device
     * @return A double array of the Usable Workspace Dimensions
     */
    public double[] getUsableWorkspaceDimensions(){return this.usableWorkspaceDimensions;}
    /**
     * Returns the Tabletop Offset for the device
     * @return A float value of the tabletop offset
     */
    public float getTabletopOffset(){return this.tabletopOffset;}
    /**
     * Returns the Input DOF the device has
     * @return int value for the number of input Degrees of Freedom (DOF) the device has
     */
    public int getInputDegreesOfFreedom(){return this.inputDegreesOfFreedom;}
    /**
     * Returns the Output DOF the device has
     * @return int value for the number of output Degrees of Freedom (DOF) the device has
     */
    public int getOutputDegreesOfFreedom(){return this.outputDegreesOfFreedom;}
    /**
     * Returns the type of Calibration the device uses, see:
     * @return A int value representing the type of calibration used for the device, see:
     * <ul>
     *  <li><a href="#CALIBRATION_AUTO">CALIBRATION_AUTO</a></li>
     *  <li><a href="#CALIBRATION_ENCODER_RESET">CALIBRATION_ENCODER_RESET</a></li>
     *  <li><a href="#CALIBRATION_INKWELL">CALIBRATION_INKWELL</a></li>
     * </ul>
     */
    public int getCalibrationStyle(){return this.calibrationStyle;}
    /**
     * Returns the update rate used for the servo loop callback
     * @return A int value representing the update rate for the device
     */
    public int getUpdateRate(){return this.updateRate;}
    /**
     * Returns the instantaneous update rate for the device
     * @return A int value representing the instantaneous update rate
     */
    public int getInstantaneousUpdateRate(){return this.instantaneousUpdateRate;}
    /**
     * Returns the nominal max stiffness for the device
     * @return A double value of the nominal max stiffness
     */
    public double getNominalMaxStiffness(){return this.nominalMaxStiffness;}
    /**
     * Returns the nominal max damping for the device
     * @return A double value of the nominal max damping
     */
    public double getNominalMaxDamping(){return this.nominalMaxDamping;}
    /**
     * Returns the nominal max force for the device
     * @return A double value of the nominal max force
     */
    public double getNominalMaxForce(){return this.nominalMaxForce;}
    /**
     * Returns the nominal max continuous force for the device
     * @return A double value of the nominal max continuous force
     */
    public double getNominalMaxContinuousForce(){return this.nominalMaxContinuousForce;}
    /**
     * Returns the force ramping rate for the device
     * @return A double value of the force ramping rate
     */
    public double getForceRampingRate(){return this.forceRampingRate;}

    
    
    /**
     * Returns the Scheduler's Time Stamp
     * @return A double value of the Time Stamp of the Scheduler
     */
    public double getSchedulerTimeStamp(){return this.schedulerTimeStamp;}
    /**
     * Returns the Motor Temperature
     * @return A double array of the motor temperatures
     */
    public double[] getMotorTemperature(){return this.motorTemperature;}
    /**
     * Returns the Current Velocity of the device
     * @return A double array of the Current Velocity
     */
    public double[] getCurrentVelocity(){return this.currentVelocity;}
    /**
     * Returns the Current Transform of the device
     * @return A double array of the Current Transform
     */
    public double[] getCurrentTransform(){return this.currentTransform;}
    /**
     * Returns the Current Angular Velocity of the device
     * @return A double array of the Current Angular Velocity
     */
    public double[] getCurrentAngularVelocity(){return this.currentAngularVelocity;}
    /**
     * Returns the Current Joint Angles of the device
     * @return A double array of the Current Joint Angles
     */
    public double[] getCurrentJointAngles(){return this.currentJointAngles;}
    /**
     * Returns the Current Gimbal Angels of the device
     * @return A double array of the Current Gimbal Angels of the device
     */
    public double[] getCurrentGimbalAngels(){return this.currentGimbalAngles;}
    /**
     * Returns the Device Total Force at the current time
     * @return A double array of the Device Total Force for x,y,z
     */
    public double[] getDeviceTotalForce(){return this.deviceTotalForce;}
    /**
     * Returns the Device Total Torque at the current time
     * @return A double array of the Device Total Torque
     */
    public double[] getDeviceTotalTorque(){return this.deviceTotalTorque;}
    /**
     * Returns the Last Position of the Device
     * @return A double array of the Last Position of the Device
     */
    public double[] getLastPosition(){return this.lastPosition;}
    /**
     * Returns the Last Velocity of the Device
     * @return A double array of the Last Velocity of the Device
     */
    public double[] getLastVelocity(){return this.lastVelocity;}
    /**
     * Returns the Last Transform of the Device
     * @return A double array of the Last Transform of the Device
     */
    public double[] getLastTransform(){return this.lastTransform;}
    /**
     * Returns the Last Angular Velocity of the Device
     * @return A double array of the Last Angular Velocity of the Device
     */
    public double[] getLastAngularVelocity(){return this.lastAngularVelocity;}
    /**
     * Returns the Last Joint Angles of the Device
     * @return A double array of the Last Joint Angles of the Device
     */
    public double[] getLastJointAngles(){return this.lastJointAngles;}
    /**
     * Returns the Last Gimbal Angles of the Device
     * @return A double array of the Last Gimbal Angles of the Device
     */
    public double[] getLastGimbalAngles(){return this.lastGimbalAngles;}
    /**
     * Returns the Current Buttons of the Device
     * @return An int value representing the Current Button(s) pressed on the Device
     */
    public int getCurrentButtons(){return this.currentButtons;}
    /**
     * Returns the Last Buttons of the Device
     * @return An int value representing the Last Button(s) pressed on the Device
     */
    public int getLastButtons(){return this.lastButtons;}
    /**
     * Returns the Calibration State of the Device
     * @return An int value representing the Calibration state of the Device
     */
    public int getCalibrationState(){return this.calibrationState;}
    /**
     * Returns the Current Encoder value of the Device
     * @return A long array of the Current Encoder value of the Device
     */
    public long[] getCurrentEncoderValue(){return this.currentEncoderValue;}
    /**
     * Returns the Current Motor DAC of the Device
     * @return A long array of the Current Motor DAC
     */
    public long[] getCurrentMotorDAC(){return this.currentMotorDAC;}
    /**
     * Returns the Last Encoder value of the Device
     * @return A long array of the Last Encoder value of the Device
     */
    public long[] getLastEncoderValue(){return this.lastEncoderValue;}
    /**
     * Returns the Current Safety Switch state for the Device
     * @return A boolean value, <code>true</code> if the Safety Switch is on and <code>false</code> if not
     */
    public boolean getCurrentSafetySwitch(){return this.currentSafetySwitch;}
    /**
     * Returns the Last Safety Switch state for the Device
     * @return A boolean value, <code>true</code> if the Safety Switch is on and <code>false</code> if not
     */
    public boolean getLastSafetySwitch(){return this.lastSafetySwitch;}
    
    /**
     * Sets the Torque to be sent to the device
     * @param torque A double array represent the torque to send to the device
     */
    public void setCurrentTorque(double[] torque)
    {
        this.torque = torque;
    }
    
    /**
     * Makes this device current within the haptic renderer loop
     * @throws DeviceException If the device was able to be made current
     */
    public void makeCurrent() throws DeviceException
    {
        try{
            if(this.isDestroyed())
                throw new DeviceException(this, "The device connection has been destroyed, unable to contact device.", this.DEVICE_DESTROYED_ERROR,0);
            else if(HDAPI.hdGetCurrentDevice() != this.deviceID)
                HDAPI.hdMakeCurrentDevice(this.deviceID);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to make device current", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Updates the Calibration for the device
     * @throws DeviceException If the device is unable to be Calibratied
     */
    public void updateCalibration() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdUpdateCalibration(HDAPI.hdGetIntegerv(HDAPI.HD_CALIBRATION_STYLE)[0]);
            this.updated = true;
            support.fireDeviceUpdate("Calibration has been updated",this.UPDATE_CALIBRATION,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to update calibration of device", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns the ID of the device, this is the code to identify this device
     * @see com.uce.userlab.haptics.HD.HDAPI#hdInitDevice
     * @return An int value representing and identifying the device
     */
    public int getID(){return this.deviceID;}
    /**
     * Returns the Software version number of this implementation of device
     * @return A double value representing the software version
     */
    public double getSoftwareVersion(){return 1.1;}
    
    /**
     * Sets the Software Velocity Limiter so that force will not be applied if the device reaches a certain velocity
     * @param limit A double value representing the Software Velocity Limit
     * @throws DeviceException If for any reason a software velocity limit is unable to be changed
     */
    public void setSoftwareVelocityLimit(double limit) throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdSetDoublev(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT, new double[] {limit});
            this.updated = true;
            support.fireDeviceUpdate("Software Velocity Limit has been updated",this.UPDATE_SOFTWARE_VELOCITY_LIMIT,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to set the Software Velocity Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Sets the Software Force Impulse Limit so that force impulse will not exceed the limit set
     * @param limit A double value representing the Software Impulse Limit
     * @throws DeviceException If for any reason a Software Force Impulse is unable to be changed 
     */
    public void setSoftwareForceImpulseLimit(double limit) throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdSetDoublev(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT, new double[] {limit});
            this.updated = true;
            support.fireDeviceUpdate("Software Force Impulse Limit has been updated",this.UPDATE_SOFTWARE_FORCE_IMPULSE_LIMIT,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to set the Software Force Impulse Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Sets the Force Ramping Rate so that force will not ramp up more quicker than what is required
     * @param rate A double value to indicate the rate in which to ramp up force
     * @throws DeviceException If the force ramping rate could not be changed
     */
    public void setForceRampingRate(double rate) throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdSetDoublev(HDAPI.HD_FORCE_RAMPING_RATE, new double[] {rate});
            this.updated = true;
            support.fireDeviceUpdate("Force Ramping Rate has been updated",this.UPDATE_FORCE_RAMPING_RATE,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to set the Force Ramping Rate", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Sets the Schedule rate so that updates are made at the give rate
     * @param rate A long value representing the rate in which the scheduler should run and refresh at
     * @throws DeviceException If the scheduler rate could not be changed
     */
    public void setSchedulerRate(long rate) throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdSetSchedulerRate(rate);
            this.updated = true;
            support.fireDeviceUpdate("Scheduler Rate has been updated",this.UPDATE_SCHEDULER_RATE,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to set the Scheduler Rate", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables force output for the device
     * @throws DeviceException If the force output could not be enabled
     */
    public void enableForceOutput() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_FORCE_OUTPUT);
            this.updated = true;
            support.fireDeviceUpdate("Force Output has been enabled",this.FORCE_OUTPUT_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Force Output", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disabled force output for the device
     * @throws DeviceException If the force output could not be disabled
     */
    public void disableForceOutput() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_FORCE_OUTPUT);
            this.updated = true;
            support.fireDeviceUpdate("Force Output has been disabled",this.FORCE_OUTPUT_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Force Output", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables max force clamping for the device
     * @throws DeviceException If the max force clamping could not be enabled
     */
    public void enableMaxForceClamping() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_MAX_FORCE_CLAMPING);
            this.updated = true;
            support.fireDeviceUpdate("Max Force Clamping has been enabled",this.MAX_FORCE_CLAMPING_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Max Force Clamping", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disables max force clamping for the device
     * @throws DeviceException If the max force clamping could not be disabled
     */
    public void disableMaxForceClamping() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_MAX_FORCE_CLAMPING);
            this.updated = true;
            support.fireDeviceUpdate("Max Force Clamping has been disabled",this.MAX_FORCE_CLAMPING_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Max Force Clamping", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables force ramping for the device
     * @throws DeviceException If the force ramping could not be enabled
     */
    public void enableForceRamping() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_FORCE_RAMPING);
            this.updated = true;
            support.fireDeviceUpdate("Force Ramping has been enabled",this.FORCE_RAMPING_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Force Ramping", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disables force ramping for the device
     * @throws DeviceException If the force ramping could not be disabled
     */
    public void disableForceRamping() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_FORCE_RAMPING);
            this.updated = true;
            support.fireDeviceUpdate("Force Ramping has been disabled",this.FORCE_RAMPING_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Force Ramping", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables software force limit for the device
     * @throws DeviceException If the software force limit could not be enabled
     */
    public void enableSoftwareForceLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Force Limit has been enabled",this.SOFTWARE_FORCE_LIMIT_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Software Force Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disables software force limit for the device
     * @throws DeviceException If the software force limit could not be disabled
     */
    public void disableSoftwareForceLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Force Limit has been disabled",this.SOFTWARE_FORCE_LIMIT_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Software Force Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables software force impulse limit for the device
     * @throws DeviceException If the software force impulse limit could not be enabled
     */
    public void enableSoftwareForceImpulseLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Force Impulse Limit has been enabled",this.SOFTWARE_FORCE_IMPULSE_LIMIT_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Software Force Impulse Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disables software force impulse limit for the device
     * @throws DeviceException If the software force impulse limit could not be disabled
     */
    public void disableSoftwareForceImpulseLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Force Impulse Limit has been disabled",this.SOFTWARE_FORCE_IMPULSE_LIMIT_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Software Force Impulse Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Enables software velocity impulse limit for the device
     * @throws DeviceException If the software velocity impulse limit could not be enabled
     */
    public void enableSoftwareVelocityLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdEnable(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Velocity Limit has been enabled",this.SOFTWARE_VELOCITY_LIMIT_ENABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to enable Software Velocity Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Disables software velocity impulse limit for the device
     * @throws DeviceException If the software velocity impulse limit could not be disabled
     */
    public void disableSoftwareVelocityLimit() throws DeviceException
    {
        this.makeCurrent();
        try{
            HDAPI.hdDisable(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
            this.updated = true;
            support.fireDeviceUpdate("Software Velocity Limit has been disabled",this.SOFTWARE_VELOCITY_LIMIT_DISABLED,this.currentPosition,this.currentButtons);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to disable Software Velocity Limit", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Returns weather the force output is enabled
     * @return A boolean value, true if force output is enabled and false if not
     * @throws DeviceException If the status of force output can not be obtained
     */
    public boolean isForceOutputEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_FORCE_OUTPUT);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Force Output state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the max force clamping is enabled
     * @return A boolean value, true if max force clamping is enabled and false if not
     * @throws DeviceException If the status of max force clamping can not be obtained
     */
    public boolean isMaxForceClampingEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_MAX_FORCE_CLAMPING);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Max Force Clamping state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the force ramping is enabled
     * @return A boolean value, true if force ramping is enabled and false if not
     * @throws DeviceException If the status of force ramping can not be obtained
     */
    public boolean isForceRampingEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_FORCE_RAMPING);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Force Ramping state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the software force limit is enabled
     * @return A boolean value, true if software force limit is enabled and false if not
     * @throws DeviceException If the status of software force limit can not be obtained
     */
    public boolean isSoftwareForceLimitEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_FORCE_LIMIT);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Software Force Limit state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the software force impulse limit is enabled
     * @return A boolean value, true if the software force impulse is enabled and false if not
     * @throws DeviceException If the status of software force impulse limit can not be obtained
     */
    public boolean isSoftwareForceImpulseLimitEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_FORCE_IMPULSE_LIMIT);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Software Force Impulse Limit state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    /**
     * Returns weather the software velocity limit is enabled
     * @return A boolean value, true if the software velocity limit is enabled and false if not
     * @throws DeviceException If the status of software velocity limit can not be obtained
     */
    public boolean isSoftwareVelocityLimitEnabled() throws DeviceException
    {
        this.makeCurrent();
        try{
            return HDAPI.hdIsEnabled(HDAPI.HD_SOFTWARE_VELOCITY_LIMIT);
        }catch(HDException e)
        {
            throw new DeviceException(this, "Unable to optain weather Software Velocity Limit state", e.getErrorCode(), e.getInternalErrorCode());
        }
    }
    
    /**
     * Implements and Initalises the default phantom device on the machine
     * @see com.uce.userlab.haptics.PhantomDevice#PhantomDevice
     */
    public static final String DEFAULT_PHANTOM = "Default PHANToM";
    
    /**
     * Indicating value that the Calibration is OK on the device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationState
     */
    public static final int CALIBRATION_OK = HDAPI.HD_CALIBRATION_OK;
    /**
     * Indicating value that the Calibration is needs updating on the device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationState
     */
    public static final int CALIBRATION_NEEDS_UPDATE = HDAPI.HD_CALIBRATION_NEEDS_UPDATE;
    /**
     * Indicating value that the Calibration needs manual input on the device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationState
     */
    public static final int CALIBRATION_NEEDS_MANUNAL_INPUT = HDAPI.HD_CALIBRATION_NEEDS_MANUAL_INPUT;
    
    /**
     * Indicating value that the Calibration is updated automaticaly on device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationStyle
     */
    public static final int CALIBRATION_AUTO = HDAPI.HD_CALIBRATION_AUTO;
    /**
     * Indicating value that the Calibration is updated when encoder values are reset on device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationStyle
     */
    public static final int CALIBRATION_ENCODER_RESET = HDAPI.HD_CALIBRATION_ENCODER_RESET;
    /**
     * Indicating value that the Calibration is updated when in inkwell on device
     * @see com.uce.userlab.haptics.PhantomDevice#getCalibrationStyle
     */
    public static final int CALIBRATION_INKWELL = HDAPI.HD_CALIBRATION_INKWELL;
    
    /**
     * Indicating value that Button 1 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_1 = HDAPI.HD_DEVICE_BUTTON_1;
    /**
     * Indicating value that Button 2 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_2 = HDAPI.HD_DEVICE_BUTTON_2;
    /**
     * Indicating value that Button 3 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_3 = HDAPI.HD_DEVICE_BUTTON_3;
    /**
     * Indicating value that Button 4 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_4 = HDAPI.HD_DEVICE_BUTTON_4;
    
    /**
     * Indicating value that Buttons 1 and 2 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_1_AND_2 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_2;
    /**
     * Indicating value that Buttons 1 and 3 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_1_AND_3 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_3;
    /**
     * Indicating value that Buttons 1 and 4 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_1_AND_4 = HDAPI.HD_DEVICE_BUTTON_1 * HDAPI.HD_DEVICE_BUTTON_4;
    
    /**
     * Indicating value that Buttons 2 and 3 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_2_AND_3 = HDAPI.HD_DEVICE_BUTTON_2 * HDAPI.HD_DEVICE_BUTTON_3;
    /**
     * Indicating value that Buttons 2 and 4 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_2_AND_4 = HDAPI.HD_DEVICE_BUTTON_2 * HDAPI.HD_DEVICE_BUTTON_4;
    
    /**
     * Indicating value that Buttons 3 and 4 on device is being pressed
     * @see com.uce.userlab.haptics.PhantomDevice#getCurrentButtons
     * @see com.uce.userlab.haptics.PhantomDevice#getLastButtons
     */
    public static final int DEVICE_BUTTON_3_AND_4 = HDAPI.HD_DEVICE_BUTTON_3 * HDAPI.HD_DEVICE_BUTTON_4;
    
    
    /**
     * Event identifier for button(s) pressed down events
     */
    public static final int EVENT_BUTTONS_DOWN =                            2000;
    /**
     * Event identifier for button(s) that are released
     */
    public static final int EVENT_BUTTONS_UP =                              2001;
    /**
     * Event identifier for gimbal joint 1 motion
     */
    public static final int EVENT_GIMBAL_JOINT_1_MOTION =                   3000;
    /**
     * Event identifier for gimbal joint 2 motion
     */
    public static final int EVENT_GIMBAL_JOINT_2_MOTION =                   3001;
    /**
     * Event identifier for gimbal joint 3 motion
     */
    public static final int EVENT_GIMBAL_JOINT_3_MOTION =                   3002;
    /**
     * Event identifier for device motion
     */
    public static final int EVENT_MOTION =                                  4000;
    
    /**
     * Update event identifier for calibration updates
     */
    public static final int UPDATE_CALIBRATION =                            7000;
    /**
     * Update event identifier for software velocity limit updates
     */
    public static final int UPDATE_SOFTWARE_VELOCITY_LIMIT =                7200;
    /**
     * Update event identifier for software force impulse limit updates
     */
    public static final int UPDATE_SOFTWARE_FORCE_IMPULSE_LIMIT =           7400;
    /**
     * Update event identifier for force ramping rate updates
     */
    public static final int UPDATE_FORCE_RAMPING_RATE =                     7600;
    /**
     * Update event identifier for scheduler rate updates
     */
    public static final int UPDATE_SCHEDULER_RATE =                         7800;
    
    /**
     * Callback event identifier
     */
    public static final int CALLBACK_EVENT =                                1234;
    /**
     * Management error event identifier
     */
    public static final int MANAGEMENT_ERROR_EVENT =                        4321;
    
    /**
     * Event identifier for when force output is enabled
     */
    public static final int FORCE_OUTPUT_ENABLED =                          10;
    /**
     * Event identifier for when force output is disabled
     */
    public static final int FORCE_OUTPUT_DISABLED =                         -10;
    
    /**
     * Event identifier for when max force clamping is enabled
     */
    public static final int MAX_FORCE_CLAMPING_ENABLED =                    20;
    /**
     * Event identifier for when max force clamping is disbled
     */
    public static final int MAX_FORCE_CLAMPING_DISABLED =                   -20;
    
    /**
     * Event identifier for when force ramping is enabled
     */
    public static final int FORCE_RAMPING_ENABLED =                         30;
    /**
     * Event identifier for when force ramping is disabled
     */
    public static final int FORCE_RAMPING_DISABLED =                        -30;
    
    /**
     * Event identifier for when software force limit is enabled
     */
    public static final int SOFTWARE_FORCE_LIMIT_ENABLED =                  40;
    /**
     * Event identifier for when software force limit is disabled
     */
    public static final int SOFTWARE_FORCE_LIMIT_DISABLED =                 -40;
    
    /**
     * Event identifier for when software force impulse limit is enabled
     */
    public static final int SOFTWARE_FORCE_IMPULSE_LIMIT_ENABLED =          50;
    /**
     * Event identifier for when software force impulse limit is disabled
     */
    public static final int SOFTWARE_FORCE_IMPULSE_LIMIT_DISABLED =         -50;
    
    /**
     * Event identifier for when software velocity limit is enabled
     */
    public static final int SOFTWARE_VELOCITY_LIMIT_ENABLED =               60;
    /**
     * Event identifier for when software velocity limit is disabled
     */
    public static final int SOFTWARE_VELOCITY_LIMIT_DISABLED =              -60;
    
    
    /**
     * Error Type for when the device connection has been destroyed
     */
    public static final int DEVICE_DESTROYED_ERROR =                        -1;
    /**
     * If the Error is with the Device and its unknown by implementation
     */
    public static final int DEVICE_ERROR_UNKNOWN =                          -2;
}