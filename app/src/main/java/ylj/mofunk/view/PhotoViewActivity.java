package ylj.mofunk.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zyyoona7.lib.EasyPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.model.Base.BaseActivity;
import ylj.mofunk.model.Entity.ImagePosition;
import ylj.mofunk.model.adapter.MyPagerViewAdapter;


public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tvposition)
    TextView tvposition;

    private List<ImagePosition> images;
    private int position;
    private EasyPopup mCirclePop;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        setContentView(R.layout.activity_photo_view);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         toolbar.setCollapsible(true);
//        toolbar.setCollapsible(true);
//        toolbar.setLogo(R.mipmap.ic_launcher);
//// Title
        toolbar.setTitle("");
//// Sub Title
//        toolbar.setSubtitle("Sub title");
        toolbar.setNavigationIcon(R.mipmap.photoview_cancle);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        viewPager.setOffscreenPageLimit(2);


        Window window = this.getWindow();
//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.black));
    }



    @Override
    protected void initData() {
        mCirclePop  = new EasyPopup(this)
                .setContentView(R.layout.item_pop)
                .setAnimationStyle(R.style.PopupAnimation)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true).setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                .createPopup();
        images= (List<ImagePosition>) getIntent().getSerializableExtra("photo_images");
        position=getIntent().getIntExtra("photo_position",0);
        viewPager.setAdapter(new MyPagerViewAdapter(this,images,mCirclePop));
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvposition.setText(position+1+"/"+images.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
