package ylj.mofunk.model.Base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.util.SparseArrayCompat;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by liukun on 2017/3/31.
 */
public class Controller {


    public Controller(Builder builder){
        this.viewGroup=builder.viewGroup;
        this.context=builder.context;
        this.controllerType=builder.controllerType;
        mViews=new SparseArrayCompat<>();

    }
    private SparseArrayCompat<View> mViews;
    private Context context;
    private View viewGroup;
    private ControllerType controllerType;



    public enum ControllerType{
        ACIVITY,VIEW
    }

    public static class Builder{
        private Context context;
        private View viewGroup;
        private ControllerType controllerType= ControllerType.ACIVITY;
        public Builder(Context context){this.context=context;}
        public Controller create() { return new Controller(this);}

        public Builder setControllerType(@VolumeProviderCompat.ControlType ControllerType controllerType) {
            this.controllerType = controllerType;
            return this;
        }
        public Builder setViewGroup(View viewGroup) {
            this.viewGroup = viewGroup;
            return this;
        }
    }
    /**
     * 回收资源
     *
     */
    public void releaseResource(){
        mViews=null;
        context=null;
        viewGroup=null;
        controllerType=null;
    };
    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null)
        {
            if(ControllerType.ACIVITY==controllerType){
                return (T) ((BaseActivity)context).findViewById(viewId);
            }
            if (ControllerType.VIEW==controllerType){
                return (T) viewGroup.findViewById(viewId);
            }
            throw new RuntimeException("未初始化");
        }
        return (T) view;
    }

    /**
     * 通过viewName获取控件
     *
     * @param viewName
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(String viewName) {
        int resID = context.getResources().getIdentifier(viewName, "id", context.getPackageName());
        return (T) ((BaseActivity)context).findViewById(resID);
    }






    public  void jumpToActivity(Class<?> cls) {
        if (context==null)throw new RuntimeException("未初始化");
        context.startActivity(new Intent(context, cls));
    }

    public  void jumpToActivity(Class<?> cls, ActivityParamter<?> ap) {
        if (context==null)throw new RuntimeException("未初始化");
        Intent i = new Intent();
        i.setClass(context, cls);
        i.putExtra("params", ap);
        context.startActivity(i);
    }

    public  void jumpToActivity(Intent intent) {
        if (context==null)throw new RuntimeException("未初始化");
        context.startActivity(intent);
    }


















    /**** -----------------------以下为辅助方法--------------------------- *****/

    /**
     * 设置textView文本内容
     * @param viewId    viewId
     * @param text  文本内容
     * @return AbsBaseActivity
     */
    public Controller setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text+"");
        return this;
    }
    /**
     * 设置textView文本颜色
     *
     * @param viewId	viewId
     * @param textColor 	颜色数值
     * @return AbsBaseActivity
     */
    public Controller setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    /**
     * 设置textView文本颜色
     *
     * @param viewId   viewId
     * @param textColorRes 颜色Id
     * @return viewHolder
     */
    public Controller setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }
    /**
     * 设置imgView的图片,通过Id设置
     *
     * @param viewId   viewId
     * @param resId 图片Id
     * @return viewHolder viewHolder
     */
    public Controller setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    /**
     * 设置img图片Bitmap
     *
     * @param viewId    viewId
     * @param bitmap    bitmap
     * @return viewHolder
     */
    public Controller setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    /**
     * 设置img的Drawable
     *
     * @param viewId   viewId
     * @param drawable drawable
     * @return viewHolder
     */
    public Controller setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
    /**
     * 设置背景颜色
     *
     * @param viewId viewId
     * @param color  颜色数值
     * @return viewHolder viewHolder
     */
    public Controller setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }
    /**
     * 设置背景颜色
     *
     * @param viewId   viewId
     * @param backgroundRes 颜色Id
     * @return viewHolder
     */
    public Controller setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
    /**
     *
     * @param viewId	viewId
     * @param value		透明度值
     * @return
     */
    @SuppressLint("NewApi")
    public Controller setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }
    /**
     * 设置控件是否显示
     *
     * @param viewId  viewId
     * @param visible true(visible)/false(gone)
     * @return viewHolder
     */
    public Controller setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    /**
     * 	设置超链接
     * @param viewId
     * @return
     */
    public Controller linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }
    /**
     * 设置TextView字体
     *
     * @param typeface typeface
     * @return viewHolder
     */
    public Controller setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }
    /**
     * @param viewId
     * @param progress 进度条
     * @return
     */
    public Controller setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }
    /**
     * @param viewId
     * @param progress
     * @param max	进度
     * @return
     */
    public Controller setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }
    /**
     * @param viewId
     * @param max   最大进度
     * @return
     */
    public Controller setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }
    /**
     *
     * @param viewId
     * @param rating  评分组件
     * @return
     */
    public Controller setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }
    /**
     *
     * @param viewId
     * @param rating  评分组件
     * @param max   设置评分
     * @return
     */
    public Controller setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }
    /**
     * 设置控件的tag
     *
     * @param viewId viewId
     * @param tag    tag
     * @return viewHolder
     */
    public Controller setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }
    /**
     * 设置控件tag
     *
     * @param viewId viewId
     * @param key    tag的key
     * @param tag    tag
     * @return viewHolder
     */
    public Controller setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }
    /**
     * 设置Checkable控件的选择情况
     *
     * @param viewId  viewId
     * @param checked 选择
     * @return viewHolder
     */
    public Controller setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

	/*------------------关于监听事件-----------------------*/

    /**
     * 设置监听	OnClickListener
     *
     * @param viewId
     * @param listener
     * @return
     */
    public Controller setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    /**
     * 设置监听	OnTouchListener
     *
     * @param viewId
     * @param listener
     * @return
     */
    public Controller setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }
    /**
     * 设置监听	OnLongClickListener
     *
     * @param viewId
     * @param listener
     * @return
     */
    public Controller setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     *  设置 abslistview	BaseAdapter
     * @param viewId
     * @param baseAdapter
     * @return
     */
    public Controller setAdapter(int viewId, BaseAdapter baseAdapter){
        AbsListView absListView=getView(viewId);
        absListView.setAdapter(baseAdapter);
        return this;
    }
    public Controller setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener){
        AbsListView absListView=getView(viewId);
        absListView.setOnItemClickListener(listener);
        return this;
    }

    public  Controller setOnCheckedChangeListener(int viewId,RadioGroup.OnCheckedChangeListener listener){
        RadioGroup radioGroup=getView(viewId);
        radioGroup.setOnCheckedChangeListener(listener);
        return this;
    }

    public  Controller setOnCheckedChangeListener(int viewId,CompoundButton.OnCheckedChangeListener listener){
        CheckBox checkBox=getView(viewId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }


}
