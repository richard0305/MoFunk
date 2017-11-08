package ylj.mofunk.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

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
