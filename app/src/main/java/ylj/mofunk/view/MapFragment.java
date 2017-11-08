package ylj.mofunk.view;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ylj.mofunk.R;
import ylj.mofunk.model.Base.BaseFragment;
import ylj.mofunk.model.adapter.ABSListViewAdapter;
import ylj.mofunk.model.adapter.BaseViewHolder;
import ylj.mofunk.model.tools.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener {
    @BindView(R.id.mapview)
    TextureMapView textureMapView;
    @BindView(R.id.edittext)
    TextInputEditText edittext;
    @BindView(R.id.maplist)
    ListView maplist;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ABSListViewAdapter<Tip> adapter;


    @Override
    protected void initView() {
        setContentView(R.layout.fragment_map);
        ButterKnife.bind(getActivity());
        textureMapView.onCreate(savedInstanceState);
        aMap = textureMapView.getMap();
        mUiSettings = aMap.getUiSettings();
        maplist.setVisibility(View.GONE);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_location68)));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.NORMAL)
    public void NORMAL(View vew) {
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.NAVI)
    public void NAVI(View vew) {
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.NIGHT)
    public void NIGHT(View vew) {
        aMap.setMapType(AMap.MAP_TYPE_NIGHT);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.SATELLITE)
    public void SATELLITE(View vew) {
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.ivMoveLocation)
    public void MoveToLocation(View vew) {
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @OnClick(R.id.ivMoveXingqu)
    public void Xingqu(View vew) {

    }

    @OnClick(R.id.ivMapSearch)
    public void Search(View vew) {
        String keyword = edittext.getText().toString();
        query = new PoiSearch.Query(keyword, "", "");

        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        maplist.setVisibility(View.VISIBLE);
        poiSearch.searchPOIAsyn();
    }


    @Override
    protected void initData() {
        adapter = new ABSListViewAdapter<Tip>(getActivity()) {
            @Override
            public int getLayoutResId() {
                return R.layout.item_map_poi;
            }

            @Override
            public void getView(int position, View convertView, ViewGroup parent, BaseViewHolder holder) {
                TextView item_map_poi = holder.obtainView(convertView, R.id.tvName);
                Tip poi = getItem(position);

                item_map_poi.setText(poi.getName() + " - " + poi.getDistrict() + poi.getPoint());
            }
        };

        maplist.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        maplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Tip poi = (Tip) parent.getAdapter().getItem(position);
                LatLng latLng = new LatLng(poi.getPoint().getLatitude(), poi.getPoint().getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poi.getName()).snippet(poi.getDistrict()));
                Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 360, 0, 0, 0);
                long duration = 1000L;
                animation.setDuration(duration);
                animation.setInterpolator(new LinearInterpolator());

                marker.setAnimation(animation);
                marker.startAnimation();
            }
        });


        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    maplist.setVisibility(View.VISIBLE);
                } else {
                    maplist.setVisibility(View.GONE);
                }

                InputtipsQuery inputquery = new InputtipsQuery(s.toString(), "南京");
                inputquery.setCityLimit(false);//限制在当前城市
                Inputtips inputTips = new Inputtips(getActivity(), inputquery);
                inputTips.setInputtipsListener(MapFragment.this);
                inputTips.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        textureMapView.onDestroy();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        textureMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        textureMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        textureMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtils.e("poiResult=" + poiResult.getPois().toString());

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtils.e("poiItem=" + poiItem.getTitle().toString());
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        LogUtils.e("list=" + list.toString());
        adapter.upData(list);
    }
}
