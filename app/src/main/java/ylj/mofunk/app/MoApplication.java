package ylj.mofunk.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.vise.baseble.ViseBle;

import ylj.mofunk.dao.DaoMaster;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.di.components.DaggerNetComponent;
import ylj.mofunk.di.components.NetComponent;
import ylj.mofunk.di.modules.GankNetModule;
import ylj.mofunk.model.Base.ActivityManager;
import ylj.mofunk.model.tools.Utils;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:13.
 * Email: 2256669598@qq.com
 */

public class MoApplication extends Application {
    private static MoApplication instance;
    private NetComponent netComponent;
    private static ActivityManager activityManager = null;
    private DaoSession daoSession;

    public static ActivityManager getActivityManager() {
        return MoApplication.activityManager;
    }
    public static void setActivityManager(ActivityManager activityManager) {
        MoApplication.activityManager = activityManager;
    }
    public static MoApplication get(Context context){
        return (MoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        setActivityManager(ActivityManager.getScreenManager());
        initNet();
        Utils.init(this);
        initGreenDao();
        MultiDex.install(this);
       //蓝牙相关配置修改
        ViseBle.config()
                .setScanTimeout(-1)//扫描超时时间，这里设置为永久扫描
                .setConnectTimeout(10 * 1000)//连接超时时间
                .setOperateTimeout(5 * 1000)//设置数据操作超时时间
                .setConnectRetryCount(3)//设置连接失败重试次数
                .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
                .setOperateRetryCount(3)//设置数据操作失败重试次数
                .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
                .setMaxConnectCount(3);//设置最大连接设备数量
        //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
        ViseBle.getInstance().init(this);
    }

    /**
     * 初始化 GreenDao
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mofunk.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }




    private void initNet() {
        netComponent= DaggerNetComponent.builder()
                .gankNetModule(new GankNetModule())
                .build();
    }

    public static MoApplication getInstance() {
        return instance;
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
