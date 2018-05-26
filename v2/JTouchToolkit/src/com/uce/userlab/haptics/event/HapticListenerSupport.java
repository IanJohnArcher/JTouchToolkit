package com.uce.userlab.haptics.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import java.util.*;
import com.uce.userlab.haptics.DeviceException;

/**
 * This supports Device implimentations with managing listeners and firing events
 * to those Haptics Listeners. It provides methods to fire all the event set out
 * within the HapticListener interface with different inputs that maybe needed to
 * create the HapticEvent object.
 * @see com.uce.userlab.haptics.Device
 * @see com.uce.userlab.haptics.event.HapticListener
 * @see com.uce.userlab.haptics.event.HapticEvent
 */
public class HapticListenerSupport
{
    /**
     * A ADT to store the listeners within so that can added, removed, retrieved
     */
    private HashSet <HapticListener> listeners;
    /**
     * The source of all the event fired by this HapticListenerSupport object
     */
    private Object source;
    
    /**
     * Creates a new HapticListenerSupport object for a give source object
     * @param source The source Object that all events will be fired by
     */
    public HapticListenerSupport(Object source)
    {
        listeners = new HashSet <HapticListener> ();
        this.source = source;
    }
    
    /**
     * Adds a new HapticListener to the notify list of any events that are fired
     * @param listener The HapticListener to add
     */
    public void addHapticListener(HapticListener listener)
    {
        listeners.add(listener);
    }
    
    /**
     * Removes a HapticListener from the notify list for any events fired from then on
     * @param listener The HapticListener to remove
     */
    public void removeHapticListener(HapticListener listener)
    {
        listeners.remove(listener);
    }
    
    /**
     * Returns the list of HapticListener objects to notify as an Array of HapticListener objects
     * @return An array of HapticListener objects
     */
    public synchronized HapticListener[] getHapticListeners()
    {
        return this.listeners.toArray(new HapticListener[listeners.size()]);
    }
    
    /**
     * Returns the number of HapticListener objects on the notify list
     * @return A integer value for the number of HapticListener objects
     */
    public int getNumOfListeners()
    {
        return this.listeners.size();
    }
    
    /**
     * Fires a Device callback event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     */
    public void fireDeviceCallback(String message, int eventCode)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode);
        this.fireDeviceCallback(event);
    }
    /**
     * Fires a Device update event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     */
    public void fireDeviceUpdate(String message, int eventCode)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode);
        this.fireDeviceUpdate(event);
    }
    /**
     * Fires a Device input event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     */
    public void fireDeviceInput(String message, int eventCode)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode);
        this.fireDeviceInput(event);
    }
    /**
     * Fires a Device management error event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     * @param exc The DeviceException describing the management error that has occured
     */
    public void fireDeviceManagementError(String message, int eventCode, DeviceException exc)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode);
        this.fireDeviceManagementError(event, exc);
    }
    
    /**
     * Fires a Device callback event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     * @param currentPos The position of the device when the event was fired
     * @param input The input on the device when the event was fired
     */
    public void fireDeviceCallback(String message, int eventCode,
                                    double[] currentPos, int input)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode,
                                                currentPos, input);
        this.fireDeviceCallback(event);
    }
    /**
     * Fires a Device update event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     * @param currentPos The position of the device when the event was fired
     * @param input The input on the device when the event was fired
     */
    public void fireDeviceUpdate(String message, int eventCode,
                                    double[] currentPos, int input)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode,
                                                currentPos, input);
        this.fireDeviceUpdate(event);
    }
    /**
     * Fires a Device input event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     * @param currentPos The position of the device when the event was fired
     * @param input The input on the device when the event was fired
     */
    public void fireDeviceInput(String message, int eventCode,
                                    double[] currentPos, int input)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode,
                                                currentPos, input);
        this.fireDeviceInput(event);
    }
    /**
     * Fires a Device management error event to all HapticListener objects that are set to be notified
     * @param message The message describing the event
     * @param eventCode The identifier for the type of event to be fired
     * @param currentPos The position of the device when the event was fired
     * @param input The input on the device when the event was fired
     * @param exc The DeviceException describing the management error that has occured
     */
    public void fireDeviceManagementError(String message, int eventCode,
                                    double[] currentPos, int input, DeviceException exc)
    {
        HapticEvent event = new HapticEvent(source, message, eventCode,
                                                currentPos, input);
        this.fireDeviceManagementError(event, exc);
    }
    
    /**
     * Fires a Device callback event to all HapticListener objects that are set to be notified
     * @param event The event to pass to all HapticListener object describing the event
     */
    public void fireDeviceCallback(HapticEvent event)
    {
        Iterator<HapticListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().deviceCallback(event);
    }
    /**
     * Fires a Device update event to all HapticListener objects that are set to be notified
     * @param event The event to pass to all HapticListener object describing the event
     */
    public void fireDeviceUpdate(HapticEvent event)
    {
        Iterator<HapticListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().deviceUpdate(event);
    }
    /**
     * Fires a Device input event to all HapticListener objects that are set to be notified
     * @param event The event to pass to all HapticListener object describing the event
     */
    public void fireDeviceInput(HapticEvent event)
    {
        Iterator<HapticListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().deviceInput(event);
    }
    /**
     * Fires a Device management error event to all HapticListener objects that are set to be notified
     * @param event The event to pass to all HapticListener object describing the event
     * @param exc The DeviceException describing the management error that has occured
     */
    public void fireDeviceManagementError(HapticEvent event, DeviceException exc)
    {
        Iterator<HapticListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().deviceManagementError(event, exc);
    }
}
