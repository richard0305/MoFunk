package ylj.mofunk.model.Entity;

import java.io.Serializable;

/**
 * Created by 我的样子平平无奇 on 2017/9/6 10:38.
 * Email: 2256669598@qq.com
 */

public class ImagePosition implements Serializable {
    private int position;
    private String url;

    public ImagePosition(int position, String url) {
        this.position = position;
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImagePosition{" +
                "position=" + position +
                ", url='" + url + '\'' +
                '}';
    }
}
