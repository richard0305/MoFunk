package ylj.mofunk.model.Entity;

/**
 * Created by 我的样子平平无奇 on 2017/10/26 16:12.
 * Email: 2256669598@qq.com
 */

public class OneListBean {
    private OneNewBean data;
    private int res;

    public OneNewBean getData() {
        return data;
    }

    public void setData(OneNewBean data) {
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
        return "OneListBean{" +
                "data=" + data +
                ", res=" + res +
                '}';
    }
}
