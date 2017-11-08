package ylj.mofunk.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ylj.mofunk.model.Api.ApiService;
import ylj.mofunk.model.Api.ProgressSubscriber;
import ylj.mofunk.model.Entity.GankBean;
import ylj.mofunk.model.Entity.GankBeauty;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 15:01.
 * Email: 2256669598@qq.com
 */

public class BeautyPresenter implements BeautyContract.Presenter {
    private ApiService apiService;
    private BeautyContract.View view;

    @Inject
    public BeautyPresenter(ApiService apiService, BeautyContract.View view) {
        this.apiService = apiService;
        this.view = view;
    }

    @Override
    public void getBeautyList(int pagenum) {
        apiService.BeautyPict(pagenum).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new ProgressSubscriber<GankBean<GankBeauty>>() {
            @Override
            public void onNext(GankBean<GankBeauty> gankBeautyGankBean) {
                super.onNext(gankBeautyGankBean);
                view.updateListUI(gankBeautyGankBean.getResults());
            }
        });
    }
}
