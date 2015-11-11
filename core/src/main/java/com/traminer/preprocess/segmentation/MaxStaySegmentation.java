package com.traminer.preprocess.segmentation;

import com.graphhopper.util.GPXEntry;
import com.traminer.base.Trajectory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Segment a trajectory if it stops for longer period than the maximum stay time.
 * @author Kevin Zheng
 */
public class MaxStaySegmentation implements TrajectorySegmentation {

    public long getMaximumStayTime() {
        return maximumStayTime;
    }

    public void setMaximumStayTime(int maximumStayTime) {
        this.maximumStayTime = maximumStayTime;
    }

    // maximum stay period allowed in milliseconds (default value is 3 minutes)
    private long maximumStayTime = 180 * 1000;

    @Override
    public List<Trajectory> doSegmentation(Trajectory trajectory) {

        GPXEntry lastPoint;
        List<Trajectory> segmentedT= new ArrayList<>();
        Trajectory tempT = new Trajectory();

        tempT.add(trajectory.get(0));

        for(int i = 1; i < trajectory.size(); i++){
            lastPoint = trajectory.get(i - 1);
            if(!timeContinues(lastPoint, trajectory.get(i))){
                if(tempT.size() > 0){
                    segmentedT.add(tempT);
                }else{
                    System.err.println("Bug found, size cannot be 0");
                }

                tempT = new Trajectory();
                tempT.add(trajectory.get(i));
            }
        }

        if(tempT.size() > 0){
            segmentedT.add(tempT);
        }
        return segmentedT;
    }

    private boolean timeContinues(GPXEntry p1, GPXEntry p2){
        if(Math.abs(p1.getTime() - p2.getTime()) > this.maximumStayTime){
            return false;
        }

        return true;
    }
}
