package ylj.mofunk.presenter;

import ylj.mofunk.model.Entity.LiveAppIndexInfo;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 10:50.
 * Email: 2256669598@qq.com
 */

public interface OneIdListContract {
    interface Presenter{
        void GetOneIdlist(String qustion,String code);
    }
    interface View{
        void OneIDList(LiveAppIndexInfo ids);
    }
}
