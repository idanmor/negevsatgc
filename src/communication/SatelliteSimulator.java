package communication;

import gnu.io.NoSuchPortException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import data.Status;
import data.Satellite.SatelliteState;

public class SatelliteSimulator {
	static String satelliteState = MessageParser.tagStateOperational;
	static Status TempratureStatus = Status.ON;
    static Status EnergyStatus = Status.ON;
    static Status SbandStatus = Status.STANDBY;
    static Status PayloadStatus = Status.STANDBY;
    static Status SolarPanelsStatus = Status.ON;
    static Status ThermalStatus = Status.STANDBY;
    static Timestamp ts;
    static boolean keepWorking = true;
    static Scanner in;
    
	public static void main(String[] args) {
		in = new Scanner(System.in);
		
		try {
			CommunicationManager.getInstance().connect("COM1");
		} catch (Exception e) {
			try {
				CommunicationManager.getInstance().connect("LOCAL");
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		
		while(keepWorking) {
			System.out.println("Choose command:\n"
					+ "1. Print components status\n"
					+ "2. Change component status\n"
					+ "3. Send status to ground station\n"
					+ "4. Send temperature packets to ground station\n"
					+ "5. Send energy packets to ground station\n"
					+ "6. Exit");
			String command = in.next();
			switch(command){
			case "1":
				printComponents();
				break;
			case "2":
				changeComponentStatus();
				break;
			case "3":
				sendStaticToGround();
				break;
			case "4":
				sendTemperatureToGround();
				break;
			case "5":
				sendEnergyToGround();
				break;
			case "6":
				System.out.println("Bye-Bye");
				keepWorking = false;
				break;
			default:
				System.out.println("Wrong input");
				break;
			}
		}
	}
	
	private static void sendTemperatureToGround() {
		ts = new Timestamp((new Date()).getTime());
		String tss = MessageParser.toRTEMSTimestamp(ts);
		String msg = CommunicationManager.msgStartDelimiter + "<?xml version=\"1.0\"?>"
				+ "<packet><downstreamPacket>\n"
				+ "<type>Temperature</type>\n"
				+ "<TemperatureSample time=\"" + tss + "\">\n"
				+ "<Sensor1 temp=\"" + randFloat(40.0f,45.0f) + "\"/>\n"
				+ "<Sensor2 temp=\"" + randFloat(55.0f,60.0f) + "\"/>\n"
				+ "<Sensor3 temp=\"" + randFloat(50.0f,55.0f) + "\"/>\n"
				+ "</TemperatureSample>\n"
				+ "</downstreamPacket>\n"
				+ "</packet>" + CommunicationManager.msgStopDelimiter;	
		CommunicationManager.getInstance().sendMessage(new Message(msg));
	}
	
	private static void sendEnergyToGround() {
		ts = new Timestamp((new Date()).getTime());
		String tss = MessageParser.toRTEMSTimestamp(ts);
		String msg = CommunicationManager.msgStartDelimiter + "<?xml version=\"1.0\"?>"
				+ "<packet><downstreamPacket>\n"
				+ "<type>Energy</type>\n"
				+ "<EnergySample time=\"" + tss + "\">\n"
				+ "<Battery1 voltage=\"" + randFloat(107.0f,112.0f) + "\" current=\"" + randFloat(1.0f,4.0f) + "\"/>\n"
				+ "<Battery2 voltage=\"" + randFloat(107.0f,112.0f) + "\" current=\"" + randFloat(1.0f,4.0f) + "\"/>\n"
				+ "<Battery3 voltage=\"" + randFloat(107.0f,112.0f) + "\" current=\"" + randFloat(1.0f,4.0f) + "\"/>\n"
				+ "</EnergySample>\n"
				+ "</downstreamPacket>\n"
				+ "</packet>" + CommunicationManager.msgStopDelimiter;	
		CommunicationManager.getInstance().sendMessage(new Message(msg));
	}
	
	private static void sendStaticToGround() {
		ts = new Timestamp((new Date()).getTime());
		String tss = MessageParser.toRTEMSTimestamp(ts);
		String msg = CommunicationManager.msgStartDelimiter + "<?xml version=\"1.0\"?>"
				+ "<packet><downstreamPacket>\n"
				+ "<type>Static</type>\n"
				+ "<state>" + satelliteState + "</state>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModuleEnergy + "' status='" + EnergyStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModuleTemperature + "' status='" + TempratureStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModuleSband + "' status='" + SbandStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModuleSolarPanels + "' status='" + SolarPanelsStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModuleThermalCtrl + "' status='" + ThermalStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "<Module time=\"" + tss + "\">\n"
				+ "<Info name='" + MessageParser.tagModulePayload + "' status='" + PayloadStatus.toString() + "'/>\n"
				+ "</Module>\n"
				+ "</downstreamPacket>\n"
				+ "</packet>" + CommunicationManager.msgStopDelimiter;	
		CommunicationManager.getInstance().sendMessage(new Message(msg));
	}

	public static void printComponents() {
		System.out.println("\n=============Satellite Components Status===============");
		System.out.println("Satellite status: " + satelliteState.toString());
		System.out.println("Temperature module: " + TempratureStatus.toString());
		System.out.println("Energy module: " + EnergyStatus.toString());
		System.out.println("Sband module: " + SbandStatus.toString());
		System.out.println("Payload module: " + PayloadStatus.toString());
		System.out.println("Solar Panels module: " + SolarPanelsStatus.toString());
		System.out.println("Thermal Control module: " + ThermalStatus.toString());
		System.out.println("=======================================================\n");
	}
	
	public static void changeComponentStatus() {
		System.out.println("Choose component to change status:\n"
				+ "1. Satellite status\n"
				+ "2. Temperature module\n"
				+ "3. Energy module\n"
				+ "4. Sband module\n"
				+ "5. Payload module\n"
				+ "6. Solar Panels module\n"
				+ "7. Thermal Control module\n");
		String module = in.next();
		switch(module){
		case "1":
			changeSatelliteState();
			break;
		case "2":
			TempratureStatus = changeModuleStatus("Temperature", TempratureStatus);
			break;
		case "3":
			EnergyStatus = changeModuleStatus("Energy", EnergyStatus);
			break;
		case "4":
			SbandStatus = changeModuleStatus("Sband", SbandStatus);
			break;
		case "5":
			PayloadStatus = changeModuleStatus("Payload", PayloadStatus);
			break;
		case "6":
			SolarPanelsStatus = changeModuleStatus("Solar Panels", SolarPanelsStatus);
			break;
		case "7":
			ThermalStatus = changeModuleStatus("Thermal Control", ThermalStatus);
			break;
		default:
			System.out.println("Wrong input");
			break;
		}
	}

	private static Status changeModuleStatus(String component, Status s) {
		System.out.println("Choose new " + component + " status:\n"
				+ "1. ON\n"
				+ "2. MALFUNCTION\n"
				+ "3. STANDBY\n"
				+ "4. NON-OPERATIONAL\n");
		String status = in.next();
		switch(status){
		case "1":
			return Status.ON;
		case "2":
			return Status.MALFUNCTION;
		case "3":
			return Status.STANDBY;
		case "4":
			return Status.NON_OPERATIONAL;
		default:
			System.out.println("Wrong input");
			return s;
		}
	}

	private static void changeSatelliteState() {
		System.out.println("Choose new satellite state:\n"
				+ "1. OPERATIONAL\n"
				+ "2. SAFE MODE\n");
		String status = in.next();
		switch(status){
		case "1":
			satelliteState = MessageParser.tagStateOperational;
			break;
		case "2":
			satelliteState = MessageParser.tagStateSafe;
			break;
		default:
			System.out.println("Wrong input");
			break;
		}
	}
	
	private static float randFloat (float min, float max) {
		Random rand = new Random();
	    float randomNum = rand.nextFloat()*(max-min) + min;
	    return randomNum;
	}
	
}
