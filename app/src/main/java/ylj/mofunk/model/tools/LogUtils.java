package ylj.mofunk.model.tools;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/21
 *     desc  : ��־��ع�����
 * </pre>
 */
public class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean logSwitch      = true;
    private static boolean log2FileSwitch = false;
    private static char    logFilter      = 'v';
    private static String tag            = "TAG";
    private static String dir            = null;
    private static int     stackIndex     = 0;

    /**
     * ��ʼ������
     * <p>��{@link #getBuilder()}����ѡ��һ</p>
     *
     * @param logSwitch      ��־�ܿ���
     * @param log2FileSwitch ��־д���ļ����أ���Ϊtrue�����Ȩ�� {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}
     * @param logFilter      ������־������{@code v, d, i, w, e}<br>v�������������Ϣ��w��ֻ�������...
     * @param tag            ��ǩ
     */
    public static void init(boolean logSwitch, boolean log2FileSwitch, char logFilter, String tag) {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator;
//        } else {
//            dir = Utils.getContext().getCacheDir().getPath() + File.separator;
//        }
        LogUtils.logSwitch = logSwitch;
        LogUtils.log2FileSwitch = log2FileSwitch;
        LogUtils.logFilter = logFilter;
        LogUtils.tag = tag;
    }

    /**
     * ��ȡLogUtils������
     * <p>��{@link #init(boolean, boolean, char, String)}����ѡ��һ</p>
     *
     * @return Builder����
     */
    public static Builder getBuilder() {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
//        } else {
//            dir = Utils.getContext().getCacheDir().getPath() + File.separator + "log" + File.separator;
//        }
        return new Builder();
    }

    public static class Builder {

        private boolean logSwitch      = true;
        private boolean log2FileSwitch = false;
        private char    logFilter      = 'v';
        private String tag            = "TAG";

        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }

        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setLogFilter(char logFilter) {
            this.logFilter = logFilter;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public void create() {
            LogUtils.logSwitch = logSwitch;
            LogUtils.log2FileSwitch = log2FileSwitch;
            LogUtils.logFilter = logFilter;
            LogUtils.tag = tag;
        }
    }

    /**
     * Verbose��־
     *
     * @param msg ��Ϣ
     */
    public static void v(Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Verbose��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     */
    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Verbose��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     * @param tr  �쳣
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug��־
     *
     * @param msg ��Ϣ
     */
    public static void d(Object msg) {
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     */
    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     * @param tr  �쳣
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info��־
     *
     * @param msg ��Ϣ
     */
    public static void i(Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     */
    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     * @param tr  �쳣
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn��־
     *
     * @param msg ��Ϣ
     */
    public static void w(Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     */
    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     * @param tr  �쳣
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error��־
     *
     * @param msg ��Ϣ
     */
    public static void e(Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     */
    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error��־
     *
     * @param tag ��ǩ
     * @param msg ��Ϣ
     * @param tr  �쳣
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * ����tag, msg�͵ȼ��������־
     *
     * @param tag  ��ǩ
     * @param msg  ��Ϣ
     * @param tr   �쳣
     * @param type ��־����
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (msg == null || msg.isEmpty()) return;
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'e');
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'w');
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'd');
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'i');
            }
            if (log2FileSwitch) {
                log2File(type, generateTag(tag), msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * ����tag, msg�͵ȼ��������־
     *
     * @param tag  ��ǩ
     * @param msg  ��Ϣ
     * @param tr   �쳣
     * @param type ��־����
     */
    private static void printLog(final String tag, final String msg, Throwable tr, char type) {
        final int maxLen = 4000;
        for (int i = 0, len = msg.length(); i * maxLen < len; ++i) {
            String subMsg = msg.substring(i * maxLen, (i + 1) * maxLen < len ? (i + 1) * maxLen : len);
            switch (type) {
                case 'e':
                    Log.e(tag, subMsg, tr);
                    break;
                case 'w':
                    Log.w(tag, subMsg, tr);
                    break;
                case 'd':
                    Log.d(tag, subMsg, tr);
                    break;
                case 'i':
                    Log.i(tag, subMsg, tr);
                    break;
            }
        }
    }

    /**
     * ����־�ļ���д����־
     *
     * @param type ��־����
     * @param tag  ��ǩ
     * @param msg  ��Ϣ
     **/
    private synchronized static void log2File(final char type, final String tag, final String msg) {
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + msg + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(bw);
                }
            }
        }).start();
    }

    /**
     * ����tag
     *
     * @return tag
     */
    private static String generateTag(String tag) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (stackIndex == 0) {
            while (!stacks[stackIndex].getMethodName().equals("generateTag")) {
                ++stackIndex;
            }
            stackIndex += 3;
        }
        StackTraceElement caller = stacks[stackIndex];
        String callerClazzName = caller.getClassName();
        String format = "Tag[" + tag + "] %s[%s, %d]";
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return String.format(format, callerClazzName, caller.getMethodName(), caller.getLineNumber());
    }
}