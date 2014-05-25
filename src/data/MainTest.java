package data;

import java.sql.Timestamp;
import java.util.List;

import persistency.dbConnection;

public class MainTest {

	public static void main(String[] args) {
		DataManager dm=DataManager.getInstance();
		java.util.Date date= new java.util.Date();
		Timestamp oldestTS=new Timestamp(463777);
	    Timestamp TS=new Timestamp(463780);
	   // dbConnection.insertEnergy(2, 2, 3, 4, 5, 6,TS);
	   // dbConnection.insertTemprature(1, 2, 3, TS);
	    dbConnection.insertSatellite(Status.ON, Status.ON,Status.ON, Status.ON, Status.ON, Status.ON, TS);
	    Timestamp t=new Timestamp(date.getTime());
	    List<? extends Component> tlst=dbConnection.getTemprature(oldestTS,t);
	    for (Component item: tlst)
	    	System.out.println("select from temprature "+item.getSampleTimestamp());
	    List<? extends Component> elst=dbConnection.getEnergy(oldestTS,t);
	    for (Component item: elst)
	    	System.out.println("select from energy "+item.getSampleTimestamp());
	    List<Satellite> stlst=dbConnection.getSatelliteData(oldestTS,t);
	    for (Satellite item: stlst)
	    	System.out.println("select from satellite "+item.getSampleTimestamp());
	}

}
