package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

public class CSVHandler {
	private String fileName;
	private String csvOut;
	private int status;
	public CSVHandler(String[] keysToOutput, Vector<Properties> vecProps, boolean stripCommas, boolean headerFirstRow){
		csvOut = "";
		status = 0;
		if (keysToOutput == null || vecProps == null){
			status = -1;
			return;
		}
		if (headerFirstRow){
			for(int j = 0;j < keysToOutput.length;j++){
				csvOut += keysToOutput[j];
				if (j != (keysToOutput.length - 1)){
					csvOut += ",";
				}
			}
			csvOut += "\n";
		}
		for(int i = 0;i < vecProps.size();i++){
			for(int j = 0;j < keysToOutput.length;j++){
				Properties props = vecProps.get(i);
				String key = keysToOutput[j];
				String val = props.getProperty(key);
				if (val == null){
					csvOut += "---";
				} else {
					if (stripCommas){
						csvOut += val.replaceAll(",", "");
					} else {
						csvOut += val;
					}
				}
				if (j != (keysToOutput.length - 1)){
					csvOut += ",";
				}
			}
			csvOut += "\n";
		}
	}
	public boolean writeCsvToFile(String fileName){
		boolean result = true;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				result = false;
				return result;
			}
		}
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(csvOut);
			bw.close();
		} catch (IOException e) {
			result = false;
			return result;
		}
		return result;
	}
	public String getCsvOut() {
		return csvOut;
	}
	public int getStatus() {
		return status;
	}
	public String getTodaysDateAndTime() {
		String myDate = "";
		Calendar today = Calendar.getInstance();
		int[] d = new int[] {today.get(Calendar.YEAR), 
				(today.get(Calendar.MONTH) + 1),
				today.get(Calendar.DATE),
				today.get(Calendar.HOUR_OF_DAY),
				today.get(Calendar.MINUTE),
				today.get(Calendar.SECOND)};
		for(int i = 0;i < d.length;i++){
			myDate += (i > 0 && d[i] >= 0 && d[i] < 10)?("0" + Integer.toString(d[i])):(Integer.toString(d[i]));
			switch(i){
			case 0:
			case 1:{
				myDate += "-";
				break;
			}
			case 2:{
				myDate += "_";
				break;
			}
			case 3:
			case 4:{
				myDate += "-";
				break;
			}
			default:{
				break;
			}
			}
		}
		return myDate;
	}
}
