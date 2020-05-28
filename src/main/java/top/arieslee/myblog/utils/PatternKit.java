package top.arieslee.myblog.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @ClassName PatternKit
 * @Description 正则表达式工具
 * @Author Aries
 * @Date 2019/5/10 16:35
 * @Version 1.0
 **/
public class PatternKit {


    /**
     * @Description 验证邮箱
     * @Param [email：邮箱]
     * @return boolean
     **/
    public static boolean isEmail(String email){
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * @Description : 验证是否有效数字
     **/
    public static boolean isNum(String str) {
        if (str != null && str.trim().length() != 0 && str.matches("\\d*")) {
            return true;
        }
        return false;
    }

    /**
     * @Description 验证是否有效路径
     **/
    public static boolean isPath(String path){
        if(StringUtils.isNotBlank(path)&&path.matches("[0-9A-Za-z_-]+")){
            return true;
        }
        return false;
    }
}
