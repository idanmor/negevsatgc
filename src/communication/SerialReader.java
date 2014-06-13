package communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialReader implements Runnable {
	InputStream in;
	private boolean isRunning;
    
    public SerialReader ( InputStream in )
    {
        this.in = in;
        isRunning = true;
    }
    
    public void run ()
    {  
        int len = -1;      
        Message msg = new Message();
        while(isRunning) {
	        try
	        {
	        	byte[] buffer = new byte[4096];
	        	len = in.read(buffer, 0, buffer.length);
	        	System.out.println("DEBUG: Read " + len + " bytes");
	        	String str = new String(buffer, 0, len);
	        	
	        	Pattern pattern = Pattern.compile(CommunicationManager.msgStartDelimiter 
	        							+ "(.*?)" + CommunicationManager.msgStopDelimiter);
	        	Matcher matcher = pattern.matcher(str);
	        	if (matcher.find())
	        	{
	        	    System.out.println(matcher.group(1));
	        	}
	        	
	        	CommunicationManager.getInstance().getInputLock().lock();
	        	CommunicationManager.getInstance().getInputDataAvailable().await();
	        }
	        catch ( IOException e )
	        {
	            e.printStackTrace();
	        } 
	        catch (InterruptedException e) {
				e.printStackTrace();
			}
	        finally {
	        	CommunicationManager.getInstance().getInputLock().unlock();
	        }
        }
    }
    
    public void stopThread() {
    	this.isRunning = false;
    }
}
