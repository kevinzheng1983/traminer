package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;

import java.util.Arrays;
import java.util.List;

/**
 * Segment a trajectory if it cross multiple days.
 * @author Kevin Zheng
 */
public class dailySegmentation implements TrajectorySegmentation {
    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {
        return Arrays.asList(trajectory);
    }
}
