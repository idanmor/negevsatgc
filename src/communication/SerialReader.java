package communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialReader implements Runnable {
	InputStream in;
	private boolean isRunning;
    
    public SerialReader ( InputStream in ) {
        this.in = in;
        isRunning = true;
    }
    
    public void run () {     // TO DO : CHANGE SWITCH-CASE LOOP FOR ALL TYPE OF MessageInterface - now working only with XMLMessage
        int len = -1;      
        XmlMessage msg = new XmlMessage();
        StringBuffer remainder = new StringBuffer();
        while(isRunning) {
	        try
	        {
	        	byte[] buffer = new byte[1024];
	        	len = in.read(buffer, 0, buffer.length);
	        	if (len > 0) {
		        	System.out.println("DEBUG: Read " + len + " bytes");
		        	StringBuffer str = new StringBuffer(new String(buffer, 0, len));
		        	str = remainder.append(str);
		        	
		        	Pattern pattern = Pattern.compile(CommunicationManager.msgStartDelimiter 
		        							+ "(.*?)" + CommunicationManager.msgStopDelimiter, Pattern.DOTALL);
		        	Matcher matcher = pattern.matcher(str);
		        	while (matcher.find())
		        	{
		        		String foundMsg = matcher.group(1);
		        		StringBuffer trash = new StringBuffer();
		        		matcher.appendReplacement(trash, foundMsg);
		        	    System.out.println(foundMsg);
		        	    CommunicationManager.getInstance().getMessageAcceptorQueue().put(new XmlMessage(foundMsg));
		        	}
		        	remainder = new StringBuffer();
		        	matcher.appendTail(remainder);
	        	}	
		        CommunicationManager.getInstance().getInputLock().lock();
		        CommunicationManager.getInstance().getInputDataAvailable().await();
	        }
	        catch ( IOException e ) {
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
