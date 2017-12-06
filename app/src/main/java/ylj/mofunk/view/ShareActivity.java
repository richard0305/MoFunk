package ylj.mofunk.view;

import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
   private  PopupWindow pop;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.recycleview)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ABSRecycleAdapter<ShareDao> adapter;


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
                ShareDao dao=   adapter.getItem(position);
                ToastUtils.showShortToast("dianjile " + position);
                pop.showAsDropDown(view, 0,0,Gravity.CENTER);
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
        View view = getLayoutInflater().inflate(R.layout.item_share_pop, null);
        pop.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
    }


}
