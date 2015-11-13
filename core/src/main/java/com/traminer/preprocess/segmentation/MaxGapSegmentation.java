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



    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {

        GPXEntry lastPoint;
        List<Trajectory> segmentedT= new ArrayList<>();
        Trajectory tempT = new Trajectory();

        tempT.setId(trajectory.getId());
        tempT.add(trajectory.get(0));

        for(int i = 1; i < trajectory.size(); i++){
            //get previous point
            lastPoint = trajectory.get(i - 1);
            if(!timeContinues(lastPoint, trajectory.get(i))){
                //Gap detected, split the trajectory
                if(tempT.size() > 0){
                    segmentedT.add(tempT);
                }else{
                    System.err.println("Bug found, size cannot be 0");
                }

                tempT = new Trajectory();
                tempT.setId(trajectory.getId());
                tempT.add(trajectory.get(i));

            }else{
                tempT.add(trajectory.get(i));
            }
        }
        //add last segment
        if(tempT.size() > 0){
            segmentedT.add(tempT);
        }
        return segmentedT;
    }


    /**
     * A function is to detect the time gap
     * @param p1 first GPS point
     * @param p2 second GPS point
     * @return false if the time gap of two points is larger than threshold
     */
    private boolean timeContinues(GPXEntry p1, GPXEntry p2){
        if(Math.abs(p1.getTime() - p2.getTime()) > this.maximumGap){
            return false;
        }

        return true;
    }

}
