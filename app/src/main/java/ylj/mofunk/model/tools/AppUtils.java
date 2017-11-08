package ylj.mofunk.model.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : App��ع�����
 * </pre>
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * �ж�App�Ƿ�װ
     *
     * @param context     ������
     * @param packageName ����
     * @return {@code true}: �Ѱ�װ<br>{@code false}: δ��װ
     */
    public static boolean isInstallApp(Context context, String packageName) {
        return !StringUtils.isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * ��װApp(֧��6.0)
     *
     * @param context  ������
     * @param filePath �ļ�·��
     */
    public static void installApp(Context context, String filePath) {
        installApp(context, FileUtils.getFileByPath(filePath));
    }

    /**
     * ��װApp��֧��6.0��
     *
     * @param context ������
     * @param file    �ļ�
     */
    public static void installApp(Context context, File file) {
        if (!FileUtils.isFileExists(file)) return;
        context.startActivity(IntentUtils.getInstallAppIntent(file));
    }

    /**
     * ��װApp��֧��6.0��
     *
     * @param activity    activity
     * @param filePath    �ļ�·��
     * @param requestCode ����ֵ
     */
    public static void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), requestCode);
    }

    /**
     * ��װApp(֧��6.0)
     *
     * @param activity    activity
     * @param file        �ļ�
     * @param requestCode ����ֵ
     */
    public static void installApp(Activity activity, File file, int requestCode) {
        if (!FileUtils.isFileExists(file)) return;
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file), requestCode);
    }

    /**
     * ��Ĭ��װApp
     * <p>��root�����Ȩ�� {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath �ļ�·��
     * @return {@code true}: ��װ�ɹ�<br>{@code false}: ��װʧ��
     */
    public static boolean installAppSilent(String filePath) {
        File file = FileUtils.getFileByPath(filePath);
        if (!FileUtils.isFileExists(file)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(Utils.getContext()), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * ж��App
     *
     * @param context     ������
     * @param packageName ����
     */
    public static void uninstallApp(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return;
        context.startActivity(IntentUtils.getUninstallAppIntent(packageName));
    }

    /**
     * ж��App
     *
     * @param activity    activity
     * @param packageName ����
     * @param requestCode ����ֵ
     */
    public static void uninstallApp(Activity activity, String packageName, int requestCode) {
        if (StringUtils.isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * ��Ĭж��App
     * <p>��root�����Ȩ�� {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param context     ������
     * @param packageName ����
     * @param isKeepData  �Ƿ�������
     * @return {@code true}: ж�سɹ�<br>{@code false}: ж�سɹ�
     */
    public static boolean uninstallAppSilent(Context context, String packageName, boolean isKeepData) {
        if (StringUtils.isSpace(packageName)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(context), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }


    /**
     * �ж�App�Ƿ���rootȨ��
     *
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            LogUtils.d("isAppRoot", result.errorMsg);
        }
        return false;
    }

    /**
     * ��App
     *
     * @param packageName ����
     */
    public static void launchApp(String packageName) {
        if (StringUtils.isSpace(packageName)) return;
        Utils.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName));
    }

    /**
     * ��App
     *
     * @param activity    activity
     * @param packageName ����
     * @param requestCode ����ֵ
     */
    public static void launchApp(Activity activity, String packageName, int requestCode) {
        if (StringUtils.isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * ��ȡApp����
     *
     * @param context ������
     * @return App����
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * ��ȡApp��������
     *
     * @param context ������
     */
    public static void getAppDetailsSettings(Context context) {
        getAppDetailsSettings(context, context.getPackageName());
    }

    /**
     * ��ȡApp��������
     *
     * @param context     ������
     * @param packageName ����
     */
    public static void getAppDetailsSettings(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return;
        context.startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
    }

    /**
     * ��ȡApp����
     *
     * @param context ������
     * @return App����
     */
    public static String getAppName(Context context) {
        return getAppName(context, context.getPackageName());
    }

    /**
     * ��ȡApp����
     *
     * @param context     ������
     * @param packageName ����
     * @return App����
     */
    public static String getAppName(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

   
    /**
     * ��ȡApp�汾��
     *
     * @param context ������
     * @return App�汾��
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * ��ȡApp�汾��
     *
     * @param context     ������
     * @param packageName ����
     * @return App�汾��
     */
    public static String getAppVersionName(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ��ȡApp�汾��
     *
     * @param context ������
     * @return App�汾��
     */
    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * ��ȡApp�汾��
     *
     * @param context     ������
     * @param packageName ����
     * @return App�汾��
     */
    public static int getAppVersionCode(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    
    /**
     * �ж�App�Ƿ���Debug�汾
     *
     * @param context ������
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isAppDebug(Context context) {
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * �ж�App�Ƿ���Debug�汾
     *
     * @param context     ������
     * @param packageName ����
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isAppDebug(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ��ȡAppǩ��
     *
     * @param context ������
     * @return Appǩ��
     */
    public static Signature[] getAppSignature(Context context) {
        return getAppSignature(context, context.getPackageName());
    }

    /**
     * ��ȡAppǩ��
     *
     * @param context     ������
     * @param packageName ����
     * @return Appǩ��
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static Signature[] getAppSignature(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * ��ȡӦ��ǩ���ĵ�SHA1ֵ
//     * <p>�ɾݴ��жϸߵ£��ٶȵ�ͼkey�Ƿ���ȷ</p>
//     *
//     * @param context ������
//     * @return Ӧ��ǩ����SHA1�ַ���, ���磺53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
//     */
//    public static String getAppSignatureSHA1(Context context) {
//        return getAppSignatureSHA1(context, context.getPackageName());
//    }

//    /**
//     * ��ȡӦ��ǩ���ĵ�SHA1ֵ
//     * <p>�ɾݴ��жϸߵ£��ٶȵ�ͼkey�Ƿ���ȷ</p>
//     *
//     * @param context     ������
//     * @param packageName ����
//     * @return Ӧ��ǩ����SHA1�ַ���, ���磺53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
//     */
//    public static String getAppSignatureSHA1(Context context, String packageName) {
//        Signature[] signature = getAppSignature(context, packageName);
//        if (signature == null) return null;
//        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
//                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
//    }

    /**
     * �ж�App�Ƿ���ǰ̨
     *
     * @param context ������
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return info.processName.equals(context.getPackageName());
            }
        }
        return false;
    }

//    /**
//     * �ж�App�Ƿ���ǰ̨
//     * <p>�����ǲ鿴��ǰApp����SDK����21ʱ��
//     * �����Ȩ�� {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
//     *
//     * @param context     ������
//     * @param packageName ����
//     * @return {@code true}: ��<br>{@code false}: ��
//     */
//    public static boolean isAppForeground(Context context, String packageName) {
//        return !StringUtils.isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
//    }

    /**
     * ��װApp��Ϣ��Bean��
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int      versionCode;
        private boolean  isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packagName) {
            this.packageName = packagName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        ����
         * @param icon        ͼ��
         * @param packageName ����
         * @param packagePath ��·��
         * @param versionName �汾��
         * @param versionCode �汾��
         * @param isSystem    �Ƿ�ϵͳӦ��
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "App������" + getPackageName() +
                    "\nApp���ƣ�" + getName() +
                    "\nAppͼ�꣺" + getIcon() +
                    "\nApp·����" + getPackagePath() +
                    "\nApp�汾�ţ�" + getVersionName() +
                    "\nApp�汾�룺" + getVersionCode() +
                    "\n�Ƿ�ϵͳApp��" + isSystem();
        }
    }

    /**
     * ��ȡApp��Ϣ
     * <p>AppInfo�����ƣ�ͼ�꣬�������汾�ţ��汾Code���Ƿ�ϵͳӦ�ã�</p>
     *
     * @param context ������
     * @return ��ǰӦ�õ�AppInfo
     */
    public static AppInfo getAppInfo(Context context) {
        return getAppInfo(context, context.getPackageName());
    }

    /**
     * ��ȡApp��Ϣ
     * <p>AppInfo�����ƣ�ͼ�꣬�������汾�ţ��汾Code���Ƿ�ϵͳӦ�ã�</p>
     *
     * @param context     ������
     * @param packageName ����
     * @return ��ǰӦ�õ�AppInfo
     */
    public static AppInfo getAppInfo(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * �õ�AppInfo��Bean
     *
     * @param pm ���Ĺ���
     * @param pi ������Ϣ
     * @return AppInfo��
     */
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * ��ȡ�����Ѱ�װApp��Ϣ
     * <p>{@link #getBean(PackageManager, PackageInfo)}�����ƣ�ͼ�꣬��������·�����汾�ţ��汾Code���Ƿ�ϵͳӦ�ã�</p>
     * <p>���������getBean����</p>
     *
     * @param context ������
     * @return �����Ѱ�װ��AppInfo�б�
     */
    public static List<AppInfo> getAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        // ��ȡϵͳ�а�װ�����������Ϣ
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

//    /**
//     * ���App��������
//     *
//     * @param context  ������
//     * @param dirPaths Ŀ¼·��
//     * @return {@code true}: �ɹ�<br>{@code false}: ʧ��
//     */
//    public static boolean cleanAppData(Context context, String... dirPaths) {
//        File[] dirs = new File[dirPaths.length];
//        int i = 0;
//        for (String dirPath : dirPaths) {
//            dirs[i++] = new File(dirPath);
//        }
//        return cleanAppData(dirs);
//    }

//    /**
//     * ���App��������
//     *
//     * @param dirs Ŀ¼
//     * @return {@code true}: �ɹ�<br>{@code false}: ʧ��
//     */
//    public static boolean cleanAppData(File... dirs) {
//        boolean isSuccess = CleanUtils.cleanInternalCache();
//        isSuccess &= CleanUtils.cleanInternalDbs();
//        isSuccess &= CleanUtils.cleanInternalSP();
//        isSuccess &= CleanUtils.cleanInternalFiles();
//        isSuccess &= CleanUtils.cleanExternalCache();
//        for (File dir : dirs) {
//            isSuccess &= CleanUtils.cleanCustomCache(dir);
//        }
//        return isSuccess;
//    }
    
    
    /**
     * �ж�App�Ƿ���ϵͳӦ��
     *
     * @param context ������
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSystemApp(Context context) {
        return isSystemApp(context, context.getPackageName());
    }

    /**
     * �ж�App�Ƿ���ϵͳӦ��
     *
     * @param context     ������
     * @param packageName ����
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isSystemApp(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
//    /**
//     * ��ȡAppͼ��
//     *
//     * @param context ������
//     * @return Appͼ��
//     */
//    public static Drawable getAppIcon(Context context) {
//        return getAppIcon(context, context.getPackageName());
//    }
//
//    /**
//     * ��ȡAppͼ��
//     *
//     * @param context     ������
//     * @param packageName ����
//     * @return Appͼ��
//     */
//    public static Drawable getAppIcon(Context context, String packageName) {
//        if (StringUtils.isSpace(packageName)) return null;
//        try {
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(packageName, 0);
//            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * ��ȡApp·��
//     *
//     * @param context ������
//     * @return App·��
//     */
//    public static String getAppPath(Context context) {
//        return getAppPath(context, context.getPackageName());
//    }
//
//    /**
//     * ��ȡApp·��
//     *
//     * @param context     ������
//     * @param packageName ����
//     * @return App·��
//     */
//    public static String getAppPath(Context context, String packageName) {
//        if (StringUtils.isSpace(packageName)) return null;
//        try {
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(packageName, 0);
//            return pi == null ? null : pi.applicationInfo.sourceDir;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


}