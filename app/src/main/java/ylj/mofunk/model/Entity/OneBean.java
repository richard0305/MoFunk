package ylj.mofunk.model.Entity;

import java.util.List;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 10:27.
 * Email: 2256669598@qq.com
 */

public class OneBean<T> {
    private List<T>data;
    private int res;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "OneBean{" +
                "data=" + data +
                ", res=" + res +
                '}';
    }
}
