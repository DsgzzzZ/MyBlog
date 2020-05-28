package top.arieslee.myblog.controller;

import top.arieslee.myblog.modal.VO.UserVo;
import top.arieslee.myblog.utils.MapCache;
import top.arieslee.myblog.utils.TaleUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName BaseController
 * @Description 全局控制器，封装基本页面控制层接口
 * @Author Aries
 * @Date 2019/5/10 16:02
 * @Version 1.0
 **/
public class BaseController {

    //主题属性
    public static String THEME = "themes/default";

    //全局缓存池
    public static MapCache cachePool=MapCache.getCachePool();

    /**
     * @Description : 页面跳转控制
     **/
    protected String rend(String viewName) {
        return THEME + "/" + viewName;
    }

    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }

    /**
     * @Description : 分页标题控制
     **/
    protected void title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
    }

    /**
     * 获取请求绑定的登录对象
     * @param request
     * @return
     */
    public UserVo user(HttpServletRequest request) {
        return TaleUtils.getLoginUser(request);
    }

    public Integer getUid(HttpServletRequest request){
        return this.user(request).getUid();
    }

    /**
     * @Description : 404页面返回
     **/
    protected String page404() {
        return "common/404_page";
    }
}
