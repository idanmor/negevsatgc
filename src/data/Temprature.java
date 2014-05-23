package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;

@DatabaseTable(tableName="Temprature")
public class Temprature extends Component {
	public static final String DATE_FIELD_NAME = "sampleTimestamp";
    @DatabaseField(id = true ,columnName = DATE_FIELD_NAME)
    private Timestamp sampleTimestamp;
    @DatabaseField
    private float sensor1;
    @DatabaseField
    private float sensor2;
    @DatabaseField
    private float sensor3;
    @DatabaseField
    private Timestamp timeReceivedTimestamp;

    public Temprature(){}
    
    public Temprature(Timestamp ts, float s1,float s2, float s3){
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.sampleTimestamp=ts;
        this.timeReceivedTimestamp=t;
        this.sensor1=s1;
        this.sensor2=s2;
        this.sensor3=s3;
        
    }
    public Timestamp getSampleTimestamp(){
        return sampleTimestamp;
    }
    public Timestamp getReceivedTimestamp(){
        return timeReceivedTimestamp;
    }
    public float getSensor1(){
        return sensor1;
    }
    public float getSensor2(){
        return sensor2;
    }
    public float getSensor3(){
        return sensor3;
    }
	
}
