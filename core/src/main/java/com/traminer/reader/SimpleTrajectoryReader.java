package com.traminer.reader;

import com.graphhopper.util.GPXEntry;
import com.traminer.base.Trajectory;
import com.traminer.util.DateUtils;

import java.io.*;

/**
 * A simple trajectory reader that can read a trajectory from a file with "lat, lon, time" format.
 * @author Kevin Zheng
 */
public class SimpleTrajectoryReader implements TrajectoryReader {
    @Override
    public Trajectory readTrajectory(String file) {
        Trajectory trajectory = null;
        DateUtils dateUtils = new DateUtils();

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null) {
                // split by either comma or any number of whitespaces
                String[] fields = line.split(",\\s*|\\s+");
                // each data line should at least contain three fields, i.e., lat, lon, time
                if(fields.length < 3) {
                    line = br.readLine();
                    continue;
                }
                double lat = Double.parseDouble(fields[0]);
                double lon = Double.parseDouble(fields[1]);
                // if less than 14 letters, then it's long type; otherwise it's date type
                long time = fields[2].length()<14? Long.parseLong(fields[2]): dateUtils.getTime(fields[2]);
                GPXEntry gpxEntry = new GPXEntry(lat, lon, time);
                if(trajectory == null) {
                    trajectory = new Trajectory();
                }
                trajectory.add(gpxEntry);
                line = br.readLine();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // set the absolute path of trajectory file as the trajectory ID
        if(trajectory != null) {
            File path = new File(file);
            trajectory.setId(path.getAbsolutePath());
        }
        return trajectory;
    }

    @Override
    public String formatDescription() {
        return "latitude,longitude,time";
    }

    public static void main(String[] args) {
        TrajectoryReader tr = new SimpleTrajectoryReader();
        Trajectory trajectory = tr.readTrajectory("data/simpletrajectoryfile");
        System.out.println(trajectory.toString());
    }
}
