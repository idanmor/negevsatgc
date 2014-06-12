package communication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CommunicationManager {
	public static final CharSequence msgDelimiter = "\n";

	private static CommunicationManager instance = null;
	private SerialPort serialPort;
	private InputStream in;
	private OutputStream out;
	
	private Lock inputLock;
	private Condition inputDataAvailable;
	
	private BlockingQueue<Message> outputQueue;
	private BlockingQueue<Message> messageAcceptorQueue;
	
	private SerialReader serialReaderThread;
	private SerialWriter serialWriterThread;
	private MessageParser messageParserThread;

	private CommunicationManager() {
		this.inputLock = new ReentrantLock();
		this.inputDataAvailable = this.inputLock.newCondition();
		this.outputQueue = new LinkedBlockingQueue<Message>();
		this.messageAcceptorQueue = new LinkedBlockingQueue<Message>();
	}
	
	public static CommunicationManager getInstance() {
		if (instance == null) 
			instance = new CommunicationManager();
		return instance;
	}
	
	public void connect (String portName) throws NoSuchPortException, 
												PortInUseException, 
												UnsupportedCommOperationException, 
												IOException, TooManyListenersException {
		if (portName.equals("LOCAL")) {
			(new Thread(new MessageParser())).start();
		}
		else {
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
	                
	                
	                serialReaderThread = new SerialReader(in);
	                serialWriterThread = new SerialWriter(out);
	                messageParserThread = new MessageParser();
	                
	                (new Thread(serialReaderThread)).start();
	                (new Thread(serialWriterThread)).start();
	                (new Thread(messageParserThread)).start();
	            }
	            else {
	                System.out.println("Error: Only serial ports are handled by this application");
	            }
	        }
		}
    }
	
	public void sendMission() {
		
	}
	
	public void sendMessage(Message msg) {
		try {
			this.outputQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLocalMessage(Message msg) {
		try {
			this.messageAcceptorQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		
	}
	
	public BlockingQueue<Message> getOutputQueue() {
		return this.outputQueue;
	}
	
	public BlockingQueue<Message> getMessageAcceptorQueue() {
		return this.messageAcceptorQueue;
	}
	
	public Lock getInputLock() {
		return inputLock;
	}
	
	public Condition getInputDataAvailable() {
		return inputDataAvailable;
	}

}
