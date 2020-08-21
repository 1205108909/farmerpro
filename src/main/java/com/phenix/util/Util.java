package com.phenix.util;

import com.google.common.base.CharMatcher;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.phenix.data.OrderSide;
import com.phenix.data.OrderStatus;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.exception.IllegalDataException;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Transaction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.lang3.SerializationUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.jms.MapMessage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Util {
	public final static double DELTA = 0.000001d;
	public final static LocalDate InvalidTradingDay = LocalDate.MIN;
	public final static double DefaultSellPrice = Double.MAX_VALUE;
	public final static double DefaultBuyPrice = Double.MIN_VALUE;

	/*0 is a valid qty such as in an uplimit or downlimit*/
	public final static double DefaultBuyQty = BigDecimal.valueOf(-1).intValue();
	public final static double DefaultSellQty = BigDecimal.valueOf(-1).intValue();

	private final static JexlEngine JEXL_ENGINE = new JexlBuilder().create();
	private final static JexlContext JEXL_CONTEXT = new MapContext();


	public static boolean isValidPrice(double price_) {
		return price_ >= 0 && price_ != Util.DefaultBuyPrice && price_ != Util.DefaultSellPrice;
	}

	public static double defaultPrice(OrderSide side_) {
		return side_ == OrderSide.BUY ? DefaultBuyPrice : DefaultSellPrice;
	}

	public static double defaultQty(OrderSide side_) {
		return side_ == OrderSide.BUY ? DefaultBuyQty : DefaultSellQty;
	}

	public static <T extends Serializable> T deepCopy(T o_) {
		return SerializationUtils.clone(o_);
	}

	public static Path getDir(@SuppressWarnings("rawtypes") Class clazz_) {
		Path path = null;

		try {
			String className = clazz_.getSimpleName() + ".class";
			URI uri = clazz_.getResource(className).toURI();
			System.out.println(uri.toString());
			if(!uri.getScheme().equalsIgnoreCase("jar"))
				path = Paths.get(uri);
			else {
				JarURLConnection con = (JarURLConnection)uri.toURL().openConnection();
				URL url = con.getJarFileURL();
				FileSystem fs = FileSystems.newFileSystem(url.toURI(), null);
				path = fs.getPath(className);
				fs.close();
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

		return path.getParent();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> mapMsg2Map(MapMessage mmsg_) {
		Map<String, String> msg = new HashMap<>();
		try {
			Iterator<String> itr = Iterators.forEnumeration(mmsg_.getMapNames());
			while (itr.hasNext()) {
				String k = itr.next();
				msg.put(k, mmsg_.getString(k));
			}
		} catch (Exception e_) {
			throw new RuntimeException(e_);
		}
		return msg;
	}

	public static double roundPriceNearestTick(double price_, double tickSize_) {
		return Double.isNaN(price_) ? price_ : BigDecimal.valueOf(Math.floor(price_ / tickSize_ + 0.5)).multiply(BigDecimal.valueOf(tickSize_)).doubleValue();
	}

	public static double roundQtyDown(double qty_, double base_) {
		return BigDecimal.valueOf(Math.floor(qty_ / base_)).multiply(BigDecimal.valueOf(base_)).doubleValue();
	}

	public static double roundQtyUp(double qty_, double base_) {
		// return Math.ceil(qty_ / base_) * base_;
		return BigDecimal.valueOf(Math.ceil(qty_ / base_)).multiply(BigDecimal.valueOf(base_)).doubleValue();
	}

	public static double roundQtyNear(double v_, double base_) {
		return roundPriceNearestTick(v_, base_);
	}

	public static double roundQtyNear4Digit(double v_) {
		return Math.round((v_ * 10000)) / 10000d;
	}

	public static double roundQtyNear6Digit(double v_) {
		return Math.round((v_ * 1000000)) / 1000000d;
	}

	public static double roundQtyNear2Digit(double v_) {
		return Math.round((v_ * 100)) / 100d;
	}

	public static boolean isValidTransaction(Transaction t_) {
		return t_.getPrice() != 0; // for sz it is the cancelled trade
									// confirmation
	}

	public static boolean isValidOrderBook(OrderBook b_) {
		return true;
	}

	public static double clamp(double v_, double min_, double max_) {
		return Math.max(min_, Math.min(v_, max_));
	}

	public static boolean isValidSymbol(String mdSymbol_) {
		return mdSymbol_.matches("^[^89a-zA-Z].*") && mdSymbol_.length() != 8;
	}

	public static boolean isOpenStatus(OrderStatus orderStatus_) {
		return orderStatus_ != OrderStatus.FILLED && orderStatus_ != OrderStatus.EXPIRED && orderStatus_ != OrderStatus.CANCELED && orderStatus_ != OrderStatus.REJECTED;
	}

	public static double computeLoss(OrderSide side_, double lastPx_, double avgPx_) {
		int sign = side_ == OrderSide.BUY ? 1 : -1;
		return sign * (lastPx_ - avgPx_) / avgPx_;
	}

	public static boolean isClosedStatus(OrderStatus status_) {
		return !Util.isOpenStatus(status_);
	}

	public static int getOrderStatusPrecedence(OrderStatus status) {
		switch (status) {
		case ACCEPTED_FOR_BIDDING:
			return 1;
		case PENDING_NEW:
		case NEW:
		case REJECTED:
			return 2;
		case REPLACED:
			return 3;
		case PARTIAL_FILLED:
			return 4;
		case CANCELED:
		case EXPIRED:
			return 5;
		case SUSPENDED:
			return 6;
		case STOPPED:
			return 7;
		case FILLED:
			return 8;
		case CALCULATED:
			return 9;
		case DONE_FOR_DAY:
			return 10;
		case PENDING_REPLACE:
			return 11;
		case PENDING_CANCEL:
			return 12;
		default:
			return -1;
		}
	}

	public static boolean isValidLimitPrice(double px_) {
		return px_ != Double.MAX_VALUE && px_ != Double.MIN_VALUE && px_ > 0;
	}

	public static boolean isValidQty(double qty_) {
		return qty_ > 0;
	}

	public static double getRefPx(OrderBook ob_) {
		double refPx = Constants.MiusOne;
		if (ob_ == null) {
			return refPx;
		}

		refPx = ob_.getClosePx();
		if (!Util.isValidLimitPrice(refPx)) {
			refPx = ob_.getLastPx();
		}
		if (!Util.isValidLimitPrice(refPx)) {
			refPx = ob_.getPreClose();
		}

		return refPx;
	}

	public static double[] readDouble(Path p_, int col_) {
		double []d;
		try {
			List<String> l = FileUtils.readLines(p_.toFile());
			d = new double[l.size() - 1];
			for(int i = 1; i < l.size(); i++) {
				String []s = l.get(i).split(",");
				d[i - 1] = Double.parseDouble(s[col_]);
			}
			return d;
		} catch (IOException e) {
			throw new IllegalDataException(e);
		}
	}

	public static String[] readString(Path p_, int col_) {
		String []ss;
		try {
			List<String> l = FileUtils.readLines(p_.toFile());
			ss = new String[l.size() - 1];
			for(int i = 1; i < l.size(); i++) {
				String []s = l.get(i).split(",");
				ss[i - 1] = s[col_];
			}
			return ss;
		} catch (IOException e) {
			throw new IllegalDataException(e);
		}
	}

	public static List<LocalTime> readLocalTime(Path p_, int col_) {
		String []ss = readString(p_, col_);
		return Arrays.stream(ss)
				.map(e -> e.length() == 5 ? "0" + e : e )
				.map(e -> DateUtil.getTime(e, DateUtil.TIME_HHMMSS))
				.collect(Collectors.toList());
	}

	public static int[] readInt(Path p_, int col_) {
		double []d = readDouble(p_, col_);
		return Arrays.stream(d).mapToInt(e -> (int)Math.round(e)).toArray();
	}

	public static int getColCount(Path p_) {
		try {
			List<String> l = FileUtils.readLines(p_.toFile());
			if(l.size() <= 1)
				throw new IllegalDataException("data file has at least 2 lines with 1 line colum header and 1 line data");
			return l.get(1).split(",").length;
		} catch (IOException e) {
			throw new IllegalDataException(e);
		}
	}

	public static List<String> merge(List<String> l1_, List<String>l2_) {
		return merge(l1_, l2_, ',', false);
	}

	public static List<String> merge(List<String> l1_, List<String>l2_, boolean padFlag_) {
		return merge(l1_, l2_, ',', true);
	}

	/**
	 * pad with delimiter
	 * @param l1_
	 * @param l2_
	 * @param delimiter_
	 * @param padFlag_
	 * @return
	 */
	public static List<String> merge(List<String> l1_, List<String>l2_, char delimiter_, boolean padFlag_) {
		Objects.requireNonNull(l1_);
		Objects.requireNonNull(l2_);
		int size = Math.max(l1_.size(), l2_.size());
		List<String> l = new ArrayList<>(size);
		int l1 = l1_.size() > 0 ? CharMatcher.is(delimiter_).countIn(l1_.get(0)) : 0;
		int l2 = l2_.size() > 0 ? CharMatcher.is(delimiter_).countIn(l2_.get(0)) : 0;
		int numOfDelimiter = l1 + l2 + 1;

		for(int i = 0; i < size; i++) {
			String s1 = i < l1_.size() ? l1_.get(i) : "";
			String s2 = i < l2_.size() ? l2_.get(i) : "";
			String s = s1 + delimiter_ + s2;

			//pad with delimiter
			if(padFlag_) {
				int paddedLength = numOfDelimiter - CharMatcher.is(delimiter_).countIn(s);
				if(paddedLength > 0)
					s += String.valueOf(delimiter_).repeat(paddedLength);
			}

			l.add(s);
		}

		return l;
	}

	public static double evaluateAsDouble(String str_) {
		//JEXL_ENGINE.createExpression("3 * 5").evaluate(JEXL_CONTEXT)
		Object o = JEXL_ENGINE.createExpression(str_).evaluate(JEXL_CONTEXT);
		return Double.parseDouble(o.toString());
	}

	public static int evaluateAsInt(String str_) {
		Object o = JEXL_ENGINE.createExpression(str_).evaluate(JEXL_CONTEXT);
		return Integer.parseInt(o.toString());
	}

	public static Element readStrAsXml(String str_) {
		SAXBuilder builder = new SAXBuilder();
		Document doc  = null;

		try {
			doc = builder.build(new StringReader(str_));
		} catch (JDOMException | IOException e_) {
			throw new RuntimeException(e_);
		}

		return doc.getRootElement();
	}

	public static <T> T getValue(Class<T> clazz_, String name_) {
		for(Field f : clazz_.getDeclaredFields()) {
			f.setAccessible(true);
			if(f.getName().equals(name_)) {
				try {
					return (T)f.get(null);
				} catch (IllegalAccessException e_) {
					throw new IllegalDataException(e_);
				}
			}
		}
		return null;
	}

	public static<R, V> Table<R, LocalDate, V> rollingBackOneDay(Table<R, LocalDate, V> table_, V nullValue_) {
		Table<R, LocalDate, V> res = HashBasedTable.create();
		for(R r : table_.rowKeySet()) {
			for (LocalDate c : table_.columnKeySet()) {
				LocalDate pd = c.minusDays(1);
				V v = table_.get(r, pd);
				if(v == null) v = nullValue_;
				res.put(r, c, v);
			}
		}
		return res;
	}

	public static String getDefaultSymbol(Security sec_) {
		String defaultSymbol = null;

		if (sec_.isStock() || sec_.isExchangeTradedFund() || sec_.isConvertibleBond()) {
			if (sec_.getSymbol().endsWith("sh")) {
				defaultSymbol = "default.sh";
			}
			else if (sec_.getSymbol().endsWith("sz")) {
				defaultSymbol = "default.sz";
			}
			else if (sec_.getSymbol().endsWith("hk")) {
				defaultSymbol = "default.hk";
			}
		}
		else if (sec_.isIndexFuture())
		{
			defaultSymbol = "default.IF";
		}
		else if (sec_.isRepo()) {
			if (sec_.getSymbol().endsWith("sh")) {
				defaultSymbol = "default.rpo.sh";
			}
			else if (sec_.getSymbol().endsWith("sz")) {
				defaultSymbol = "default.rpo.sz";
			}
		}
		return defaultSymbol;
	}
}
