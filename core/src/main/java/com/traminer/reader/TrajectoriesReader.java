package com.traminer.reader;

import com.traminer.base.Trajectory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** A class is used to read multiple files in a directory, assuming there is no sub-directory in the given path
 * Created by Haozhou on 2015/11/12.
 */
public class TrajectoriesReader extends SimpleTrajectoryReader {

    public List<Trajectory> readTrajetcoies(String path){

        List<String> fileList = this.fileNames(path);

        List<Trajectory> trajectories = new ArrayList<>();

        if(fileList.isEmpty()){
            return trajectories;
        }

        //add trajectory ID
        for(String file : fileList){
           Trajectory temp = this.readTrajectory(path + "//" + file);
            temp.setId(file);
            trajectories.add(temp);
        }
        return trajectories;
    }

    /**
     *
     * @param dir the directory path
     * @return the list of files
     */
    private List<String> fileNames(String dir){

        List<String> fileList = new ArrayList<>();
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dir))){
            for(Path path : directoryStream){
                fileList.add(path.getFileName().toString());
            }

        }catch (IOException ex){
            System.err.println("File read error, please check");
            System.err.println(ex.getMessage());
        }

        return fileList;

    }

}
