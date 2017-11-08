package ylj.mofunk.model.SwipeMenu;

/**
 * Created by 我的样子平平无奇 on 2017/9/4 14:26.
 * Email: 2256669598@qq.com
 */


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import ylj.mofunk.R.styleable;

public class SwipeMenuLayout extends ViewGroup {
    private int mExpandLimit;
    private int mCollapseLimit;
    private float expandRatio;
    private float collapseRatio;
    private int expandDuration;
    private int collapseDuration;
    private boolean isLeftMenu;
    private int mWidthofMenu;
    private boolean isExpand;
    private boolean isClickEvent;
    private int mScaleTouchSlop;
    private int mMaxVelocity;
    private VelocityTracker mVelocityTracker;
    private int mPointerId;
    private static boolean sIsTouching = false;
    private static SwipeMenuLayout sSwipeMenuLayout;
    private boolean enableParentLongClick;
    private PointF mPointDownF;
    private PointF mPointGapF;
    private boolean isInterceptTouch;
    private boolean isInterceptParent;
    private ValueAnimator mExpandAnim;
    private ValueAnimator mCollapseAnim;

    public SwipeMenuLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.expandRatio = 0.3F;
        this.collapseRatio = 0.7F;
        this.expandDuration = 150;
        this.collapseDuration = 150;
        this.isLeftMenu = true;
        this.isExpand = false;
        this.isClickEvent = true;
        this.enableParentLongClick = false;
        this.mPointDownF = new PointF();
        this.mPointGapF = new PointF();
        this.isInterceptTouch = false;
        this.isInterceptParent = false;
        TypedArray ta = context.obtainStyledAttributes(attrs, styleable.SwipeMenuLayout, defStyleAttr, 0);
        int count = ta.getIndexCount();

        for(int i = 0; i < count; ++i) {
            int attr = ta.getIndex(i);
            if(attr == styleable.SwipeMenuLayout_isLeftMenu) {
                this.isLeftMenu = ta.getBoolean(attr, true);
            } else if(attr == styleable.SwipeMenuLayout_enableParentLongClick) {
                this.enableParentLongClick = ta.getBoolean(attr, false);
            } else if(attr == styleable.SwipeMenuLayout_expandRatio) {
                this.expandRatio = ta.getFloat(attr, 0.3F);
            } else if(attr == styleable.SwipeMenuLayout_collapseRatio) {
                this.collapseRatio = 1.0F - ta.getFloat(attr, 0.3F);
            } else if(attr == styleable.SwipeMenuLayout_expandDuration) {
                this.expandDuration = ta.getInt(attr, 150);
            } else if(attr == styleable.SwipeMenuLayout_collapseDuration) {
                this.collapseDuration = ta.getInt(attr, 150);
            }
        }

        ta.recycle();
        this.mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setClickable(true);
        this.mWidthofMenu = 0;
        int widthOfContent = 0;
        int heightOfContent = 0;
        int childCount = this.getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View childView = this.getChildAt(i);
            childView.setClickable(true);
            if(childView.getVisibility() != 8) {
                this.measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                heightOfContent = Math.max(heightOfContent, childView.getMeasuredHeight());
                if(i == 0) {
                    widthOfContent = this.getMeasuredWidth();
                } else {
                    this.mWidthofMenu += childView.getMeasuredWidth();
                }
            }
        }

        this.mExpandLimit = (int)((float)this.mWidthofMenu * this.expandRatio);
        this.mCollapseLimit = (int)((float)this.mWidthofMenu * this.collapseRatio);
        this.setMeasuredDimension(widthOfContent, heightOfContent);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        int left = l;
        int right = r;

        for(int i = 0; i < childCount; ++i) {
            View childView = this.getChildAt(i);
            if(childView.getVisibility() != 8) {
                if(i == 0) {
                    childView.layout(left, this.getPaddingTop(), left + childView.getMeasuredWidth(), this.getPaddingTop() + childView.getMeasuredHeight());
                } else if(this.isLeftMenu) {
                    childView.layout(left - childView.getMeasuredWidth(), this.getPaddingTop(), left, this.getPaddingTop() + childView.getMeasuredHeight());
                    left -= childView.getMeasuredWidth();
                } else {
                    childView.layout(right, this.getPaddingTop(), right + childView.getMeasuredWidth(), this.getPaddingTop() + childView.getMeasuredHeight());
                    right += childView.getMeasuredWidth();
                }
            }
        }

    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(null == this.mVelocityTracker) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }

        this.mVelocityTracker.addMovement(ev);
        switch(ev.getAction()) {
            case 0:
                if(sIsTouching) {
                    return false;
                }

                sIsTouching = true;
                this.isClickEvent = true;
                this.isInterceptTouch = false;
                this.isInterceptParent = false;
                if(sSwipeMenuLayout != null && sSwipeMenuLayout != this) {
                    sSwipeMenuLayout.collapseSmooth();
                    sIsTouching = false;
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.isInterceptTouch = true;
                }

                this.mPointGapF.x = ev.getX();
                this.mPointDownF.x = ev.getX();
                this.mPointDownF.y = ev.getY();
                this.mPointerId = ev.getPointerId(0);
                break;
            case 1:
            case 3:
                sIsTouching = false;
                if(this.isInterceptTouch) {
                    return false;
                }

                this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaxVelocity);
                float velocityX = this.mVelocityTracker.getXVelocity(this.mPointerId);
                if(this.isLeftMenu) {
                    if(!this.isExpand) {
                        if(-this.getScrollX() <= this.mExpandLimit && (velocityX <= 1000.0F || !this.isInterceptParent)) {
                            this.collapseSmooth();
                        } else {
                            this.expandSmooth();
                        }
                    } else if(-this.getScrollX() < this.mCollapseLimit || velocityX < -1000.0F && this.isInterceptParent) {
                        this.collapseSmooth();
                    } else if(!this.isClickEvent) {
                        this.expandSmooth();
                    } else if(ev.getX() > (float)(-this.getScrollX())) {
                        this.collapseSmooth();
                    }
                } else if(!this.isExpand) {
                    if(this.getScrollX() <= this.mExpandLimit && (velocityX >= -1000.0F || !this.isInterceptParent)) {
                        this.collapseSmooth();
                    } else {
                        this.expandSmooth();
                    }
                } else if(this.getScrollX() < this.mCollapseLimit || velocityX > 1000.0F && this.isInterceptParent) {
                    this.collapseSmooth();
                } else if(!this.isClickEvent) {
                    this.expandSmooth();
                } else if(ev.getX() < (float)(this.getWidth() - this.getScrollX())) {
                    this.collapseSmooth();
                }

                this.releaseVelocityTracker();
                break;
            case 2:
                if(this.isInterceptTouch) {
                    return false;
                }

                this.isClickEvent = Math.abs(this.mPointDownF.x - ev.getX()) < (float)this.mScaleTouchSlop;
                float gapX = this.mPointDownF.x - ev.getX();
                float gapY = this.mPointDownF.y - ev.getY();
                if(Math.abs(gapX) < (float)this.mScaleTouchSlop && Math.abs(gapX) > Math.abs(gapY) * 2.0F) {
                    this.isInterceptParent = true;
                } else if(Math.abs(gapX) > (float)this.mScaleTouchSlop || Math.abs(this.getScrollX()) > this.mScaleTouchSlop) {
                    this.isInterceptParent = true;
                }

                if(this.isInterceptParent) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.scrollBy((int)(this.mPointGapF.x - ev.getX()), 0);
                    this.mPointGapF.x = ev.getX();
                    if(this.isLeftMenu) {
                        if(-this.getScrollX() < 0) {
                            this.scrollTo(0, 0);
                            this.isExpand = false;
                        }

                        if(this.getScrollX() <= -this.mWidthofMenu) {
                            this.scrollTo(-this.mWidthofMenu, 0);
                            this.isExpand = true;
                        }
                    } else {
                        if(this.getScrollX() < 0) {
                            this.scrollTo(0, 0);
                            this.isExpand = false;
                        }

                        if(this.getScrollX() >= this.mWidthofMenu) {
                            this.scrollTo(this.mWidthofMenu, 0);
                            this.isExpand = true;
                        }
                    }
                }
        }

        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!this.isInterceptTouch && (this.isInterceptParent || this.isClickEvent) && this.isClickEvent) {
            if(this.isExpand) {
                if(this.isLeftMenu && ev.getX() > (float)this.mWidthofMenu) {
                    return true;
                }

                if(!this.isLeftMenu && ev.getX() < (float)(this.getWidth() - this.mWidthofMenu)) {
                    return true;
                }
            } else if(this.enableParentLongClick) {
                return true;
            }
            else if(!this.isExpand){
                return true;
            }

            return super.onInterceptTouchEvent(ev);
        }else if (this.isInterceptTouch){
            return true;
        }
        else {
            return true;
        }
    }

    public boolean performLongClick() {
        return Math.abs(this.getScrollX()) <= this.mScaleTouchSlop && !this.isInterceptTouch?super.performLongClick():false;
    }

    public void expandSmooth() {
        sSwipeMenuLayout = this;
        this.cancelAnim();
        this.mExpandAnim = ValueAnimator.ofInt(new int[]{this.getScrollX(), this.isLeftMenu?-this.mWidthofMenu:this.mWidthofMenu});
        this.mExpandAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                SwipeMenuLayout.this.scrollTo(((Integer)animation.getAnimatedValue()).intValue(), 0);
            }
        });
        this.mExpandAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeMenuLayout.this.isExpand = true;
            }
        });
        this.mExpandAnim.setInterpolator(new LinearInterpolator());
        this.mExpandAnim.setDuration((long)this.expandDuration).start();
    }

    public void collapseSmooth() {
        sSwipeMenuLayout = null;
        this.cancelAnim();
        this.mCollapseAnim = ValueAnimator.ofInt(new int[]{this.getScrollX(), 0});
        this.mCollapseAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                SwipeMenuLayout.this.scrollTo(((Integer)animation.getAnimatedValue()).intValue(), 0);
            }
        });
        this.mCollapseAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeMenuLayout.this.isExpand = false;
            }
        });
        this.mCollapseAnim.setInterpolator(new AccelerateInterpolator());
        this.mCollapseAnim.setDuration((long)this.collapseDuration).start();
    }

    private void cancelAnim() {
        if(this.mCollapseAnim != null && this.mCollapseAnim.isRunning()) {
            this.mCollapseAnim.cancel();
        }

        if(this.mExpandAnim != null && this.mExpandAnim.isRunning()) {
            this.mExpandAnim.cancel();
        }

    }

    private void releaseVelocityTracker() {
        if(null != this.mVelocityTracker) {
            this.mVelocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }

    }

    protected void onDetachedFromWindow() {
        if(sSwipeMenuLayout != null) {
            sSwipeMenuLayout.collapseInstant();
            sSwipeMenuLayout = null;
        }

        super.onDetachedFromWindow();
    }

    public void collapseInstant() {
        if(this == sSwipeMenuLayout) {
            this.cancelAnim();
            sSwipeMenuLayout.scrollTo(0, 0);
            sSwipeMenuLayout = null;
        }

    }

    public void setLeftMenu(boolean leftMenu) {
        this.isLeftMenu = leftMenu;
    }

    public void setEnableParentLongClick(boolean enableParentLongClick) {
        this.enableParentLongClick = enableParentLongClick;
    }

    public void setExpandRatio(float expandRatio) {
        this.expandRatio = expandRatio;
    }

    public void setCollapseRatio(float collapseRatio) {
        this.collapseRatio = 1.0F - collapseRatio;
    }

    public void setExpandDuration(int expandDuration) {
        this.expandDuration = expandDuration;
    }

    public void setCollapseDuration(int collapseDuration) {
        this.collapseDuration = collapseDuration;
    }
}
