package ylj.mofunk.view;


import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.SingleFilterScanCallback;
import com.vise.baseble.model.BluetoothLeDeviceStore;

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


    @Inject
    OneIdListPresenter presenter;
    @BindView(R.id.tvContent)
    TextView tvContent;


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
        menuItemName.add("扫描所有设备");
        menuItemName.add("QQ");
        menuItemName.add("微博");
        menuItemName.add("电话");


    }

    private void initPresenter() {
        DaggerOneIDListComponent.builder().oneIdListModule(new OneIdListModule(this)).netComponent(MoApplication.getInstance().getNetComponent()).build().inject(this);
    }

    @Override
    protected void initData() {
        satelliteMenu.getmBuilder().setMenuImage(R.mipmap.ic_launcher_round).
                setMenuItemImageResource(imageMenuItemImageResource).setMenuItemNameTexts(menuItemName).
                setOnMenuItemClickListener((view,position)-> switchOnclick(position)).creat();

    }

        void switchOnclick(int position){
            ViseBle.getInstance().startScan(new SingleFilterScanCallback(new IScanCallback() {
                @Override
                public void onDeviceFound(BluetoothLeDeviceStore device) {
                        LogUtils.i("开始扫描");
//                    if(device.getDeviceList().size()!=0){
//                        tvContent.setText("名称："+device.getDeviceList().get(0).getName()+"\n状态："+device.getDeviceList().get(0).getBluetoothDeviceBondState()+"\n地址："+
//                                device.getDeviceList().get(0).getAddress()+"\n类型："+device.getDeviceList().get(0).getBluetoothDeviceClassName()+"\nuuid："+device.getDeviceList().get(0).getBluetoothDeviceKnownSupportedServices());
//                    }

                    tvContent.setText(device.toString());


                }

                @Override
                public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
                    LogUtils.i("扫描完成");
                }

                @Override
                public void onScanTimeout() {
                    LogUtils.i("超时");
                }
            }).setDeviceName("PE-TL20"));
//            ViseBle.getInstance().startLeScan(new BluetoothAdapter.LeScanCallback(){
//                @Override
//                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//
//                    tvContent.setText("名称："+device.getName()+"\n状态："+device.getBondState()+"\n地址："+
//                            device.getAddress()+"\n类型："+device.getType()+"\nuuid："+device.getUuids());
//                }
//            });
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












