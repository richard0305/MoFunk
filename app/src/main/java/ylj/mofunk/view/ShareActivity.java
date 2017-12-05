package ylj.mofunk.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.dao.ShareDaoDao;
import ylj.mofunk.model.Base.ActivityController;
import ylj.mofunk.model.Entity.ShareDao;
import ylj.mofunk.model.adapter.ABSRecycleAdapter;
import ylj.mofunk.model.adapter.BaseRecycleViewHolder;
import ylj.mofunk.model.adapter.MyItemDecoration;
import ylj.mofunk.model.tools.ToastUtils;

public class ShareActivity extends AppCompatActivity {
    private DaoSession dao;
    private ShareDaoDao zDao;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);


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

        // 设置监听器。
        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
//
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ToastUtils.showShortToast("dianjile "+position);
            }
        });

// 菜单点击监听。
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);


        recyclerView.setAdapter(adapter);
        if (zDao.queryBuilder().list().size() > 0) {
            adapter.upData(zDao.queryBuilder().list());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (zDao.count() > 0) {
//            zDao.deleteAll();
//            ToastUtils.showShortToast(zDao.queryBuilder().list().toString());
            if (zDao.queryBuilder().list().size() > 0) {
                adapter.upData(zDao.queryBuilder().list());
            }
        }

    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.DP_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem deleteItem = new SwipeMenuItem(ShareActivity.this);
            deleteItem.setText("编辑").setHeight(height).setTextColor(getResources().getColor(R.color.white)).setWidth(width).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ; // 各种文字和图标属性设置。
            rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。

            SwipeMenuItem deleteItem1 = new SwipeMenuItem(ShareActivity.this); // 各种文字和图标属性设置。
            deleteItem1.setText("删除").setHeight(height).setTextColor(getResources().getColor(R.color.white)).setWidth(width).setBackgroundColor(getResources().getColor(R.color.black));

            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。

            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };

    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            ToastUtils.showShortToast("onClick点击了" + "  direction" + direction + "  adapterPosition" + adapterPosition + "  menuPosition" + menuPosition);
        }
    };

}
