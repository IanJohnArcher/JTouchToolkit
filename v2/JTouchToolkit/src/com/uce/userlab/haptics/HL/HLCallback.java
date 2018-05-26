package com.uce.userlab.haptics.HL;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Interface should be implemented by any object which wishes to receive Callbacks from the
 * HL toolkit. This is very simular to the C/C++ Callback functions, you will be forced by the
 * compiler to implement all the method but many will not be used in most case.
 */
public interface HLCallback
{
    /**
     * This method is called when the proxy is intersecting a shape or surface
     * @param startPt The start position of the surface/shape
     * @param endPt The end position of the surface/shape
     * @param intersectionPt The interesection point in the start and end of the surface/shape
     * @param intersectionNormal The normal of the point of interestion
     * @param face The face that is being interesected
     * @param data An array of objects that have been set to be passed at Callback scheduling
     * @return A boolean value to indicate weather to continue to calling the callback(s) or to stop
     */
    public boolean HL_CALLBACK_INTERSECT_LS(double[] startPt, double[] endPt, 
                                        double[] intersectionPt, double[] intersectionNormal,
                                        int face, Object[] data);
    /**
     * This method is called when the proxy is near a feature
     * @param queryPt The point in which the proxy was at querying
     * @param targetPt The target point of the feature
     * @param closestPt The closest point of the feature
     * @param data An array of objects that have been set to be passed at Callback scheduling
     * @return A boolean value to indicate weather to continue to calling the callback(s) or to stop
     */
    public boolean HL_CALLBACK_CLOSEST_FEATURE(double[] queryPt, double[] targetPt,
                                                double[] closestPt, Object[] data);
    /**
     * This method is called when you are required to compute the force of an effect
     * @param force The current force sent to the device
     * @param cache The cached object ready for querying
     * @param data An array of objects that have been set to be passed at Callback scheduling
     */
    public void HL_CALLBACK_COMPUTE_FORCE(double[] force, HLCache cache, Object[] data);
    /**
     * This method is called at the start of the force effect
     * @param cache The cached object ready for querying
     * @param data An array of objects that have been set to be passed at Callback scheduling
     */
    public void HL_CALLBACK_START_FORCE(HLCache cache, Object[] data);
    /**
     * This method is called at the end of the force effect
     * @param cache The cached object ready for querying
     * @param data An array of objects that have been set to be passed at Callback scheduling
     */
    public void HL_CALLBACK_STOP_FORCE(HLCache cache, Object[] data);
}
