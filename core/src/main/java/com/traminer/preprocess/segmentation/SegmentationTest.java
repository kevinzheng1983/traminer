package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;
import com.traminer.reader.SimpleTrajectoryReader;
import com.traminer.reader.TrajectoryReader;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kevin on 10/11/15.
 */
public class SegmentationTest {

    public static void main(String[] args) {

        //DailySegmentation dailySegmentation = new DailySegmentation();
        MaxGapSegmentation maxGapSegmentation = new MaxGapSegmentation();
        MaxStaySegmentation maxStaySegmentation = new MaxStaySegmentation();

        TrajectoryReader tr = new SimpleTrajectoryReader();

        Trajectory trajectory = tr.readTrajectory("/Users/haozhouwang/MyUQ/expdata/testData/281");
        List<Trajectory> results = Stream.of(trajectory)
                .flatMap(t -> maxGapSegmentation.doSegmentation(t).stream())
                .flatMap(t -> maxStaySegmentation.doSegmentation(t).stream())
                .filter(t -> t.size() > 5)
                .collect(Collectors.toList());

        for(Trajectory t : results){
           // System.out.println(t.toString());
           // System.out.println("-------------------------");
        }

        //results.forEach(System.out::println);

    }
}
