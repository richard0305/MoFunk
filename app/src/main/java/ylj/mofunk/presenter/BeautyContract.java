package ylj.mofunk.presenter;

import java.util.List;

import ylj.mofunk.model.Entity.GankBeauty;

/**
 * Created by 我的样子平平无奇 on 2017/8/30 14:59.
 * Email: 2256669598@qq.com
 */

public interface BeautyContract {
    interface Presenter{
     void  getBeautyList(int pagenum);
    }
    interface View{
        void updateListUI(List<GankBeauty> itemList);
        void showOnFailure();
    }

}
