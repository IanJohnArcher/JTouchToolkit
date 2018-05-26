package com.uce.userlab.haptics.motion;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This represents a location in a 3D space, it provides access to x,y,z coOrdinates
 * and comparison and basic function support.
 */
public class PointerLocation
{
    /**
     * The location in 3D space, array of double x,y and z
     */
    private double[] loc;
    
    /**
     * Creates a new PointerLocation with coOrdinates x,y,z
     * @param x The X coOrdinate within the space
     * @param y The Y coOrdinate within the space
     * @param z The Z coOrdinate within the space
     */
    public PointerLocation(double x, double y, double z)
    {
        loc = new double[3];
        loc[0] = x;
        loc[1] = y;
        loc[2] = z;
    }
    /**
     * Returns the coOrdinates as an array of 3 double values x,y,z
     * @return The coOrdinates x,y,z
     */
    public double[] getLocation()
    {
        return loc;
    }
    /**
     * Returns the X coOrdinate
     * @return The X coOrdinate
     */
    public double getX()
    {
        return loc[0];
    }
    /**
     * Returns the Y coOrdinate
     * @return The Y coOrdinate
     */
    public double getY()
    {
        return loc[1];
    }
    /**
     * Returns the Z coOrdinate
     * @return The Z coOrdinate
     */
    public double getZ()
    {
        return loc[2];
    }
    /**
     * Returns the distance between this point int space and a given point in space
     * @param loc The point in space to work out the distance from this point in space
     * @return A double value of the distance between this location and a given location
     */
    public double getDistance(PointerLocation loc)
    {
        return this.getDistance(loc.getLocation());
    }
    /**
     * Returns the distance between this point int space and a given point in space, as x,y,z
     * @param x The X coOrdinate within the space
     * @param y The Y coOrdinate within the space
     * @param z The Z coOrdinate within the space
     * @return A double value of the distance between this location and a given location
     */
    public double getDistance(double x, double y, double z)
    {
        return this.getDistance(new double[]{x,y,z});
    }
    /**
     * Returns the distance between this point int space and a given point in space, as array of x,y,z
     * @param point The X,Y and Z coOrdinate within an array of doubles
     * @return A double value of the distance between this location and a given location
     */
    public double getDistance(double[] point)
    {
        double tmp = Math.pow(loc[0] - point[0], 2) + Math.pow(loc[1] - point[1], 2) + Math.pow(loc[2] - point[2], 2);
        double distance = Math.sqrt(tmp);
        return distance;
    }
    /**
     * Compares this location in space with another given location in space
     * @param obj The given location in space to compare
     * @return A boolean value, true if points in space are equal for all x,y and z, false if not all equal
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof PointerLocation)
        {
            PointerLocation loc = (PointerLocation)obj;
            if(loc.getX() == this.getX() && loc.getY() == this.getY() && loc.getZ() == this.getZ())
                return true;
            else
                return false;
        }
        else
            return false;
    }
    /**
     * Returns a String describing the point location and all its variables
     * @return A String describing the point location and all its variables
     */
    public String toString()
    {
        return loc[0] + "," + loc[1] + "," + loc[2];
    }
}
