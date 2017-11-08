package ylj.mofunk.di.components;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import ylj.mofunk.di.modules.GankNetModule;
import ylj.mofunk.model.Api.ApiService;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:10.
 * Email: 2256669598@qq.com
 */
@Component(modules = GankNetModule.class)
@Singleton
public interface NetComponent {
    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
