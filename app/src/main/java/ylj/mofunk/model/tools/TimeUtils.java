package ylj.mofunk.model.tools;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : ʱ����ع�����
 * </pre>
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * <p>�ڹ������о���ʹ�õ�������ĸ�ʽ�������������Ҫ��һ�����ڵĲ����࣬������־��ʽ��Ҫʹ�� SimpleDateFormat�Ķ����ʽ.</p>
     * ��ʽ���������£� ���ں�ʱ��ģʽ <br>
     * <p>���ں�ʱ���ʽ�����ں�ʱ��ģʽ�ַ���ָ���������ں�ʱ��ģʽ�ַ����У�δ�����ŵ���ĸ 'A' �� 'Z' �� 'a' �� 'z'
     * ������Ϊģʽ��ĸ��������ʾ���ڻ�ʱ���ַ���Ԫ�ء��ı�����ʹ�õ����� (') ��������������н��͡�"''"
     * ��ʾ�����š����������ַ��������ͣ�ֻ���ڸ�ʽ��ʱ�����Ǽ򵥸��Ƶ�����ַ����������ڷ���ʱ�������ַ�������ƥ�䡣
     * </p>
     * ����������ģʽ��ĸ�����������ַ� 'A' �� 'Z' �� 'a' �� 'z' ������������ <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">��ĸ</th>
     * <th align="left">���ڻ�ʱ��Ԫ��</th>
     * <th align="left">��ʾ</th>
     * <th align="left">ʾ��</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era ��־��</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>�� </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>���е��·� </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>���е����� </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>���е����� </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>�·��е����� </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>�����е����� </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm ��� </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>һ���е�Сʱ����0-23�� </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>һ���е�Сʱ����1-24�� </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm �е�Сʱ����0-11�� </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm �е�Сʱ����1-12�� </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>Сʱ�еķ����� </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>�����е����� </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>������ </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>ʱ�� </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>ʱ�� </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 ����
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 �й���׼ʱ��
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 �й���׼ʱ��
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    ������ 2016-08-12 15:44:40 �й���׼ʱ��
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 ��Ԫ at 15:44:40 CST
     *                         K:mm a    3:44 ����
     *               EEE, MMM d, ''yy    ������, ���� 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock ����, �й���׼ʱ��
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.����.12 ��Ԫ 03:44 ����
     *     EEE, d MMM yyyy HH:mm:ss Z    ������, 12 ���� 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    ������ DATE(2016-08-12) TIME(15:44:40) �й���׼ʱ��
     * </pre>
     * ע�⣺SimpleDateFormat�����̰߳�ȫ�ģ��̰߳�ȫ����{@code ThreadLocal<SimpleDateFormat>}
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * ��ʱ���תΪʱ���ַ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis ����ʱ���
     * @return ʱ���ַ���
     */
    public static String millis2String(long millis) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * ��ʱ���תΪʱ���ַ���
     * <p>��ʽΪpattern</p>
     *
     * @param millis  ����ʱ���
     * @param pattern ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * ��ʱ���ַ���תΪʱ���
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return ����ʱ���
     */
    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_PATTERN);
    }

    /**
     * ��ʱ���ַ���תΪʱ���
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return ����ʱ���
     */
    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * ��ʱ���ַ���תΪDate����
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return Date����
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * ��ʱ���ַ���תΪDate����
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return Date����
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2Millis(time, pattern));
    }

    /**
     * ��Date����תΪʱ���ַ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date����ʱ��
     * @return ʱ���ַ���
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }

    /**
     * ��Date����תΪʱ���ַ���
     * <p>��ʽΪpattern</p>
     *
     * @param date    Date����ʱ��
     * @param pattern ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * ��Date����תΪʱ���
     *
     * @param date Date����ʱ��
     * @return ����ʱ���
     */
    public static long date2Millis(Date date) {
        return date.getTime();
    }

    /**
     * ��ʱ���תΪDate����
     *
     * @param millis ����ʱ���
     * @return Date����ʱ��
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     * <p>time0��time1��ʽ��Ϊyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 ʱ���ַ���0
     * @param time1 ʱ���ַ���1
     * @param unit  ��λ����
     *              <ul>
     *              <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *              <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *              <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *              <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *              <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *              </ul>
     * @return unitʱ���
     */
    public static long getTimeSpan(String time0, String time1, ConstUtils.TimeUnit unit) {
        return getTimeSpan(time0, time1, unit, DEFAULT_PATTERN);
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     * <p>time0��time1��ʽ��Ϊformat</p>
     *
     * @param time0   ʱ���ַ���0
     * @param time1   ʱ���ַ���1
     * @param unit    ��λ����
     *                <ul>
     *                <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *                <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *                <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *                </ul>
     * @param pattern ʱ���ʽ
     * @return unitʱ���
     */
    public static long getTimeSpan(String time0, String time1, ConstUtils.TimeUnit unit, String pattern) {
        return ConvertUtils.millis2TimeSpan(Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)), unit);
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     *
     * @param date0 Date����ʱ��0
     * @param date1 Date����ʱ��1
     * @param unit  ��λ����
     *              <ul>
     *              <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *              <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *              <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *              <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *              <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *              </ul>
     * @return unitʱ���
     */
    public static long getTimeSpan(Date date0, Date date1, ConstUtils.TimeUnit unit) {
        return ConvertUtils.millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * ��ȡ����ʱ����λ��unit��
     *
     * @param millis0 ����ʱ���0
     * @param millis1 ����ʱ���1
     * @param unit    ��λ����
     *                <ul>
     *                <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *                <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *                <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *                </ul>
     * @return unitʱ���
     */
    public static long getTimeSpan(long millis0, long millis1, ConstUtils.TimeUnit unit) {
        return ConvertUtils.millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * ��ȡ����������ʱ���
     * <p>time0��time1��ʽ��Ϊyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0     ʱ���ַ���0
     * @param time1     ʱ���ַ���1
     * @param precision ����
     *                  <p>precision = 0������null</p>
     *                  <p>precision = 1��������</p>
     *                  <p>precision = 2���������Сʱ</p>
     *                  <p>precision = 3�������졢Сʱ�ͷ���</p>
     *                  <p>precision = 4�������졢Сʱ�����Ӻ���</p>
     *                  <p>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</p>
     * @return ����������ʱ���
     */
    public static String getFitTimeSpan(String time0, String time1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(string2Millis(time0, DEFAULT_PATTERN) - string2Millis(time1, DEFAULT_PATTERN)), precision);
    }

    /**
     * ��ȡ����������ʱ���
     * <p>time0��time1��ʽ��Ϊpattern</p>
     *
     * @param time0     ʱ���ַ���0
     * @param time1     ʱ���ַ���1
     * @param precision ����
     *                  <p>precision = 0������null</p>
     *                  <p>precision = 1��������</p>
     *                  <p>precision = 2���������Сʱ</p>
     *                  <p>precision = 3�������졢Сʱ�ͷ���</p>
     *                  <p>precision = 4�������졢Сʱ�����Ӻ���</p>
     *                  <p>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</p>
     * @param pattern   ʱ���ʽ
     * @return ����������ʱ���
     */
    public static String getFitTimeSpan(String time0, String time1, int precision, String pattern) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)), precision);
    }

    /**
     * ��ȡ����������ʱ���
     *
     * @param date0     Date����ʱ��0
     * @param date1     Date����ʱ��1
     * @param precision ����
     *                  <p>precision = 0������null</p>
     *                  <p>precision = 1��������</p>
     *                  <p>precision = 2���������Сʱ</p>
     *                  <p>precision = 3�������졢Сʱ�ͷ���</p>
     *                  <p>precision = 4�������졢Сʱ�����Ӻ���</p>
     *                  <p>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</p>
     * @return ����������ʱ���
     */
    public static String getFitTimeSpan(Date date0, Date date1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * ��ȡ����������ʱ���
     *
     * @param millis0   ����ʱ���1
     * @param millis1   ����ʱ���2
     * @param precision ����
     *                  <p>precision = 0������null</p>
     *                  <p>precision = 1��������</p>
     *                  <p>precision = 2���������Сʱ</p>
     *                  <p>precision = 3�������졢Сʱ�ͷ���</p>
     *                  <p>precision = 4�������졢Сʱ�����Ӻ���</p>
     *                  <p>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</p>
     * @return ����������ʱ���
     */
    public static String getFitTimeSpan(long millis0, long millis1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * ��ȡ��ǰ����ʱ���
     *
     * @return ����ʱ���
     */
    public static long getNowTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * ��ȡ��ǰʱ���ַ���
     * <p>��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @return ʱ���ַ���
     */
    public static String getNowTimeString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_PATTERN);
    }

    /**
     * ��ȡ��ǰʱ���ַ���
     * <p>��ʽΪpattern</p>
     *
     * @param pattern ʱ���ʽ
     * @return ʱ���ַ���
     */
    public static String getNowTimeString(String pattern) {
        return millis2String(System.currentTimeMillis(), pattern);
    }

    /**
     * ��ȡ��ǰDate
     *
     * @return Date����ʱ��
     */
    public static Date getNowTimeDate() {
        return new Date();
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @param unit ��λ����
     *             <ul>
     *             <li>{@link ConstUtils.TimeUnit#MSEC}:����</li>
     *             <li>{@link ConstUtils.TimeUnit#SEC }:��</li>
     *             <li>{@link ConstUtils.TimeUnit#MIN }:��</li>
     *             <li>{@link ConstUtils.TimeUnit#HOUR}:Сʱ</li>
     *             <li>{@link ConstUtils.TimeUnit#DAY }:��</li>
     *             </ul>
     * @return unitʱ���
     */
    public static long getTimeSpanByNow(String time, ConstUtils.TimeUnit unit) {
        return getTimeSpan(getNowTimeString(), time, unit, DEFAULT_PATTERN);
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param unit    ��λ����
     *                <ul>
     *                <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *                <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *                <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *                <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *                </ul>
     * @param pattern ʱ���ʽ
     * @return unitʱ���
     */
    public static long getTimeSpanByNow(String time, ConstUtils.TimeUnit unit, String pattern) {
        return getTimeSpan(getNowTimeString(), time, unit, pattern);
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     *
     * @param date Date����ʱ��
     * @param unit ��λ����
     *             <ul>
     *             <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *             <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *             <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *             <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *             <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *             </ul>
     * @return unitʱ���
     */
    public static long getTimeSpanByNow(Date date, ConstUtils.TimeUnit unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
     *
     * @param millis ����ʱ���
     * @param unit   ��λ����
     *               <ul>
     *               <li>{@link ConstUtils.TimeUnit#MSEC}: ����</li>
     *               <li>{@link ConstUtils.TimeUnit#SEC }: ��</li>
     *               <li>{@link ConstUtils.TimeUnit#MIN }: ��</li>
     *               <li>{@link ConstUtils.TimeUnit#HOUR}: Сʱ</li>
     *               <li>{@link ConstUtils.TimeUnit#DAY }: ��</li>
     *               </ul>
     * @return unitʱ���
     */
    public static long getTimeSpanByNow(long millis, ConstUtils.TimeUnit unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * ��ȡ�������뵱ǰʱ��Ĳ�
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time      ʱ���ַ���
     * @param precision ����
     *                  <ul>
     *                  <li>precision = 0������null</li>
     *                  <li>precision = 1��������</li>
     *                  <li>precision = 2���������Сʱ</li>
     *                  <li>precision = 3�������졢Сʱ�ͷ���</li>
     *                  <li>precision = 4�������졢Сʱ�����Ӻ���</li>
     *                  <li>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</li>
     *                  </ul>
     * @return �������뵱ǰʱ��Ĳ�
     */
    public static String getFitTimeSpanByNow(String time, int precision) {
        return getFitTimeSpan(getNowTimeString(), time, precision, DEFAULT_PATTERN);
    }

    /**
     * ��ȡ�������뵱ǰʱ��Ĳ�
     * <p>time��ʽΪpattern</p>
     *
     * @param time      ʱ���ַ���
     * @param precision ����
     * @param pattern   ʱ���ʽ
     *                  <ul>
     *                  <li>precision = 0������null</li>
     *                  <li>precision = 1��������</li>
     *                  <li>precision = 2���������Сʱ</li>
     *                  <li>precision = 3�������졢Сʱ�ͷ���</li>
     *                  <li>precision = 4�������졢Сʱ�����Ӻ���</li>
     *                  <li>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</li>
     *                  </ul>
     * @return �������뵱ǰʱ��Ĳ�
     */
    public static String getFitTimeSpanByNow(String time, int precision, String pattern) {
        return getFitTimeSpan(getNowTimeString(), time, precision, pattern);
    }

    /**
     * ��ȡ�������뵱ǰʱ��Ĳ�
     *
     * @param date      Date����ʱ��
     * @param precision ����
     *                  <ul>
     *                  <li>precision = 0������null</li>
     *                  <li>precision = 1��������</li>
     *                  <li>precision = 2���������Сʱ</li>
     *                  <li>precision = 3�������졢Сʱ�ͷ���</li>
     *                  <li>precision = 4�������졢Сʱ�����Ӻ���</li>
     *                  <li>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</li>
     *                  </ul>
     * @return �������뵱ǰʱ��Ĳ�
     */
    public static String getFitTimeSpanByNow(Date date, int precision) {
        return getFitTimeSpan(getNowTimeDate(), date, precision);
    }

    /**
     * ��ȡ�������뵱ǰʱ��Ĳ�
     *
     * @param millis    ����ʱ���
     * @param precision ����
     *                  <ul>
     *                  <li>precision = 0������null</li>
     *                  <li>precision = 1��������</li>
     *                  <li>precision = 2���������Сʱ</li>
     *                  <li>precision = 3�������졢Сʱ�ͷ���</li>
     *                  <li>precision = 4�������졢Сʱ�����Ӻ���</li>
     *                  <li>precision &gt;= 5�������졢Сʱ�����ӡ���ͺ���</li>
     *                  </ul>
     * @return �������뵱ǰʱ��Ĳ�
     */
    public static String getFitTimeSpanByNow(long millis, int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * ��ȡ�Ѻ����뵱ǰʱ��Ĳ�
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return �Ѻ����뵱ǰʱ��Ĳ�
     * <ul>
     * <li>���С��1�����ڣ���ʾ�ո�</li>
     * <li>�����1�����ڣ���ʾXXX��ǰ</li>
     * <li>�����1Сʱ�ڣ���ʾXXX����ǰ</li>
     * <li>�����1Сʱ��Ľ����ڣ���ʾ����15:32</li>
     * <li>���������ģ���ʾ����15:32</li>
     * <li>������ʾ��2016-10-15</li>
     * <li>ʱ�䲻�Ϸ������ȫ�����ں�ʱ����Ϣ���������� ʮ�� 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_PATTERN);
    }

    /**
     * ��ȡ�Ѻ����뵱ǰʱ��Ĳ�
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return �Ѻ����뵱ǰʱ��Ĳ�
     * <ul>
     * <li>���С��1�����ڣ���ʾ�ո�</li>
     * <li>�����1�����ڣ���ʾXXX��ǰ</li>
     * <li>�����1Сʱ�ڣ���ʾXXX����ǰ</li>
     * <li>�����1Сʱ��Ľ����ڣ���ʾ����15:32</li>
     * <li>���������ģ���ʾ����15:32</li>
     * <li>������ʾ��2016-10-15</li>
     * <li>ʱ�䲻�Ϸ������ȫ�����ں�ʱ����Ϣ���������� ʮ�� 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time, String pattern) {
        return getFriendlyTimeSpanByNow(string2Millis(time, pattern));
    }

    /**
     * ��ȡ�Ѻ����뵱ǰʱ��Ĳ�
     *
     * @param date Date����ʱ��
     * @return �Ѻ����뵱ǰʱ��Ĳ�
     * <ul>
     * <li>���С��1�����ڣ���ʾ�ո�</li>
     * <li>�����1�����ڣ���ʾXXX��ǰ</li>
     * <li>�����1Сʱ�ڣ���ʾXXX����ǰ</li>
     * <li>�����1Сʱ��Ľ����ڣ���ʾ����15:32</li>
     * <li>���������ģ���ʾ����15:32</li>
     * <li>������ʾ��2016-10-15</li>
     * <li>ʱ�䲻�Ϸ������ȫ�����ں�ʱ����Ϣ���������� ʮ�� 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * ��ȡ�Ѻ����뵱ǰʱ��Ĳ�
     *
     * @param millis ����ʱ���
     * @return �Ѻ����뵱ǰʱ��Ĳ�
     * <ul>
     * <li>���С��1�����ڣ���ʾ�ո�</li>
     * <li>�����1�����ڣ���ʾXXX��ǰ</li>
     * <li>�����1Сʱ�ڣ���ʾXXX����ǰ</li>
     * <li>�����1Сʱ��Ľ����ڣ���ʾ����15:32</li>
     * <li>���������ģ���ʾ����15:32</li>
     * <li>������ʾ��2016-10-15</li>
     * <li>ʱ�䲻�Ϸ������ȫ�����ں�ʱ����Ϣ���������� ʮ�� 27 14:21:20 CST 2007</li>
     * </ul>
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return String.format("%tc", millis);// U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        if (span < 1000) {
            return "�ո�";
        } else if (span < ConstUtils.MIN) {
            return String.format("%d��ǰ", span / ConstUtils.SEC);
        } else if (span < ConstUtils.HOUR) {
            return String.format("%d����ǰ", span / ConstUtils.MIN);
        }
        // ��ȡ����00:00
        long wee = (now / ConstUtils.DAY) * ConstUtils.DAY - 8 * ConstUtils.HOUR;
        if (millis >= wee) {
            return String.format("����%tR", millis);
        } else if (millis >= wee - ConstUtils.DAY) {
            return String.format("����%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * �ж��Ƿ�ͬһ��
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSameDay(String time) {
        return isSameDay(string2Millis(time, DEFAULT_PATTERN));
    }

    /**
     * �ж��Ƿ�ͬһ��
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSameDay(String time, String pattern) {
        return isSameDay(string2Millis(time, pattern));
    }

    /**
     * �ж��Ƿ�ͬһ��
     *
     * @param date Date����ʱ��
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSameDay(Date date) {
        return isSameDay(date.getTime());
    }

    /**
     * �ж��Ƿ�ͬһ��
     *
     * @param millis ����ʱ���
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSameDay(long millis) {
        long wee = (System.currentTimeMillis() / ConstUtils.DAY) * ConstUtils.DAY - 8 * ConstUtils.HOUR;
        return millis >= wee && millis < wee + ConstUtils.DAY;
    }

    /**
     * �ж��Ƿ�����
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(String time) {
        return isLeapYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * �ж��Ƿ�����
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(String time, String pattern) {
        return isLeapYear(string2Date(time, pattern));
    }

    /**
     * �ж��Ƿ�����
     *
     * @param date Date����ʱ��
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * �ж��Ƿ�����
     *
     * @param millis ����ʱ���
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * �ж��Ƿ�����
     *
     * @param year ���
     * @return {@code true}: ����<br>{@code false}: ƽ��
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * ��ȡ����
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return ����
     */
    public static String getWeek(String time) {
        return getWeek(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ����
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return ����
     */
    public static String getWeek(String time, String pattern) {
        return getWeek(string2Date(time, pattern));
    }

    /**
     * ��ȡ����
     *
     * @param date Date����ʱ��
     * @return ����
     */
    public static String getWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    /**
     * ��ȡ����
     *
     * @param millis ����ʱ���
     * @return ����
     */
    public static String getWeek(long millis) {
        return getWeek(new Date(millis));
    }

    /**
     * ��ȡ����
     * <p>ע�⣺���յ�Index����1������Ϊ7</p>
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        return getWeekIndex(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ����
     * <p>ע�⣺���յ�Index����1������Ϊ7</p>
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return 1...7
     */
    public static int getWeekIndex(String time, String pattern) {
        return getWeekIndex(string2Date(time, pattern));
    }

    /**
     * ��ȡ����
     * <p>ע�⣺���յ�Index����1������Ϊ7</p>
     *
     * @param date Date����ʱ��
     * @return 1...7
     */
    public static int getWeekIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * ��ȡ����
     * <p>ע�⣺���յ�Index����1������Ϊ7</p>
     *
     * @param millis ����ʱ���
     * @return 1...7
     */
    public static int getWeekIndex(long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * ��ȡ�·��еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ�·��еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, String pattern) {
        return getWeekOfMonth(string2Date(time, pattern));
    }

    /**
     * ��ȡ�·��еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     *
     * @param date Date����ʱ��
     * @return 1...5
     */
    public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * ��ȡ�·��еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     *
     * @param millis ����ʱ���
     * @return 1...5
     */
    public static int getWeekOfMonth(long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * ��ȡ����еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ����еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return 1...54
     */
    public static int getWeekOfYear(String time, String pattern) {
        return getWeekOfYear(string2Date(time, pattern));
    }

    /**
     * ��ȡ����еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     *
     * @param date Date����ʱ��
     * @return 1...54
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * ��ȡ����еĵڼ���
     * <p>ע�⣺�������ղ����µ�һ�ܵĿ�ʼ</p>
     *
     * @param millis ����ʱ���
     * @return 1...54
     */
    public static int getWeekOfYear(long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

    private static final String[] CHINESE_ZODIAC = {"��", "��", "��", "��", "��", "ţ", "��", "��", "��", "��", "��", "��"};

    /**
     * ��ȡ��Ф
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return ��Ф
     */
    public static String getChineseZodiac(String time) {
        return getChineseZodiac(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ��Ф
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return ��Ф
     */
    public static String getChineseZodiac(String time, String pattern) {
        return getChineseZodiac(string2Date(time, pattern));
    }

    /**
     * ��ȡ��Ф
     *
     * @param date Date����ʱ��
     * @return ��Ф
     */
    public static String getChineseZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * ��ȡ��Ф
     *
     * @param millis ����ʱ���
     * @return ��Ф
     */
    public static String getChineseZodiac(long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * ��ȡ��Ф
     *
     * @param year ��
     * @return ��Ф
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    private static final String[] ZODIAC       = {"ˮƿ��", "˫����", "������", "��ţ��", "˫����", "��з��", "ʨ����", "��Ů��", "�����", "��Ы��", "������", "ħ����"};
    private static final int[]    ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};

    /**
     * ��ȡ����
     * <p>time��ʽΪyyyy-MM-dd HH:mm:ss</p>
     *
     * @param time ʱ���ַ���
     * @return ��Ф
     */
    public static String getZodiac(String time) {
        return getZodiac(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * ��ȡ����
     * <p>time��ʽΪpattern</p>
     *
     * @param time    ʱ���ַ���
     * @param pattern ʱ���ʽ
     * @return ��Ф
     */
    public static String getZodiac(String time, String pattern) {
        return getZodiac(string2Date(time, pattern));
    }

    /**
     * ��ȡ����
     *
     * @param date Date����ʱ��
     * @return ����
     */
    public static String getZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * ��ȡ����
     *
     * @param millis ����ʱ���
     * @return ����
     */
    public static String getZodiac(long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * ��ȡ����
     *
     * @param month ��
     * @param day   ��
     * @return ����
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }
}