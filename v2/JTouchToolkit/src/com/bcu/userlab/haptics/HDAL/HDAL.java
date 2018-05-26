/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics.HDAL;

/**
 *
 * @author id108336
 */
public class HDAL {
    public static final int HDAL_ISREADY = 0;
    public static final int HDAL_NOT_CALIBRATED = 0x04;
    public static final int HDAL_SERVO_NOT_STARTED = 0x02;
    public static final int HDAL_UNINITIALIZED = 0x01;
    
    public static final int HDL_BUTTON_1 = 0x00000001;
    public static final int HDL_BUTTON_2 = 0x00000002;
    public static final int HDL_BUTTON_3 = 0x00000004;
    public static final int HDL_BUTTON_4 = 0x00000008;
    public static final int HDL_BUTTON_ANY = 0xffffffff;
    
    public static final int HDL_DEFAULT_DEVICE_ID = 0;
    public static final int HDL_INVALID_HANDLE = -1;
    public static final int HDL_SERVOOP_CONTINURE = 1;
    public static final int HDL_SERVOOP_EXIT = 0;
    
    public static final int HDL_VERSION_INVALID = -1;
    public static final int HDL_VERSION_NOT_APPLICABLE = -2;
    public static final int HDL_VERSION_UNAVAILABLE = -3;
    
    public static final int HDL_HDAL = 0x11;
    public static final int HDL_DEVICE = 0x21;
    public static final int HDL_DEVICE_SDK = 0x22;
    public static final int HDL_DEVICE_COMMS = 0x23;
    public static final int HDL_DEVICE_OS = 0x24;
    public static final int HDL_GRIP = 0x33;
    
    public static final int HDL_ERROR_INIT_FAILED = 0x10;
    public static final int HDL_ERROR_INTERNAL = 0x02;
    public static final int HDL_ERROR_STACK_OVERFLOW = 0x01;
    public static final int HDL_INIT_DEVICE_ALREADY_INITED = 0x16;
    public static final int HDL_INIT_DEVICE_FAILURE = 0x15;
    public static final int HDL_INIT_DEVICE_NOT_CONNECTED = 0x17;
    public static final int HDL_INIT_DLL_LOAD_ERROR = 0x14;
    public static final int HDL_INIT_ERROR_MASK = 0x1F;
    public static final int HDL_INIT_INI_DLL_STRING_NOT_FOUND = 0x12;
    public static final int HDL_INIT_INI_MANUFACTURER_NAME_STRING_NOT_FOUND = 0x13;
    public static final int HDL_INIT_INI_NOT_FOUND = 0x11;
    public static final int HDL_NO_ERROR = 0x0;
    public static final int HDL_SERVO_START_ERROR = 0x18;
    
    //public static native int HDL_BUILD_VERSION();
    //public static native int HDL_MAJOR_VERSION();
    //public static native int HDL_MINOR_VERSION();
    
    public static native int hdlCountDevices();
    public static native int hdlCreateServoOp(HDLCallback callback, Object[] pUserData, boolean bBlocking);
    public static native void hdlDestroyServoOp(int hServoOp);
    public static native String hdlDeviceModel();
    public static native double[] hdlDeviceWorkspace() throws HDLException;
    
    public static native int hdlGetState() throws HDLException;
    public static native HDLVersionInfo hdlGetVersion(int requestType);
    
    public static native int hdlInitDevice(int deviceID) throws HDLException;
    public static native int hdlInitIndexedDevice(int index) throws HDLException;
    public static native int hdlInitNamedDevice(String deviceName) throws HDLException;
    
    public static native void hdlMakeCurrent(int hHandle) throws HDLException;
    public static native void hdlSetToolForce(double[] force) throws HDLException;
    
    public static native void hdlStart() throws HDLException;
    public static native void hdlStop();
    
    public static native boolean hdlToolButton();
    public static native int hdlToolButtons();
    public static native double[] hdlToolPosition();
    
    public static native void hdlUninitDevice(int hHandle) throws HDLException;
    
        //code that loads DLL/library file containing the C implementation of these methods
    	static
	{
            try{
                System.loadLibrary("JHDAL");
            }catch(Exception e){}
	}
}
