package ru.test.ixtens;

import java.io.Serializable;
import java.util.Arrays;

/** @author mike */
public class Command implements Serializable{
    Integer serial;
    String serviceName;
    String methodName;
    Object[] params;
    
    public Command(Integer serial,String serviceName,String methodName,Object[] params){
        this.serial=serial;
        this.serviceName=serviceName;
        this.methodName=methodName;
        this.params=params;
    }
    
    @Override
    public String toString(){
        return String.format("Command serial=%d serviceName=%s methodName=%s params=%s",serial,serviceName,methodName,Arrays.toString(params));
    }
}
