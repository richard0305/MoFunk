package ylj.mofunk.di.modules;

import dagger.Module;
import dagger.Provides;
import ylj.mofunk.presenter.BeautyContract;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 15:04.
 * Email: 2256669598@qq.com
 */
@Module
public class BeautyModule {
    private BeautyContract.View view;

    public BeautyModule(BeautyContract.View view) {
        this.view = view;
    }
    @Provides
    public BeautyContract.View provideBeautyView(){
        return view;
    }
}
