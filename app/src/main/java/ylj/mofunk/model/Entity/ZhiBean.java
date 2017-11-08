package ylj.mofunk.model.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 我的样子平平无奇 on 2017/11/6 16:43.
 * Email: 2256669598@qq.com
 */

@Entity
public class ZhiBean {
@Id(autoincrement = true)
    private long id;
    private String content;
    private int type;
    @Generated(hash = 1941941648)
    public ZhiBean(long id, String content, int type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }
    @Generated(hash = 40329324)
    public ZhiBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
