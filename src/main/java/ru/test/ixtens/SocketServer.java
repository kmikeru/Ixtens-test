package ru.test.ixtens;

import java.net.*;
import java.io.*;
import org.apache.log4j.Logger;

/** @author mike */
public class SocketServer {
    final static Logger LOGGER = Logger.getLogger(SocketServer.class);
    
    public static void main(String[] args){
        Server server=new Server();
        try {
            ServerSocket sock = new ServerSocket(2323);
            while(true){
                Socket s=sock.accept();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Command cmd;
                try {
                    cmd=(Command) ois.readObject();
                    LOGGER.debug("received command:"+cmd.serial);
                    CommandProcessor cmdp=new CommandProcessor(server,cmd);
                    cmdp.run();
                    CommandResult res=cmdp.result;                    
                    oos.writeObject(res);
                    oos.flush();
                    s.close();
                } catch (Exception ex) {
                    LOGGER.error(ex);
                }                    
            }
        }catch (IOException ex) {
           LOGGER.error(ex);
        }
    }
}
