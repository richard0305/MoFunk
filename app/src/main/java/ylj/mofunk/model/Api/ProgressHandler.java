package ylj.mofunk.model.Api;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import ylj.mofunk.R;


public class ProgressHandler  extends Handler {
	public static final int SHOW_PROGRESS = 0;
    public static final int DISMISS_PROGRESS = 1;
    private Dialog pd;
    private Context mContext;
    private ProgressCancelListener mProgressCancelListener;
    private boolean cancelable;
    public ProgressHandler(Context context, ProgressCancelListener listener, boolean cancelable){
        this.mContext = context;
        mProgressCancelListener = listener;
        this.cancelable = cancelable;
    }
    public void initProgressDialog(){
      if(pd == null){
 	 View view= LayoutInflater.from(mContext).inflate(R.layout.custom_progerss_item, null);
 	pd = new Dialog(mContext, R.style.loading_dialog_style);
//   对话框是否可以取消
 	pd.setCancelable(cancelable);
 	pd.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

      pd.setCancelable(cancelable);
      if(cancelable){
          pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
              @Override
              public void onCancel(DialogInterface dialog) {
                  mProgressCancelListener.onCancelProgress();
              }
          });
      }
      if(!pd.isShowing()){
          pd.show();//显示进度条
      }
  }
    }
    
    public void dismissProgressDialog(){
        if(pd!=null){
            pd.dismiss();//取消进度条
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch(msg.what){
            case SHOW_PROGRESS:
                initProgressDialog();
            break;
            case DISMISS_PROGRESS:
                dismissProgressDialog();
            break;
        }
    } 
    
    public interface ProgressCancelListener {
        void onCancelProgress();
    }
    
    public interface SubscriberOnNextListener<T> {
        void onNext(T t);
    }
}
