package com.dy.sws.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Title DateTimeUtil	
 * @Description		时间转换类
 * @author dy
 * @date 2017年12月16日
 */
public class DateTimeUtil {
	
	//时间格式
	private static final String datetime1 = "yyyy-MM-dd HH:mm:ss";
	private static final String datetime2 = "yyyy-MM-dd HH:mm";
	private static final String datetime3 = "yyyy/MM/dd HH:mm:ss";
	private static final String datetime4 = "yyyy/MM/dd HH:mm";
	private static final String datetime5 = "yyyy年MM月dd日 HH时mm分ss秒";
	private static final String datetime6 = "yyyy年MM月dd日 HH时mm分";
	private static final String datetime7 = "yyyy年MM月dd日 HH:mm:ss";
	private static final String datetime8 = "yyyy年MM月dd日 HH:mm";
	private static final String datetime9 = "yyyyMMddHHmmss";
	private static final String datetime10 = "yyyyMMdd";
	private static final String date1 = "yyyy-MM-dd";
	private static final String date2 = "yyyy/MM/dd";
	private static final String date3 = "yyyy年MM月dd日";
	private static final String[] datetimeFormat = {datetime1,datetime2,datetime3,datetime4,datetime5,datetime6,datetime7,datetime8,date1,date2,date3,datetime9,datetime10};

	/**
	 * @method 						boolean isValidDatetime(String datetime)	
	 * @Description					判断当前传入的字符串是否是时间			
	 * @param datetime				时间字符串
	 * @return 						是：true，否：false	
	 * @author dy
	 * @date 2017年12月16日
	 */
	public static boolean isValidDatetime(String str) {
		if(str != null){
			for (String format : datetimeFormat) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				try {
					// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
					sdf.setLenient(false);
					sdf.parse(str);
					return true;
				} catch (ParseException e) {} 
			}
		}
		return false;
	}
	
	/**
	 * @method 							Date string2Datetime(String datetime)	
	 * @Description						将string转换为Date类型	
	 * @param datetime					string
	 * @return 							转换成功：date，转换失败：null	
	 * @author dy
	 * @date 2017年12月16日
	 */
	public static LocalDateTime string2Datetime(String str) {
		if(str != null){
			for (String format : datetimeFormat) {
				try {
					DateTimeFormatter df = DateTimeFormatter.ofPattern(format);		
					return LocalDateTime.parse(str,df);
				}catch (Exception e) {}
			}
		}
		return null;
	}
	
	/**
	 * @method 							Date string2Date(String datetime)	
	 * @Description						将string转换为Date类型	
	 * @param datetime					string
	 * @return 							转换成功：date，转换失败：null	
	 * @author dy
	 * @date 2017年12月16日
	 */
	public static LocalDate string2Date(String str) {
		if(str != null){
			for (String format : datetimeFormat) {
				try {
					DateTimeFormatter df = DateTimeFormatter.ofPattern(format);		
					return LocalDate.parse(str,df);
				}catch (Exception e) {}
			}
		}
		return null;
	}
	
	/**
	 * @method 						String datetime2String(Date datetime,int format)	
	 * @Description					将时间转换为指定的时间格式的字符串		
	 * @param datetime				时间
	 * @param format				时间格式 ：								
								0："yyyy-MM-dd HH:mm:ss";		1："yyyy-MM-dd HH:mm";
								2："yyyy/MM/dd HH:mm:ss"; 		3："yyyy/MM/dd HH:mm";
								4："yyyy年MM月dd日 HH时mm分ss秒"; 	5："yyyy年MM月dd日 HH时mm分";
								6："yyyy年MM月dd日 HH:mm:ss"; 		7："yyyy年MM月dd日 HH:mm";
								8："yyyy-MM-dd"; 				9："yyyy/MM/dd";
								10："yyyy年MM月dd日";				11:"yyyyMMddHHmmss"
	 * @return 								
	 * @author dy
	 * @date 2017年12月16日
	 */
	public static String datetime2String(LocalDateTime datetime,int format){
		if(datetime != null){
			for (int i=0 ; i<datetimeFormat.length ; i++) {
				if(format == i){
					DateTimeFormatter df = DateTimeFormatter.ofPattern(datetimeFormat[i]);
					return df.format(datetime);
				}
			}
		}
		return null;
	}

	/**
	 * @Description
	 * @param date
	 * @param format 时间格式 ：	8："yyyy-MM-dd"; 				9："yyyy/MM/dd";
								10："yyyy年MM月dd日";	
	 * @return 
	 * @author dy
	 * @date 2019年10月17日
	 */
	public static Object date2String(LocalDate date, int format) {
		if(date != null){
			for (int i=0 ; i<datetimeFormat.length ; i++) {
				if(format == i){
					DateTimeFormatter df = DateTimeFormatter.ofPattern(datetimeFormat[i]);
					return df.format(date);
				}
			}
		}
		return null;
	}
	
}
