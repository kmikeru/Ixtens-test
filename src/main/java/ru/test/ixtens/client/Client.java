package ru.test.ixtens.client;

import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;
import ru.test.ixtens.Command;
import ru.test.ixtens.CommandResult;

/** @author mike */
public class Client {
    final static Logger LOGGER = Logger.getLogger(Client.class);
    
    public static void main(String[] args){
        Command cmd=new Command(1,"Service1","getCurrentDate", new Object[]{});
        System.out.println(""+cmd.toString());
        try {
            Socket s=new Socket("localhost",2323);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(cmd);
            CommandResult res=(CommandResult)ois.readObject();
            System.out.println("result:"+res.result);
        } catch (IOException ex) {
            LOGGER.error(ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex);
        } 
        
        
    }
}
