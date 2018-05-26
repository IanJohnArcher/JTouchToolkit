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
public final class PhantomUnknown extends Phantom {
    public PhantomUnknown(String deviceName, int deviceID, double res) 
            throws DeviceException {
        super(deviceName, deviceID, res);
    }
}
