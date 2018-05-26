package com.uce.userlab.haptics.HL;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Object is used to represent the cache of the parameters stored when the event is fired. This is because of the
 * time delay of the event call and multi-threads that mean that parameters may change before callback is carried out,
 * to solve this a cache is made of parameters values so they can be refered to by the callback method.
 */
public class HLCache
{
    private boolean button1State, button2State, proxyIsTouch;
    private double[] proxyTouchNormal, proxyPosition, proxyRotation, proxyTransfrom,
                    devicePosition, deviceRotation, deviceTransform, deviceForce, deviceTorque;
    
    /** 
     * Creates a new instance of HLCache 
     * @param but1 Not currently in use, sorry
     * @param but2 Not currently in use, sorry
     * @param proxyTouch Not currently in use, sorry
     * @param proxyTouchNom Not currently in use, sorry
     * @param proxyPos The current Proxy Position
     * @param proxyRot The current Proxy Rotation
     * @param proxyTrans The current Proxy Transform
     * @param devicePos Not currently in use, sorry
     * @param deviceRot Not currently in use, sorry
     * @param deviceTrans Not currently in use, sorry
     * @param deviceFor Not currently in use, sorry
     * @param deviceTor Not currently in use, sorry
     */
    public HLCache(boolean but1, boolean but2, boolean proxyTouch,
                    double[] proxyTouchNom, double[] proxyPos, double[] proxyRot, double[] proxyTrans,
                    double[] devicePos, double[] deviceRot, double[] deviceTrans, double[] deviceFor, double[] deviceTor)
    {
        this.button1State = but1;
        this.button2State = but2;
        
        this.proxyIsTouch = proxyTouch;
        this.proxyTouchNormal = proxyTouchNom;
        this.proxyPosition = proxyPos;
        this.proxyRotation = proxyRot;
        this.proxyTransfrom = proxyTrans;
        
        this.devicePosition = devicePos;
        this.deviceRotation = deviceRot;
        this.deviceTransform = deviceTrans;
        this.deviceForce = deviceFor;
        this.deviceTorque = deviceTor;
    }
    
    
    //public boolean getButton1State(){return this.button1State;}
    //public boolean getButton2State(){return this.button2State;}
    //public boolean isProxyTouching(){return this.proxyIsTouch;}
    //public double[] getProxyTouchNormal(){return this.proxyTouchNormal;}
    
    /**
     * Returns the Proxy Position that was cached when the event was fired
     * @return A double array of the cached proxy position, <I>vector x,y,z</I>
     */
    public double[] getProxyPosition(){return this.proxyPosition;}
    /**
     * Returns the Proxy Rotation that was cached when the event was fired
     * @return A double array of the cached proxy rotation, <I>vector x,y,z</I>
     */
    public double[] getProxyRotation(){return this.proxyRotation;}
    /**
     * Returns the Proxy Transform that was cached when the event was fired
     * @return A double array of the cached proxy transform, <I>vector x,y,z</I>
     */
    public double[] getProxyTransform(){return this.proxyTransfrom;}
    
    //public double[] getDevicePosition(){return this.devicePosition;}
    //public double[] getDeviceRatation(){return this.deviceRotation;}
    //public double[] getDeviceTransform(){return this.deviceTransform;}
    //public double[] getDeviceForce(){return this.deviceForce;}
    //public double[] getDeviceTorgue(){return this.deviceTorque;}
}
