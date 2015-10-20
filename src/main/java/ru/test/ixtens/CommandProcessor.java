package ru.test.ixtens;
import org.apache.log4j.Logger;

/** @author mike */
public class CommandProcessor {  //implements Runnable{
    final static Logger LOGGER = Logger.getLogger(CommandProcessor.class);
    private final Command command;
    private final Server server;
    public CommandResult result;
    
    public CommandProcessor(Server server,Command command){
        this.server=server;
        this.command=command;        
    }
    //@Override
    public void run(){
        CommandResult res=new CommandResult();
        res.serial=command.serial;
        res.result=server.call(command.serviceName,command.methodName, command.params);
        this.result=res;        
    }

}
