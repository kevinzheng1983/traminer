package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;

import java.util.Arrays;
import java.util.List;

/**
 * Segment a trajectory if it stops for longer period than the maximum stay time.
 * @author Kevin Zheng
 */
public class MaxStaySegmentation implements TrajectorySegmentation {

    public int getMaximumStayTime() {
        return maximumStayTime;
    }

    public void setMaximumStayTime(int maximumStayTime) {
        this.maximumStayTime = maximumStayTime;
    }

    // maximum stay period allowed in milliseconds (default value is 3 minutes)
    private int maximumStayTime = 180 * 1000;

    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {
        return Arrays.asList(trajectory);
    }
}
