package ylj.mofunk.model.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ylj.mofunk.app.MoApplication;


/**
 * Created by 我的样子平平无奇 on 2017/7/21 16:57.
 * Email: 2256669598@qq.com
 */

public class RxSaveImageUtils {


    public static void Save(View photoView){
        saveImageView(getViewBitmap(photoView));
    }



    public static class SaveObservable implements ObservableOnSubscribe<String> {

        private Bitmap drawingCache = null;

        public SaveObservable(Bitmap drawingCache) {
            this.drawingCache = drawingCache;
        }


        @Override
        public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
            if (drawingCache == null) {
                e.onError(new NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"));
            } else {
                try {
                    File imageFile = new File(Environment.getExternalStorageDirectory()+"/NPC/PIC", System.currentTimeMillis()+".jpg");
                    FileOutputStream outStream;
                    outStream = new FileOutputStream(imageFile);
                    drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    e.onNext(Environment.getExternalStorageDirectory().getPath()+"/NPC/PIC");
                    e.onComplete();
                    outStream.flush();
                    outStream.close();
                } catch (IOException n) {
                    e.onError(n);
                }
            }
        }
    }

    public static class SaveSubscriber implements Observer<String> {


        @Override
        public void onError(Throwable e) {
            Log.i(getClass().getSimpleName(), e.toString());
            Toast.makeText(MoApplication.getActivityManager().currentActivity(), "保存失败——> " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {
            Toast.makeText(MoApplication.getActivityManager().currentActivity(), "保存成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }
        @Override
        public void onNext(String s) {
            Toast.makeText(MoApplication.getActivityManager().currentActivity(), "保存路径为：-->  " + s, Toast.LENGTH_SHORT).show();
        }
    }


    public static void saveImageView(Bitmap drawingCache) {
        Observable.create( new SaveObservable(drawingCache))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new SaveSubscriber());
    }

    /**
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    public static Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}
