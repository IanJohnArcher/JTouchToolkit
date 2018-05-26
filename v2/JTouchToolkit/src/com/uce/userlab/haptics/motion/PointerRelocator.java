package com.uce.userlab.haptics.motion;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.*;

/**
 * This Object represents a Pointer relocator, it allows a point to be give that
 * you wish the haptic device to travel to and stay until another location is
 * specified.
 */
public class PointerRelocator implements Runnable
{
    /**
     * The device to keep within the boundaries of a point in space
     */
    private Device device;
    /**
     * The thread used to loop to control the force needed to maintain a location in space
     */
    private Thread thread;
    /**
     * The max force allowed to apply to the device when trying to move device to location
     */
    private double maxForce;
    /**
     * The min force allowed to apply to the device when trying to move device to location
     */
    private double minForce;
    /**
     * The boundary allowed around the location before force is applied
     */
    private double boundary;
    /**
     * The new location to move the haptic device to
     */
    private double[] newLoc;
    /**
     * The old location to move the haptic device to
     */
    private double[] oldLoc;
    
    /**
     * Creates a new PointerRelocator for a given device
     * @param device The Device to relocate
     */
    public PointerRelocator(Device device)
    {
        this.device = device;
        this.thread = new Thread(this);
        try{
            this.maxForce = device.getMaxForce();
            this.minForce = device.getMinForce();
        }catch(Exception e)
        {
            this.maxForce = 0;
            this.minForce = 0;
        }
        this.newLoc = new double[]{0,0,0};
        this.oldLoc = new double[]{0,0,0};
        this.boundary = 1;
    }
    
    /**
     * Sets the Max force that is allowed to be applied to the device to get it to a set location
     * @param force The maximum force that can be applied to the device
     */
    public void setMaxForce(double force){this.maxForce = force;}
    /**
     * Sets the Min force that is allowed to be applied to the device to get it to a set location
     * @param force The minimum force that can be applied to the device
     */
    public void setMinForce(double force){this.minForce = force;}
    
    /**
     * Returns the Maximum force set that is allowed to be applied to the device e.g. Positive force <push>
     * @return The Maximum force set
     */
    public double getMaxForce(){return this.maxForce;}
    /**
     * Returns the Minimum force set that is allowed to be applied to the device, e.g. Negative force <pull>
     * @return The Minimum force set
     */
    public double getMinForce(){return this.minForce;}
    
    /**
     * Set the Location to move the device to
     * @param loc The location to move the device to, x,y,z
     */
    public void setLocation(double[] loc)
    {
        if(loc.length == 3)
        {
            this.oldLoc = this.newLoc;
            this.newLoc = loc;
        }
    }
    /**
     * Returns the Location that is currently set to move the device to
     * @return The Location that is currently set to move the device to
     */
    public double[] getLocation(){return this.newLoc;}
    
    /**
     * Sets the boundary to give the point before the force is applied
     * @param distance The distance from the point set before applying force
     */
    public void setBoundary(double distance){this.boundary = distance;}
    /**
     * Returns the boundary that is set for the point in space
     * @return The distance from the set point before force is applied
     */
    public double getBoundary(){return this.boundary;}
    /**
     * Checks weather the haptic device is within the boundary
     * @return A boolean value, true if the haptic device is within the boundary, false if not
     */
    public boolean isInBoundary()
    {
        try{
            return (new PointerLocation(this.newLoc[0], this.newLoc[1], this.newLoc[2]).getDistance(device.getCurrentPosition())) <= this.boundary;
        }catch(Exception e)
        {
            return false;
        }
    }
    
    /**
     * Returns the actual location of the device
     * @return The actual location of the device
     */
    public double[] getActualLocation()
    {
        try{
            return device.getCurrentPosition();
        }catch(Exception e)
        {
            return new double[] {0,0,0};
        }
    }
    /**
     * Returns the distance the device is from the boundary of the point set
     * @return The distance the device is from the boundart of the point set
     */
    public double getDistanceFromBoundary()
    {
        try{
            return (new PointerLocation(this.newLoc[0], this.newLoc[1], this.newLoc[2]).getDistance(device.getCurrentPosition()))-this.boundary;
        }catch(Exception e)
        {
            return -1;
        }
    }
    
    /**
     * Starts the PointerRelocator appling force to move device to specified location
     */
    public void start()
    {
        if(!this.thread.isAlive())
        {
            this.oldLoc = this.newLoc;
            this.thread.start();
        }
    }
    /**
     * Stops the PointerRelocator appling force to move device to specified location
     */
    public void stop()
    {
        if(this.thread.isAlive())
        {
            try{
                if(this.thread != null)
                {
                    this.thread.interrupt();
                    this.device.clearForce();
                }
            }catch(Exception e){e.printStackTrace();}
        }
    }
    
    /**
     * Checks the force that is about to be applied, this prevents it from 
     * exceeding max and minimum force parameters
     * @param force The force for x,y and Z to check against the max and minium 
     * parameters
     * @return The force that is allowed to be applied to the device, if exceeded
     * force will be trimed to min or max if not will return without change
     */
    private double[] checkForce(double[] force)
    {
        for(int i=0; i<3; i++)
        {
            if(force[i] > this.maxForce)
                force[i] = this.maxForce;
            else if(force[i] < this.minForce)
                force[i] = this.minForce;
        }
        return force;
    }
    
    /**
     * Runs within thread to manage the device loaction and the force needed to 
     * applie to make the device stay at location
     */
    public void run()
    {
        try
        {
            while(true)
            {               
                double[] pos = device.getCurrentPosition();
                double tmp = Math.pow(pos[0] - this.newLoc[0], 2) + 
                                Math.pow(pos[1] - this.newLoc[1], 2) + 
                                Math.pow(pos[2] - this.newLoc[2], 2);
                double distance = Math.sqrt(tmp);
                
                if(distance > this.boundary)
                {
                    double[] force = new double[] {(pos[0] - this.newLoc[0])/distance,
                                                    (pos[1] - this.newLoc[1])/distance,
                                                    (pos[2] - this.newLoc[2])/distance};
                    force[0] = force[0]*-1;
                    force[1] = force[1]*-1;
                    force[2] = force[2]*-1;

                    double k = 0.5; //stiffness;
                    force[0] = force[0]*(distance-this.boundary);
                    force[1] = force[1]*(distance-this.boundary);
                    force[2] = force[2]*(distance-this.boundary);

                    force[0] = force[0]*k;
                    force[1] = force[1]*k;
                    force[2] = force[2]*k;

                    device.clearForce();
                    device.sendForce(this.checkForce(force));
                }
                Thread.sleep(1);
            }
        }
        catch(Exception e)
        {
            
        }
    }
}
