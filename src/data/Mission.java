package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName="Mission")
public class Mission {
	public static final String DATE_FIELD_NAME = "creationTimestamp";
    @DatabaseField(id = true ,columnName = DATE_FIELD_NAME)
    private Timestamp creationTimestamp;
    @DatabaseField
    private Timestamp missionExecutionTS;
    @DatabaseField
    private Command command;
    @DatabaseField
    private int priority;
    
    public Mission(){}
    
    public Mission(Timestamp _missionExecutionTS, Command _command, int _priority) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.creationTimestamp=t;
        this.missionExecutionTS=_missionExecutionTS;
        this.command=_command;
        this.priority=_priority;
	}
    
    public Timestamp getMissionExecutionTS(){
    	return this.missionExecutionTS;
    }

    public Command getcommand(){
    	return this.command;
    }
    public int getPriority(){
    	return this.priority;
    }
          
    public Timestamp getExecutionTime () {
    	return missionExecutionTS;
    }

      
    public Timestamp getCreationTimestamp(){
    	return this.creationTimestamp;
    }
}
