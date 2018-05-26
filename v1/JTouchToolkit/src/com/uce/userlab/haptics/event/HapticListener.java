package com.uce.userlab.haptics.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.DeviceException;

/**
 * This interface lays out the methods that a haptic Device listener must have
 * to listen to any haptic Device, there are methods for regualar update calls
 * updates, inputs and event interal errors so that a listener is always kept
 * informed about the state inwhich the haptic Device is in.
 */
public interface HapticListener 
{
    /**
     * This methods is fired at a regular intervile to ask for updates from the
     * listeners
     * @param event The HapticEvent object that describes the event and the 
     * situation at the time of the event fire
     */
    public void deviceCallback(HapticEvent event);
    /**
     * This methods is fired when an update has been made to the device
     * @param event The HapticEvent object that describes the event and the 
     * situation at the time of the event fire
     */
    public void deviceUpdate(HapticEvent event);
    /**
     * This methods is fired when an input has been made on the device or its 
     * state has been changed by the user
     * @param event The HapticEvent object that describes the event and the 
     * situation at the time of the event fire
     */
    public void deviceInput(HapticEvent event);
    /**
     * This methods is fired when an internal error occures, usualy within a 
     * device management loop
     * @param event The HapticEvent object that describes the event and the 
     * situation at the time of the event fire
     * @param exc The DeviceException describing the error that occured internaly
     */
    public void deviceManagementError(HapticEvent event, DeviceException exc);
}
