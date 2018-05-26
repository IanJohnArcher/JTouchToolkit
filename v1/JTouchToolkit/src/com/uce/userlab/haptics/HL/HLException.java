package com.uce.userlab.haptics.HL;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Class is for representing Exception that occure during the use of the HL
 * API, this object encapsulates everything that is give by the HLAPI for c/c++
 * and even allows a creation of a HD Exception. If you are already aware of the
 * c/c++ error codes for HLAPI then this should clear to you.
 */
public class HLException extends java.lang.Exception
{
    /**
     * The Error code for the Exception
     */
    public int errorCode;
    /**
     * The Internal Error code for the Exception
     */
    public int internalErrorCode;
    /**
     * The Device code that the Exception occured with
     */
    public int device;
    
    /**
     * Creates a new HLException ready to be thrown
     * @param message A String describing the exception
     * @param errorCode The error code give to this type of exception in the C/C++ API
     * @param internalErrorCode The internal error code give to this type of exception in the C/C++ API
     * @param device The device identifier for the device that the error occured with
     */
    public HLException(String message, int errorCode, int internalErrorCode, int device)
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
        return this.errorCode;
    }
    /**
     * Returns the internal error code for the exception
     * @return The internal error code for the exception
     */
    public int getInternalErrorCode()
    {
        return this.internalErrorCode;
    }
    /**
     * Returns the device identifier for the exception
     * @return The device identifier for the exception
     */
    public int getDevice()
    {
        return this.device;
    }
    /**
     * Returns the HDException object for the same error within HD API
     * @return HDException for the same error within HD API
     */
    public com.uce.userlab.haptics.HD.HDException getHDException()
    {
        return new com.uce.userlab.haptics.HD.HDException("Error retrieved from HL toolkit: " + this.getMessage(),
                this.errorCode,
                this.internalErrorCode,
                this.device);
    }
}
