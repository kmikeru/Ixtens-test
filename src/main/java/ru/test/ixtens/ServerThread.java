package ru.test.ixtens;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import static ru.test.ixtens.CommandProcessor.LOGGER;

/** @author mike */
public class ServerThread implements Runnable{
    final static Logger LOGGER = Logger.getLogger(ServerThread.class);
    Server server;
    Socket socket;
    
    public ServerThread(Server server,Socket socket){
        this.server=server;        
        this.socket=socket;
    }
    
    @Override
    public void run(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());        
            while(true){                
                    Command command=(Command) ois.readObject();
                    LOGGER.debug("received command:"+command.serial);
                                    
                    CommandProcessor cmdp=new CommandProcessor(server,command);
                    CompletableFuture.supplyAsync(()-> cmdp.execute()).thenAccept((x)->{
                        try {
                            oos.writeObject(x);
                        } catch (IOException ex) {
                            LOGGER.error(ex);
                        }
                    } );
                    //Thread commandThread=new Thread(cmdp);
                    //commandThread.start();
                    /*long t0=System.currentTimeMillis();                    
                    long t1=System.currentTimeMillis();                    
                    LOGGER.debug(String.format("elapsed:%d ms",(t1-t0)));*/
                
                    //oos.writeObject(res);
                    //oos.flush();
            }
                
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        
    }
}
