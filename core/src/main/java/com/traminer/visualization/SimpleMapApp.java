package com.traminer.visualization;

import com.graphhopper.util.GPXEntry;
import com.traminer.base.Trajectory;
import com.traminer.reader.SimpleTrajectoryReader;
import com.traminer.reader.TrajectoryReader;
import com.traminer.util.ChinaGPSConverter;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An example of visualizing a trajectory.
 * @author Kevin Zheng
 * @see http://unfoldingmaps.org/
 */
public class SimpleMapApp extends PApplet {

    UnfoldingMap map;

    Location beijing = new Location(39.9167, 116.3833);

    SimplePointMarker beijingMaker;

    public void setup() {
        size(1000, 700);

        map = new UnfoldingMap(this, new Google.GoogleMapProvider());
        map.zoomAndPanTo(12, beijing);
        map.setTweening(true);
        // Add mouse and keyboard interactions
        MapUtils.createDefaultEventDispatcher(this, map);
        TrajectoryReader tr = new SimpleTrajectoryReader();
        Trajectory trajectory = tr.readTrajectory("data/simpletrajectoryfile");
        List<Location> locations = trajectory.stream()
                .map(ChinaGPSConverter::getEncryPoint)
                .collect(Collectors.toList());
        SimpleLinesMarker simpleLinesMarker = new SimpleLinesMarker(locations);
        simpleLinesMarker.setColor(color(0,230,0));
        simpleLinesMarker.setStrokeWeight(3);
        map.addMarker(simpleLinesMarker);
    }


    public void draw() {
        map.draw();

    }



    public static void main(String[] args) {
        PApplet.main(new String[] {SimpleMapApp.class.getName()});
    }
}
