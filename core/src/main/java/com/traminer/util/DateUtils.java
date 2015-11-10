package com.traminer.util;

import org.apache.xmlgraphics.util.DateFormatUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A utility class to help parsing date string in various formats into date.
 * @author Kevin Zheng
 */
public class DateUtils {
    // XML formater: this only for converting date into XML string
    private static final SimpleDateFormat xmlFormater =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    // define all known date formats
    private static final SimpleDateFormat[] allFormats = new SimpleDateFormat[] {
            // start with year
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyyMMddHHmmss"),
            // start with month
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ssX"),
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
            // start with day
            new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
    };

    private DateFormat dateFormat;

    public DateUtils() {}

    public DateUtils(DateFormat df) {
        this.dateFormat = df;
    }
    //-----------------------------------------------------------------------
    public long getTime(final String date) throws ParseException {
        return parseDate(date).getTime();
    }
    public Date parseDate(final String date) throws ParseException {
        if (isEmptyString(date))
            throw new ParseException("Error: date is null or empty!", 0);

        // iterate over the array and parse
        Date myDate = null;
        // test if a date formatter already existed to avoid enumeration of all formats
        if (this.dateFormat != null) {
            try {
                myDate = this.dateFormat.parse(date);
                return myDate;
            }
            catch (Exception e) {

            }
        }
        if (date.endsWith("Z")) {
            myDate = parseXMLDate(date);
        }
        if (myDate == null) {
            for (DateFormat df : allFormats) {
                try {
                    myDate = df.parse(date);
                    this.dateFormat = df;
                    return myDate;
                }
                catch (Exception e) {
                    // do nothing because we want to try the next
                    // format if current one fails
                }
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

    public static void main (String[] args) {
        DateUtils dateUtils = new DateUtils();
        String[] dateStr = {
                "20151109103546",
                "2015-11-09T10:35:46",
                "2015-11-09 10:35:46",
                "11/09/2015 10:35:46",
                "9.11.2015 10:35:46"
        };
        try {
            for(String str : dateStr) {
                Date date = dateUtils.parseDate(str);
                System.out.println(date.toString());
                System.out.println(dateUtils.getTime(str));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
