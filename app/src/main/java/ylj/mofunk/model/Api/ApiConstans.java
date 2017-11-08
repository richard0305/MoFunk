package ylj.mofunk.model.Api;

import android.os.Environment;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 09:27.
 * Email: 2256669598@qq.com
 */

public  interface ApiConstans {
    public static String BASE_URL="http://gank.io/api/";
    public static String Gank_BASE_URL="http://gank.io/api/";
    public static String One_BASE_URL="http://v3.wufazhuce.com:8000/api/";
    public static String ZHI_BASE_URL="http://jisuznwd.market.alicloudapi.com/";
    public static String SavePath= Environment.getExternalStorageDirectory() + "/MoFunk/pic/";
    public static final String LIVE_BASE_URL = "http://live.bilibili.com/";
    public static final String COMMON_UA_STR = "OhMyBiliBili Android Client/2.1 (100332338@qq.com)";
    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    public static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ApiConstans.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

}
