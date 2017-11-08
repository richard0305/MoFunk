package ylj.mofunk.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ylj.mofunk.di.modules.ApplicationModule;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:24.
 * Email: 2256669598@qq.com
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
}
