package com.phenix.farmer;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.phenix.data.IndexFuture;
import com.phenix.data.OrderSide;
import com.phenix.data.Security;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestRunner {
    public static void main(String[] args) throws IOException {
		/*CountDownLatch latch = new CountDownLatch(3);
		long ct = System.currentTimeMillis();

		for(int i = 0; i < 3; i++) {
			long xx = 0;
			if (i == 1) {
				new Thread(() -> {
					try {
						Thread.currentThread().sleep(300);
						System.out.println(Thread.currentThread().getName() + "done");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					latch.countDown();
				}).start();
			} else new Thread(() -> {
				try {
					Thread.currentThread().sleep(400);
					System.out.println(Thread.currentThread().getName() + "done");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}).start();
		}

		try {
			latch.await();
			System.out.println("all 3 thread are done after " + (System.currentTimeMillis() - ct) + " ms");
			System.out.println(Thread.currentThread().getName() + "*" + latch.getCount());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();*/
		/*LocalDateTime ldt1 = DateUtil.getDateTime("20170717 14:50:22");//Date_YYYYMMDDHHMMSS
		LocalDateTime ldt2 = DateUtil.getDateTime("20170719 13:10:22");
		//System.out.println(Duration.between(ldt1.toLocalDate(), ldt2.toLocalDate()).toDays());
		System.out.println(ChronoUnit.DAYS.between(ldt1.toLocalDate(), ldt2.toLocalDate()));
		try {
			FileUtils.writeStringToFile(new File("c:/phenix/chinese_test.csv"), "好的", "GB2312");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
//		String path = "G:\\transaction_matlab_20160930\\orderqueue20171101-20180515";
//		File[] files = new File(path).listFiles();
//
//		for (File f1 : files) {
//			if (f1.getName().contains("queue") && !f1.getName().contains("orderqueue")) {
//				String newName = f1.getName().replace("queue", "orderqueue");
//
//				//newName = newName.replace("tran", "trans");
//				f1.renameTo(new File(f1.getParent() + "/" + newName));
//			}
//		}
        System.out.println("000905.sh".matches("^000300.*|^2000905.*"));
        System.out.println("000905.sh".matches("000300.*|2000905.*"));
        double[] x = new double[]{1, -1, -3, 2};
        System.out.println(Arrays.toString(Arrays.stream(x).map(e -> Math.abs(e)).toArray()));
        System.out.println((double) 3 / 2);
        System.out.println(5.2 % 4.0);

        Stopwatch s = Stopwatch.createStarted();
        LocalTime t = LocalTime.now();
        long xz = t.toNanoOfDay();

        for (int i = 0; i <= 1000000; i++) {
            xz++;
        }

        System.out.println(s.stop());
        System.out.println(xz);

        double c1 = 0.00139999;
        System.out.println(Util.roundQtyNear4Digit(c1));
        System.out.println(Util.roundQtyNear4Digit(0.00134999));
        System.out.println(Util.roundQtyNear4Digit(0.00135001));

        System.out.println(0.002 * -1);
        //System.out.println(CurvePath.UNKNOWN.hashCode());

        System.out.println(Math.max(Double.NEGATIVE_INFINITY, 0.1));

        System.out.println(1000000 * 0.00000002);
        System.out.println(Math.round(1.023659d * 10000L));
        System.out.println(FarmerDataManager.getCommission(Security.INDEX_HS300, OrderSide.BUY, false));
        System.out.println(FarmerDataManager.getCommission(Security.INDEX_SZ50, OrderSide.BUY, false));
        System.out.println(FarmerDataManager.getCommission(Security.INDEX_ZZ500, OrderSide.BUY, false));
        System.out.println(FarmerDataManager.getCommission(Security.INDEX_ZZ1000, OrderSide.BUY, false));

        System.out.println(FarmerDataManager.getInstance().getTickSize("000905.sh"));
        System.out.println(36000 >= 10d * 60d * 60d);
        System.out.println(0.31034482758620696 >= ((double) (2 / 5)));
        System.out.println(0.1 * 2 / 5);

        LinkedHashMap<String, String> test = new LinkedHashMap<>();
        test.put("1", "v1");
        test.put("2", "v2");
        System.out.println(test.values().stream().filter(e -> e.equals("3")).collect(Collectors.toList()).size());
        System.out.println(SignalMode.valueOf("MANUAL"));

        JexlEngine jexl = new JexlBuilder().create();
        JexlContext jc = new MapContext();
        System.out.println(jexl.createExpression("3 * 5").evaluate(jc).getClass());

        Class<?> clazz1 = Integer.class;
        Class<?> clazz2 = Integer.valueOf("3").getClass();
        System.out.println(clazz1 == clazz2);

        Element ele = Util.readStrAsXml("<Security>" +
                "<symbol>000300.sh|000905.sh</symbol>" +
                "<type>INDEX</type>" +
                "</Security>");
        System.out.println(new XMLOutputter().outputString(ele));

        //test performance
        LinkedListMultimap<String, Integer> lmap = LinkedListMultimap.create();
        for (int i = 0; i < 100; i++) {
            lmap.put("test", i);
        }
        Stopwatch sw = Stopwatch.createStarted();
        int d = 0;
        for (int i = 0; i < 100000; i++) {
            List<Integer> l = lmap.get("test");
            d = l.get(l.size() - 1);
        }

        System.out.println(sw.stop());
        System.out.println(d);

        List<Integer> v = Lists.newArrayList(1, 2, 3);
        ListIterator<Integer> lv = v.listIterator(v.size());
        while (lv.hasPrevious()) {
            System.out.println(lv.previous());
        }
        Iterator i = v.iterator();
        while ((i.hasNext())) {
            System.out.println(i.next());
        }

        System.out.println(FarmerDataManager.getInstance().getOrderBookXml());
        ArrayListMultimap<String, String> lists = ArrayListMultimap.create(10, 2);
        lists.get("123").add("2");
        lists.get("123").add("3");
        System.out.println(lists.get("123").size());
        System.out.println(lists.size());
        System.out.println(lists.keySet().size());

        String o = null;
        List<String> xx = Lists.newArrayList(o);
        System.out.println(xx.size());
        System.out.println(xx.isEmpty());

        System.out.println(Security.INDEX_HS300.getBasketID());
        System.out.println(IndexFuture.INDEX_FUTURE_IF1904.getBasketID());
        System.out.println(((Security) IndexFuture.INDEX_FUTURE_IF1904).getBasketID());
        System.out.println(Security.INDEX_HS300.hashCode());
        System.out.println(IndexFuture.INDEX_FUTURE_IF1904.hashCode());

        List<Integer> listIs = new ArrayList<>();
        for (int j = 0; j < 10000; j++) {
            listIs.add(j);
        }

        long curTime = System.nanoTime();
        for (int j = 0; j < 10000; j++) {
            if (j > 5000) {
                Integer ttt = listIs.get(j);
            }
        }
        long curTime2 = System.nanoTime();
        System.out.println(curTime2 - curTime);

        Object[] copy = Arrays.<Object>copyOf(listIs.toArray(), listIs.size());
        System.out.println(System.nanoTime() - curTime2);

        System.out.println(0.375 * 3);
        System.out.println("xxx" + -0.02);

        TreeMap<Integer, Integer> ints = new TreeMap<>();
        for (int ii = 0; ii < 500; ii++) {
            ints.put(ii, ii);
        }
        curTime = System.nanoTime();
        for (int ii = 0; ii < 240; ii++) {
            ints.lastEntry().getValue();
        }
        System.out.println(System.nanoTime() - curTime);
        System.out.println(FarmerDataManager.getMarginRate("000852.sh"));
        System.out.println(FarmerDataManager.getMarginRate("000300.sh"));
        System.out.println(FarmerDataManager.getMarginRate("000016.sh"));
        System.out.println(FarmerDataManager.getMarginRate("000905.sh"));

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(100000);
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");

        try {
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
            System.out.println(queue.poll(1, TimeUnit.SECONDS));
            System.out.println(queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FileUtils.writeStringToFile(new File("d:/feiyang.csv"), "1,2,31\n", true);
        FileUtils.writeStringToFile(new File("d:/feiyang.csv"), "3.1,2,31\n", true);
        System.out.println(3.0 * 2 - Double.NaN);
    }
}
