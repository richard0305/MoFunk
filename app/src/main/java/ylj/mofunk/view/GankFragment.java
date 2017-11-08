package ylj.mofunk.view;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.adapter.ViewPagerFragmentAdapter;
import ylj.mofunk.model.tools.ToastUtils;
import ylj.mofunk.util.AppBarStateChangeListener;
import ylj.mytoastlibrary.EmptyUtils;


public class GankFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
@BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbar_layout;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;

    private List<Fragment> fragments=new ArrayList<>();
    private List<CharSequence>titles=new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_gank);
        ButterKnife.bind(getActivity());

    }

    @Override
    protected void initData() {
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        fragments.add(new MainActivity());
        fragments.add(new BeautyActivity());
        fragments.add(new TestFragment());
        fragments.add(new AnswerFragment());
        DrawerLayout drawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        titles.add("Android");
        titles.add("Beauty");
        titles.add("TestOne");
        titles.add("Answer");
//        titles.add("V-Layout");
////        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
//        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));

        viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getActivity().getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(viewPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initEvent() {
        ToastUtils.showLongToast("是佛为空啊啊啊啊"+ EmptyUtils.isEmpty(""));
        app_bar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    //展开状态
                    toolbar.setTitle("");
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    toolbar.setTitle("我的样子平平无奇");
                }else {
                    //中间状态
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

}
