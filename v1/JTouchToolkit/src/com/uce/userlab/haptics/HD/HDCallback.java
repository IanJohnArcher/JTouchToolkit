package com.uce.userlab.haptics.HD;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This interface layout the contract for a Objects that wish to recieve callbacks from HDAPI Haptic device.
 * It is recommende Ian John Archer
 * @author id108336d that only a single Object within a Application use this method and all other classes receive
 * event from that method.
 */
public interface HDCallback
{
    /**
     * This method is called each execution of the scheduler in the servo loop for HDAPI
     * @param obj The Array of Objects set to be passed back to callback methods
     * @return An parameter identifier to indecate the state of the call back, see: <br><ul>
     *      <li><a href="HDAPI.html#HD_CALLBACK_CONTINUE"><code>HD_CALLBACK_CONTINUE</code></a></li>
     *      <li><a href="HDAPI.html#HD_CALLBACK_DONE"><code>HD_CALLBACK_DONE</code></a></li></ul>
     */
    public int HD_Callback(Object[] obj);
}
