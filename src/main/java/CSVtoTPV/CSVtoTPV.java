package CSVtoTPV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.types.ENMEAMode;
import de.taimos.gpsd4java.types.IGPSObject;
import de.taimos.gpsd4java.types.TPVObject;

public class CSVtoTPV {
	
	private long sleeptime = 500;
	private double speed = Double.NaN;
	private String directory = "";
	private List<TPVObject> tpvlist = new ArrayList<TPVObject>(10000);
	
	private static final Logger LOG = LoggerFactory.getLogger(CSVtoTPV.class);
	
	private final List<IObjectListener> listeners = new ArrayList<IObjectListener>(1);
	//Constructores
	public CSVtoTPV(){	
	}
	
	public CSVtoTPV(long sleeptime, String directory){
		this.sleeptime = sleeptime;
		this.setDirectory(directory);
	}
	
	public CSVtoTPV(double speed, long sleeptime, String directory){
		this.speed = speed;
		this.sleeptime = sleeptime;
		this.setDirectory(directory);
	}
	//Fin constructores
	
	//Metodos
	public void read(String file) throws IOException, ParseException {
		//"Segmento","Punto","Latitud (grados)","Longitud (grados)","Altitud (m)","Rumbo (grados)","Precisión (m)","Velocidad (m/s)","Tiempo","Potencia (W)","Cadencia (rpm)","Frecuencia cardíaca (lpm)"
		BufferedReader br = new BufferedReader(new FileReader(this.getDirectory()+file+".csv"));
		String line;
		
		while((line=br.readLine())!=null){
			if(line.isEmpty()) continue;
			if(!Character.isDigit(line.charAt(1))) continue;
			
			TPVObject tpv = new TPVObject();
			
			line = line.replace("\"", "");
			String[] tokens = line.split(",");
			
			double latitude = Double.parseDouble(tokens[2]);
			double longitude = Double.parseDouble(tokens[3]);
			double altitude = Double.parseDouble(tokens[4]);
			double course = Double.parseDouble(tokens[5]);
			
			if(!tokens[7].isEmpty()){
				tpv.setSpeed(Double.parseDouble(tokens[7]));
			}
			if(this.speed == Double.NaN){
				tpv.setSpeed(this.speed);
			}
			//TODO Testing
			//##################################
			/*
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        Date timestamp = formatter.parse(tokens[8]);
	        System.out.println(timestamp);*/
			
			tpv.setTag("Simulator");
			tpv.setDevice(file);
			tpv.setLatitude(latitude);
			tpv.setLongitude(longitude);
			tpv.setAltitude(altitude);
			tpv.setCourse(course);
			//tpv.setTimestamp(timestamp);
			tpv.setMode(ENMEAMode.NotSeen);
			
			tpvlist.add(tpv);
		}
		
		CSVtoTPV.LOG.info("File successfuly readed");	
		br.close();
	}
	
	//Format tu use this method: "Origin-Middle1-Middle2-MiddleN-Destiny"
	public void readSeveral(String file) throws IOException, ParseException {
		
		String[] cities = file.split("-");
		
		for(int i = 0 ; i < (cities.length)-1 ; i++){
			String s = cities[i]+"-"+cities[i+1];
			read(s);
		}
	}
	
	public void send(){
		for(final IGPSObject tpv : this.tpvlist){
			this.handle(tpv);
			
			try {
				Thread.sleep(this.sleeptime);
			} catch (InterruptedException e) {
				CSVtoTPV.LOG.debug("Interrupted while sleeping", e);
			}
		}
	}
	
	public void send(int start, int end){
		for(start++ ; start <= end; start++){
			this.handle(this.tpvlist.get(start));
			
			try {
				Thread.sleep(this.sleeptime);
			} catch (InterruptedException e) {
				CSVtoTPV.LOG.debug("Interrupted while sleeping", e);
			}
		}
	}
	
	public void setSleeptime(long sleeptime){
		this.sleeptime = sleeptime;
	}
	
	public long getSleeptime(){
		return this.sleeptime;
	}
	
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public List<TPVObject> getTPVList(){
		return tpvlist;
	}

	public void addListener(final IObjectListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(final IObjectListener listener) {
		this.listeners.remove(listener);
	}
	
	void handle(final IGPSObject object) {
		if (object instanceof TPVObject) {
			for (final IObjectListener l : this.listeners) {
				l.handleTPV((TPVObject) object);
			}
		}
	}
	//Fin Metodos
}//Fin Clase CSVtoTPV
