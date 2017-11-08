package ylj.mofunk.view;


import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.dao.ZhiBeanDao;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.Entity.ZhiBaseBean;
import ylj.mofunk.model.Entity.ZhiBean;
import ylj.mofunk.model.adapter.ABSRecycleAdapter;
import ylj.mofunk.model.adapter.BaseRecycleViewHolder;
import ylj.mofunk.util.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends BaseFragment {

    private boolean isPrepared;
    @BindView(R.id.ettext)
    EditText ettext;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private DaoSession dao;
    private ZhiBeanDao zDao;
    private ABSRecycleAdapter<ZhiBean> adapter;
    private SoundPool soundPool;
    private int Type=0;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_answer);
        ButterKnife.bind(getActivity());
        dao = MoApplication.getInstance().getDaoSession();
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        isPrepared = true;
        lazyLoad();
    }
    @OnClick(R.id.button)
    void onclick() {
        String text = ettext.getText().toString().trim();
        zDao.insert(new ZhiBean(zDao.count() + 1, text, 1));
        if(Type==0){
            List<ZhiBean> zhibeans = zDao.queryBuilder().list();
            adapter.upData(zhibeans);
            Type=1;
        }else{
            adapter.add(new ZhiBean(zDao.count() + 1, text, 1));
            recycleview.smoothScrollToPosition(adapter.getItemCount()-1);
        }

        ettext.setText("");
        LinkToOnline(text);
    }
    @Override
    protected void initData() {
        zDao = dao.getZhiBeanDao();
        List<ZhiBean> zhibeans = zDao.queryBuilder().list();
        adapter = new ABSRecycleAdapter<ZhiBean>(getActivity()) {
            @Override
            public BaseRecycleViewHolder getBaseRecycleViewHolder(ViewGroup parent, int viewType) {
                View view = null;
                if (viewType == 1) {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.message_item_right, parent, false);
                    BaseRecycleViewHolder rightholder = new BaseRecycleViewHolder(view);
                    return rightholder;
                } else {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.message_item_left, parent, false);
                    BaseRecycleViewHolder leftholder = new BaseRecycleViewHolder(view);
                    return leftholder;
                }
            }
            @Override
            public void operationViewHolder(BaseRecycleViewHolder holder, int position) {
                ZhiBean zhiBean = getItem(position);
                if (zhiBean.getType() == 1) {
                    TextView tv_right_content = holder.obtainView(R.id.tv_right_content);
                    tv_right_content.setText(zhiBean.getContent());
                } else {
                    TextView tv_left_content = holder.obtainView(R.id.tv_left_content);
                    tv_left_content.setText(zhiBean.getContent());
                }
            }
            @Override
            public int getItemViewType(int position) {
                int type = getItem(position).getType();
                if (type == 1) {
                    return 1;
                } else {
                    return 2;
                }
            }
        };
        recycleview.setAdapter(adapter);
        if(zhibeans.size()>0){
            Type=1;
            adapter.upData(zhibeans);
            recycleview.smoothScrollToPosition(adapter.getItemCount()-1);
        }

    }

    private void LinkToOnline(String wsd) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String host = "http://jisuznwd.market.alicloudapi.com";
                String path = "/iqa/query";
                String method = "GET";
                String appcode = "bf8e385b6b744e36a835d4da3692036a";
                Map<String, String> headers = new HashMap<String, String>();
                //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
                headers.put("Authorization", "APPCODE " + appcode);
                Map<String, String> querys = new HashMap<String, String>();
                querys.put("question", wsd);
                try {
                    HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                    Gson gson = new Gson();
                    ZhiBaseBean.BaseZhi baseZhi = gson.fromJson(EntityUtils.toString(response.getEntity()), ZhiBaseBean.class).getResult();

                    soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
                    soundPool.load(getActivity(),R.raw.beep,1);
                    soundPool.play(1,1, 1, 0, 0, 1);

                    zDao.insert(new ZhiBean(zDao.count() + 1, baseZhi.getContent(), 2));
                    adapter.add(new ZhiBean(zDao.count() + 1, baseZhi.getContent(), 2));
                    recycleview.smoothScrollToPosition(adapter.getItemCount()-1);
                    e.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

    }

}
