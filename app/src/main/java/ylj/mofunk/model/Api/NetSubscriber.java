package ylj.mofunk.model.Api;

import android.content.Context;
import android.widget.Toast;


import org.reactivestreams.Subscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import ylj.mofunk.app.MoApplication;


public abstract class NetSubscriber<T> implements Subscriber<T> {

    private Context context;

    public NetSubscriber() {
        this.context = MoApplication.getActivityManager().currentActivity();
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
    }
    @Override
    public void onNext(T t) {
    	
    }


}
