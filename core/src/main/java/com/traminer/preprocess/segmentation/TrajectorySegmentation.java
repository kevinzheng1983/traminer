package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;

import java.util.List;

/**
 * A common interface for segmenting a long trajectory into multiple short, more meaningful trajectories.
 * @author Kevin Zheng
 */
public interface TrajectorySegmentation {
    List<Trajectory> doSegmentation(Trajectory trajectory);
}
