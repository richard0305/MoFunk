package ylj.mofunk.presenter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ylj.mofunk.model.Api.ApiConstans;
import ylj.mofunk.model.Api.ApiService;
import ylj.mofunk.model.Entity.LiveAppIndexInfo;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 10:53.
 * Email: 2256669598@qq.com
 */

public class OneIdListPresenter implements OneIdListContract.Presenter {
    private ApiService apiService;
    private OneIdListContract.View view;

    @Inject
    public OneIdListPresenter(ApiService apiService, OneIdListContract.View view) {
        this.apiService = apiService;
        this.view = view;
    }

    @Override
    public void GetOneIdlist(String qustion,String code) {
        new Retrofit.Builder()
                .client(new OkHttpClient.Builder().addInterceptor(new ApiConstans.UserAgentInterceptor()).build())
                .baseUrl(ApiConstans.LIVE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .build().create(ApiService.class).getLiveAppIndex().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<LiveAppIndexInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(LiveAppIndexInfo stringOneBean) {
                    view.OneIDList(stringOneBean);
            }
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
            @Override
            public void onComplete() {

            }
        });
    }
}
