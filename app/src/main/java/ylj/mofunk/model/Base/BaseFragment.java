package ylj.mofunk.model.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import ylj.mofunk.model.tools.LogUtils;


public abstract class BaseFragment extends RxFragment {

    protected final String TAG = "------Fragment-------";

    protected Controller controller;


    protected BaseActivity context = null;

    protected View view = null;

    protected LayoutInflater inflater = null;
    protected Bundle savedInstanceState = null;


    protected Intent intent = null;

    protected Bundle argument = null;

    @Nullable
    protected ViewGroup container = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isAlive = true;
        this.inflater = inflater;
        this.container = container;
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();

        //功能归类分区方法，必须调用>>>>>>>>>>
        return view;
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initEvent();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) view.findViewById(id);
    }


    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        V v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    public Intent getIntent() {
        return context.getIntent();
    }


    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }


    public void setContentView(View v) {
        setContentView(v, null);
    }


    public void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
        ButterKnife.bind(this,v);
        controller=new Controller.Builder(getActivity()).setControllerType(Controller.ControllerType.VIEW).setViewGroup(view).create();
    }


    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            LogUtils.w(TAG, "runUiThread  isAlive() == false >> return;");
            return;
        }
        context.runUiThread(action);
    }

    public final Handler runThread(String name, Runnable runnable) {
        if (isAlive() == false) {
            LogUtils.w(TAG, "runThread  isAlive() == false >> return null;");
            return null;
        }
        return null;// context.runThread(runnable);//name,
    }

    public final boolean isAlive() {
        return isAlive && context != null;// & !
    }

    public final boolean isRunning() {
        return isRunning & isAlive();
    }

    @Override
    public void onResume() {
        LogUtils.d(TAG, "\n onResume <<<<<<<<<<<<<<<<<<<<<<<");
        super.onResume();
        isRunning = true;
        LogUtils.d(TAG, "onResume >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    @Override
    public void onPause() {
        LogUtils.d(TAG, "\n onPause <<<<<<<<<<<<<<<<<<<<<<<");
        super.onPause();
        isRunning = false;
        LogUtils.d(TAG, "onPause >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG, "\n onDestroy <<<<<<<<<<<<<<<<<<<<<<<");
        // dismissProgressDialog();
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                LogUtils.w(TAG, "onDestroy  try { view.destroyDrawingCache();" + " >> } catch (Exception e) {\n"
                        + e.getMessage());
            }
        }

        isAlive = false;
        isRunning = false;
        super.onDestroy();

        view = null;
        inflater = null;
        container = null;

        intent = null;
        argument = null;

        context = null;

        controller.releaseResource();

        LogUtils.d(TAG, "onDestroy >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    public void showProgressDialog(String dialogTitle, String dialogMessage) {
        if (isAlive() == false) {
            LogUtils.w(TAG, "showProgressDialog  isAlive() == false >> return;");
            return;
        }
        context.dismissProgressDialog();
    }

    public void dismissProgressDialog() {
        if (isAlive() == false) {
            LogUtils.w(TAG, "dismissProgressDialog  isAlive() == false >> return;");
            return;
        }
        context.dismissProgressDialog();
    }
    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}
}
