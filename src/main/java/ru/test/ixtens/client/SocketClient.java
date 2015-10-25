package ru.test.ixtens.client;

import java.util.Random;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/** @author mike */
public class SocketClient {
    final static Logger LOGGER = Logger.getLogger(SocketClient.class);
    public static void main(String[] args){
        int port=2323;
        String host="localhost";
        if(args.length==2){
            host=args[0];
            port=Integer.parseInt(args[1]);
        }
        LOGGER.info(String.format("Calling %s:%d",host,port));
        Client cl=new Client(host,port);
        cl.remoteCall("Service1", "sleep1", new Object[]{3000L});       
        for(int i=0;i<6;i++){
            new Thread(new TestThread(cl)).start();            
        }
        
    }
    
    private static class TestThread implements Runnable{
        final static Logger LOGGER = Logger.getLogger(TestThread.class);
        Client cl;
        Random r=new Random();
        
        public TestThread(Client cl){
            this.cl=cl;
        }
        
        @Override
        public void run(){
            while(true){
                cl.remoteCall("Service1", "sleep", new Object[]{(long)r.nextInt(5000)});
                LOGGER.debug("finished");
                try {
                    Thread.sleep(1000+r.nextInt(5000));
                } catch (InterruptedException ex) {
                    LOGGER.warn(ex);
                }
            }
        }
    }

}
