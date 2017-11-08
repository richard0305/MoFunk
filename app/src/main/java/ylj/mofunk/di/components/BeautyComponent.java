package ylj.mofunk.di.components;

import dagger.Component;
import ylj.mofunk.di.modules.BeautyModule;
import ylj.mofunk.di.scopes.UserScope;
import ylj.mofunk.view.BeautyActivity;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 15:06.
 * Email: 2256669598@qq.com
 */
@UserScope
@Component(modules = BeautyModule.class,dependencies = NetComponent.class)
public interface BeautyComponent {
    void inject(BeautyActivity beautyActivity);
}
