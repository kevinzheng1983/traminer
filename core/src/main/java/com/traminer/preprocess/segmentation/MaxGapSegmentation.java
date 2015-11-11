package com.traminer.preprocess.segmentation;

import com.graphhopper.util.GPXEntry;
import com.traminer.base.Trajectory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Segment a trajectory if consecutive gps points have very long time gap.
 * @author Kevin Zheng
 */
public class MaxGapSegmentation implements TrajectorySegmentation {

    public long getMaximumGap() {
        return maximumGap;
    }

    public void setMaximumGap(int maximumGap) {
        this.maximumGap = maximumGap;
    }

    // maximum time gap allowed in milliseconds (default value is 10 minutes)
    private long maximumGap = 600 * 1000;

    private double minimumMove = 0.00001;

    public double getMinimumMove() {
        return minimumMove;
    }

    public void setMinimumMove(double minimumMove) {
        this.minimumMove = minimumMove;
    }

    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {

        List<Trajectory> segmentedT = new ArrayList<>();


        GPXEntry lastPoint;
        int stoppedIndex = 0;
        int startIndex = 0;
        long timeCount = 0;
        boolean firstStoppedFlag = true;

        for(int i = 1; i < trajectory.size(); i++){

            lastPoint = trajectory.get(i - 1);
            if(pointMatch(lastPoint, trajectory.get(i))){
                if(firstStoppedFlag){
                    stoppedIndex = i - 1;
                    firstStoppedFlag = false;
                    timeCount = Math.abs(trajectory.get(i).getTime() - lastPoint.hashCode());
                }else{
                    timeCount = timeCount + Math.abs(trajectory.get(i).getTime() - lastPoint.hashCode());
                }
                if(timeCount > this.maximumGap){
                    if(stoppedIndex - startIndex > 0){
                        segmentedT.add(new Trajectory(trajectory.subList(startIndex, stoppedIndex)));
                    }

                    startIndex = i + 1;
                    stoppedIndex = 0;
                    firstStoppedFlag = true;
                    timeCount = 0;
                }
            }
        }

        if(trajectory.size()  - startIndex > 1){
            segmentedT.add(new Trajectory(trajectory.subList(startIndex, trajectory.size() - 1)));
        }

        return segmentedT;
    }

    private boolean pointMatch(GPXEntry p1, GPXEntry p2){

        if(Math.abs(p1.getLat() - p2.getLat()) < this.minimumMove &&
                Math.abs(p1.getLon() - p2.getLon()) < this.minimumMove){
            return true;
        }

        return false;

    }
}
