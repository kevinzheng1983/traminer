package com.traminer.reader;

import com.traminer.base.Trajectory;

import java.io.IOException;

/**
 * This interface should be implemented by all trajectory readers.
 * @author Kevin Zheng
 */
public interface TrajectoryReader {
    // read a trajectory from a file
    Trajectory readTrajectory(String file);
    // return the description of the file format, e.g., lat, lon, time
    String formatDescription();
}
