package communication;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import java.sql.Timestamp; 

import data.DataManager;
import data.Energy;
import data.Temprature;

public class CommTest {

	@Test
	public final void testParseTemp() {

		// TO DO :: CLEAR ALL TEMP TABLE
		 DataManager.getInstance().setTestMode(true);
		long g =  Long.MAX_VALUE;
		//DataManager.getInstance().deleteComponent("Temprature", new Timestamp(g)); // clean this row	
		byte[] test= {2,1,0,0,18,81,90,99,53,-42,0,0,0,4};
		MessageParser.parseTemp(test);
		List<Temprature> t =	DataManager.getInstance().getTemprature(new Timestamp(0), new Timestamp(g)); 
		assertTrue(t.size()== 1); 
	}
	
	@Test
	public final void testParseEnergy() {
		DataManager.getInstance().setTestMode(true);
		long g =  Long.MAX_VALUE;
		//DataManager.getInstance().deleteComponent("Temprature", new Timestamp(g)); // clean this row	
		byte[] test= {1,6,0,0,18,81,90,99,53,-42,0,0,0,18,0,0,0,1,
				0,0,18,81,90,99,53,-43,0,0,0,22,0,0,0,6,
				0,0,18,81,90,99,53,-44,0,0,0,16,0,0,0,22,
				0,0,18,81,90,99,53,-45,0,0,0,32,0,0,0,13,
				0,0,18,81,90,99,53,-46,0,0,0,8,0,0,0,5,
				0,0,18,81,90,99,53,-47,0,0,0,21,0,0,0,44
				};
		MessageParser.parseEnergy(test); 
		List<Energy> t =	DataManager.getInstance().getEnergy(new Timestamp(0), new Timestamp(g)); 
		assertTrue(t.size()== 6); 
	}

	
	
	@Test
	public final void testParseStatic() {
		long g =  Long.MAX_VALUE;
		DataManager.getInstance().getSatellite(new Timestamp(0), new Timestamp(g));
		DataManager.getInstance().setTestMode(true);
		byte[] test= {0,6,
				2,0,0,18,81,90,99,53,-42,3,
				3,0,0,18,81,90,99,53,-42,0,
				0,0,0,18,81,90,99,53,-42,0,
				4,0,0,18,81,90,99,53,-42,0,
				1,0,0,18,81,90,99,53,-42,3,
				5,0,0,18,81,90,99,53,-42,3
				};
		MessageParser.parseStatic(test);
	}
	 

	
}
