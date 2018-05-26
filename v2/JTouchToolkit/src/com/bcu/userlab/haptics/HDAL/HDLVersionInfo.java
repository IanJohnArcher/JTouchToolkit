/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics.HDAL;

/**
 *
 * @author id108336
 */
public class HDLVersionInfo {
    private int build;
    private int major;
    private int minor;
    
    public HDLVersionInfo(int build, int major, int minor) {
        this.build = build;
        this.major = major;
        this.minor = minor;
    }
    
    public int getBuildVersion() {
        return build;
    }
    public int getMajorVersion() {
        return major;
    }
    public int getMinorVersion() {
        return minor;
    }
    
    @Override
    public String toString() {
        return build + " :" + major + "." + minor;
    }
}
