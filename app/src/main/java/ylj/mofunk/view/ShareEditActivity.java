package ylj.mofunk.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_edit);
        ButterKnife.bind(this);
        dao= MoApplication.getInstance().getDaoSession();
        zDao=  dao.getShareDaoDao();
      dataset = new LinkedList<>(Arrays.asList("Music", "MV", "NOTE", "WEIBO"));
        niceSpinner.attachDataSource(dataset);
    }

    @OnClick(R.id.btnSave)
    void Save() {
        String title=etTitle.getText().toString().trim();
        String content=etcontent.getText().toString().trim();
        String desc=etdesc.getText().toString().trim();
       String type=dataset.get(niceSpinner.getSelectedIndex()) ;

       if(StringUtils.isEmpty(title)){
           ToastUtils.showShortToast("标题不能为空！");
       }    if(StringUtils.isEmpty(content)){
           ToastUtils.showShortToast("内容不能为空！");
       }

        if(!StringUtils.isEmpty(title)&& !StringUtils.isEmpty(content)){
            zDao.insert(new ShareDao(zDao.count()+1,content,title,type,desc));
        }

        ToastUtils.showLongToastSafe("title:"+title+"  type"+type+"  content:"+content+"  desc:"+desc);
    }


}
