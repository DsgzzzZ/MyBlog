package top.arieslee.myblog.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.arieslee.myblog.modal.VO.MetaVo;

/**
 * 后台公共函数
 * <p>
 * Created by 13 on 2019/5/10.
 */
@Component
public final class AdminCommons {

    /**
     * 判断category和cat的交集
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(MetaVo category, String cats) {
        String[] arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    public static String rand_color() {
        int r = Tools.rand(0, COLORS.length - 1);
        return COLORS[r];
    }



}
