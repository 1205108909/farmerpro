package com.phenix.util;

import com.phenix.cache.TradingDayCache;
import com.phenix.exception.IllegalDataException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;

public class DateUtil {
	public final static DateTimeFormatter Date_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
	public final static DateTimeFormatter Date_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
	public final static DateTimeFormatter Date_YYYYMMDDHHMMSS2 = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
	public final static DateTimeFormatter Date_YYYYMMDDHHMMSS_SSS = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss:SSS");
	public final static DateTimeFormatter Date_YYYYMMDDHHMMSS_SSS2 = DateTimeFormatter.ofPattern("yyyyMMdd HHmmssSSS");
	public final static DateTimeFormatter Date_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public final static DateTimeFormatter TIME_HHMMSS_SSS = DateTimeFormatter.ofPattern("HH:mm:ssSSS");
	public final static DateTimeFormatter TIME_HHMMSS_SSS2 = DateTimeFormatter.ofPattern("HHmmssSSS");
	public final static DateTimeFormatter TIME_HHMMSS =  DateTimeFormatter.ofPattern("HHmmss");
	public final static DateTimeFormatter TIME_HHMMSS2 =  DateTimeFormatter.ofPattern("HH:mm:ss");
	public final static LocalDateTime MaxValue = LocalDateTime.MAX;
	public final static LocalDateTime MinValue = LocalDateTime.MIN;
	public final static long TIME_0925_AS_INT = LocalTime.of(9, 25).toSecondOfDay();
	public final static long TIME_0930_AS_INT = LocalTime.of(9, 30).toSecondOfDay();
	public final static long TIME_1130_AS_INT = LocalTime.of(11, 30).toSecondOfDay();
	public final static long TIME_1300_AS_INT = LocalTime.of(13, 00).toSecondOfDay();
	public final static long TIME_1500_AS_INT = LocalTime.of(15, 00).toSecondOfDay();
	public final static int LUNCH_BTREAK_DURATION_IN_SECS = 90 * 60;

	public final static Duration DURAION_OF_LUNCHBREAK = Duration.ofSeconds(TIME_1300_AS_INT - TIME_1130_AS_INT);
	
	public static LocalTime getTime(String timeStr_) {
			return LocalTime.parse(timeStr_, TIME_HHMMSS_SSS);
	}
	
	public static LocalTime getTime(String timeStr_, DateTimeFormatter formatter_) {
		return LocalTime.parse(timeStr_, formatter_);
	}
	
	public static LocalTime getTime2(String timeStr_) {
		return LocalTime.parse(timeStr_, TIME_HHMMSS_SSS2);
}
	
	public static LocalDate getDate(String dateStr_) {
		return LocalDate.parse(dateStr_, Date_YYYYMMDD);
	}
	
	/*with million second HH:mm:ss*/
	public static LocalDateTime getDateTime(String dateStr_) {
		return LocalDateTime.parse(dateStr_, Date_YYYYMMDDHHMMSS);
	}
	
	/*with million second HHmmss*/
	public static LocalDateTime getDateTime2(String dateStr_) {
		return LocalDateTime.parse(dateStr_, Date_YYYYMMDDHHMMSS2);
	}	

	public static LocalDateTime getDateTime3(String dateStr_) {
		return LocalDateTime.parse(dateStr_, Date_YYYYMMDDHHMMSS_SSS);
	}
	
	public static LocalDateTime getDateTime4(String dateStr_) {
		return LocalDateTime.parse(dateStr_, Date_YYYYMMDDHHMMSS_SSS2);
	}

	public static String time2str(LocalTime lt_) {
		return lt_.format(TIME_HHMMSS);
	}

	public static String time2Str2(LocalTime lt_) {
		return lt_.format(TIME_HHMMSS2);
	}

	public static String time2str(LocalTime lt_, DateTimeFormatter dtf_) {
		return lt_.format(dtf_);
	}

	public static String dateTime2Str(LocalDateTime dt_) {
		return dt_.format(Date_YYYYMMDDHHMMSS);
	}
	
	public static String dateTime2Str2(LocalDateTime dt_) {
		return dt_.format(Date_YYYYMMDDHHMMSS_SSS);
	}
	
	public static String date2Str(LocalDateTime dt_) {
		return dt_.format(Date_YYYYMMDD);
	}
	
	public static String date2Str(LocalDate dt_) {
		return dt_.format(Date_YYYYMMDD);
	}

	
	//15:00:00 is counted to next start
	public static LocalDateTime getFrom(LocalDateTime t_, Duration d_) {	
		int minute = t_.getMinute();		
		int from = (int)Util.roundQtyDown(minute, d_.toMinutes());		
		LocalDateTime fromDt = t_.withMinute(from).withSecond(0).withNano(0);		
		return fromDt;
	}
	
	//15:00:00 is counted to 
	public static LocalDateTime getTo(LocalDateTime t_, Duration d_) {		
		int minute = t_.getMinute();			
		int to =(int)Util.roundQtyUp(minute + 1, d_.toMinutes());		
		if(to < 60)
			return t_.withMinute(to).withSecond(0).withNano(0);
		else 
			return t_.plusHours(1).withMinute(0).withSecond(0).withNano(0);
	}

	public static LocalTime roundTimeDown(LocalTime t_, int baseInSecs_) {
		LocalTime t = LocalTime.ofSecondOfDay(roundTimeDownInSecs(t_, baseInSecs_));
		return t;
	}

	public static int getTimeResidual(LocalTime t_, int baseInSecs_) {
		return t_.toSecondOfDay() - t_.toSecondOfDay() / baseInSecs_ * baseInSecs_;
	}


	public static int time2Int(LocalTime t_) {
		return t_.getHour() * 10000 + t_.getMinute() * 100 + t_.getSecond();
	}

	public static int roundTimeDownInSecs(LocalTime t_, int baseInSecs_) {
		return t_.toSecondOfDay() / baseInSecs_ * baseInSecs_;
	}

	public static LocalTime minusSeconds(LocalTime t_, int secs_) {
		int tInSecs = t_.toSecondOfDay();
		if(tInSecs < TIME_0925_AS_INT || tInSecs > TIME_1500_AS_INT) {
			throw new IllegalDataException("only[0925-1500] is supported, t1=" + t_);
		}

		int t = tInSecs - secs_;
		if(t > TIME_1130_AS_INT && t < TIME_1300_AS_INT) {
			//return t_.minusSeconds(secs_ + LUNCH_BTREAK_DURATION_IN_SECS);
			return LocalTime.ofSecondOfDay(t - LUNCH_BTREAK_DURATION_IN_SECS);
		} else {
			return LocalTime.ofSecondOfDay(t);
		}
	}

	public static long tradingTimeDiffInSecs(long t1InSecs_, long t2InSecs_) {
		if(t2InSecs_ < t1InSecs_) {
			throw new IllegalDataException(String.format("t2InSecs[%s] should be after t1InSecs[%s]", t2InSecs_, t1InSecs_));
		}
		if(t1InSecs_ < TIME_0925_AS_INT || t2InSecs_ > TIME_1500_AS_INT) {
			throw new IllegalDataException(String.format("only[0925-1500] is supported, t1=[%s], t2=[%s]", LocalTime.ofSecondOfDay(t1InSecs_), LocalTime.ofSecondOfDay(t2InSecs_)));
		}

		long diff = t2InSecs_ - t1InSecs_;
		if(t1InSecs_ <= TIME_1130_AS_INT && t2InSecs_ <= TIME_1130_AS_INT) {
			//good to go
		} else if(t1InSecs_ >= TIME_1300_AS_INT && t2InSecs_ >= TIME_1300_AS_INT) {
			//good to go
		} else if(t1InSecs_ > TIME_1130_AS_INT) {
			if(t2InSecs_ < TIME_1300_AS_INT) {
				diff = 0;
			} else {
				diff -= TIME_1300_AS_INT - t1InSecs_;
			}
		} else if(t1InSecs_ <= TIME_1130_AS_INT) {
			if(t2InSecs_ >= TIME_1130_AS_INT && t2InSecs_ <= TIME_1300_AS_INT) {
				diff -= t2InSecs_ - TIME_1130_AS_INT;
			} else if(t2InSecs_ >= TIME_1300_AS_INT) {
				diff -= TIME_1300_AS_INT - TIME_1130_AS_INT;
			}
		}
		//correct AMBreak
		if(t1InSecs_ >= TIME_0925_AS_INT && t1InSecs_ < TIME_0930_AS_INT) {
			if(t2InSecs_ >= TIME_0930_AS_INT) {
				diff -= TIME_0930_AS_INT - t1InSecs_;
				diff = Math.max(1, diff);
			}
		}

		return diff;
	}

	public final static Comparator<String> REVERSE_DATE_LIST = (e1, e2) -> {
		LocalDate d1 = DateUtil.getDate(e1);
		LocalDate d2 = DateUtil.getDate(e2);
		if (d1.isBefore(d2)) return 1;
		else return -1;
	};
}
