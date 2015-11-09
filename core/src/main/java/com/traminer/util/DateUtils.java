package com.traminer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by uqkzheng on 9/11/2015.
 */
public class DateUtils {
    // XML formater: this only for converting date into XML string
    private static final SimpleDateFormat xmlFormater =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    // define all known date formats
    private static final SimpleDateFormat[] allFormats = new SimpleDateFormat[] {
            new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a"),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"), // XSD
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ssZ"), // Oracle
            new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy"),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"), // rfc822
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"), // XSD
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS"),
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"), // Oracle
            new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
            new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z"),
            new SimpleDateFormat("yyyy.MM.dd GGG hh:mm aaa"),
            new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS"),
            new SimpleDateFormat("M/d/yyyy hh:mm:ss"),
            new SimpleDateFormat("M/d/yyyy hh:mm a"),
            new SimpleDateFormat("M/d/yy hh:mm:ss"),
            new SimpleDateFormat("M/d/yy hh:mm a"),
            new SimpleDateFormat("M/d/yy HH:mm"),
            new SimpleDateFormat("M/d/yyyy HH:mm"),
            new SimpleDateFormat("M-d-yy HH:mm"),
            new SimpleDateFormat("M-d-yyyy HH:mm"),
            new SimpleDateFormat("M/d/yy"),
            new SimpleDateFormat("M/d/yyyy"),
            new SimpleDateFormat("M-d-yy"),
            new SimpleDateFormat("M-d-yyyy"),
            new SimpleDateFormat("MMMM d, yyyyy"),
            new SimpleDateFormat("MMM d, yyyyy")
    };

    private DateFormat dateFormat;

    //-----------------------------------------------------------------------
    public Date parseDate(final String date) throws ParseException {
        if (isEmptyString(date))
            throw new ParseException("Error: date is null or empty!", 0);

        // iterate over the array and parse
        Date myDate = null;
        if (date.endsWith("Z")) {
            myDate = parseXMLDate(date);
        }
        if (myDate == null) {
            for (DateFormat df : allFormats) {
                try {
                    myDate = df.parse(date);
                    return myDate;
                }
                catch (Exception e) {
                    // do nothing because we want to try the next
                    // format if current one fails
                }
            }
            if (myDate == null) {
                myDate = parseXMLDate(date);
            }
            // nothing returned so couldn't parse
            if (myDate == null) throw new ParseException("Error: unable to parse date: " + date, 0);
        }
        return myDate;
    }

    //Use the JAXB data type conversion, since it already know the xml valid date format
    private java.util.Date parseXMLDate(final String date) {
        Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(date);
        return cal.getTime();
    }

    //convert to the string representation of the datetime format
    //NOTES: this should be the same as formatDate() but keep
    // and preferred to use this one instead.  Just in case the JAXB
    // document DatetypeConverter change in the future.
    public String formatXMLDate(final java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return javax.xml.bind.DatatypeConverter.printDateTime(cal);
    }

    // should yield the same results as formatXMLDate() method above.
    public String formatDate(final java.util.Date date) {
        return xmlFormater.format(date);
    }

    public boolean isEmptyString(String str)
    {
        if (str == null) return true;
        return "".equals(str.trim());
    }
}
