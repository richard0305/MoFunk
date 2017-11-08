package ylj.mofunk.di.modules;

import dagger.Module;
import dagger.Provides;
import ylj.mofunk.presenter.MainContract;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:07.
 * Email: 2256669598@qq.com
 */

@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    public MainContract.View provideMainView(){
        return view;
    }
}
