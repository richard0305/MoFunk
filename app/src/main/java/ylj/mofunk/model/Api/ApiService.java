package ylj.mofunk.model.Api;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ylj.mofunk.model.Entity.GankAndroid;
import ylj.mofunk.model.Entity.GankBean;
import ylj.mofunk.model.Entity.GankBeauty;
import ylj.mofunk.model.Entity.LiveAppIndexInfo;
import ylj.mofunk.model.Entity.OneBean;
import ylj.mofunk.model.Entity.chatBean;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 09:31.
 * Email: 2256669598@qq.com
 */

public interface ApiService {

    /**
     * 首页直播
     */
    @GET("AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    Flowable<LiveAppIndexInfo> getLiveAppIndex();

    /**
     * 直播url
     */
    @GET("api/playurl?player=1&quality=0")
    Flowable<ResponseBody> getLiveUrl(@Query("cid") int cid);





    //1.获取福利图片
    @GET("data/福利/10/{pagenum}")
    Flowable<GankBean<GankBeauty>> BeautyPict(@Path("pagenum") int pagenum);
    //2.获取Android
    @GET("data/Android/20/{pagenum}")
    Flowable<GankBean<GankAndroid>> AndroidList(@Path("pagenum") int pagenum);

    //3.获取One 获取最新 idlist
    @Headers(ApiConstans.One_BASE_URL)
    @GET("onelist/idlist/?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
    Flowable<OneBean<String>> GetOneIDList();

    //4.获取One 获取最新 最新的One
    @Headers(ApiConstans.One_BASE_URL)
    @GET("onelist/{countid}/0?cchannel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
    Flowable<OneBean<String>> GetTodayOne(@Path("countid")String countid);

    //5.获取One 读取文章详情
    @Headers(ApiConstans.One_BASE_URL)
    @GET("essay/{countid}?channel=wdj&source=channel_reading&source_id=9264&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
    Flowable<OneBean<String>> ReadAuthorDetail(@Path("countid")String countid);

    //5.获取One 读取文章详情
    @Headers(ApiConstans.One_BASE_URL)
    @GET("iqa/query?")
    Flowable<chatBean> getChat(@Query("question") String question, @Header("Authorization") String appcode);
}
