package ylj.mofunk.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import ylj.mofunk.model.adapter.ABSRecycleAdapterSlide;
import ylj.mofunk.model.tools.ScreenUtils;

/**
 * Created by 我的样子平平无奇 on 2017/9/8 15:17.
 * Email: 2256669598@qq.com
 */

public class SlidingMenu  extends HorizontalScrollView{
    private static final float radio=0.3f;//菜单占屏幕比例
    private final int mScreenWidth;
    private final int mMenuWidth;
    private boolean once=true;
    private boolean isOpen=false;
    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth= ScreenUtils.getScreenWidth(context);
        mMenuWidth=(int)(mScreenWidth*radio) ;
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(once){
            LinearLayout wrapper=(LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width=mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width=mMenuWidth;
            once=false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                closeOpenMenu();
                break;
            case MotionEvent.ACTION_UP:
                int scrollx=getScrollX();
                if(Math.abs(scrollx)>mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    onOpenMenu();
                }else{
                    this.smoothScrollTo(0,0);
                }
                return  true;
        }
        return super.onTouchEvent(ev);
    }

    public void CloseMenu(){
        this.smoothScrollTo(0,0);
        isOpen=false;
    }


    //菜单是否打开
    public boolean isOpen(){
        return isOpen;
    }

    //当打开菜单时记录此view，方便下次关闭
    private  void onOpenMenu(){
        View view=this;
        while (true){
            view= (View) view.getParent();
            if(view instanceof RecyclerView){
                break;
            }
        }
        ((ABSRecycleAdapterSlide)((RecyclerView)view).getAdapter()).holdOpenMenu(this);
        isOpen=true;
    }
//当触摸此item时，关闭上一次打开的 item
    private void closeOpenMenu(){
        if(!isOpen){
            View view=this;
            while (true){
                view= (View) view.getParent();
                if(view instanceof RecyclerView){
                    break;
                }
            }
            ((ABSRecycleAdapterSlide)((RecyclerView)view).getAdapter()).closeOpenMenu();
        }
    }

}
