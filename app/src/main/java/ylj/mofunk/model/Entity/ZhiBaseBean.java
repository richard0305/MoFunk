package ylj.mofunk.model.Entity;

/**
 * Created by 我的样子平平无奇 on 2017/11/7 10:57.
 * Email: 2256669598@qq.com
 */

public class ZhiBaseBean {
//    "msg": "ok",
//            "result": {
//        "content": "你好，我是小i机器人，很高兴认识你。",
//                "relquestion": "",
//                "type": "聊天"
//    },
//            "status": "0"

    private String msg;
    private String status;
    private BaseZhi result;

    @Override
    public String toString() {
        return "ZhiBaseBean{" +
                "msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BaseZhi getResult() {
        return result;
    }

    public void BaseZhi(BaseZhi result) {
        this.result = result;
    }

    public  class BaseZhi{
        private String content;
        private String relquestion;
        private String type;

        @Override
        public String toString() {
            return "BaseZhi{" +
                    "content='" + content + '\'' +
                    ", relquestion='" + relquestion + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRelquestion() {
            return relquestion;
        }

        public void setRelquestion(String relquestion) {
            this.relquestion = relquestion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
