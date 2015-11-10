package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;

import java.util.Arrays;
import java.util.List;

/**
 * Segment a trajectory if consecutive gps points have very long time gap.
 * @author Kevin Zheng
 */
public class MaxGapSegmentation implements TrajectorySegmentation {

    public int getMaximumGap() {
        return maximumGap;
    }

    public void setMaximumGap(int maximumGap) {
        this.maximumGap = maximumGap;
    }

    // maximum time gap allowed in milliseconds (default value is 10 minutes)
    private int maximumGap = 600 * 1000;

    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {
        return Arrays.asList(trajectory);
    }
}
