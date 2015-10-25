package ru.test.ixtens;

import java.io.EOFException;
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
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    public ServerThread(Server server,Socket socket){
        
        LOGGER.debug("ServerThread constructed");
        this.server=server;
        this.socket=socket;
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            LOGGER.debug(ex);
        }
    }
    
    @Override
    public void run(){
        LOGGER.debug("ServerThread run");
        try{
            while(true){                
                Command command=(Command) ois.readObject();
                LOGGER.debug("received command:"+command.toString());                                    
                CommandProcessor cmdp=new CommandProcessor(this,command);                    
                Thread commandThread=new Thread(cmdp);
                SocketServer.executor.execute(cmdp);
            }

        } catch (EOFException ex) {
            LOGGER.error("Connection closed");
        }catch (Exception ex) {
            LOGGER.error(ex);
        }
        
    }
    
    
    synchronized public void write(CommandResult res){
        try {            
            oos.writeObject(res);
            oos.flush();
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }
}
