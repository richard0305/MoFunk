package ylj.mofunk.model.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ylj.mofunk.model.tools.LogUtils;


/**
 * Created by Administrator on 2017/3/23.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    // 共享资源
    public static List<BaseActivity> activities = new ArrayList<>();



    protected String TAG = "------"+getClassName()+"-------";

    /**
     * @return
     */
    public String getClassName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;
    /**
     * 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;

    protected Context mContext = null;
    /**
     * 该Activity的界面，即contentView
     * @warn 不能在子类中创建
     */
    protected View view = null;
    /**
     * 布局解释器
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * Fragment管理器
     * @warn 不能在子类中创建
     */
    protected FragmentManager fragmentManager = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    protected Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        mContext=this.getApplicationContext();
        isAlive = true;
        fragmentManager = getSupportFragmentManager();
        inflater = getLayoutInflater();
        ActivityController.addActicity(this);

        controller=new Controller.Builder(context).setControllerType(Controller.ControllerType.ACIVITY).create();

        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initView();
        initData();
        initEvent();
        rewriteData();
        setReceiver();

    }
    protected void setImmerseLayout(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//                /*window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
//            view.setPadding(0, statusBarHeight, 0, 0);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

    }

    /**
     *
     */
    protected abstract void initView();
    /**
     *
     */
    protected abstract void initData();
    /**
     *
     */
    protected  void initEvent(){};
    /**
     *
     */
    protected  void rewriteData(){};
    /**
     *
     */
    protected  void setReceiver(){};




    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        view = inflater.inflate(layoutResID, null);
        super.setContentView(layoutResID);
    }



    /**ͨ  通过id查找并获取控件，并setOnClickListener
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        V v = (V) findViewById(id);
        v.setOnClickListener(l);
        return v;
    }


    public final boolean isAlive() {
        return isAlive && context != null;// & !
        // isFinishing();����finish��onDestroy��runUiThread������
    }
    /**
     * @return   �Ƿ�������
     */
    public final boolean isRunning() {
        return isRunning & isAlive();
    }


    // 运行线程 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            LogUtils.w("runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }

    @Override
    protected void onResume() {
        LogUtils.d(TAG, "\n onResume <<<<<<<<<<<<<<<<<<<<<<<");
        super.onResume();
        isRunning = true;
        LogUtils.d(TAG, "onResume >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    @Override
    protected void onPause() {
        LogUtils.d(TAG, "\n onPause <<<<<<<<<<<<<<<<<<<<<<<");
        super.onPause();
        isRunning = false;
        LogUtils.d(TAG, "onPause >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    /**
     *销毁并回收内存
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    protected void onDestroy() {
        LogUtils.d(TAG, "\n onDestroy <<<<<<<<<<<<<<<<<<<<<<<");
        // dismissProgressDialog();
        //// ThreadManager.getInstance().destroyThread(threadNameList);

        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                LogUtils.w(TAG, "onDestroy  try { view.destroyDrawingCache();" + " >> } catch (Exception e) {\n"
                        + e.getMessage());
            }
        }
        TAG=null;
        isAlive = false;
        isRunning = false;
        inflater = null;
        view = null;
        fragmentManager = null;
        intent = null;
        context = null;

        controller.releaseResource();

        ActivityController.delActicity(this);// ����ջ

        super.onDestroy();
    }

    public void showProgressDialog() {
        Toast.makeText(this,"DIALOGISSHW", Toast.LENGTH_LONG).show();
    };

    public void dismissProgressDialog() {
    };
}
