package ylj.mofunk.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 10:23.
 * Email: 2256669598@qq.com
 */
@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplication(){
        return context;
    }
}
