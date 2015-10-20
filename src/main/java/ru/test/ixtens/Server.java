package ru.test.ixtens;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.log4j.Logger;

/** @author mike */
public class Server {
    private Map<String,Object> serviceMap=new HashMap();
    final static Logger LOGGER = Logger.getLogger(SocketServer.class);
    
    public Server (){
        Properties props=new Properties();
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("server.properties");
            if(inputStream!=null){
                props.load(inputStream);
                for(String serviceName : props.stringPropertyNames()){
                    String serviceClassName = props.getProperty(serviceName);
                    LOGGER.debug(String.format("Read line from properties: %s -> %s",serviceName, serviceClassName));
                    Class serviceClass=getClass().getClassLoader().loadClass(serviceClassName);
                    LOGGER.debug(String.format("Class loaded:%s",serviceClass));                    
                    this.serviceMap.put(serviceName, serviceClass);
                }                
            }else{
                throw new Exception("properties not loaded");
            }
        }catch(Exception e){
            LOGGER.fatal(e);            
        }
    }
    
    public Object call(String serviceName,String methodName,Object[] params){
                        
        Class[] cls=new Class[params.length];
        
        for(int i=0;i<params.length;i++){
            cls[i]=params[i].getClass();
        }
        
        try {
            Class c = (Class)serviceMap.get(serviceName);
            Method m = c.getDeclaredMethod(methodName, cls);
            Object i = c.newInstance();
            Object r = m.invoke(i, params);
            return r;
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return null;
    }
    
}
