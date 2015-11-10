package com.traminer.base;

import com.graphhopper.util.GPXEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class of spatial trajectory that consists of a collection
 * of GPXEntry. Each GPXEntry represents a GPS point with lat, lon,
 * elevation (optional) and timestamp.
 *
 * @author Kevin Zheng
 * @see GPXEntry
 */
public class Trajectory extends ArrayList<GPXEntry>
{
    private String id;

    public Trajectory() {
        super();
    }

    public Trajectory(int initialCapacity) {
        super(initialCapacity);
    }

    public Trajectory(Collection<? extends GPXEntry> c) {
        super(c);
    }

    /**
     * Initialize a trajectory object with an array of GPXEntry objects.
     * @param c the array of GPXEntry objects that form the trajectory.
     */
    public Trajectory(GPXEntry[] c) {
        super(Arrays.asList(c));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * String representation of a trajectory.
     * @return string in the format of <tt>id : [{gps1}, {gps2}, ..., {gpsn}]</tt>
     */
    @Override
    public String toString() {
        return this.id + " : " + gpsString();
    }

    private String gpsString() {
        Iterator<GPXEntry> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            GPXEntry e = it.next();

            sb.append("{" + e + "}");
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
