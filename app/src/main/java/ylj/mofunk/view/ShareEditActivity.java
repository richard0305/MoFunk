package ylj.mofunk.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.dao.DaoSession;
import ylj.mofunk.dao.ShareDaoDao;
import ylj.mofunk.model.Entity.ShareDao;
import ylj.mofunk.model.tools.StringUtils;
import ylj.mofunk.model.tools.TimeUtils;
import ylj.mofunk.model.tools.ToastUtils;

public class ShareEditActivity extends AppCompatActivity {

    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etcontent)
    EditText etcontent;
    @BindView(R.id.etdesc)
    EditText etdesc;
    @BindView(R.id.spinner)
    NiceSpinner niceSpinner;
    List<String> dataset;
    private DaoSession dao;
    private ShareDaoDao zDao;
    public ShareDao sharedao=null;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_edit);
        ButterKnife.bind(this);
        dao= MoApplication.getInstance().getDaoSession();
        zDao=  dao.getShareDaoDao();
        dataset = new ArrayList<>();
        dataset.add("Music");
        dataset.add("MV");
        dataset.add("NOTE");
        dataset.add("WEIBO");
        niceSpinner.attachDataSource(dataset);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedao= (ShareDao) getIntent().getSerializableExtra("sharedao");
            if(sharedao!=null){
                etTitle.setText(sharedao.getTitle());
                etcontent.setText(sharedao.getUrl());
                etdesc.setText(sharedao.getDesc());

                for (int i=0;i<dataset.size() ;i++){
                    if(dataset.get(i).equals(sharedao.getType())){
                        niceSpinner.setSelectedIndex(i);
                        return;
                    }
                }



            }
    }

    @OnClick(R.id.btnSave)
    void Save() {
        String title=etTitle.getText().toString().trim();
        String content=etcontent.getText().toString().trim();
        String desc=etdesc.getText().toString().trim();
       String type=dataset.get(niceSpinner.getSelectedIndex()) ;
String time= TimeUtils.millis2String(System.currentTimeMillis());
       if(StringUtils.isEmpty(title)){
           ToastUtils.showShortToast("标题不能为空！");
       }    if(StringUtils.isEmpty(content)){
           ToastUtils.showShortToast("内容不能为空！");
       }

        if(!StringUtils.isEmpty(title)&& !StringUtils.isEmpty(content)){
            zDao.insert(new ShareDao(zDao.count()+1,content,title,type,desc,time));
            ToastUtils.showShortToast("添加成功！");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },1000);

        }

    }


}
