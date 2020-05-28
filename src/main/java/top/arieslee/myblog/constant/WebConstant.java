package top.arieslee.myblog.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WebConstant
 * @Description 存放项目常量值
 * @Author Aries
 * @Date 2019/5/10 16:51
 * @Version 1.0
 **/
public class WebConstant {

    /**
     * @Description 管理员用户名cookie值
     **/
    public static final String USER_IN_COOKIE = "S_L_ID";

    /**
     * @Description session用户属性值
     **/
    public static final String LOGIN_SESSION_KEY ="login_user";

    /**
     * @Description AES盐值（密钥）
     **/
    public static final String AES_SALT="1a2b3c4d5e6f7g8h9i0j";

    /**
     * @Description :设置分页页码最大值
     **/
    public static final int MAX_PAGE = 100;

    /**
     * @Description 每页上限数
     **/
    public static final int MAX_LIMIT = 100;

    /**
     * @Description : 保存前台页面配置信息
     **/
    public static Map<String, String> initConfig = new HashMap<>();

    /**
     * @Description 临界点击数
     **/
    public static final int CRITICAL_HIT = 10;

    /**
     * @Description 最大文章标题长度
     **/
    public static final int MAX_TITLE_LENGTH=100;

    /**
     * @Description 最大文章内容长度
     **/
    public static final int MAX_CONTENT_LENGTH=200000;

    /**
     * 最大获取文章条数
     */
    public static final int MAX_POSTS = 9999;

    /**
     * 上传文件最大1M
     */
    public static Integer MAX_FILE_SIZE = 1048576;

    /**
     * 成功返回
     */
    public static String SUCCESS_RESULT = "SUCCESS";

    /**
     * 同一篇文章在2个小时内无论点击多少次只算一次阅读
     */
    public static Integer HITS_LIMIT_TIME = 7200;
}