package com.traminer;

import com.graphhopper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uqkzheng on 28/10/2015.
 */
public class test1
{
    public static void main(String[] args)
    {
        GraphHopper g = new GraphHopper();
        Logger logger = LoggerFactory.getLogger(test1.class);
        logger.info("Hello World");
        System.out.println(System.currentTimeMillis());
    }
}
