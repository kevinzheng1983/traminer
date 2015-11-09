package com.traminer.visualization;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.List;

/**
 * Created by kevin on 7/11/15.
 */
public class SimpleMapApp extends PApplet {

    UnfoldingMap map;

    Location beijing = new Location(39.9167, 116.3833);

    public void setup() {
        size(1000, 700);

        map = new UnfoldingMap(this, new Google.GoogleMapProvider());
        map.zoomAndPanTo(12, beijing);
        map.setTweening(true);
        // Add mouse and keyboard interactions
        MapUtils.createDefaultEventDispatcher(this, map);

    }


    public void draw() {
        map.draw();
    }



    public static void main(String[] args) {
        PApplet.main(new String[] {SimpleMapApp.class.getName()});
    }
}
