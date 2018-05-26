package com.uce.userlab.haptics.HD;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Class is for representing exception thrown by the HD API haptic device interface,
 * this exception encapsulate everything that is given in the errors within c/c++ HD API
 * by OpenHaptics toolkit. This means that if you are aware of the c/c++ toolkit you will
 * find it easier to understand the codes and information.
 */
public class HDException extends java.lang.Exception
{
    /**
     * The error code of the exception from c/c++
     */
    private int errorCode;
    /**
     * The internal error code from the device
     */
    private int internalErrorCode;
    /**
     * The device identifier that the error has occured with
     */
    private int device;
    
    /**
     * The Constructor of the exception, it sets all the variables and information
     * @param message The message that states the name of the exception
     * @param errorCode The error code of the exception from c/c++
     * @param internalErrorCode The internal error code from the device
     * @param device The device identifier that the error has occured with
     */
    public HDException(String message, int errorCode, int internalErrorCode, int device)
    {
        super(message);
        this.errorCode = errorCode;
        this.internalErrorCode = internalErrorCode;
        this.device = device;
    }
    /**
     * Returns the error code for the exception
     * @return The error code for the exception
     */
    public int getErrorCode()
    {
        return errorCode;
    }
    /**
     * Returns the internal error code for the exception
     * @return The internal error code for the exception
     */
    public int getInternalErrorCode()
    {
        return internalErrorCode;
    }
    /**
     * Returns the device identifier for the exception
     * @return The device identifier for the exception
     */
    public int getDevice()
    {
        return device;
    }
}