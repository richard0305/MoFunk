package ylj.mofunk.model.tools;

  
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP��ع�����
 * </pre>
 */
public class SPUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * SPUtils���캯��
     * <p>��Application�г�ʼ��</p>
     *
     * @param spName  spName
     */
    public SPUtils(String spName) {
        sp = Utils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    /**
     * SP��д��String����value
     *
     * @param key   ��
     * @param value ֵ
     */
    public void put(String key, String value) {
        editor.putString(key, value).apply();
    }

    /**
     * SP�ж�ȡString
     *
     * @param key ��
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code null}
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * SP�ж�ȡString
     *
     * @param key          ��
     * @param defaultValue Ĭ��ֵ
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * SP��д��int����value
     *
     * @param key   ��
     * @param value ֵ
     */
    public void put(String key, int value) {
        editor.putInt(key, value).apply();
    }

    /**
     * SP�ж�ȡint
     *
     * @param key ��
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ-1
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP�ж�ȡint
     *
     * @param key          ��
     * @param defaultValue Ĭ��ֵ
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP��д��long����value
     *
     * @param key   ��
     * @param value ֵ
     */
    public void put(String key, long value) {
        editor.putLong(key, value).apply();
    }

    /**
     * SP�ж�ȡlong
     *
     * @param key ��
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP�ж�ȡlong
     *
     * @param key          ��
     * @param defaultValue Ĭ��ֵ
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP��д��float����value
     *
     * @param key   ��
     * @param value ֵ
     */
    public void put(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    /**
     * SP�ж�ȡfloat
     *
     * @param key ��
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP�ж�ȡfloat
     *
     * @param key          ��
     * @param defaultValue Ĭ��ֵ
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP��д��boolean����value
     *
     * @param key   ��
     * @param value ֵ
     */
    public void put(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    /**
     * SP�ж�ȡboolean
     *
     * @param key ��
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP�ж�ȡboolean
     *
     * @param key          ��
     * @param defaultValue Ĭ��ֵ
     * @return ���ڷ��ض�Ӧֵ�������ڷ���Ĭ��ֵ{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP�л�ȡ���м�ֵ��
     *
     * @return Map����
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP���Ƴ���key
     *
     * @param key ��
     */
    public void remove(String key) {
        editor.remove(key).apply();
    }

    /**
     * SP���Ƿ���ڸ�key
     *
     * @param key ��
     * @return {@code true}: ����<br>{@code false}: ������
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * SP�������������
     */
    public void clear() {
        editor.clear().apply();
    }
}