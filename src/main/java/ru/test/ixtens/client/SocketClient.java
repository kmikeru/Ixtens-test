package ru.test.ixtens.client;

import org.apache.log4j.Logger;

/** @author mike */
public class SocketClient {
    final static Logger LOGGER = Logger.getLogger(SocketClient.class);
    public static void main(String[] args){
        Client cl=new Client("localhost",2323);
        cl.remoteCall("Service1", "sleep", new Object[]{3000L});       
        for(int i=0;i<6;i++){
            new Thread(new TestThread(cl)).start();            
        }
        
    }
    
    private static class TestThread implements Runnable{
        final static Logger LOGGER = Logger.getLogger(TestThread.class);
        Client cl;
        
        public TestThread(Client cl){
            this.cl=cl;
        }
        
        @Override
        public void run(){
            cl.remoteCall("Service1", "sleep", new Object[]{3000L});
            LOGGER.debug("finished");
        }
    }

}
