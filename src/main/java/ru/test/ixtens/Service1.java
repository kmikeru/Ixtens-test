package ru.test.ixtens;

import java.util.Date;

/** @author mike */
public class Service1 {
    public void sleep(Long millis) throws InterruptedException{
        System.out.println("sleep called");
        Thread.sleep(millis.longValue());
        System.out.println("sleep done");
    }
    public Date getCurrentDate(){
        return new Date();
    }
    
    public void sleep() throws InterruptedException{
        System.out.println("sleep called");
        Thread.sleep(1000L);
        System.out.println("sleep done");
    }
}
