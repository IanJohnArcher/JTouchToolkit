/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics;

import com.uce.userlab.haptics.Device;
import com.uce.userlab.haptics.DeviceException;
import com.uce.userlab.haptics.event.HapticListener;
import com.uce.userlab.haptics.event.HapticListenerSupport;

/**
 *
 * @author id108336
 */
public abstract class HapticDevice implements Device {
    protected HapticListenerSupport listenerSupport;
    
    protected double[] lastPos;
    protected double[] currentPos;
    
    protected double[] lastForce;
    protected double[] currentForce;
    
    protected boolean enable;
    protected Object state;
    
    private double maxPositiveForceCap;
    private double maxNegativeForceCap;
    
    public HapticDevice() {
        listenerSupport = new HapticListenerSupport(this);
        
        lastPos = new double[] {0, 0, 0};
        currentPos = new double[] {0, 0, 0};
        
        lastForce = new double[] {0, 0, 0};
        currentForce = new double[] {0, 0, 0};
        
        enable = true;
        state = null;
        
        maxPositiveForceCap = Integer.MAX_VALUE;
        maxNegativeForceCap = Integer.MIN_VALUE;
    }

    public void addHapticListener(HapticListener listener) 
            throws DeviceException {
        listenerSupport.addHapticListener(listener);
    }
    public void removeHapticListener(HapticListener listener) 
            throws DeviceException {
        listenerSupport.removeHapticListener(listener);
    }

    public double[] getCurrentPosition() {
        return (currentPos != null && currentPos.length == 3) ?
            new double[] {currentPos[0], currentPos[1], currentPos[2]} :
            new double[] {0, 0, 0};
    }
    public double[] getLastPosition() {
        return (lastPos != null && lastPos.length == 3) ?
            new double[] {lastPos[0], lastPos[1], lastPos[2]} :
            new double[] {0, 0, 0};
    }
    protected void setPosition(double[] pos) {
        lastPos = this.getCurrentPosition();
        this.currentPos = (pos != null && pos.length == 3) ? 
            new double[] {pos[0], pos[1], pos[2]} :
            new double[] {0, 0, 0};
    }
    
    public void clearForce() {
        this.currentForce = new double[] {0, 0, 0};
    }
    public double[] getForce() {
        return (currentForce != null && currentForce.length == 3) ?
            new double[] {currentForce[0], currentForce[1], currentForce[2]} :
            new double[] {0, 0, 0};
    }
    public void sendForce(double[] force) {
        this.lastForce = this.getForce();
        this.currentForce = this.capForce((force != null && force.length == 3) ?
            new double[] {force[0], force[1], force[2]} :
            new double[] {0, 0, 0});
    }

    public Object getCurrentState() {
        return state;
    }
    public void updateCurrentState(Object state) {
        this.state = state;
        this.fireUpdateEvent("Current state changed", 
                HapticDevice.EVENT_UPDATE);
    }

    public abstract String getVendor();
    public abstract String getVersion();
    public abstract String getDevice();
    public abstract String getDeviceID();

    public void enable() {
        this.enable = true;
        this.fireUpdateEvent("Device enabled", 
                HapticDevice.EVENT_UPDATE);
    }
    public void disable() {
        this.enable = false;
        this.fireUpdateEvent("Device disabled", 
                HapticDevice.EVENT_UPDATE);
    }

    public boolean isEnabled() {
        return enable;
    }
    public boolean isDiabled() {
        return !enable;
    }
    
    public void setMaxPositiveForce(double maxPositive) {
        this.maxPositiveForceCap = (maxPositive >= 0) ? 
            maxPositive : -maxPositive;
        this.fireUpdateEvent("Max positive force changed", 
                HapticDevice.EVENT_UPDATE);
    }
    public void setMaxNegativeForce(double maxNegative) {
        this.maxNegativeForceCap = (maxNegative < 0) ? 
            maxNegative : -maxNegative;
        this.fireUpdateEvent("Max Negative force changed", 
                HapticDevice.EVENT_UPDATE);
    }
    
    public double getMaxPositiveForce() {
        return maxPositiveForceCap;
    }
    public double getMaxNegativeForce() {
        return maxNegativeForceCap;
    }
    
    protected double[] capForce(double[] force) {
        if(force != null && force.length == 3) {
            double fX = (force[0] > maxPositiveForceCap) ? maxPositiveForceCap : force[0];
                    fX = (fX < maxNegativeForceCap) ? maxNegativeForceCap : fX;
            double fY = (force[1] > maxPositiveForceCap) ? maxPositiveForceCap : force[1];
                    fY = (fY < maxNegativeForceCap) ? maxNegativeForceCap : fY;
            double fZ = (force[2] > maxPositiveForceCap) ? maxPositiveForceCap : force[2];
                    fZ = (fZ < maxNegativeForceCap) ? maxNegativeForceCap : fZ;
            
            return new double[] {fX, fY, fZ};
        } else {
            return new double[] {0, 0, 0};
        }
    }
    
    protected abstract void fireUpdateEvent(String message, int eventCode);
    protected abstract void fireInputEvent(String message, int eventCode);
    protected abstract void fireDeviceManagementError(String message, 
            int eventCode, DeviceException exc);
    
    /** @deprecated  **/
    public void setMaxForce(double max) throws Exception {
        throw new UnsupportedOperationException("Not supported");
    }
    /** @deprecated  **/
    public void setMinForce(double min) throws Exception {
        throw new UnsupportedOperationException("Not supported");
    }
    /** @deprecated  **/
    public double getMaxForce() throws Exception {
        throw new UnsupportedOperationException("Not supported");
    }
    /** @deprecated  **/
    public double getMinForce() throws Exception {
        throw new UnsupportedOperationException("Not supported");
    }
    
    public static final int EVENT_UPDATE = 0x114;
}
