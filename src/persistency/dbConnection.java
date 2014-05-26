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
            connectionSource =new JdbcConnectionSource("jdbc:sqlite:D:\\sqlite\\negevSatDB.db");
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
 
    public List<Temprature> getTemprature(Timestamp date1, Timestamp date2){
        
        List<Temprature> data=null;
        try{
            data = tmpDao.queryBuilder().where().between(Temprature.DATE_FIELD_NAME, date1, date2).query();
        }
        catch( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
        return data;
    }
    public List<Energy> getEnergy(Timestamp date1, Timestamp date2){
        
        List<Energy> data=null;
        try{
           data = egDao.queryBuilder().where().between(Energy.DATE_FIELD_NAME, date1, date2).query();          
        }
        catch( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
        return data;
    }

    public static List<Satellite> getSatelliteData(Timestamp date1, Timestamp date2){
    	List<Satellite> data=null;
    	try{
    		data=satDao.queryBuilder().where().between(Satellite.DATE_FIELD_NAME, date1, date2).query();
    		}
    	catch( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
    	}	
    	return data;
    }

    
    public static void insertSatellite(Status s1,Status s2, Status s3, Status s4, Status s5, Status s6, Timestamp ts){
        Satellite sat=new Satellite(ts,s1,s2,s3,s4,s5,s6);
        try{
            satDao.create(sat);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
         }
    }
    
    public static void insertTemprature(float s1,float s2, float s3, Timestamp ts){
        Temprature tmp=new Temprature(ts,s1,s2,s3);
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
