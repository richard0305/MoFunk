package ylj.mofunk.model.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by 我的样子平平无奇 on 2017/12/4 09:26.
 * Email: 2256669598@qq.com
 */
@Entity
public class ShareDao implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private long id;
    private String url;
    private String title;
    private String type;
    private String desc;
    private String creattime;
    @Generated(hash = 869155366)
    public ShareDao(long id, String url, String title, String type, String desc,
            String creattime) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.creattime = creattime;
    }

    @Generated(hash = 1019071151)
    public ShareDao() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreattime() {
        return this.creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

}
