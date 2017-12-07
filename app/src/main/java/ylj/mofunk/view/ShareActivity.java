package ylj.mofunk.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.dao.ShareDaoDao;
import ylj.mofunk.model.Base.ActivityController;
import ylj.mofunk.model.Base.BaseActivity;
import ylj.mofunk.model.Entity.ShareDao;
import ylj.mofunk.model.adapter.ABSRecycleAdapter;
import ylj.mofunk.model.adapter.BaseRecycleViewHolder;
import ylj.mofunk.model.adapter.MyItemDecoration;
import ylj.mofunk.model.tools.ToastUtils;

public class ShareActivity extends BaseActivity {
    private DaoSession dao;
    private ShareDaoDao zDao;
    private PopupWindow pop;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.recycleview)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ABSRecycleAdapter<ShareDao> adapter;

    private TextView tvType;
    private TextView tvtitle;
    private TextView tvcontent;
    private TextView tvdesc;
    private View popview;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        showPop();
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        setSupportActionBar(toolbar);
        dao = MoApplication.getInstance().getDaoSession();
        zDao = dao.getShareDaoDao();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityController.jumpToActivity(ShareActivity.this, ShareEditActivity.class);
            }
        });

        adapter = new ABSRecycleAdapter<ShareDao>(this) {
            @Override
            public BaseRecycleViewHolder getBaseRecycleViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ShareActivity.this).inflate(R.layout.item_share, parent, false);
                BaseRecycleViewHolder viewholder = new BaseRecycleViewHolder(view);
                return viewholder;
            }

            @Override
            public void operationViewHolder(BaseRecycleViewHolder holder, final int position) {

                final ShareDao b = getItem(position);
                TextView tvsharetime = holder.obtainView(R.id.tvsharetime);
                TextView tvsharecontent = holder.obtainView(R.id.tvsharecontent);
                TextView tvsharetitle = holder.obtainView(R.id.tvsharetitle);
                TextView tvsharetype = holder.obtainView(R.id.tvsharetype);

                tvsharecontent.setText(b.getUrl());
                tvsharetime.setText(b.getCreattime());
                tvsharetitle.setText(b.getTitle());
                tvsharetype.setText(b.getType());

                holder.itemView.setTag(position);
            }
        };

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SetBack(1.0f);
            }
        });

        //点击事件
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                SetBack(0.8f);
               ShareDao sharedao = adapter.getItem(position);
                tvcontent.setText(sharedao.getUrl());
                tvdesc.setText(sharedao.getDesc());
                tvtitle.setText(sharedao.getTitle());
                tvType.setText(sharedao.getType());
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);
                //渐变，从半透明变成不透明
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(popview, "alpha" , 0.4f, 1.0f );
                //缩放，自身的中心 从小变大
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(popview, "scaleX" , 0.1f, 1.3f );
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(popview, "scaleY" , 0.1f, 1.3f );
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(popview, "scaleX" , 1.3f, 1f );
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(popview, "scaleY" , 1.3f, 1f );
//                //属性动画，使用AnimatorSet来执行
                AnimatorSet set1 = new AnimatorSet();
//                //设置动画一起执行， 还是顺序执行 还是有选择的执行,这里选择一起执行
                set1.playTogether(animator1 , animator2);
//                set1.playTogether(animator3 , animator4);
//                //设置动画执行时间和 开始动画的操作
                set1.setDuration(1000).start();


//                @SuppressLint("ResourceType")
//                Animator anim = AnimatorInflater.loadAnimator(
//                        ShareActivity.this, R.anim.property_animatorer);
//                anim.setTarget(popview);
//                anim.start();
            }
        });

        recyclerView.setAdapter(adapter);
        if (zDao.queryBuilder().list().size() > 0) {
            adapter.upData(zDao.queryBuilder().list());
        }
    }

    @Override
    protected void initData() {

    }

    private void SetBack(float f) {
        WindowManager.LayoutParams lp = ShareActivity.this.getWindow().getAttributes();
        lp.alpha = f;
        ShareActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ShareActivity.this.getWindow().setAttributes(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (zDao.count() > 0) {
            if (zDao.queryBuilder().list().size() > 0) {
                adapter.upData(zDao.queryBuilder().list());
            }
        }
    }


    private void showPop() {
        pop = new PopupWindow(ShareActivity.this);
         popview = getLayoutInflater().inflate(R.layout.item_share_pop, null);
        pop.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(popview);

        ImageView ivcancle = (ImageView) popview.findViewById(R.id.ivpopcancle);
        ImageView ivpopshare = (ImageView) popview.findViewById(R.id.ivpopshare);
         tvType = (TextView) popview.findViewById(R.id.tvpoptype);
         tvtitle = (TextView) popview.findViewById(R.id.ivpoptitle);
         tvcontent = (TextView) popview.findViewById(R.id.ivpopcontent);
         tvdesc = (TextView) popview.findViewById(R.id.ivpopdesc);
        Button btnpopdelete = (Button) popview.findViewById(R.id.btnpopdelete);
        Button btnpopedit = (Button) popview.findViewById(R.id.btnpopedit);

        ivcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        ivpopshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("分享");
            }
        });
        btnpopdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("删除");
            }
        });
        btnpopedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("编辑");
            }
        });

    }


}
