package ylj.mofunk.di.components;

import dagger.Component;
import ylj.mofunk.view.MainActivity;
import ylj.mofunk.di.modules.MainModule;
import ylj.mofunk.di.scopes.UserScope;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:27.
 * Email: 2256669598@qq.com
 */
@UserScope
@Component(modules = MainModule.class,dependencies = NetComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
