package com.traminer.preprocess.segmentation;

import com.traminer.base.Trajectory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kevin on 10/11/15.
 */
public class SegmentationTest {

    public static void main(String[] args) {

        DailySegmentation dailySegmentation = new DailySegmentation();
        MaxGapSegmentation maxGapSegmentation = new MaxGapSegmentation();
        MaxStaySegmentation maxStaySegmentation = new MaxStaySegmentation();

        Trajectory trajectory = new Trajectory();
        List<Trajectory> results = Stream.of(trajectory)
                .flatMap(t -> dailySegmentation.doSegmentation(t).stream())
                .flatMap(t -> maxGapSegmentation.doSegmentation(t).stream())
                .flatMap(t -> maxStaySegmentation.doSegmentation(t).stream())
                .filter(t -> t.size() > 5)
                .collect(Collectors.toList());

        results.forEach(System.out::println);

    }
}
