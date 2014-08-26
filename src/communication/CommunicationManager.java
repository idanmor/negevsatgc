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
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data.Mission;


public class CommunicationManager {
	public static final Character startDelimiter = 2;
	public static final Character stopDelimiter = 4;
	public static final CharSequence msgStartDelimiter = startDelimiter.toString();
	public static final CharSequence msgStopDelimiter = stopDelimiter.toString();

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
	                serialPort.setSerialPortParams(19200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
	                //serialPort.setFlowControlMode(serialPort.FLOWCONTROL_RTSCTS_IN);
	                
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
	
	public void sendMission(Mission mission) {
		Collection<Mission> missions = new LinkedList<Mission>();
		missions.add(mission);
		sendMissions(missions);
	}
	
	public void sendMissions(Collection<Mission> missions) {
		Date now = new Date();
		String msg = 
				"<?xml version=\"1.0\"?>" +
                "<packet>" +
                "<upstreamPacket time=\""+ MessageParser.toRTEMSTimestamp(new Timestamp(now.getTime())) +"\">";
		for (Mission mission : missions) {
			String exeTimeString;
			if (mission.getExecutionTime() == null) {
				exeTimeString = "0";
			}
			else {
				exeTimeString = MessageParser.toRTEMSTimestamp(mission.getExecutionTime());
			}
			msg = msg.concat("<mission time=\"" + exeTimeString + 
					"\" opcode=\"" + mission.getCommand().getValue() + "\" priority=\"" +
					mission.getPriority() + "\"/>");
		}
		
		msg = msg.concat("</upstreamPacket>" + 
							"</packet>");
		
		System.out.println("DEBUG: Sending message:\n" + msg);
		this.sendMessage(new Message(msg));
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
