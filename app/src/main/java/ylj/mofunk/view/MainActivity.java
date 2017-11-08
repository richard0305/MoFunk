package ylj.mofunk.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.di.components.DaggerMainComponent;
import ylj.mofunk.di.modules.MainModule;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.Entity.GankAndroid;
import ylj.mofunk.model.adapter.ABSRecycleAdapterOnClick;
import ylj.mofunk.model.adapter.BaseRecycleViewHolder;
import ylj.mofunk.model.adapter.MyItemDecoration;
import ylj.mofunk.model.tools.LogUtils;
import ylj.mofunk.model.tools.ToastUtils;
import ylj.mofunk.presenter.MainContract;
import ylj.mofunk.presenter.MainPresenter;

public class MainActivity extends BaseFragment implements MainContract.View{
    private boolean isPrepared;
@BindView(R.id.refreshLayout)
SmartRefreshLayout refreshLayout;
    @BindView(R.id.lvandroid)
    RecyclerView recyclerView;
    @Inject
     MainPresenter presenter;
    private int page=1;
    private ABSRecycleAdapterOnClick<GankAndroid> adapter;

    public Pair[] pairs;
    public ArrayList<ImageView> imagelist;
    public ImageView iv;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(getActivity());
        initPresenter();
        isPrepared = true;
        lazyLoad();

    }

    @Override
    protected void initData() {
        refreshLayout.setRefreshHeader(new DropboxHeader(getActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        adapter=new ABSRecycleAdapterOnClick<GankAndroid>(getActivity()) {
            @Override
            public BaseRecycleViewHolder getBaseRecycleViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.item_gank_android_list,parent,false);
                BaseRecycleViewHolder viewholder=new BaseRecycleViewHolder(view);
                view.setOnClickListener(this);
                return viewholder;
            }
            @Override
            public void operationViewHolder(BaseRecycleViewHolder holder, final int position) {

                final GankAndroid b=getItem(position);
                TextView tvTitle=holder.obtainView(R.id.tvTitle);
                TextView tvType=holder.obtainView(R.id.tvType);
                TextView tvName=holder.obtainView(R.id.tvName);
                 iv=holder.obtainView(R.id.imageView);
                imagelist=new ArrayList<ImageView>();
                tvName.setText("by "+b.getWho());
                tvTitle.setText(b.getDesc());
                tvType.setText(b.getType());
                if(b.getImages()!=null){
                    Glide.with(MainActivity.this).load(b.getImages().get(0)).error(R.mipmap.apply_fail).into(iv);
                }else{
                    iv.setImageResource(R.mipmap.apply_fail);
                }
                imagelist.add(iv);
                holder.itemView.setTag(position);
            }
        };
            recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initEvent() {

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                presenter.getAndroidByList(page);
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                presenter.getAndroidByList(page);
                refreshlayout.finishRefresh();
            }
        });




       adapter.setOnItemClickListener(new ABSRecycleAdapterOnClick.OnItemClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onItemClick(View view, int position) {

                LogUtils.e("android_url=所有的Image"+imagelist.toString()+"个数："+imagelist.size());
               GankAndroid gankAndroid=  adapter.getmObjects().get(position);


               Intent i=new Intent(getActivity(),WebActivity.class);
               i.putExtra("android_url",(Serializable) gankAndroid);

               if(Build.VERSION.SDK_INT>=21){
                   view.setTransitionName("tran_01");
                   ActivityOptions options = ActivityOptions
                           .makeSceneTransitionAnimation(getActivity(), view, "tran_01");
                   // start the new activity
                   startActivity(i, options.toBundle());
               }else{
                   startActivity(i);
               }

           }
       });

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        presenter.getAndroidByList(page);
    }

    private void initPresenter() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(MoApplication.get(getActivity()).getNetComponent())
                .build().inject(this);
    }

    @Override
    public void updateListUI(List<GankAndroid> itemList) {
            if(page<=1){
                adapter.upData(itemList);

            }else{
                adapter.addAll(itemList);
            }
    }

    @Override
    public void showOnFailure() {
        page--;
        ToastUtils.showShortToastSafe("无更多的数据");
    }
}
