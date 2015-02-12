package CSVtoTPV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import de.taimos.gpsd4java.types.TPVObject;

public class CSVtoTPV {
	
	private TPVObject tpv;
	
	public CSVtoTPV (){
		tpv = new TPVObject();
	}
	
	public void read(File file) throws IOException, ParseException {
		//"Segmento","Punto","Latitud (grados)","Longitud (grados)","Altitud (m)","Rumbo (grados)","Precisión (m)","Velocidad (m/s)","Tiempo","Potencia (W)","Cadencia (rpm)","Frecuencia cardíaca (lpm)"
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		while((line=br.readLine())!=null){
			if(line.isEmpty()) continue;
			if(!Character.isDigit(line.charAt(0))) continue;
			
			String[] tokens = line.split(",");
			double latitude = Double.parseDouble(tokens[2]);
			double longitude = Double.parseDouble(tokens[3]);
			double altitude = Double.parseDouble(tokens[4]);
			double course = Double.parseDouble(tokens[5]);
			double speed = Double.parseDouble(tokens[7]);
			double timestamp = Double.parseDouble(tokens[8]);
			
			tpv.setLatitude(latitude);
			tpv.setLongitude(longitude);
			tpv.setAltitude(altitude);
			tpv.setCourse(course);
			tpv.setSpeed(speed);
			tpv.setTimestamp(timestamp);
		}
		
		br.close();
	}
	
	public TPVObject getTPV(){
		return tpv;
	}

}
