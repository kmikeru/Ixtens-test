package ru.test.ixtens.client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.apache.log4j.Logger;
import ru.test.ixtens.Command;
import ru.test.ixtens.CommandResult;

/** @author mike */
public class Client {
    final static Logger LOGGER = Logger.getLogger(Client.class);
    String host;
    int port;
    int serial=0;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Map<Integer,Future<CommandResult>> callState=new ConcurrentHashMap<>();
    
    public Client(String host, int port){
        this.host=host;
        this.port=port;
        try{
            socket=new Socket(host,port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException ex){
            LOGGER.error(ex);
        }
    }
    
    public Object remoteCall(String serviceName,String methodName,Object[] params){
        Command cmd=new Command(++serial,serviceName,methodName,params);
        LOGGER.debug(cmd.toString());
        CommandResult res;
        try{
            oos.writeObject(cmd);
            res=(CommandResult)ois.readObject();
            return res.result;
        }catch(Exception ex){
            LOGGER.error(ex);
        }
        return null;
    }
    
    public static void main(String[] args){
        Client cl=new Client("localhost",2323);
        cl.remoteCall("Service1", "sleep", new Object[]{3000L});       
        
    }
}
