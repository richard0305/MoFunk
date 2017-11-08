package ylj.mofunk.model.tools;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/11/13
 *     desc  : ��λ��ع�����
 * </pre>
 */
public class LocationUtils {

    private static OnLocationChangeListener mListener;
    private static MyLocationListener       myLocationListener;
    private static LocationManager mLocationManager;

    private LocationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * �ж�Gps�Ƿ����
     *
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) Utils.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * �ж϶�λ�Ƿ����
     *
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) Utils.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * ��Gps���ý���
     */
    public static void openGpsSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getContext().startActivity(intent);
    }

    /**
     * ע��
     * <p>ʹ����ǵõ���{@link #unregister()}</p>
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}</p>
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     * <p>���{@code minDistance}Ϊ0����ͨ��{@code minTime}����ʱ���£�</p>
     * <p>{@code minDistance}��Ϊ0������{@code minDistance}Ϊ׼��</p>
     * <p>���߶�Ϊ0������ʱˢ�¡�</p>
     *
     * @param minTime     λ����Ϣ�������ڣ���λ�����룩
     * @param minDistance λ�ñ仯��С���룺��λ�þ���仯������ֵʱ��������λ����Ϣ����λ���ף�
     * @param listener    λ��ˢ�µĻص��ӿ�
     * @return {@code true}: ��ʼ���ɹ�<br>{@code false}: ��ʼ��ʧ��
     */
    public static boolean register(long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return false;
        mLocationManager = (LocationManager) Utils.getContext().getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
        if (!isLocationEnabled()) {
            ToastUtils.showShortToastSafe("�޷���λ����򿪶�λ����");
            return false;
        }
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) listener.getLastKnownLocation(location);
        if (myLocationListener == null) myLocationListener = new MyLocationListener();
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationListener);
        return true;
    }


    /**
     * ע��
     */
    public static void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
    }

    /**
     * ���ö�λ����
     *
     * @return {@link Criteria}
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //���ö�λ��ȷ�� Criteria.ACCURACY_COARSE�Ƚϴ��ԣ�Criteria.ACCURACY_FINE��ȽϾ�ϸ
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //�����Ƿ�Ҫ���ٶ�
        criteria.setSpeedRequired(false);
        // �����Ƿ�������Ӫ���շ�
        criteria.setCostAllowed(false);
        //�����Ƿ���Ҫ��λ��Ϣ
        criteria.setBearingRequired(false);
        //�����Ƿ���Ҫ������Ϣ
        criteria.setAltitudeRequired(false);
        // ���öԵ�Դ������
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * ���ݾ�γ�Ȼ�ȡ����λ��
     *
     * @param latitude  γ��
     * @param longitude ����
     * @return {@link Address}
     */
    public static Address getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(Utils.getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ���ݾ�γ�Ȼ�ȡ���ڹ���
     *
     * @param latitude  γ��
     * @param longitude ����
     * @return ���ڹ���
     */
    public static String getCountryName(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * ���ݾ�γ�Ȼ�ȡ���ڵ�
     *
     * @param latitude  γ��
     * @param longitude ����
     * @return ���ڵ�
     */
    public static String getLocality(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * ���ݾ�γ�Ȼ�ȡ���ڽֵ�
     *
     * @param latitude  γ��
     * @param longitude ����
     * @return ���ڽֵ�
     */
    public static String getStreet(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    private static class MyLocationListener
            implements LocationListener {
        /**
         * ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����
         *
         * @param location ����
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * provider���ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
         *
         * @param provider �ṩ��
         * @param status   ״̬
         * @param extras   provider��ѡ��
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogUtils.d("onStatusChanged", "��ǰGPS״̬Ϊ�ɼ�״̬");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtils.d("onStatusChanged", "��ǰGPS״̬Ϊ��������״̬");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtils.d("onStatusChanged", "��ǰGPS״̬Ϊ��ͣ����״̬");
                    break;
            }
        }

        /**
         * provider��enableʱ�����˺���������GPS����
         */
        @Override
        public void onProviderEnabled(String provider) {
        }

        /**
         * provider��disableʱ�����˺���������GPS���ر�
         */
        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public interface OnLocationChangeListener {

        /**
         * ��ȡ���һ�α���������
         *
         * @param location ����
         */
        void getLastKnownLocation(Location location);

        /**
         * ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����
         *
         * @param location ����
         */
        void onLocationChanged(Location location);

        /**
         * provider���ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
         *
         * @param provider �ṩ��
         * @param status   ״̬
         * @param extras   provider��ѡ��
         */
        void onStatusChanged(String provider, int status, Bundle extras);//λ��״̬�����ı�
    }
}
