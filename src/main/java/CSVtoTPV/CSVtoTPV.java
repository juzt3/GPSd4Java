package CSVtoTPV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.TPVObject;

public class CSVtoTPV {
	
	private TPVObject tpv;
	private BufferedReader br;
	private boolean read = false;
	private double speed = 0;
	
	private static final Logger LOG = LoggerFactory.getLogger(CSVtoTPV.class);
	
	private final List<IObjectListener> listeners = new ArrayList<IObjectListener>(1);
	
	public CSVtoTPV (){
		this.read = true;
	}
	
	public CSVtoTPV(double speed){
		this.speed = speed;
		this.read = true;
	}
	
	public void read(File file) throws IOException, ParseException {
		//"Segmento","Punto","Latitud (grados)","Longitud (grados)","Altitud (m)","Rumbo (grados)","Precisión (m)","Velocidad (m/s)","Tiempo","Potencia (W)","Cadencia (rpm)","Frecuencia cardíaca (lpm)"
		this.br = new BufferedReader(new FileReader(file));
		String line;
		
		while((line=br.readLine())!=null && read){
			if(line.isEmpty()) continue;
			if(!Character.isDigit(line.charAt(1))) continue;
			
			tpv = new TPVObject();
			
			line = line.replace("\"", "");
			String[] tokens = line.split(",");
			
			double latitude = Double.parseDouble(tokens[2]);
			double longitude = Double.parseDouble(tokens[3]);
			double altitude = Double.parseDouble(tokens[4]);
			double course = Double.parseDouble(tokens[5]);
			
			if(tokens[7].isEmpty()){
				tpv.setSpeed(this.speed);
			}
			else{
				tpv.setSpeed(Double.parseDouble(tokens[7]));
			}
			
			//double timestamp = Double.parseDouble(tokens[8]);
			
			tpv.setTag("Simulator");
			tpv.setDevice(file.getName());
			tpv.setLatitude(latitude);
			tpv.setLongitude(longitude);
			tpv.setAltitude(altitude);
			tpv.setCourse(course);
			tpv.setSpeed(speed);
			//tpv.setTimestamp(timestamp);
			tpv.setMode(ENMEAMode.NotSeen);
			
			for (final IObjectListener l : this.listeners) {
				l.handleTPV((TPVObject) tpv);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				CSVtoTPV.LOG.debug("Interrupted while sleeping", e);
			}
		}
		
		this.stop();
		CSVtoTPV.LOG.warn("End of file");
	}
	
	// TODO better stop
	// ########################################
	public void stop(){
		this.read = false;
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TPVObject getTPV(){
		return tpv;
	}
	
	public void addListener(final IObjectListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(final IObjectListener listener) {
		this.listeners.remove(listener);
	}

}
