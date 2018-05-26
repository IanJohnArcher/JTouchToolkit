package com.uce.userlab.haptics;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.event.*;

/**
 * This Interface provides a gerneric Interface for haptic device communication,
 * it provides the basic methods/functionality for communicating with a haptic
 * device. This has been developed to provide a standard contract for device
 * encapsulating Objects that provide access to devices of different manufacture
 * and specification. The purpose of this is allow classes to be developed that
 * provide processing and managing of haptic devices without needing to know the
 * device persific details of that device.
 */
public interface Device
{
    /**
     * Allows a haptic listener to be added to the device
     * @param listener The HapticListener Object to be added to the notify list
     * @throws Exception if for any reason it is not possiable to add HapticListener(s) Object
     */
    public void addHapticListener(HapticListener listener)throws Exception;
    /**
     * Allows a haptic listener to be removed from the device notify list
     * @param listener The HapticListener Object to be removed from the notify list
     * @throws Exception if for any reason it is not possiable to remove HapticListener(s) Object
     */
    public void removeHapticListener(HapticListener listener)throws Exception;
    
    /**
     * Return the current position values of device coOrdinates
     * @return A double array that contains the location coOrdinates for x,y,z
     * @throws Exception if for any reason the current position is not obtainable
     */
    public double[] getCurrentPosition() throws Exception;
    
    /**
     * Sets the max force to use on the device, [saftey feature]
     * @param max The maxium force allowed to be sent to the device
     * @throws Exception if for any reason the device forces cannot be controlled
     */
    public void setMaxForce(double max) throws Exception;
    /**
     * Sets the minimum force to use on the device, [saftey feature]
     * @param min The minimum force allowed to be sent (this force is usually negative force [pull])
     * @throws Exception if for any reason the device forces cannot be controlled
     */
    public void setMinForce(double min) throws Exception;
    
    /**
     * Returns the current maximum force that the device can send to the device
     * @return A double value representing the max force that has been set
     * @throws Exception if for any reason the device doesn't provide force control
     */
    public double getMaxForce() throws Exception;
    /**
     * Returns the current minimum force that the device can send to the device
     * @return A double value representing the minimum force that has been set
     * @throws Exception if for any reason the device doesn't provide force control
     */
    public double getMinForce() throws Exception;
    
    /**
     * Returns the current state of the device
     * @return A Object that represent details of the current state the device has been set to
     * @throws Exception if for any reason the device implementation doesn't support this
     */
    public Object getCurrentState() throws Exception;
    /**
     * Sets/Updates the current state of the device
     * @param state An Object that represents the current state the device is in
     * @throws Exception if for any reason the device implementation doesn't support this
     */
    public void updateCurrentState(Object state) throws Exception;
    
    /**
     * Returns the device vendor
     * @return A String value of the device Vendor name
     */
    public String getVendor();
    /**
     * Returns the device version
     * @return A String value of the device Version
     */
    public String getVersion();
    /**
     * Returns the device name
     * @return A String value of the device name
     */
    public String getDevice();
    /**
     * Returns the device id or serial number
     * @return A String value of the device id or serial number
     */
    public String getDeviceID();
    
    /**
     * Enables the device for use
     * @throws Exception if for any reason the device doesn't support or cannot do this
     */
    public void enable() throws Exception;
    /**
     * Disables the device
     * @throws Exception if for any reason the device doesn't support or cannot do this
     */
    public void disable() throws Exception;
    /**
     * Returns weather the device is enabled or not
     * @return A boolean value, <code>true</code> if device is enabled or <code>false</code> if not
     * @throws Exception if the device doesn't support this function
     */
    public boolean isEnabled() throws Exception;
    /**
     * Returns weather the device is disabled or not
     * @return A boolean value, <code>true</code> if device is disabled or <code>false</code> if not
     * @throws Exception if the device doesn't support this function
     */
    public boolean isDiabled() throws Exception;
    
    /**
     * Sends force to the device in bases of force for x,y,z axis
     * @param force A double array representing the force for x,y,z
     * @throws Exception if the device doesn't support this function
     */
    public void sendForce(double[] force) throws Exception;
    /**
     * Clears the current force set for sending to the device
     * @throws Exception if the device doesn't support this function
     */
    public void clearForce() throws Exception;
    /**
     * Returns the current force values for the device
     * @return A double array representing the force values on the x,y,z axis
     * @throws Exception if the device doesn't support this function
     */
    public double[] getForce() throws Exception;
}
