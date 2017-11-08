package ylj.mofunk.presenter;

import java.util.List;

import ylj.mofunk.model.Entity.GankAndroid;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 09:47.
 * Email: 2256669598@qq.com
 */

public interface MainContract {
    interface Presenter{
        void getAndroidByList(int pagenum);
    }
    interface  View{
        void updateListUI(List<GankAndroid> itemList);
        void showOnFailure();
    }
}
