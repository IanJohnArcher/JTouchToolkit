package com.uce.userlab.haptics.HD;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Class is the Java Implementation of OpenHaptics API HDAPI, this class is
 * includes all the methods needed to manage and use the device. It is not recommended
 * to use this class as direct interface to any haptic device within an application, it
 * is recommended you use an implementation of Device interface where possible.
 * This class should be used when implementing new Device classes, or for very
 * complex device management. It is also very good if you have experience using C/C++
 * OpenHaptics Toolkit already.
 */
public class HDAPI 
{
    /**
     * Enables or Disables force output for device.
     */
    public static final int HD_FORCE_OUTPUT = 0x4000;
    /**
     * Enables or Disables max force clamping for device.
     */
    public static final int HD_MAX_FORCE_CLAMPING = 0x4001;
    /**
     * Enables or Disables force ramping.
     */
    public static final int HD_FORCE_RAMPING = 0x4002;
    /**
     * Enables or Disables the software max force checking.
     */
    public static final int HD_SOFTWARE_FORCE_LIMIT = 0x4003;
    /**
     * Enables or Disables the software max force impulse checking. <I>Pass parameter required Array Size 1</I>
     */
    public static final int HD_SOFTWARE_FORCE_IMPULSE_LIMIT = 0x2607;
    /**
     * Enables or Disables the software max velocity checking. <I>Pass parameter required Array Size 1</I>
     */
    public static final int HD_SOFTWARE_VELOCITY_LIMIT = 0x2606;
    /**
     * Enables or Disables the one frame limit checking.
     */
    public static final int HD_ONE_FRAME_LIMIT = 0x4004;
    /**
     * Gets Button that is currently being used, Button N=1,2,3,4. <I>Return Array Size 1</I>
     */
    public static final int HD_CURRENT_BUTTONS = 0x2000;
    /**
     * Gets the safety switch sate. <I>Return Array Size 1</I>
     */
    public static final int HD_CURRENT_SAFETY_SWITCH = 0x2001;
    /**
     * Gets encoder values. <I>Return Array Size 6</I>
     */
    public static final int HD_CURRENT_ENCODER_VALUES = 0x2010;
    /**
     * Gets the current position of the device. <I>Return Array Size 3</I>
     */
    public static final int HD_CURRENT_POSITION = 0x2050;
    /**
     * Gets the current velocity of the device. <I>Return Array Size 3</I>
     */
    public static final int HD_CURRENT_VELOCITY = 0x2051;
    /**
     * Gets the current transform of the device. <I>Return Array Size 16</I>
     */
    public static final int HD_CURRENT_TRANSFORM = 0x2052;
    /**
     * Gets the angular velocity of the device. <I>Return Array Size 3</I>
     */
    public static final int HD_CURRENT_ANGULAR_VELOCITY = 0x2053;
    /**
     * Gets the joint angles of the device. <I>Return Array Size 3</I>
     */
    public static final int HD_CURRENT_JOINT_ANGLES = 0x2100;
    /**
     * Gets the gimbal angles of the device. <I>Return Array Size 3</I>
     */
    public static final int HD_CURRENT_GIMBAL_ANGLES = 0x2150;
    /**
     * Gets the current force of the device. <I>Return Array Size 3, Pass parameter required Array Size 3</I>
     */
    public static final int HD_CURRENT_FORCE = 0x2700;
    /**
     * Gets the current torgue of the device. <I>Return Array Size 3, Pass parameter required Array Size 3</I>
     */
    public static final int HD_CURRENT_TORQUE = 0x2701;
    /**
     * Gets the current motor DAC values. <I>Return Array Size 6, Pass parameter required Array Size 6</I>
     */
    public static final int HD_CURRENT_MOTOR_DAC_VALUES = 0x2750;
    /**
     * Value to indicate that calibration is Ok.
     */
    public static final int HD_CALIBRATION_OK = 0x5000;
    /**
     * Value to indicate that calibration needs updating.
     */
    public static final int HD_CALIBRATION_NEEDS_UPDATE = 0x5001;
    /**
     * Value to indicate that calibration needs manual input.
     */
    public static final int HD_CALIBRATION_NEEDS_MANUAL_INPUT = 0x5002;
    /**
     * Gets the Hardware revision version. <I>Return Array Size 1</I>
     * @deprecated
     */
    public static final int HD_HARDWAVE_REVISION = 23;
    /**
     * Gets the HDAPI version. <I>Return Array Size 1</I>
     */
    public static final int HD_VERSION = 0x2500;
    /**
     * Gets the device model type. <I>Return Array Size 1</I>
     */
    public static final int HD_DEVICE_MODEL_TYPE = 0x2501;
    /**
     * Gets the device driver version. <I>Return Array Size 1</I>
     */
    public static final int HD_DEVICE_DRIVER_VERSION = 0x2502;
    /**
     * Gets the device vendor information. <I>Return Array Size 1</I>
     */
    public static final int HD_DEVICE_VENDOR = 0x2503;
    /**
     * Gets the device serial number. <I>Return Array Size 1</I>
     */
    public static final int HD_DEVICE_SERIAL_NUMBER = 0x2504;
    /**
     * Gets the max workspace dimensions. <I>Return Array Size 6</I>
     */
    public static final int HD_MAX_WORKSPACE_DIMENSIONS = 0x2550;
    /**
     * Gets the usable workspace dimensions. <I>Return Array Size 6</I>
     */
    public static final int HD_USABLE_WORKSPACE_DIMENSIONS = 0x2551;
    /**
     * Gets the tabletop offset. <I>Return Array Size 1</I>
     */
    public static final int HD_TABLETOP_OFFSET = 0x2552;
    /**
     * Gets the input Degrees of Freedom. <I>Return Array Size 1</I>
     */
    public static final int HD_INPUT_DOF= 0x2553;
    /**
     * Gets the output Degrees of Freedom. <I>Return Array Size 1</I>
     */
    public static final int HD_OUTPUT_DOF = 0x2554;
    /**
     * Gets the calibration style for the device. <I>Return Array Size 1</I>
     */
    public static final int HD_CALIBRATION_STYLE = 0x2555;
    /**
     * Gets the last button pressed on device. <I>Return Array Size 1</I>
     */
    public static final int HD_LAST_BUTTONS = 0x2200;
    /**
     * Gets the last safety switch state on device. <I>Return Array Size 1</I>
     */
    public static final int HD_LAST_SAFETY_SWITCH = 0x2201;
    /**
     * Gets the last encoder values. <I>Return Array Size 6</I>
     */
    public static final int HD_LAST_ENCODER_VALUES = 0x2210;
    /**
     * Gets the last position the device was in. <I>Return Array Size 3</I>
     */
    public static final int HD_LAST_POSITION = 0x2250;
    /**
     * Gets the last velocity the device. <I>Return Array Size 3</I>
     */
    public static final int HD_LAST_VELOCITY = 0x2251;
    /**
     * Gets the last transform of the device. <I>Return Array Size 16</I>
     */
    public static final int HD_LAST_TRANSFORM = 0x2252;
    /**
     * Gets the last angular velocity. <I>Return Array Size 3</I>
     */
    public static final int HD_LAST_ANGULAR_VELOCITY = 0x2253;
    /**
     * Gets the last joint angles. <I>Return Array Size 3</I>
     */
    public static final int HD_LAST_JOINT_ANGLES = 0x2300;
    /**
     * Gets the last gimbal angles. <I>Return Array Size 3</I>
     */
    public static final int HD_LAST_GIMBAL_ANGLES = 0x2350;
    /**
     * Gets the nominal max stiffness. <I>Return Array Size 1</I>
     */
    public static final int HD_NOMINAL_MAX_STIFFNESS = 0x2602;
    /**
     * Gets the nominal max damping. <I>Return Array Size 1</I>
     */
    public static final int HD_NOMINAL_MAX_DAMPING = 0x2609;
    /**
     * Gets the nominal max force. <I>Return Array Size 1</I>
     */
    public static final int HD_NOMINAL_MAX_FORCE = 0x2603;
    /**
     * Gets the nominal max continuous force. <I>Return Array Size 1</I>
     */
    public static final int HD_NOMINAL_MAX_CONTINUOUS_FORCE = 0x2604;
    /**
     * Gets the motor temperature. <I>Return Array Size 6</I>
     */
    public static final int HD_MOTOR_TEMPERATURE = 0x2605;
    /**
     * Gets the software max velocity. <I>Return Array Size 1</I>
     * @deprecated 
     */
    public static final int HD_SOFTWARE_MAX_VELOCITY = 49;
    /**
     * Gets the force ramping rate. <I>Return Array Size 1, Pass parameter required Array Size 1</I>
     */
    public static final int HD_FORCE_RAMPING_RATE = 0x2608;
    /**
     * Gets the update rate. <I>Return Array Size 1</I>
     */
    public static final int HD_UPDATE_RATE = 0x2600;
    /**
     * Gets the instantaneous update rate. <I>Return Array Size 1</I>
     */
    public static final int HD_INSTANTANEOUS_UPDATE_RATE = 0x2601;
    /**
     * Parameter for auto calibration.
     */
    public static final int HD_CALIBRATION_AUTO = (1 << 1);
    /**
     * Parameter for when calibration needs encoder reset.
     */
    public static final int HD_CALIBRATION_ENCODER_RESET = (1 << 0);
    /**
     * Parameter for when calibration needs return to inkwell or rest position.
     */
    public static final int HD_CALIBRATION_INKWELL = (1 << 2);
    /**
     * Parameter for device button 1.
     */
    public static final int HD_DEVICE_BUTTON_1 = (1 << 0);
    /**
     * Parameter for device button 2.
     */
    public static final int HD_DEVICE_BUTTON_2 = (1 << 1);
    /**
     * Parameter for device button 3.
     */
    public static final int HD_DEVICE_BUTTON_3 = (1 << 2);
    /**
     * Parameter for device button 4.
     */
    public static final int HD_DEVICE_BUTTON_4 = (1 << 3);
    /**
     * Parameter for max scheduler priority.
     */
    public static final short HD_MAX_SCHEDULER_PRIORITY = Short.MAX_VALUE;
    /**
     * Parameter for min scheduler priority.
     */
    public static final short HD_MIN_SCHEDULER_PRIORITY = Short.MIN_VALUE;
    /**
     * Parameter for default scheduler priority.
     */
    public static final short HD_DEFAULT_SCHEDULER_PRIORITY = ((Short.MAX_VALUE-Short.MIN_VALUE)/2)+Short.MIN_VALUE;
    /**
     * Parameter to return when callbacks can continue.
     */
    public static final int HD_CALLBACK_CONTINUE = 1;
    /**
     * Parameter to return when callbacks are done.
     */
    public static final int HD_CALLBACK_DONE = 0;
    /**
     * Error parameter for full scheduler.
     */
    public static final int HD_SCHEDULER_FULL = 0x0501;
    /**
     * Error parameter for invalid priority passing.
     */
    public static final int HD_INVALID_PRIORITY = 0x0500;
    /**
     * Error parameter for invalid value passing.
     */
    public static final int HD_INVALID_VALUE = 0x0101;
    /**
     * Error parameter for timmer problems.
     */
    public static final int HD_TIMER_ERROR = 0x0304;
    /**
     * Error parameter for invalid operation.
     */
    public static final int HD_INVALID_OPERATION = 0x0102;
    /**
     * Error parameter for invalid scheduler handler.
     */
    public static final int HD_INVALID_HANDLE = 0xFFFFFFFF;
    /**
     * Waiting state for wait for completion function.
     */
    public static final int HD_WAIT_CHECK_STATUS = 0;
    /**
     * Waiting state for wait for completion function.
     */
    public static final int HD_WAIT_INFINITE = 1;
    
    public static final int HD_SUCCESS = 0x0000;
    
    public static final int HD_INVALID_ENIM = 0x0100;
    public static final int HD_INVALID_INPUT_TYPE = 0x0103;
    public static final int HD_BAD_HANDLE = 0x0104;
    
    public static final int HD_WARM_MOTORS = 0x0200;
    public static final int HD_EXCEEDED_MAX_FORCE = 0x0201;
    public static final int HD_EXCEEDED_MAX_FORCE_IMPULSE = 0x0202;
    public static final int HD_EXCEEDED_MAX_VELOCITY = 0x0203;
    public static final int HD_FORCE_ERROR = 0x0204;
    
    public static final int HD_DEVICE_FAULT = 0x0300;
    public static final int HD_DEVICE_ALREADY_INITIATED = 0x0301;
    public static final int HD_COMM_ERROR = 0x0302;
    public static final int HD_COMM_CONFIG_ERROR = 0x0303;
    
    public static final int HD_ILLEGAL_BEGIN = 0x0400;
    public static final int HD_ILLEGAL_END = 0x0401;
    public static final int HD_FRAME_ERROR = 0x0402;
    
    public static final int HD_DEVICE_FIRMWARE_VERSION = 0x2505;
    
    public static final int HD_LAST_FORCE = 0x2800;
    public static final int HD_LAST_TORQUE = 0x2801;
    public static final int HD_LAST_MOTOR_DAC_VALUES = 0x2850;
    
    public static final String HD_DEFAULT_DEVICE = "Default PHANToM";
    
    //********************************************************//
    /**
     * Begins an haptic frame, this opens a block of code that guarantee consistance
     * @param hHD The haptic device identifier given to you by <code><a href="#hdInitDevice(java.lang.String)">hdInitDevice</a></code>
     * @throws HDException If frame has already been open or is unable to open frame
     */
    public static native void hdBeginFrame(int hHD) throws HDException;
    /**
     * Disables a capability that is specified
     * @param cap The Capability to disable, see following list
     * <code><ul>
     *  <li><a href="#HD_FORCE_OUTPUT">HD_FORCE_OUTPUT</a></li>
     *  <li><a href="#HD_MAX_FORCE_CLAMPING">HD_MAX_FORCE_CLAMPING</a></li>
     *  <li><a href="#HD_FORCE_RAMPING">HD_FORCE_RAMPING</a></li>
     *  <li><a href="#HD_SOFTWARE_FORCE_LIMIT">HD_SOFTWARE_FORCE_LIMIT</a></li>
     *  <li><a href="#HD_SOFTWARE_FORCE_IMPULSE_LIMIT">HD_SOFTWARE_FORCE_IMPULSE_LIMIT</a></li>
     *  <li><a href="#HD_SOFTWARE_VELOCITY_LIMIT">HD_SOFTWARE_VELOCITY_LIMIT</a></li>
     *  <li><a href="#HD_ONE_FRAME_LIMIT">HD_ONE_FRAME_LIMIT</a></li>
     * </ul></code>
     * @throws HDException If the capability is not able to be disabled or is unknown
     */
    public static native void hdDisable(int cap) throws HDException;
    /**
     * Disables a device, renders current handler un-usable
     * @param hHD The haptic device identifier given to you by <code><a href="#hdInitDevice(java.lang.String)">hdInitDevice</a></code>
     */
    public static native void hdDisableDevice(int hHD);
    /**
     * Enables a capability that is specified
     * @param cap The Capability to enable, see following list
     * <code><ul>
     *  <li><a href="#HD_FORCE_OUTPUT">HD_FORCE_OUTPUT</a></li>
     *  <li><a href="#HD_MAX_FORCE_CLAMPING">HD_MAX_FORCE_CLAMPING</a></li>
     *  <li><a href="#HD_FORCE_RAMPING">HD_FORCE_RAMPING</a></li>
     *  <li><a href="#HD_SOFTWARE_FORCE_LIMIT">HD_SOFTWARE_FORCE_LIMIT</a></li>
     *  <li><a href="#HD_SOFTWARE_FORCE_IMPULSE_LIMIT">HD_SOFTWARE_FORCE_IMPULSE_LIMIT</a></li>
     *  <li><a href="#HD_SOFTWARE_VELOCITY_LIMIT">HD_SOFTWARE_VELOCITY_LIMIT</a></li>
     *  <li><a href="#HD_ONE_FRAME_LIMIT">HD_ONE_FRAME_LIMIT</a></li>
     * </ul></code>
     * @throws HDException If the capability is not able to be enabled or is unknown
     */
    public static native void hdEnable(int cap) throws HDException;
    /**
     * Ends an haptic frame, this closes a block of code that guarantees consistance
     * @param hHD The haptic device identifier given to you by <code><a href="#hdInitDevice(java.lang.String)">hdInitDevice</a></code>
     * @throws HDException If frame is not open or is unable to be closed
     */
    public static native void hdEndFrame(int hHD) throws HDException;
    //public static native void hdGet();
    /**
     * Gets information in the form of a boolean value from device for a give capability
     * @param cap The Capability to return the value for
     * @return Boolean Array of varying sizes depending on capability specified
     * @throws HDException If the capability is unable to return a boolean value or a value at all
     */
    public static native boolean[] hdGetBooleanv(int cap) throws HDException;
    /**
     * Gets information in the form of a integer value from device for a give capability
     * @param cap The Capability to return the value for
     * @return Integer Array of varying sizes depending on capability specified
     * @throws HDException If the capability is unable to return a integer value or a value at all
     */
    public static native int[] hdGetIntegerv(int cap) throws HDException;
    /**
     * Gets information in the form of a float value from device for a give capability
     * @param cap The Capability to return the value for
     * @return Float Array of varying sizes depending on capability specified
     * @throws HDException If the capability is unable to return a float value or a value at all
     */
    public static native float[] hdGetFloatv(int cap) throws HDException;
    /**
     * Gets information in the form of a double value from device for a give capability
     * @param cap The Capability to return the value for
     * @return Double Array of varying sizes depending on capability specified
     * @throws HDException If the capability is unable to return a double value or a value at all
     */
    public static native double[] hdGetDoublev(int cap) throws HDException;
    /**
     * Gets information in the form of a long value from device for a give capability
     * @param cap The Capability to return the value for
     * @return Long Array of varying sizes depending on capability specified
     * @throws HDException If the capability is unable to return a long value or a value at all
     */
    public static native long[] hdGetLongv(int cap) throws HDException;
    /**
     * Returns the identifier for the current device
     * @return The identifier for the current device
     * @throws HDException If no device is current, this can happen if no device has been initiated
     */
    public static native int hdGetCurrentDevice() throws HDException;
    //public static native HDErrorInfo hdGetError();
    //public static native String hdGetErrorString(HDErrorInfo error);
    /**
     * Gets the String value for an associated parameter name
     * @param cap The Capability/Parameter name to return String for
     * @return The Sting value for the Capability/Parameter specified
     * @throws HDException If the Capability does not support String as an input, or is the Capability is Unknown
     */
    public static native String hdGetString(int cap) throws HDException;
    /**
     * Initializes devices depending on specified device names (e.g. "Default PHANToM")
     * @param configName The name of the device to initialize
     * @return An identifier for the Haptic device that has been initialized
     * @throws HDException If the device is already intialized or if the device has a fault
     */
    public static native int hdInitDevice(String configName) throws HDException;
    /**
     * Checks if a specified capability is enabled or not
     * @param cap The Capability to check if is enabled or not
     * @return Boolean value, true if enabled and false if disabled
     * @throws HDException If the Capability is not able to be enabled or disabled, or if the Capability is Unknown
     */
    public static native boolean hdIsEnabled(int cap) throws HDException;
    /**
     * Makes the specified device the current device
     * @param hHD The haptic device identifier given to you by <code><a href="#hdInitDevice(java.lang.String)">hdInitDevice</a></code>
     * @throws HDException If the device is Unknown
     */
    public static native void hdMakeCurrentDevice(int hHD) throws HDException;
    
    //public static native void hdSet();
    /**
     * Sets information in the form of a boolean value for a give device capability
     * @param cap The Capability to set the value for
     * @param v Boolean Array with the information to use to set the capability to
     * @throws HDException If the capability is unable to be set to a boolean value or a value at all
     */
    public static native void hdSetBooleanv(int cap, boolean v[]) throws HDException;
    /**
     * Sets information in the form of a integer value for a give device capability
     * @param cap The Capability to set the value for
     * @param v Integer Array with the information to use to set the capability to
     * @throws HDException If the capability is unable to be set to a integer value or a value at all
     */
    public static native void hdSetIntegerv(int cap, int v[]) throws HDException;
    /**
     * Sets information in the form of a float value for a give device capability
     * @param cap The Capability to set the value for
     * @param v Float Array with the information to use to set the capability to
     * @throws HDException If the capability is unable to be set to a float value or a value at all
     */
    public static native void hdSetFloatv(int cap, float v[]) throws HDException;
    /**
     * Sets information in the form of a double value for a give device capability
     * @param cap The Capability to set the value for
     * @param v Double Array with the information to use to set the capability to
     * @throws HDException If the capability is unable to be set to a double value or a value at all
     */
    public static native void hdSetDoublev(int cap, double v[]) throws HDException;
    /**
     * Sets information in the form of a long value for a give device capability
     * @param cap The Capability to set the value for
     * @param v Long Array with the information to use to set the capability to
     * @throws HDException If the capability is unable to be set to a long value or a value at all
     */
    public static native void hdSetLongv(int cap, long v[]) throws HDException;
    
    //public static native int HD_DEVICE_ERROR(HDErrorInfo error);
    
    //********************************************************//
    //Calibration Routines
    /**
     * Checks the Calibration state of the current device
     * @return The Status of the Calibration for the current device in the form of identifiers <br><ul>
     *                  <li><code><a href="#HD_CALIBRATION_OK">HD_CALIBRATION_OK</a></code></li>
     *                  <li><code><a href="#HD_CALIBRATION_NEEDS_UPDATE">HD_CALIBRATION_NEEDS_UPDATE</a></code></li>
     *                  <li><code><a href="#HD_CALIBRATION_NEEDS_MANUAL_INPUT">HD_CALIBRATION_NEEDS_MANUAL_INPUT</a></code></li></ul>
     * @throws HDException If there is a device fault and the Calibration state could not be obtainned
     */
    public static native int hdCheckCalibration() throws HDException;
    /**
     * Calibrates the current device depending on the Style Specified
     * @param cap The Style of the Calibration to perform on the device
     * @throws HDException If there is a device fault and the Calibration could not be carried out
     */
    public static native void hdUpdateCalibration(int cap) throws HDException;
    
    //********************************************************//
    //Scheduler Routines
    /**
     * Returns elapse time since the start of the servo loop
     * @return A double value of the time that has elapse since the start of the servo loop
     */
    public static native double hdGetSchedulerTimeStamp();
    /**
     * Set the Callback from the scheduler to be Asynchronous, so it won't wait for completion before calling again
     * @param par The Object to callback to
     * @param obj Array of Objects to store and to pass back every callback as a parameter to HDCallback Object
     * @param cap The priority to give the callback in the loop
     * @return The handler for that callback so it can be unscheduled later
     * @throws HDException If the scheduler is full and if the priority thats set is incorrect.
     */
    public static native int hdScheduleAsynchronous(HDCallback par, Object[] obj, short cap) throws HDException;
    /**
     * Set the Callback from the scheduler to be Synchronous, so it will wait till completion before calling again
     * @param par The Object to callback to
     * @param obj Array of Objects to store and to pass back every callback as a parameter to HDCallback Object
     * @param cap The priority to give the callback in the loop
     * @throws HDException If the scheduler is full and if the priority thats set is incorrect.
     */
    public static native void hdScheduleSynchronous(HDCallback par, Object[] obj, short cap) throws HDException;
    /**
     * Sets the number of times the scheduler ticks before it Callback
     * @param rate The rate to Callback, Hz
     * @throws HDException If rate is not a valid rate in Hz
     */
    public static native void hdSetSchedulerRate(long rate) throws HDException;
    /**
     * Starts the Scheduler's Callback from the Servo Loop
     * @throws HDException If the servo loop thread could not be initialized
     */
    public static native void hdStartScheduler() throws HDException;
    /**
     * Stops the Scheduler's Callback from the Servo Loop
     * @throws HDException If the servo loop thread could not be initialized or stop for any reason
     */
    public static native void hdStopScheduler() throws HDException;
    /**
     * Unschedule the call back for an asynchronous scheduler
     * @param handler The handler for the asynchronous callback, returned by hdScheduleAsynchronous method
     */
    public static native void hdUnschedule(int handler);
    /**
     * Checks the callback is still scheduled to execution
     * @param cap The type of wait that is required <br><ul>
     *                  <li><code><a href="#HD_WAIT_CHECK_STATUS">HD_WAIT_CHECK_STATUS</a></code></li>
     *                  <li><code><a href="#HD_WAIT_INFINITE">HD_WAIT_INFINITE</a></code></li></ul>
     * @return Boolean value, true if the callback is scheduled or false if it is not
     */
    public static native boolean hdWaitForCompletion(int cap);
    
        //code that loads DLL/library file containing the C implementation of these methods
    	static
	{
            try{
                System.loadLibrary("JHDAPI");
            }catch(Exception e){}
	}
}
