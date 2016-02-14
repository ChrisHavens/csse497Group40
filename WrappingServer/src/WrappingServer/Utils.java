package WrappingServer;

import java.util.Calendar;

public class Utils {
	public static String mainURL="http://s40server.csse.rose-hulman.edu:9200/s40";
	//Utils.mainURL+"
	public static String databaseURL="http://s40server.csse.rose-hulman.edu:9200/database_info";
	
	public static String getCurrentDateTimeAsString(){
		Calendar rightNow = Calendar.getInstance();
		StringBuilder sb=new StringBuilder();
		sb.append(String.format("%04d", rightNow.get(Calendar.YEAR)));
		sb.append("-");
		sb.append(String.format("%02d", rightNow.get(Calendar.MONTH)+1));
		sb.append("-");
		sb.append(String.format("%02d", rightNow.get(Calendar.DAY_OF_MONTH)));
		sb.append(" ");
		sb.append(String.format("%02d", rightNow.get(Calendar.HOUR_OF_DAY)));
		sb.append(":");
		sb.append(String.format("%02d", rightNow.get(Calendar.MINUTE)));
		return sb.toString();
	}
	public static String getCurrentDateAsString(){
		Calendar rightNow = Calendar.getInstance();
		StringBuilder sb=new StringBuilder();
		sb.append(String.format("%04d", rightNow.get(Calendar.YEAR)));
		sb.append("-");
		sb.append(String.format("%02d", rightNow.get(Calendar.MONTH)+1));
		sb.append("-");
		sb.append(String.format("%02d", rightNow.get(Calendar.DAY_OF_MONTH)));
		return sb.toString();
	}

}
