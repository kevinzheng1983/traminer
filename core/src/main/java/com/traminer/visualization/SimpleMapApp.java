package com.traminer.visualization;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;

import java.util.List;

/**
 * Created by kevin on 7/11/15.
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

        beijingMaker = new SimplePointMarker(beijing);

    }


    public void draw() {
        map.draw();

        ScreenPosition posBeijing = beijingMaker.getScreenPosition(map);
        strokeWeight(12);
        stroke(200, 0, 0, 200);
        strokeCap(SQUARE);
        noFill();
        float s = 44;
        arc(posBeijing.x, posBeijing.y, s, s, -PI * 0.9f, -PI * 0.1f);
        arc(posBeijing.x, posBeijing.y, s, s, PI * 0.1f, PI * 0.9f);
        fill(0);
        text("BEIJING", posBeijing.x - textWidth("BEIJING") / 2, posBeijing.y + 4);
    }



    public static void main(String[] args) {
        PApplet.main(new String[] {SimpleMapApp.class.getName()});
    }
}
