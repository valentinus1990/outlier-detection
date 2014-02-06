package edu.unl.bsm.OutlierDetection.Util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.exception.NullArgumentException;
import org.joda.time.DateTime;

import edu.unl.bsm.OutlierDetection.Entity.PermanentHistoryData;

/**
 * 
 * @author ZhongYin Zhang
 *
 */
public class Utility {
	
	//for debugging
	public static final boolean DEBUG = false;

	/**
	 * convert date format based on specific string
	 * @param str
	 * @return converted date format
	 * @throws ParseException
	 */
	public static Date convertToDate(String str) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MM/dd/yy HH:mm");
		Date date = simpleDateFormat.parse(str);
		
		return date;
}
	

	
	/**
	 * convert date format to MM/dd/yy HH
	 * @param date
	 * @return converted date format
	 * @throws ParseException
	 */
	public static Timestamp converToDateHH(Date date) throws ParseException{
		
		DateTime dt = new DateTime(date);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MM/dd/yy HH");
		Date result = simpleDateFormat.parse(dt.toString("MM/dd/yy HH"));
		
		return new Timestamp(result.getTime());
	}
	
	/**
	 * convert time to HH:mm:ss
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Time converToTimeHHMMSS(Date date) throws ParseException{
		
		
		java.sql.Time time = new Time(date.getTime());
//		System.out.println(time);
		
		return time;
	}
	
	
	/**
	 * convert date format to date only MM/dd/yy
	 * @param date
	 * @return converted date format
	 * @throws ParseException
	 */
	public static Timestamp converToOnlyDate(Date date) throws ParseException{
		
		DateTime dt = new DateTime(date);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MM/dd/yy");
		Date result = simpleDateFormat.parse(dt.toString("MM/dd/yy"));
		
		return new Timestamp(result.getTime());
	}
	
	
	
	/**
	 * print a two dimension array
	 * @param array
	 * @param start
	 * @param end
	 */
	public static void printArray(double[][] array, int start, int end){
		if(array == null || array.length == 0){
			return;
		}
		
		for(int i = start; i < end; i++){
			System.out.println(Arrays.toString(array[i]));
		}
		
	}
	
	
	/**
	 * print a two dimension array
	 * @param array
	 * @return
	 */
	public static String printArray(double[][] array){
		if(array == null || array.length == 0){
			return null;
		}
		String str = "";
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[0].length; j++){
				str += String.format("%.3f ", array[i][j]);
			}
			str += String.format(",");
		}
		
		System.out.println(str);
		
		return str;
	}
	
	
	/**
	 * print a two dimension array in one line
	 * @param array
	 */
	public static void printArraySingleLine(double[][] array){
		if(array == null || array.length == 0){
			return;
		}
		
		for(int i = 0; i < array.length; i++){
			System.out.println(Arrays.toString(array[i]));
		}
		
	}
	
	/**
	 * print an array list based on Object[]
	 * @param list
	 */
	public static void printArrayList(List<Object[]> list){
		for(Object[] obj: list){
			System.out.println(Arrays.toString(obj));
		}
	}
	
	/**
	 * print an array List based on double[]
	 * @param list
	 */
	public static void printArrayListDouble(List<double[]> list){
		for(double[] obj: list){
			System.out.println(Arrays.toString(obj));
		}
	}
	
	
	/**
	 * print a Permanent History data lsit
	 * @param list
	 */
	public static void printArrayListCL(List<PermanentHistoryData> list){
		for(PermanentHistoryData data: list){
			System.out.println(data.getId().getHistDateTime() + ", " + data.getHistValue());
		}
	}
	
	/**
	 * Algorithm for getting max value in a list
	 * @param list
	 * @return
	 */
	public static float getMax(List<Object[]> list){
		if(list == null){
			throw new NullPointerException("List is null");
		}
		
		float max = Float.MIN_VALUE;
		for(int i = 0 ; i < list.size(); i++){
			max = (float)list.get(i)[1] > max ? (float)list.get(i)[1] : max;
		}
		
		return max;
	}
	
	/**
	 * Algorithm for getting Min value in a list
	 * @param list
	 * @return
	 */
	public static float getMin(List<Object[]> list){
		if(list == null){
			throw new NullPointerException("List is null");
		}
		
		float min = Float.MAX_VALUE;
		for(int i = 0 ; i < list.size(); i++){
			min = (float)list.get(i)[1] < min ? (float)list.get(i)[1] : min;
		}
		
		return min;
	}
	
	/**
	 * copy an array based on specific index.
	 * @param arr
	 * @param start
	 * @param end
	 * @return array copy
	 */
	public static double[] copyArray(double[][] arr, int start, int end){
		if(arr == null){
			throw new NullArgumentException();
		}
		
		double[] arrCopy = new double[arr[0].length];
		for(int i = start; i < end; i++){
			for(int j = 0; j < arr[i].length; j++){
				arrCopy[j] = arr[i][j];
			}
		}
		
		return arrCopy;
		
	}
	
	
	/**
	 * print an array
	 * @param arr
	 */
	public static void printArray(double[] arr){
		if(arr == null){
			throw new NullArgumentException();
		}
		
		
		for(double element: arr){
			System.out.print(element+ ", ");
		}
		System.out.println();
		
		
	}

	
	
	/**
	 * convert a list to  a two dimension array
	 * @param list
	 * @return a two dimension array
	 */
	public static double[][] listToArray(List<double[]> list) {
		if(list ==  null){
			throw new NullPointerException();
		}
		
		double[][] result =  new double[list.size()][12];
		for(int i = 0; i < list.size(); i++){
			result[i] =  list.get(i);
		}
		return result;
	}
}
