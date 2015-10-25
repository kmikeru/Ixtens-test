package ru.test.ixtens;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.log4j.Logger;
import ru.test.ixtens.exceptions.NoMethodException;

/** @author mike */
public class Server {
    private Map<String,Object> serviceMap=new HashMap();
    final static Logger LOGGER = Logger.getLogger(Server.class);
    
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
                    Object i = serviceClass.newInstance();
                    LOGGER.debug(String.format("Class loaded:%s",serviceClass));                    
                    this.serviceMap.put(serviceName, i);
                }                
            }else{
                throw new Exception("properties not loaded");
            }
        }catch(Exception e){
            LOGGER.fatal(e);            
        }
    }
    
    public Object call(String serviceName,String methodName,Object[] params) throws NoMethodException{
        LOGGER.debug(String.format("executing: %s.%s(%s)",serviceName,methodName,params));
        Class[] cls=new Class[params.length];
        
        for(int i=0;i<params.length;i++){
            cls[i]=params[i].getClass();
        }
        
        try {
            Object serviceObject =serviceMap.get(serviceName);
            Class c=serviceObject.getClass();
            Method m = c.getDeclaredMethod(methodName, cls);
            Object r = m.invoke(serviceObject, params);
            return r;
        } catch(NoSuchMethodException ex){
            LOGGER.error(ex);
            throw new NoMethodException("No method found:"+methodName);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return null;
    }
    
}
