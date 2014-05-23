package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;

@DatabaseTable(tableName="Energy")
public class Energy extends Component {
	public static final String DATE_FIELD_NAME = "sampleTimestamp";
    @DatabaseField(id = true ,columnName = DATE_FIELD_NAME)
    private Timestamp sampleTimestamp;
    @DatabaseField
    private float batt1Voltage;
    @DatabaseField
    private float batt2Voltage;
    @DatabaseField
    private float batt3Voltage;
    @DatabaseField
    private float batt1Current;
    @DatabaseField
    private float batt2Current;
    @DatabaseField
    private float batt3Current;
    @DatabaseField
    private Timestamp timeReceivedTimestamp;
    
    public Energy(){}
    
    
    public Energy(Timestamp ts,float _batt1Voltage,float _batt2Voltage,float _batt3Voltage, float _batt1Current,float _batt2Current,float _batt3Current){
        java.util.Date date1= new java.util.Date();
        this.sampleTimestamp=ts;
        this.timeReceivedTimestamp= new Timestamp(date1.getTime());
        this.batt1Voltage=_batt1Voltage;
        this.batt2Voltage=_batt2Voltage;
        this.batt3Voltage=_batt3Voltage;
        this.batt1Current=_batt1Current;
        this.batt2Current=_batt2Current;
        this.batt3Current=_batt3Current;
    }
	

    public Timestamp getSampleTimestamp(){
        return sampleTimestamp;
    }
    public Timestamp getReceivedTimestamp(){
        return timeReceivedTimestamp;
    }
    public float getBatt1v(){
        return batt1Voltage;
    }
    public float getBatt2v(){
        return batt2Voltage;
    }
    public float getBatt3v(){
        return batt3Voltage;
    }
    public float getBatt1c(){
        return batt1Current;
    }
    public float getBatt2c(){
        return batt2Current;
    }
    public float getBatt3c(){
        return batt3Current;
    }
}
