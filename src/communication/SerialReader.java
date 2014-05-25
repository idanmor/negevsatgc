package communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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
        //final InputStreamReader isr = new InputStreamReader(in);
        //final BufferedInputStream bis = new BufferedInputStream(in);
        final StringBuilder out = new StringBuilder();
        while(true) {
	        try
	        {
	        	len = in.read(buffer, 0, buffer.length);
	        	System.out.println("Read " + len + " bytes");
	        	String str = new String(buffer, 0, len);
	        	System.out.println(str);
	        	CommunicationManager.getInstance().getInputLock().lock();
	        	CommunicationManager.getInstance().getDataAvailable().await();
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
