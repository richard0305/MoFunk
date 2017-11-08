package ylj.mofunk.presenter;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ylj.mofunk.model.Api.ApiService;
import ylj.mofunk.model.Api.ProgressSubscriber;
import ylj.mofunk.model.Entity.GankAndroid;
import ylj.mofunk.model.Entity.GankBean;


/**
 * Created by 我的样子平平无奇 on 2017/8/30 09:50.
 * Email: 2256669598@qq.com
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private ApiService apiService;

    @Inject
    public MainPresenter(MainContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getAndroidByList(int pagenum) {
        apiService.AndroidList(pagenum).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new ProgressSubscriber<GankBean<GankAndroid>>(){
            @Override
            public void onNext(GankBean<GankAndroid> gankAndroidGankBean) {
                super.onNext(gankAndroidGankBean);
                if(gankAndroidGankBean.isError()){
                    view.showOnFailure();
                }else{
                   view.updateListUI(gankAndroidGankBean.getResults());
                }
            }
        });


    }

}
