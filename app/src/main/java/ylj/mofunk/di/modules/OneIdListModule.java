package ylj.mofunk.di.modules;

import dagger.Module;
import dagger.Provides;
import ylj.mofunk.presenter.OneIdListContract;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 11:19.
 * Email: 2256669598@qq.com
 */
@Module
public class OneIdListModule {
    private OneIdListContract.View view;

    public OneIdListModule(OneIdListContract.View view) {
        this.view = view;
    }
    @Provides
    public OneIdListContract.View provideOneIDList(){
        return view;
    }
}
