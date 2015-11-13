package com.traminer.preprocess.segmentation;

import com.graphhopper.util.GPXEntry;
import com.traminer.base.Trajectory;
import com.traminer.reader.SimpleTrajectoryReader;
import com.traminer.reader.TrajectoriesReader;
import com.traminer.reader.TrajectoryReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        TrajectoriesReader tr = new TrajectoriesReader();

        List<Trajectory> trajectory = tr.readTrajetcoies("C:\\Users\\Haozhou\\Documents\\MyUQ\\expData");


        List<Trajectory> results = Stream.of(trajectory.get(0))
                .flatMap(t -> maxGapSegmentation.doSegmentation(t).stream())
                .flatMap(t -> maxStaySegmentation.doSegmentation(t).stream())
                .filter(t -> t.size() > 5)
                .collect(Collectors.toList());
/*
        for(Trajectory t : results){
            System.out.println(t.toString());
            System.out.println("-------------------------");
        }
*/
        writeToFiles(results, "C:\\Users\\Haozhou\\Documents\\MyUQ\\expData");
        //results.forEach(System.out::println);

    }

    /**
     *
     * @param r trajectory set
     * @param outputPath the output file path
     */

    public static void writeToFiles(List<Trajectory> r, String outputPath){

        SimpleDateFormat foldFormater = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;
        FileWriter outFile;


        for(Trajectory t : r){

            Date date = new Date(t.get(0).getTime());
            //select the file path based on the start time of trajectory
            String folderName = foldFormater.format(date);
            String folderpath = outputPath + "/" + folderName;
            File dir = new File(folderpath);

            if(!dir.exists()){
                dir.mkdir();
            }
            try{
                //System.out.println("ID is " + t.getId() );
                if(t.getId() != null){
                    outFile = new FileWriter(folderpath + "/" + t.getId() + "_" + +count);
                }else{
                    outFile = new FileWriter(folderpath + "/" + count);
                }

                BufferedWriter bw = new BufferedWriter(outFile);

                for(GPXEntry p : t){
                    bw.write(p.getLat() + "," + p.getLon() + "," + p.getTime());
                    bw.newLine();
                }

                bw.flush();
                bw.close();

            }catch(Exception e){
                e.printStackTrace();
            }

            count++;

        }



    }
}
