package ylj.mofunk.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.dao.ShareDaoDao;
import ylj.mofunk.model.Base.ActivityController;
import ylj.mofunk.model.adapter.MyItemDecoration;
import ylj.mofunk.model.tools.ToastUtils;

public class ShareActivity extends AppCompatActivity {
    private DaoSession dao;
    private ShareDaoDao zDao;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        setSupportActionBar(toolbar);
        dao= MoApplication.getInstance().getDaoSession();
        zDao=  dao.getShareDaoDao();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityController.jumpToActivity(ShareActivity.this,ShareEditActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(  zDao.count()>0){
            ToastUtils.showShortToast(zDao.queryBuilder().list().toString());
        }

    }
}
