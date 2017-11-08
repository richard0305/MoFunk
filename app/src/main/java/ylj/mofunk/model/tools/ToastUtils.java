package ylj.mofunk.model.tools;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/29
 *     desc  : ��˾��ع�����
 * </pre>
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;

    /**
     * ��˾��ʼ��
     *
     * @param isJumpWhenMore ������������˾ʱ����Ҫ��������˾����ֻ�޸��ı�����
     *                       <p>{@code true}: ��������˾<br>{@code false}: ֻ�޸��ı�����</p>
     *                       <p>���Ϊ{@code false}�Ļ�����������ʾ����ʱ������˾</p>
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param text �ı�
     */
    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     */
    public static void showShortToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     * @param args  ����
     */
    public static void showShortToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param format ��ʽ
     * @param args   ����
     */
    public static void showShortToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param text �ı�
     */
    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     */
    public static void showLongToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     * @param args  ����
     */
    public static void showLongToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * ��ȫ����ʾ��ʱ��˾
     *
     * @param format ��ʽ
     * @param args   ����
     */
    public static void showLongToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param text �ı�
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     */
    public static void showShortToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     * @param args  ����
     */
    public static void showShortToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param format ��ʽ
     * @param args   ����
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param text �ı�
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     */
    public static void showLongToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param resId ��ԴId
     * @param args  ����
     */
    public static void showLongToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * ��ʾ��ʱ��˾
     *
     * @param format ��ʽ
     * @param args   ����
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * ��ʾ��˾
     *
     * @param resId    ��ԴId
     * @param duration ��ʾʱ��
     */
    private static void showToast(@StringRes int resId, int duration) {
        showToast(Utils.getContext().getResources().getText(resId).toString(), duration);
    }

    /**
     * ��ʾ��˾
     *
     * @param resId    ��ԴId
     * @param duration ��ʾʱ��
     * @param args     ����
     */
    private static void showToast(@StringRes int resId, int duration, Object... args) {
        showToast(String.format(Utils.getContext().getResources().getString(resId), args), duration);
    }

    /**
     * ��ʾ��˾
     *
     * @param format   ��ʽ
     * @param duration ��ʾʱ��
     * @param args     ����
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * ��ʾ��˾
     *
     * @param text     �ı�
     * @param duration ��ʾʱ��
     */
    private static void showToast(CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (sToast == null) {
            sToast = Toast.makeText(Utils.getContext(), text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * ȡ����˾��ʾ
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
