package ylj.mofunk.di.modules;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ylj.mofunk.BuildConfig;
import ylj.mofunk.model.Api.ApiConstans;
import ylj.mofunk.model.Api.ApiService;
import ylj.mofunk.model.Api.BaseUrlInterceptor;


/**
 * Created by 我的样子平平无奇 on 2017/8/30 09:17.
 * Email: 2256669598@qq.com
 */
@Module
public class GankNetModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).addInterceptor(new BaseUrlInterceptor())
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstans.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return  retrofit.create(ApiService.class);
    }


}
