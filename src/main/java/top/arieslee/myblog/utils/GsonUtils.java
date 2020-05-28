package top.arieslee.myblog.utils;

import com.google.gson.Gson;

/**
 * json转换工具
 * Created by Administrator on 2019/5/13 013.
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

    public static String toJsonString(Object object){
      return object==null?null:gson.toJson(object);
    }
}
