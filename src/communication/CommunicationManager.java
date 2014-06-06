package communication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CommunicationManager {
	public static final CharSequence msgDelimiter = "###";

	private static CommunicationManager instance = null;
	private SerialPort serialPort;
	private InputStream in;
	private OutputStream out;
	private Lock inputLock;
	private Condition dataAvailable;

	private CommunicationManager() {
		this.inputLock = new ReentrantLock();
		this.dataAvailable = this.inputLock.newCondition();
	}
	
	public static CommunicationManager getInstance() {
		if (instance == null) 
			instance = new CommunicationManager();
		return instance;
	}
	
	public void connect (String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() ) {
            System.out.println("Error: Port is currently in use");
        }
        else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort ){
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                serialPort.addEventListener(new SerialListener(in));
                serialPort.notifyOnDataAvailable(true);
                
                (new Thread(new SerialReader(in))).start();
                //(new Thread(new SerialWriter(out))).start();
            }
            else {
                System.out.println("Error: Only serial ports are handled by this application");
            }
        }     
    }
	
	public void sendData(byte[] dataToSend) throws Exception {
		
	}
	
	public Lock getInputLock() {
		return inputLock;
	}
	
	public Condition getDataAvailable() {
		return dataAvailable;
	}

}
