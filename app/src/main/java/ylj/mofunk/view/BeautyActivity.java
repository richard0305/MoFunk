package ylj.mofunk.view;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import ylj.mofunk.di.components.DaggerBeautyComponent;
import ylj.mofunk.di.modules.BeautyModule;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.Entity.GankBeauty;
import ylj.mofunk.model.Entity.ImagePosition;
import ylj.mofunk.model.adapter.ABSRecycleAdapterOnClick;
import ylj.mofunk.model.adapter.BaseRecycleViewHolder;
import ylj.mofunk.model.tools.LogUtils;
import ylj.mofunk.presenter.BeautyContract;
import ylj.mofunk.presenter.BeautyPresenter;
import ylj.mofunk.util.GlideRoundTransform;

public class BeautyActivity extends BaseFragment implements BeautyContract.View{
    private boolean isPrepared;
@BindView(R.id.tvBeauty)
RecyclerView tvBeauty;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Inject
    BeautyPresenter presenter;
    ABSRecycleAdapterOnClick<GankBeauty> adapter;
    private int page=1;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_beauty);
        ButterKnife.bind(getActivity());
        initPresenter();
        isPrepared = true;
        lazyLoad();

        //瀑布流
        tvBeauty.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        refreshLayout.setRefreshHeader(new DropboxHeader(getActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void initPresenter() {
        DaggerBeautyComponent.builder().beautyModule(new BeautyModule(this))
                .netComponent(MoApplication.get(getActivity()).getNetComponent())
                .build().inject(this);
    }

    @Override
    protected void initData() {
        adapter=new ABSRecycleAdapterOnClick<GankBeauty>(getActivity()) {
            @Override
            public BaseRecycleViewHolder getBaseRecycleViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.layout_item,parent,false);
                BaseRecycleViewHolder viewholder=new BaseRecycleViewHolder(view);
                view.setOnClickListener(this);
                return viewholder;
            }

            @Override
            public void operationViewHolder(BaseRecycleViewHolder holder, int position) {
                GankBeauty b=getItem(position);
                final ImageView iv=holder.obtainView(R.id.item_iv);
                TextView tv=holder.obtainView(R.id.item_tv);
                tv.setText(b.getDesc());
                if(b.getUrl()!=null){
                    Glide.with(getActivity()).load(b.getUrl()).transform(new GlideRoundTransform(getActivity(),20)).crossFade().into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            iv.setImageDrawable(resource);
                        }
                    });
                }else{
                    iv.setImageResource(R.mipmap.apply_fail);
                }
                holder.itemView.setTag(position);
            }
        };

        tvBeauty.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new ABSRecycleAdapterOnClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<GankBeauty>gankBeauties=adapter.getmObjects();
                List<ImagePosition>images=new ArrayList<ImagePosition>();
                for(int i=0;i<gankBeauties.size();i++){
                    images.add(new ImagePosition(i,gankBeauties.get(i).getUrl()));
                }
                LogUtils.e("photo_images=","photo_images="+images.toString());
                Intent intent=new Intent(getActivity(),PhotoViewActivity.class);
                intent.putExtra("photo_position",position);
                intent.putExtra("photo_images", (Serializable) images);
                startActivity(intent);

            }
        });

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                presenter.getBeautyList(page);
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                presenter.getBeautyList(page);
                refreshlayout.finishRefresh();
            }
        });

    }
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        presenter.getBeautyList(page);
        //填充各控件的数据
    }

    @Override
    public void updateListUI(List<GankBeauty> itemList) {
        if(page<=1){
            adapter.upData(itemList);
        }else{
            adapter.addAll(itemList);
        }
    }

    @Override
    public void showOnFailure() {

    }
}
