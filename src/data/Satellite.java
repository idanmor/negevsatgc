package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.ArrayList;

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
    
    public Satellite(Timestamp ts, Status temp, Status energy, Status Sband, Status Payload,Status SolarPanels, Status Thermal) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.sampleTimestamp=ts;
        this.timeReceivedTimestamp=t;
        this.TempStatus=temp;
        this.EnergyStatus=energy;
        this.SbandStatus=Sband;
        this.PayloadStatus=Payload;
        this.SolarPanelsStatus=SolarPanels;
        this.ThermalStatus=Thermal;
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
    public ArrayList<Status> getAllStatus(){
    	ArrayList<Status> statList= new ArrayList<Status>();
    	statList.add(getTempStatus());
    	statList.add(getEnergyStatus());
    	statList.add(getSbandStatus());
    	statList.add(getPayloadStatus());
    	statList.add(getSolarPanelsStatus());
    	statList.add(getThermalStatus());
    	return statList;
    }
}
