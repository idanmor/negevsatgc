package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class SerialWriter implements Runnable {
	OutputStream out;
	private boolean isRunning;
	private final BlockingQueue<Message> queue;
    
    public SerialWriter (OutputStream out,BlockingQueue<Message> Q)
    {
        this.out = out;
        this.isRunning = true;
        this.queue = Q;
    }

	public void run ()
    {
		 
		try {
				while ( this.isRunning ){
				// this.w CommunicationManager.getInstance().getOutputQueue().wait();
		//		while (!this.queue.isEmpty()){
					Message msg = this.queue.take();
				//MessageInterface msg = CommunicationManager.getInstance().getOutputQueue().take();
				//System.out.println("DEBUG: Sending message:\n" + msg.toString());
				//this.out.write(CommunicationManager.startDelimiter);
				this.out.write(msg.getBytes(), 0, msg.getBytes().length);
				//this.out.write(CommunicationManager.stopDelimiter);
				this.out.write(10);
				}
				
	     //   }
		}
	        catch ( IOException  e )
	        {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}       
    
	
	public void stopThread() {
    	this.isRunning = false;
    }
}
