package ylj.mofunk.model.Entity;

import java.util.List;

/**
 * Created by 我的样子平平无奇 on 2017/7/21 10:25.
 * Email: 2256669598@qq.com
 */

public class GankBean<E> {
    private boolean error;
    private List<E> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<E> getResults() {
        return results;
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
