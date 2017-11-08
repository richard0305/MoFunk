package ylj.mofunk.model.Api;

import android.content.Context;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import ylj.mofunk.app.MoApplication;
import ylj.mofunk.model.tools.LogUtils;


public abstract class ProgressSubscriber<T> implements ProgressHandler.ProgressCancelListener, Subscriber<T> {

    private ProgressHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber() {
        this.context = MoApplication.getActivityManager().currentActivity();
        mProgressDialogHandler = new ProgressHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressHandler.DISMISS_PROGRESS).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onSubscribe(Subscription s) {
        showProgressDialog();
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
                       Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ConnectException) {
                       Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                   } else {
                         Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
        dismissProgressDialog();
       LogUtils.e(e.getMessage());
    }
    @Override
    public void onNext(T t) {
    	
    }

    @Override
    public void onCancelProgress() {

    }
}
