package ylj.mofunk.model.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SavaAllUtil {
	
	
	public static String toJson(Object object){
		return new Gson().toJson(object);
	}
	
	public static <T> T  fromJson(String json, Class<T> tClass){
		return new Gson().fromJson(json, tClass);
	}
	
	
	
	
	
	
	
	
	



	
	public static void clearData(Context context) {
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		sharedPre.edit().clear().commit();
		
	}

}
