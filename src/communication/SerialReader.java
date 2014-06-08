package communication;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements Runnable {
	InputStream in;
    
    public SerialReader ( InputStream in )
    {
        this.in = in;
    }
    
    public void run ()
    {  
        int len = -1;
        byte[] buffer = new byte[1024];
        Message msg = new Message();
        while(true) {
	        try
	        {
	        	len = in.read(buffer, 0, buffer.length);
	        	System.out.println("DEBUG: Read " + len + " bytes");
	        	String str = new String(buffer, 0, len);
	        	if(str.contains(CommunicationManager.msgDelimiter)) {
	        		boolean isFinished = str.endsWith(CommunicationManager.msgDelimiter.toString());
	        		String[] splitted = str.split(CommunicationManager.msgDelimiter.toString());
	        		msg.append(splitted[0]);
	        		CommunicationManager.getInstance().getMessageAcceptorQueue().put(msg);
	        		//System.out.println(msg.toString());
	        		for(int i=1; i<splitted.length-1; i++) {
		        		msg = new Message(splitted[i]);
		        		CommunicationManager.getInstance().getMessageAcceptorQueue().put(msg);
		        		//System.out.println(msg.toString());
	        		}
	        		if(splitted.length!=1) {
	        			msg = new Message(splitted[splitted.length-1]);
	        			if(isFinished) {
	        				CommunicationManager.getInstance().getMessageAcceptorQueue().put(msg);
	        				//System.out.println(msg.toString());
	        				msg = new Message();
	        			}
	        		}
	        	}
	        	else {
	        		//msg.append(str);
	        		msg = new Message(str);
	        		CommunicationManager.getInstance().getMessageAcceptorQueue().put(msg);
	        	}
	        	CommunicationManager.getInstance().getInputLock().lock();
	        	CommunicationManager.getInstance().getInputDataAvailable().await();
	        }
	        catch ( IOException e )
	        {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
	        finally {
	        	CommunicationManager.getInstance().getInputLock().unlock();
	        }
        }
    }
}
