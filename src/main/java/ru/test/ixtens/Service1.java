package ru.test.ixtens;

import java.util.Date;
import org.apache.log4j.Logger;

/** @author mike */
public class Service1 {
    final static Logger LOGGER = Logger.getLogger(Service1.class);
    
    public Service1(){
        LOGGER.debug("Constructor called");
    }
    
    public void sleep(Long millis) throws InterruptedException{
        LOGGER.debug(String.format("sleep(%d) called",millis));
        Thread.sleep(millis.longValue());
        LOGGER.debug(String.format("sleep(%d) done",millis));
    }
    public Date getCurrentDate(){
        return new Date();
    }
    
    public void sleep() throws InterruptedException{
        LOGGER.debug("sleep called");
        Thread.sleep(1000L);
        LOGGER.debug("sleep done");
    }
}
