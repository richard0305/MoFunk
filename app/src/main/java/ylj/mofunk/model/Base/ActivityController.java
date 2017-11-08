
package ylj.mofunk.model.Base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import ylj.mofunk.app.MoApplication;


public class ActivityController {

	public static void addActicity(Activity activity) {
		MoApplication.getActivityManager().pushActivity(activity);
	}

	public static void delActicity(Activity activity) {
		MoApplication.getActivityManager().popActivity(activity);
	}
	public static void jumpToActivity(Activity activity, Class<?> cls) {
		activity.startActivity(new Intent(activity, cls));
	}
	public static void jumpToActivity(Context activity, Class<?> cls) {
		activity.startActivity(new Intent(activity, cls));
	}
	public static void jumpToActivity(Context activity, Class<?> cls, ActivityParamter<?> ap) {
		Intent i = new Intent();
		i.setClass(activity, cls);
		i.putExtra("params", ap);
		activity.startActivity(i);
	}
	public static void jumpToActivity(Activity activity, Class<?> cls, ActivityParamter<?> ap) {
		Intent i = new Intent();
		i.setClass(activity, cls);
		i.putExtra("params", ap);
		activity.startActivity(i);
	}
	public static void jumpToActivity(Activity activity, Intent intent, ActivityParamter<?> ap) {
		intent.putExtra("params", ap);
		activity.startActivity(intent);
	}

//	public static void delActivityExceptOne(Activity activity, Class<?> cls) {
//		intentJump(activity, cls);
//		MyApp application = (MyApp) activity.getApplicationContext();
//		application.getActivityManager().popAllActivityExceptOne(cls);
//	}
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(Activity context, int id) {
		return (T) context.findViewById(id);
	}
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewByName(Activity context, String name) {
		int resID = context.getResources().getIdentifier(name, "id", context.getPackageName());
		return (T)findViewById(context,resID);
	}

	/**
	 * rootView.findViewById()
	 * 
	 * @param rootView
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(View rootView, int id) {
		return (T) rootView.findViewById(id);
	}

}
