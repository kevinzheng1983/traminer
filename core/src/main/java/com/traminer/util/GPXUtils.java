package com.traminer.util;

import com.graphhopper.util.GPXEntry;

/**
 * Created by uqkzheng on 10/11/2015.
 */
public class GPXUtils {
    public static boolean isEqual(GPXEntry a, GPXEntry b) {
        return Math.abs(a.getLat() - b.getLat()) < 1e-4 && Math.abs(a.getLon() - b.getLon()) < 1e-4;
    }

    public static void main(String[] args) {
        GPXEntry p1 = new GPXEntry(39.398423, 106.38293, 1348392838293L);
        GPXEntry p2 = new GPXEntry(39.398445, 106.38294, 1348392838293L);

        System.out.println(GPXUtils.isEqual(p1, p2));
    }
}
