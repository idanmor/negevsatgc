package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;

@DatabaseTable(tableName="Satellite")
public class Satellite {
    public static final String DATE_FIELD_NAME = "sampleTimestamp";
    @DatabaseField(id = true) 
    private Timestamp sampleTimestamp;
    @DatabaseField
    private Status TempStatus;
    @DatabaseField
    private Status EnergyStatus;
    @DatabaseField
    private Status SbandStatus;
    @DatabaseField
    private Status PayloadStatus;
    @DatabaseField
    private Status SolarPanelsStatus;
    @DatabaseField
    private Status ThermalStatus;
    @DatabaseField
    private Timestamp timeReceivedTimestamp;
    
    public Satellite(){}
    
    public Satellite(Timestamp ts, Status s1, Status s2, Status s3, Status s4,Status s5, Status s6) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.sampleTimestamp=ts;
        this.timeReceivedTimestamp=t;
        this.TempStatus=s1;
        this.EnergyStatus=s2;
        this.SbandStatus=s3;
        this.PayloadStatus=s4;
        this.SolarPanelsStatus=s5;
        this.ThermalStatus=s6;
	}



	public Timestamp getSampleTimestamp(){
    	return this.sampleTimestamp;
    }
    public Timestamp getTimeReceivedTimestamp(){
    	return this.sampleTimestamp;
    }
    public Status getTempStatus(){
    	return this.TempStatus;
    }
    public Status getEnergyStatus(){
    	return this.EnergyStatus;
    }
    public Status getSbandStatus(){
    	return this.SbandStatus;
    }
    public Status getPayloadStatus(){
    	return this.PayloadStatus;
    }
    public Status getSolarPanelsStatus(){
    	return this.SolarPanelsStatus;
    }
    public Status getThermalStatus(){
    	return this.ThermalStatus;
    }
}
