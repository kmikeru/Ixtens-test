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
                ServerThread serverThread=new ServerThread(server,s);
                Thread thr=new Thread(serverThread);
                thr.start();
                //cmdp.run();
            }            
        }catch (Exception ex) {
           LOGGER.error(ex);
        }
    }
}
