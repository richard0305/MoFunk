package ylj.mofunk.view;


import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import csy.menu.satellitemenulib.view.SatelliteMenu;
import ylj.mofunk.R;
import ylj.mofunk.app.MoApplication;
import ylj.mofunk.di.components.DaggerOneIDListComponent;
import ylj.mofunk.di.modules.OneIdListModule;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.Entity.LiveAppIndexInfo;
import ylj.mofunk.model.tools.LogUtils;
import ylj.mofunk.model.tools.ToastUtils;
import ylj.mofunk.presenter.OneIdListContract;
import ylj.mofunk.presenter.OneIdListPresenter;

/**
 *  @author:  richard_yang
 */

public class OneFragment extends BaseFragment implements OneIdListContract.View{
    private boolean isPrepared;
    @BindView(R.id.satellitemenu)
    SatelliteMenu satelliteMenu;
    List<Integer> imageMenuItemImageResource=new ArrayList<Integer>();
    List<String> menuItemName=new ArrayList<String>();

    @BindView(R.id.listview)
    ListView listView;
    @Inject
    OneIdListPresenter presenter;
    @Override
    protected void initView() {
        setContentView(R.layout.fragment_one);
        ButterKnife.bind(getActivity());
        initPresenter();
        isPrepared = true;
        lazyLoad();
        imageMenuItemImageResource.add(R.mipmap.ic_launcher_round);
        imageMenuItemImageResource.add(R.mipmap.ic_launcher_round);
        imageMenuItemImageResource.add(R.mipmap.ic_launcher_round);
        imageMenuItemImageResource.add(R.mipmap.ic_launcher_round);
        menuItemName.add("微信");
        menuItemName.add("QQ");
        menuItemName.add("微博");
        menuItemName.add("电话");

        ArrayList<HashMap<String, String>> readContact = readContact();
        listView.setAdapter(new SimpleAdapter(getActivity(), readContact,
                R.layout.item_list_phone, new String[] { "name", "phone" },
                new int[] { R.id.tv_name, R.id.tv_phone }));
    }

    private void initPresenter() {
        DaggerOneIDListComponent.builder().oneIdListModule(new OneIdListModule(this)).netComponent(MoApplication.getInstance().getNetComponent()).build().inject(this);
    }

    @Override
    protected void initData() {
        satelliteMenu.getmBuilder().setMenuImage(R.mipmap.ic_launcher_round).
                setMenuItemImageResource(imageMenuItemImageResource).setMenuItemNameTexts(menuItemName).
                setOnMenuItemClickListener((view,position)-> ToastUtils.showLongToast("点击了:"+position)).creat();

    }

    @Override
    protected void initEvent() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.GetOneIdlist("你好！","bf8e385b6b744e36a835d4da3692036a");
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        presenter.GetOneIdlist("你好！","bf8e385b6b744e36a835d4da3692036a");
        //填充各控件的数据
    }


    @Override
    public void OneIDList(LiveAppIndexInfo ids) {
        LogUtils.e("LIVE数据==="+ids.getData().getRecommend_data().getLives().get(0).toString());
    }




    /**
     *得到联系人
     **/
    private ArrayList<HashMap<String, String>> readContact() {
        // 首先,从raw_contacts中读取联系人的id("contact_id")
        // 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
        // 然后,根据mimetype来区分哪个是联系人,哪个是电话号码

        Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // 从raw_contacts中读取所有联系人的id("contact_id")
        Cursor rawContactsCursor = getActivity().getContentResolver().query(rawContactsUri,
                new String[] { "contact_id" }, null, null, null);
        if (rawContactsCursor != null) {
            while (rawContactsCursor.moveToNext()) {
                String contactId = rawContactsCursor.getString(0);
                // System.out.println("得到的contact_id="+contactId);

                // 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
                Cursor dataCursor = getActivity().getContentResolver().query(dataUri,
                        new String[] { "data1", "mimetype" }, "contact_id=?",
                        new String[] { contactId }, null);

                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        // System.out.println(contactId + ";" + data1 + ";"
                        // + mimetype);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {//手机号码
                            map.put("phone", data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {//联系人名字
                            map.put("name", data1);
                        }
                    }
                    list.add(map);
                    dataCursor.close();
                }
            }
            rawContactsCursor.close();
        }
        return list;
    }
}












