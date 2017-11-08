package ylj.mofunk.di.components;

import dagger.Component;
import ylj.mofunk.di.modules.OneIdListModule;
import ylj.mofunk.di.scopes.UserScope;
import ylj.mofunk.view.OneFragment;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 11:22.
 * Email: 2256669598@qq.com
 */
@UserScope
@Component(modules = OneIdListModule.class,dependencies = NetComponent.class)
public interface OneIDListComponent {
    void inject(OneFragment oneFragment);
}
