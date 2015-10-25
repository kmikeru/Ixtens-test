package ru.test.ixtens.client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
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
    Map<Integer,CompletableFuture<CommandResult>> callState=new ConcurrentHashMap<>();
    Lock writeLock=new ReentrantLock();
    
    public Client(String host, int port){
        this.host=host;
        this.port=port;
        try{
            socket=new Socket(host,port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            Thread dispatcherThread=new Thread(new ClientDispatcher());
            dispatcherThread.setDaemon(true);
            dispatcherThread.start();
            
        }catch (IOException ex){
            LOGGER.error(ex);
        }
    }
    
    public Object remoteCall(String serviceName,String methodName,Object[] params){
        Command cmd=new Command(++serial,serviceName,methodName,params);
        LOGGER.debug(cmd.toString());
        CommandResult res;
        try{
            callState.put(cmd.serial, new CompletableFuture<>());
            writeLock.lock();
            oos.writeObject(cmd);
            writeLock.unlock();
            Future<CommandResult> cf=callState.get(cmd.serial);
            res=cf.get();
            if(res.exception!=null){
                throw res.exception;
            }else{
                return res.result;
            }
        }catch(Exception ex){
            LOGGER.error(ex);
        }
        return null;
    }
    
    
    
    private class ClientDispatcher implements Runnable{        
        @Override
        public void run(){
            LOGGER.debug("dispatcher thread started");
            try{
                while(true){
                    CommandResult res=(CommandResult)ois.readObject();
                    LOGGER.debug("got response:"+res);
                    CompletableFuture<CommandResult> cf=callState.get(res.serial);
                    cf.complete(res);
                    callState.put(res.serial, cf);
                }
            } catch (Exception ex){
                LOGGER.error(ex);
            }
        }
    }
    
    
}
