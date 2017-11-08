package ylj.mofunk.model.tools;


import android.content.Context;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils��ʼ�����
 * </pre>
 */
public class Utils {

	private static Context context;
	private static SPUtils spUtils;

	
	private Utils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * ��ʼ��������
	 *
	 * @param context
	 *            ������
	 */
	public static void init(Context context) {
		
		Utils.context = context.getApplicationContext();
		spUtils = new SPUtils("utilcode");
		ViewUtil.setScreenHeight(ScreenUtils.getScreenHeight(context));
		ViewUtil.setScreenWidth(ScreenUtils.getScreenWidth(context));

		LogUtils.getBuilder().setTag("---liu---").setLogSwitch(true).setLogFilter('v').setLog2FileSwitch(true).create();
		ToastUtils.init(true);
		
		
		LogUtils.i(ScreenUtils.getScreenWidth(context));
		LogUtils.i(ScreenUtils.getScreenHeight(context));
	}

	/**
	 * ��ȡApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static Context getContext() {
		if (context != null)
			return context;
		throw new NullPointerException("u should init first");
	}

	public static SPUtils getSpUtils() {
		return spUtils;
	}
}