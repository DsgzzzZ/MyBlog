package top.arieslee.myblog.constant;

/**
 * @ClassName Types
 * @Description 存放各种全局类型值
 * @Author Aries
 * @Date 2019/5/11 11:27
 * @Version 1.0
 **/
public enum Types {
    PUBLISH("publish"),
    ARTICLE("post"),
    PAGE("page"),
    TAG("tag"),
    IMAGE("image"),
    FILE("file"),
    CSRF_TOKEN("csrf_token"),
    COMMENT_FREQUENCY("comment_frequency"),
    CATEGORY("category"),
    LINK("link"),
    HITS_FREQUENCY("hits:frequency"),
    /**
     * 附件存放的URL，默认为网站地址，如集成第三方则为第三方CDN域名
     */
    ATTACH_URL("attach_url");

    private String type;

    Types(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
