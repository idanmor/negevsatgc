package communication;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.w3c.dom.*;

import data.DataManager;
import data.Status;

public class MessageParser implements Runnable {
	private static final String tagType = "type";
	private static final String tagState = "state";
	private static final String tagUpPacket = "upstreamPacket";
	private static final String tagDownPacket = "downstreamPacket";
	private static final String tagEnergySample = "EnergySample";
	private static final String tagTempSample = "TemperatureSample";
	private static final String tagModule = "Module";
	private static final String tagInfo = "Info";
	private static final String tagName = "name";
	private static final String tagStatus = "status";
	
	private static final String tagTime = "time";
	private static final String tagVoltage = "voltage";
	private static final String tagCurrent = "current";
	private static final String tagTemp = "temp";
	
	private static final String tagStatusOn = "ON";
	private static final String tagStatusMalfunction = "MALFUNCTION";
	private static final String tagStatusStandby = "STANDBY";
	private static final String tagStatusNonOperational = "NON_OPERATIONAL";
	
	private static final String tagEnergyPacketItem1 = "Battery1";
	private static final String tagEnergyPacketItem2 = "Battery2";
	private static final String tagEnergyPacketItem3 = "Battery3";
	
	private static final String tagTempPacketItem1 = "Sensor1";
	private static final String tagTempPacketItem2 = "Sensor2";
	private static final String tagTempPacketItem3 = "Sensor3";
	
	private static final String tagModuleTemperature = "Temperature";
	private static final String tagModuleEnergy = "Energy";
	private static final String tagModuleSband = "Sband";
	private static final String tagModulePayload = "Payload";
	private static final String tagModuleSolarPanels = "SolarPanels";
	private static final String tagModuleThermalCtrl = "ThermalControl";
	
	private boolean isRunning;
	
	public MessageParser () {
		this.isRunning = true;
	}
	
	public void run ()
    {
		while (isRunning) {
			try {
				System.out.println("DEBUG: MessageAcceptor waiting for new message");
				Message m = CommunicationManager.getInstance().getMessageAcceptorQueue().take();
				System.out.println("DEBUG: Message Accepted");
				System.out.println(m.toString());
				Document msg;
				/*
				try {
					msg = m.toDocument();
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				try {
					parseMessage(msg);
				} catch (InvalidMessageException e) {
					if (msg.getElementsByTagName(tagUpPacket).getLength() != 0) {
						continue; // Ignore upstream packets sent from airborne control system simulator
					}
					else {
						System.out.println("PARSING ERROR: " + e.getMessage());
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}*/
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}       
    }
	
	public void parseMessage(Document msg) throws InvalidMessageException {
    	NodeList nList = msg.getElementsByTagName(tagDownPacket);
    	if(nList.getLength() == 0) {
    		throw new InvalidMessageException("No downstreamPacket Element!");
    	}
    	Node packet = nList.item(0);
    	NodeList typeNodes = msg.getElementsByTagName(tagType);
    	if(typeNodes.getLength() == 0) {
    		throw new InvalidMessageException("No type Element!");
    	}
    	String type = typeNodes.item(0).getTextContent();
    	switch(type) {
    	case "Static":
    		parseStaticPacket(packet);
    		break;
    	case "Temperature":
    		parseTemperaturePacket(packet);
    		break;
    	case "Energy":
    		parseEnergyPacket(packet);
    		break;
    	default:
    		throw new InvalidMessageException("Wrong packet type!");
    	}
    }
	
	public void parseStaticPacket (Node packet) {
		System.out.println("DEBUG: Static packet parsing");
		Status defaultStatus = Status.UNKNOWN;
		NodeList children = packet.getChildNodes();
		String satState = "";
		Status energyStatus = defaultStatus;
		Timestamp energyStatusTS = null;
		Status temperatureStatus = defaultStatus;
		Timestamp temperatureStatusTS = null;
		Status payloadStatus = defaultStatus;
		Timestamp payloadStatusTS = null;
		Status sbandStatus = defaultStatus;
		Timestamp sbandStatusTS = null;
		Status solarPanelsStatus = defaultStatus;
		Timestamp solarPanelsStatusTS = null;
		Status thermalCtrlStatus = defaultStatus;
		Timestamp thermalCtrlStatusTS = null;
		
		for (int i=0; i < children.getLength(); i++) { //For each packet element
			Node child = children.item(i);
			if (child.getNodeName().equals(tagState)) {
				satState = child.getTextContent();
			} else if (child.getNodeName().equals(tagModule)) {
				Timestamp ts;
				NamedNodeMap attr = child.getAttributes();
				String moduleTimestamp = attr.getNamedItem(tagTime).getNodeValue();
				
				NodeList infos = child.getChildNodes();
				for (int j=0; j < infos.getLength(); j++) {
					Node info = infos.item(j);
					NamedNodeMap infoattrs = info.getAttributes();
					String moduleName = infoattrs.getNamedItem(tagName).getNodeValue();
					Status moduleStatus = stringToStatus(infoattrs.getNamedItem(tagStatus).getNodeValue());
					switch (moduleName) {
					case tagModuleTemperature:
						temperatureStatus = moduleStatus;
						temperatureStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					case tagModuleEnergy:
						energyStatus = moduleStatus;
						energyStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					case tagModulePayload:
						payloadStatus = moduleStatus;
						payloadStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					case tagModuleSband:
						sbandStatus = moduleStatus;
						sbandStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					case tagModuleSolarPanels:
						solarPanelsStatus = moduleStatus;
						solarPanelsStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					case tagModuleThermalCtrl:
						thermalCtrlStatus = moduleStatus;
						thermalCtrlStatusTS = new Timestamp(parseRTEMSTimestamp(moduleTimestamp));
						break;
					default:
						break;	
					}
				}
			}
		}
		System.out.println("===========");
		System.out.println("Inserting Static Update. Satellite State: " + satState + "\n" +
				"Energy Status: " + energyStatus.toString() + " at " + energyStatusTS + "\n" +
				"Temperature Status: " + temperatureStatus.toString() + " at " + temperatureStatusTS + "\n" +
				"SBand Status: " + sbandStatus.toString() + " at " + sbandStatusTS + "\n" +
				"Payload Status: " + payloadStatus.toString() + " at " + payloadStatusTS + "\n" +
				"Solar Panels Status: " + solarPanelsStatus.toString() + " at " + solarPanelsStatusTS + "\n" +
				"Thermal Control Status: " + thermalCtrlStatus.toString() + " at " + thermalCtrlStatusTS);
		System.out.println("===========");
		DataManager.getInstance().insertSatellite(temperatureStatus, temperatureStatusTS, energyStatus, energyStatusTS, 
								sbandStatus, sbandStatusTS, payloadStatus, payloadStatusTS, solarPanelsStatus, 
								solarPanelsStatusTS, thermalCtrlStatus, thermalCtrlStatusTS);
	}
	
	public void parseTemperaturePacket (Node packet) {
		System.out.println("DEBUG: Temperature packet parsing");
		NodeList children = packet.getChildNodes();
		for (int i=0; i < children.getLength(); i++) { //For each packet element
			Node child = children.item(i);
			if (child.getNodeName().equals(tagTempSample)) {
				float sensor1 = 0, sensor2 = 0, sensor3 = 0;
				Timestamp ts;
				NamedNodeMap attr = child.getAttributes();
				String sampleTimestamp = attr.getNamedItem(tagTime).getNodeValue();
				ts = new Timestamp (parseRTEMSTimestamp(sampleTimestamp)); //Parse timestamp
				
				NodeList sensors = child.getChildNodes();
				for (int j=0; j < sensors.getLength(); j++) {
					Node sensor = sensors.item(j);
					NamedNodeMap sensattrs = sensor.getAttributes();
					String temp = sensattrs.getNamedItem(tagTemp).getNodeValue();
					switch (sensor.getNodeName()) {
					case tagTempPacketItem1:
						sensor1 = Float.parseFloat(temp);
						break;
					case tagTempPacketItem2:
						sensor2 = Float.parseFloat(temp);
						break;
					case tagTempPacketItem3:
						sensor3 = Float.parseFloat(temp);
						break;
					default:
						break;	
					}
				}
				System.out.println("===========");
				System.out.println("Inserting Temperature Sample. Time: " + ts + "\n" +
									"Sensor1 temperature: " + sensor1 + "C\n" +
									"Sensor2 temperature: " + sensor2 + "C\n" +
									"Sensor3 temperature: " + sensor3 + "C");
				System.out.println("===========");
				DataManager.getInstance().insertTemprature(sensor1, sensor2, sensor3, ts);
			}
		}
	}
	
	public void parseEnergyPacket (Node packet) {
		System.out.println("DEBUG: Energy packet parsing");
		NodeList children = packet.getChildNodes();
		for (int i=0; i < children.getLength(); i++) { //For each packet element
			Node child = children.item(i);
			if (child.getNodeName().equals(tagEnergySample)) {
				float batt1V = 0, batt2V = 0, batt3V = 0, batt1C = 0, batt2C = 0, batt3C = 0;
				Timestamp ts;
				NamedNodeMap attr = child.getAttributes();
				String sampleTimestamp = attr.getNamedItem(tagTime).getNodeValue();
				ts = new Timestamp (parseRTEMSTimestamp(sampleTimestamp)); //Parse timestamp
				
				NodeList batts = child.getChildNodes();
				for (int j=0; j < batts.getLength(); j++) {
					Node batt = batts.item(j);
					NamedNodeMap battattrs = batt.getAttributes();
					String voltage = battattrs.getNamedItem(tagVoltage).getNodeValue();
					String current = battattrs.getNamedItem(tagCurrent).getNodeValue();
					switch (batt.getNodeName()) {
					case tagEnergyPacketItem1:
						batt1V = Float.parseFloat(voltage);
						batt1C = Float.parseFloat(current);
						break;
					case tagEnergyPacketItem2:
						batt2V = Float.parseFloat(voltage);
						batt2C = Float.parseFloat(current);
						break;
					case tagEnergyPacketItem3:
						batt3V = Float.parseFloat(voltage);
						batt3C = Float.parseFloat(current);
						break;
					default:
						break;	
					}
				}
				System.out.println("===========");
				System.out.println("Inserting Energy Sample. Time: " + ts + "\n" +
									"Battery1: " + batt1V + "V " + batt1C + "A\n" +
									"Battery2: " + batt2V + "V " + batt2C + "A\n" +
									"Battery3: " + batt3V + "V " + batt3C + "A");
				System.out.println("===========");
				DataManager.getInstance().insertEnergy(batt1V, batt2V, batt3V, batt1C, batt2C, batt3C, ts);
			}
		}
	}
	
	/**
	 * Translate RTEMS timestamp format to standard time format
	 * @param timestamp String timestamp of format yyyymmddhhmmss
	 * @return
	 */
	public static long parseRTEMSTimestamp (String timestamp) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		//df.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return df.parse(timestamp).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String toRTEMSTimestamp (Timestamp timestamp) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(timestamp.getTime());
	}
	
	public void stopThread() {
    	this.isRunning = false;
    }
	
	public Status stringToStatus (String strst) {
		Status st;
		switch (strst) {
		case tagStatusOn:
			st = Status.ON;
			break;
		case tagStatusMalfunction:
			st = Status.MALFUNCTION;
			break;
		case tagStatusStandby:
			st = Status.STANDBY;
			break;
		case tagStatusNonOperational:
			st = Status.NON_OPERATIONAL;
			break;
		default:
			st = null;
			break;
		}
		return st;
	}
}
