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

    //if the distance of two points less than minimumMove, the last point will be considered as a stopped point
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
        //the index for first stopped point
        int stoppedIndex = 0;
        //the index for a new trajectory segment
        int startIndex = 0;
        long timeCount = 0;
        //a flag to indicate whether stopped point is detected or not
        boolean firstStoppedFlag = true;

        for(int i = 1; i < trajectory.size(); i++){

            lastPoint = trajectory.get(i - 1);
            if(pointMatch(lastPoint, trajectory.get(i))){
                if(firstStoppedFlag){
                    //record the first stopped point index
                    stoppedIndex = i;
                    firstStoppedFlag = false;
                    timeCount = Math.abs(trajectory.get(i).getTime() - lastPoint.getTime());
                }else{
                    timeCount = timeCount + Math.abs(trajectory.get(i).getTime() - lastPoint.getTime());
                }
                if(timeCount > this.maximumStayTime){
                    //reach the maximum allowed stay time, split
                    if(stoppedIndex - startIndex > 0){
                        //split trajectory and remove all of stopped point
                        segmentedT.add(new Trajectory(trajectory.subList(startIndex, stoppedIndex - 1)));
                    }

                    //reset
                    startIndex = i + 1;
                    stoppedIndex = 0;
                    firstStoppedFlag = true;
                    timeCount = 0;
                }
            }
        }

        // add last segment
        if(trajectory.size()  - startIndex > 1){
            segmentedT.add(new Trajectory(trajectory.subList(startIndex, trajectory.size() - 1)));
        }

        return segmentedT;

    }

    /**
     * A function to dectect the stopped point
     * @param p1 first GPS point
     * @param p2 second GPS point
     * @return true if two points are close enough
     */
    private boolean pointMatch(GPXEntry p1, GPXEntry p2){

        if(Math.abs(p1.getLat() - p2.getLat()) < this.minimumMove &&
                Math.abs(p1.getLon() - p2.getLon()) < this.minimumMove){
            return true;
        }

        return false;

    }
}
