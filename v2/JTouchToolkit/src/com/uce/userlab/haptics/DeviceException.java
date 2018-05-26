package com.uce.userlab.haptics;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Class represents an Exception that is thrown by Haptic Device objects to
 * indicate that has been a problem with the device for whatever reason.
 */
public class DeviceException extends Exception
{
    /**
     * The device error code, the code returned by the device when an error occures
     */
    private int deviceErrorCode;
    /**
     * The device internal error code, the code that referes to the hardware/internal error
     */
    private int internalErrorCode;
    /**
     * The device code that referes to the device that the error occured with
     */
    private int device;
    /**
     * The object source the Object that the device was being used from
     */
    private Object source;
    
    /**
     * The constructor for creating a new DeviceException
     * @param source The source object that the error occured in
     * @param message A String of the message decribing the type of error
     * @param errorCode The error code for the exception
     * @param internalErrorCode The error code from the device
     */
    public DeviceException(Object source, String message, int errorCode, int internalErrorCode)
    {
        super(message);
        this.source = source;
        this.deviceErrorCode = errorCode;
        this.internalErrorCode = internalErrorCode;
    }
    
    /**
     * Returns the source object that the error occured with
     * @return An Object refereing to the object the source code has
     */
    public Object getSource()
    {
        return source;
    }
    
    /**
     * Returns the Device error code
     * @return The device error code
     */
    public int getDeviceErrorCode()
    {
        return this.deviceErrorCode;
    }
    
    /**
     * Returns the Internal error code
     * @return The Internal error code
     */
    public int getInternalErrorCode()
    {
        return this.internalErrorCode;
    }
    
    /**
     * Returns the device code
     * @return The device code
     */
    public int getDevice()
    {
        return this.device;
    }
}
