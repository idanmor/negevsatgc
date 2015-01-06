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
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data.DataManager;
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

	private BlockingQueue<MessageInterface> outputQueue;
	//public BlockingQueue<MessageInterface> outputQ;			// binary
	private BlockingQueue<MessageInterface> messageAcceptorQueue;

	private SerialReader serialReaderThread;
	private SerialWriter serialWriterThread;
	private MessageParser messageParserThread;

	private boolean isSimulator;

	private CommunicationManager() {
		this.inputLock = new ReentrantLock();
		this.inputDataAvailable = this.inputLock.newCondition();
		this.outputQueue = new LinkedBlockingQueue<MessageInterface>();
		//	outputQ = new LinkedBlockingQueue<MessageInterface>();
		this.messageAcceptorQueue = new LinkedBlockingQueue<MessageInterface>();
		this.isSimulator = false;
	}

	public static CommunicationManager getInstance() {
		if (instance == null) 
			instance = new CommunicationManager();
		return instance;
	}

	public void connectSimulator (String portName) throws NoSuchPortException, 
	PortInUseException, 
	UnsupportedCommOperationException, 
	IOException, TooManyListenersException {
		this.isSimulator = true;
		connect(portName);
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
		Timestamp creational = new Timestamp(now.getTime());
		XmlMessage message = new XmlMessage();
		Vector<Byte> ByteColl = new Vector<Byte>();
		
		String msg = 
				"<?xml version=\"1.0\"?>" +
						"<packet>" +
						"<upstreamPacket time=\""+ MessageParser.toRTEMSTimestamp(creational) +"\">";
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
			Date nw= new java.util.Date();
			Timestamp sentTime=new Timestamp(nw.getTime());
			DataManager.getInstance().setMissionSentTS(mission, sentTime);


			

			String CreatTime = MessageParser.toRTEMSTimestamp(creational);
			long CreatT =  Long.valueOf(CreatTime).longValue();

			
			long ExecT; 
			ExecT = Long.valueOf(exeTimeString).longValue();

			//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
			byte[] AllOpCode = ByteBuffer.allocate(4).putInt(mission.getCommand().getValue()).array();
			byte opcode = AllOpCode[3];
			byte[] Priority = ByteBuffer.allocate(4).putInt(mission.getPriority()).array();
			//System.out.println(opcode + "   " + Priority.toString());
			byte[] CreatTbytes = ByteBuffer.allocate(8).putLong(CreatT).array();
			byte[] ExecTbytes = ByteBuffer.allocate(8).putLong(ExecT).array();
			
			 // insert byte to vector
			for (int i =0 ; i < CreatTbytes.length; i++ )
				ByteColl.addElement(CreatTbytes[i]);
			ByteColl.addElement(opcode);
			for (int i =0 ; i < Priority.length; i++ )
				ByteColl.addElement(Priority[i]);			
			for (int i =0 ; i < ExecTbytes.length; i++ )
				ByteColl.addElement(ExecTbytes[i]);
			
		}

		msg = msg.concat("</upstreamPacket>" + 
				"</packet>");

		System.out.println("DEBUG: Sending message:\n" + msg);
		message.setTosend(ByteColl);
		message.SetMessageText(msg);
		this.sendMessage(message);
		//this.sendMessage(new Message(msg));









	}

	//public Byte CheckNReplace(byte b){
	// Byte ans;
	// if 
	//}

	public void sendMessage(XmlMessage msg) {
		try {
			this.outputQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendLocalMessage(XmlMessage msg) {
		try {
			this.messageAcceptorQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {

	}

	public BlockingQueue<MessageInterface> getOutputQueue() {
		return this.outputQueue;
	}

	public BlockingQueue<MessageInterface> getMessageAcceptorQueue() {
		return this.messageAcceptorQueue;
	}

	public Lock getInputLock() {
		return inputLock;
	}

	public Condition getInputDataAvailable() {
		return inputDataAvailable;
	}

	public boolean isSimulator() {
		return isSimulator;
	}

}
