package top.arieslee.myblog.modal.VO;

/**
 * @ClassName ContentVo
 * @Description content表实体类
 * @Author Aries
 * @Date 2019/5/11 8:36
 * @Version 1.0
 **/
public class ContentVo {
    /**
     * @Description : 内容id
     **/
    private Integer cid;
    /**
     * @Description : 内容标题
     **/
    private String title;
    /**
     * @Description :缩写
     **/
    private String slug;
    /**
     * @Description : 创建时间戳
     **/
    private Long created;
    /**
     * @Description : 最后一次修改时间戳
     **/
    private Long modified;
    /**
     * @Description : 内容
     **/
    private String content;
    /**
     * @Description : 内容所属者id
     **/
    private Integer authorId;
    /**
     * @Description : 内容类型
     **/
    private String type;
    /**
     * @Description : 内容状态
     **/
    private String status;
    /**
     * @Description : 内容标签
     **/
    private String tags;
    /**
     * @Description :分类
     **/
    private String categories;
    /**
     * @Description : 点击次数
     **/
    private Integer hits;
    /**
     * @Description : 评论数量
     **/
    private Integer commentsNum;
    /**
     * @Description : 是否允许评论
     **/
    private Boolean allowComment;
    /**
     * @Description : 是否允许ping
     **/
    private Boolean allowPing;
    /**
     * @Description : 是否聚合
     **/
    private Boolean allowFeed;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getAllowPing() {
        return allowPing;
    }

    public void setAllowPing(Boolean allowPing) {
        this.allowPing = allowPing;
    }

    public Boolean getAllowFeed() {
        return allowFeed;
    }

    public void setAllowFeed(Boolean allowFeed) {
        this.allowFeed = allowFeed;
    }

    @Override
    public String toString() {
        return "ContentVo{" +
                "cid=" + cid +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                ", hits=" + hits +
                ", commentsNum=" + commentsNum +
                ", allowComment=" + allowComment +
                ", allowPing=" + allowPing +
                ", allowFeed=" + allowFeed +
                '}';
    }
}
