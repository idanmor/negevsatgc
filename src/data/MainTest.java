package data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;
import persistency.dbConnection;

public class MainTest {

	public static void main(String[] args) {
		DataManager dm=DataManager.getInstance();
		dbConnection db=dbConnection.getdbCon();
		java.util.Date date= new java.util.Date();
		final long monthInMS = 26280000;//need to mult by 100
		final long hourInMs = 3600000;
		Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
		final long dayinMS = 86400000;
		int g=6;
	    Timestamp TS=new Timestamp(System.currentTimeMillis()-dayinMS*g);
	 	
    	
    	
       // addRandomTemp(db, hourInMs, oldestTS, TS);
//        addRandomEnergy(db, hourInMs, oldestTS, TS);
//        addRandomTemp(db, hourInMs, oldestTS, TS);
//	    db.insertMission(TS, Command.FORMAT_STATIC, 1);
//	    List<Mission> mission=db.getMission(TS);
//	    for (Mission m: mission)
//    	System.out.println("select from mission "+m.getCreationTimestamp());
//	    db.insertMission(TS, Command.FORMAT_STATIC, 1);
//	    dbConnection.insertEnergy(2, 2, 3, 4, 5, 6,TS);
    	int i=360000;
    	
    	//db.insertTemprature(38, 2, 3, TS);
	    db.insertTemprature(38, 30, 3, new Timestamp(System.currentTimeMillis()-dayinMS*g));
	    db.insertTemprature(40, 32, 3, new Timestamp(System.currentTimeMillis()+2*i-dayinMS*g));
	    db.insertTemprature(42, 34, 3, new Timestamp(System.currentTimeMillis()+3*i-dayinMS*g));
	    db.insertTemprature(43, 36, 3, new Timestamp(System.currentTimeMillis()+4*i-dayinMS*g));
	    db.insertTemprature(45, 38, 3, new Timestamp(System.currentTimeMillis()+5*i-dayinMS*g));
	    db.insertTemprature(47, 40, 3, new Timestamp(System.currentTimeMillis()+6*i-dayinMS*g));
	    db.insertTemprature(50, 42, 3, new Timestamp(System.currentTimeMillis()+7*i-dayinMS*g));
	    db.insertTemprature(47, 40, 3, new Timestamp(System.currentTimeMillis()+8*i-dayinMS*g));
	    db.insertTemprature(45, 38, 3, new Timestamp(System.currentTimeMillis()+9*i-dayinMS*g));
	    db.insertTemprature(43, 36, 3, new Timestamp(System.currentTimeMillis()+10*i-dayinMS*g));
	    db.insertTemprature(40, 34, 3, new Timestamp(System.currentTimeMillis()+11*i-dayinMS*g));
	    
//	    db.insertSatellite(Status.ON, TS, Status.ON,TS, Status.ON, TS, Status.ON, TS, Status.ON, TS, Status.ON, TS);
//	    db.insertSatellite(Status.STANDBY, TS, Status.STANDBY,TS, Status.STANDBY, TS, Status.STANDBY, TS, Status.STANDBY, TS, Status.STANDBY, TS);
//	    db.insertSatellite(Status.MALFUNCTION, TS, Status.MALFUNCTION,TS, Status.MALFUNCTION, TS, Status.MALFUNCTION, TS, Status.MALFUNCTION, TS, Status.MALFUNCTION, TS);
//	    Satellite sat=db.getLatestSatelliteData();
//	    System.out.println("*******"+sat.getObjectCreationTimestamp()+"*******"+ sat.getEnergyStatus()+"*******");
//	    Timestamp t=new Timestamp(date.getTime());
//	    List<? extends Component> tlst=dm.getTemprature(oldestTS,t);
//	    for (Component item: tlst)
//	    	System.out.println("select from temprature "+item.getSampleTimestamp());
//	    List<? extends Component> elst=dm.getEnergy(oldestTS,t);
//	    for (Component item: elst)
//	    	System.out.println("select from energy "+item.getSampleTimestamp());
//	    List<Satellite> stlst=db.getSatelliteData(oldestTS,TS);
//	    for (Satellite item: stlst)
//	    	System.out.println("select from satellite "+item.getSampleTimestamp());
	}

	private static void addRandomTemp(dbConnection db, final long hourInMs,
			Timestamp oldestTS, Timestamp TS) {
		int startTemp = 50;
        int i = 5;
	    while(oldestTS.before(TS)){
	    	int randomModifier = (int) (Math.random() * 10);
	    	int temp = startTemp + i * (randomModifier > 5 ? 1 : -1);
	    	
	    	db.insertTemprature(temp, temp,temp, oldestTS);
	    	oldestTS.setTime(oldestTS.getTime() + hourInMs * 2);
	    }
	}
	
	private static void addRandomEnergy(dbConnection db, final long hourInMs,
			Timestamp oldestTS, Timestamp TS) {
		int startEnergy = 100;
        int i = 10;
	    while(oldestTS.before(TS)){
	    	int randomModifier = (int) (Math.random() * 10);
	    	int en = startEnergy + i * (randomModifier > 5 ? 1 : -1);
	    	
	    	db.insertEnergy(en + randomModifier, en - randomModifier, en - randomModifier, en, en, en - randomModifier, oldestTS);
	    	oldestTS.setTime(oldestTS.getTime() + hourInMs * 2);
	    }
	}

}
