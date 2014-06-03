package persistency;



import data.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;

import java.util.List;
import java.sql.Timestamp;

public class dbConnection {
    private static dbConnection dbcon;
    static ConnectionSource connectionSource;
    static Dao<Energy, Timestamp> egDao;
    static Dao<Temprature, Timestamp> tmpDao;
    static Dao<Satellite, Timestamp> satDao;
    static Dao<Mission, Timestamp> missionDao;
    



    private dbConnection(){
        try{
            connectionSource =new JdbcConnectionSource("jdbc:sqlite:C:\\sqlite\\negevSatDB.db");
            egDao =DaoManager.createDao(connectionSource, Energy.class);
            tmpDao =DaoManager.createDao(connectionSource, Temprature.class);
            satDao =DaoManager.createDao(connectionSource, Satellite.class);
            missionDao = DaoManager.createDao(connectionSource, Mission.class);
            
       
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
    }
    
    public static dbConnection getdbCon(){ //singelton
        if(dbcon==null)
            dbcon=new dbConnection();
        return dbcon;
    }
 
    public List<Mission> getMission(Timestamp creationTimestamp){
    	List<Mission> mission=null;
    	try{
    		mission=missionDao.queryBuilder().where().eq(Mission.DATE_FIELD_NAME, creationTimestamp).query();
    		}
    	catch( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
    	}	
    	return mission;
    }
    
    public List<Satellite> getSatelliteData(Timestamp startDate, Timestamp endDate){
    	List<Satellite> data=null;
    	try{
    		data=satDao.queryBuilder().where().between(Satellite.DATE_FIELD_NAME, startDate, endDate).query();
    		}
    	catch( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
    	}	
    	return data;
    }
    
    public List<Temprature> getTemprature(Timestamp startDate, Timestamp endDate){
        
        List<Temprature> data=null;
        try{
            data = tmpDao.queryBuilder().where().between(Temprature.DATE_FIELD_NAME, startDate, endDate).query();
        }
        catch( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
        return data;
    }
    public List<Energy> getEnergy(Timestamp startDate, Timestamp endDate){
        
        List<Energy> data=null;
        try{
           data = egDao.queryBuilder().where().between(Energy.DATE_FIELD_NAME, startDate, endDate).query();          
        }
        catch( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
        return data;
    }

 

    public void insertMission(Timestamp _missionExecutionTS, Command _command, int _priority){
        Mission mission=new Mission(_missionExecutionTS,_command,_priority);
        try{
            missionDao.create(mission);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    
    public void insertSatellite(Status temp, Status energy, Status Sband, Status Payload,Status SolarPanels, Status Thermal, Timestamp ts){
        Satellite sat=new Satellite(ts,temp,energy,Sband,Payload,SolarPanels,Thermal);
        try{
            satDao.create(sat);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public void insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
        Temprature tmp=new Temprature(ts,sensor1,sensor2,sensor3);
        try{
            tmpDao.create(tmp);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public void insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
        Energy eng=new Energy(ts,batt1V,batt2V,batt3V,batt1C,batt2C,batt3C);  
        try{
            egDao.create(eng);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
   

    
    public void delete(String component,Timestamp ts) {
        try{
            if(component.equals("Energy"))
                egDao.deleteById(ts);
             else if(component.equals("Temprature"))
                      tmpDao.deleteById(ts);
            }
            catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
            } 
            
    }

}
