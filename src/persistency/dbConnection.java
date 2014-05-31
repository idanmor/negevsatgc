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
    



    private dbConnection(){
        try{
            connectionSource =new JdbcConnectionSource("jdbc:sqlite:C:\\sqlite\\negevSatDB.db");
            egDao =DaoManager.createDao(connectionSource, Energy.class);
            tmpDao =DaoManager.createDao(connectionSource, Temprature.class);
            satDao =DaoManager.createDao(connectionSource, Satellite.class);
            
       
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
 
    public static List<Temprature> getTemprature(Timestamp startDate, Timestamp endDate){
        
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
    public static List<Energy> getEnergy(Timestamp startDate, Timestamp endDate){
        
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

    public static List<Satellite> getSatelliteData(Timestamp startDate, Timestamp endDate){
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

    
    public static void insertSatellite(Status temp, Status energy, Status Sband, Status Payload,Status SolarPanels, Status Thermal, Timestamp ts){
        Satellite sat=new Satellite(ts,temp,energy,Sband,Payload,SolarPanels,Thermal);
        try{
            satDao.create(sat);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public static void insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
        Temprature tmp=new Temprature(ts,sensor1,sensor2,sensor3);
        try{
            tmpDao.create(tmp);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public static void insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
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
