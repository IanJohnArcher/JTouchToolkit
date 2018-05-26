/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics;

import com.uce.userlab.haptics.DeviceException;

/**
 *
 * @author id108336
 */
public final class PhantomDesktop extends Phantom {
    protected PhantomDesktop(String deviceName, int deviceID) throws DeviceException {
        super(deviceName, deviceID, 0.02);
    }
}
