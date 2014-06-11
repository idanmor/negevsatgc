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
    static Dao<Energy, Timestamp> energygDao;
    static Dao<Temprature, Timestamp> tempratureDao;
    static Dao<Satellite, Timestamp> satelliteDao;
    static Dao<Mission, Timestamp> missionDao;
    



    private dbConnection(){
        try{
            connectionSource =new JdbcConnectionSource("jdbc:sqlite:c:\\sqlite\\negevSatDB.db");
            energygDao =DaoManager.createDao(connectionSource, Energy.class);
            tempratureDao =DaoManager.createDao(connectionSource, Temprature.class);
            satelliteDao =DaoManager.createDao(connectionSource, Satellite.class);
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
    		data=satelliteDao.queryBuilder().where().between(Satellite.DATE_FIELD_NAME, startDate, endDate).query();
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
            data = tempratureDao.queryBuilder().where().between(Temprature.DATE_FIELD_NAME, startDate, endDate).query();
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
           data = energygDao.queryBuilder().where().between(Energy.DATE_FIELD_NAME, startDate, endDate).query();          
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
    
    
    public void insertSatellite(Status temp, Timestamp tempTS, Status energy, Timestamp energyTS, Status Sband, Timestamp sbandTS, Status Payload,Timestamp payloadTS, Status SolarPanels, Timestamp solarPanelsTS, Status Thermal, Timestamp ThermalTS){
        Satellite sat=new Satellite(temp,tempTS,energy,energyTS,Sband,sbandTS,Payload,payloadTS,SolarPanels,solarPanelsTS,Thermal,ThermalTS);
        try{
            satelliteDao.create(sat);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public void insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
        Temprature tmp=new Temprature(ts,sensor1,sensor2,sensor3);
        try{
            tempratureDao.create(tmp);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public void insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
        Energy eng=new Energy(ts,batt1V,batt2V,batt3V,batt1C,batt2C,batt3C);  
        try{
            energygDao.create(eng);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
   

    
    public void deleteComponent(String component,Timestamp timestamp) {
        try{
            if(component.equals("Energy"))
                energygDao.deleteById(timestamp);
             else if(component.equals("Temprature"))
                      tempratureDao.deleteById(timestamp);
            }
            catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
            } 
            
    }
    
    public void deleteCompletedMission(Timestamp creationTimestamp){
    	try{
    		missionDao.deleteById(creationTimestamp);
    	}
    	catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
         }
    }

}
