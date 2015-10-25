package ru.test.ixtens;
import org.apache.log4j.Logger;
import ru.test.ixtens.exceptions.NoMethodException;


/** @author mike */
public class CommandProcessor implements Runnable{
    final static Logger LOGGER = Logger.getLogger(CommandProcessor.class);    
    Command command;
    ServerThread srvThread;
    
    
    public CommandProcessor(ServerThread srvThread,Command command){        
        this.srvThread=srvThread;
        this.command=command;
    }
    @Override
    public void run(){
        LOGGER.debug("executing command:"+command.toString());
        CommandResult res=new CommandResult();
        res.serial=command.serial;
        try{
            res.result=srvThread.server.call(command.serviceName,command.methodName, command.params);
            LOGGER.debug("finished execution:"+command.toString());
            srvThread.write(res);
        }catch(NoMethodException ex){
            res.exception=ex;
            srvThread.write(res);
        }
    }
    
}

