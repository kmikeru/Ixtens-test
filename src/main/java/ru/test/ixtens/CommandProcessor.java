package ru.test.ixtens;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import static ru.test.ixtens.SocketServer.LOGGER;

/** @author mike */
public class CommandProcessor implements Runnable{
    final static Logger LOGGER = Logger.getLogger(CommandProcessor.class);
    Server server;
    Command command;
    CommandResult result;
    
    public CommandProcessor(Server server,Command command){
        this.server=server;
        this.command=command;
    }
    @Override
    public void run(){
        CommandResult res=new CommandResult();
        res.serial=command.serial;
        res.result=server.call(command.serviceName,command.methodName, command.params);        
    }
    
    public CommandResult execute(){
        CommandResult res=new CommandResult();
        res.serial=command.serial;
        res.result=server.call(command.serviceName,command.methodName, command.params);        
        return res;
    }
}

