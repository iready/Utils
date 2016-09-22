package org.zyq.core.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by zouyq on 2016/3/20.
 */
public class Lapender implements Appender {

    public void addFilter(Filter newFilter) {

    }


    public Filter getFilter() {
        return null;
    }


    public void clearFilters() {

    }


    public void close() {

    }


    public void doAppend(LoggingEvent event) {
        if (!event.getLevel().toString().equalsIgnoreCase("debug")) {
            System.out.println(event.getMessage());
        }
    }


    public String getName() {
        return "console";
    }


    public void setName(String name) {

    }


    public ErrorHandler getErrorHandler() {
        return null;
    }


    public void setErrorHandler(ErrorHandler errorHandler) {

    }

    public Layout getLayout() {
        return new SimpleLayout();
    }


    public void setLayout(Layout layout) {

    }


    public boolean requiresLayout() {
        return false;
    }

}
