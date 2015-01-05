package communication;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import data.DataManager;
import data.Mission;

public class BinaryComm implements CommCtrl {

	public BinaryComm(){}
	
	public void  SendMissions(Collection<Mission> missions) {
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
			Date nw= new java.util.Date();
	        Timestamp sentTime=new Timestamp(nw.getTime());
	        DataManager.getInstance().setMissionSentTS(mission, sentTime);
		}
		
		msg = msg.concat("</upstreamPacket>" + 
							"</packet>");
		
		System.out.println("DEBUG: Sending message:\n" + msg);
		//this.sendMessage(new Message(msg));
	}
}
