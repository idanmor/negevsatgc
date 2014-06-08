package communication;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {
	OutputStream out;
    
    public SerialWriter (OutputStream out)
    {
        this.out = out;
    }

	public void run ()
    {
		while (true) {
			try {
				Message msg = CommunicationManager.getInstance().getOutputQueue().take();
				this.out.write(msg.getBytes(), 0, msg.getBytes().length);
				this.out.write(10);
				//this.out.write(CommunicationManager.msgDelimiter.toString().getBytes(), 0, 
				//		CommunicationManager.msgDelimiter.toString().getBytes().length);
	        }
	        catch ( IOException e )
	        {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}       
    }
}
