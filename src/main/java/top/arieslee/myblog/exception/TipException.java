package top.arieslee.myblog.exception;

/**
 * @ClassName TipException
 * @Description 自定义异常提示类
 * @Author Aries
 * @Date 2019/5/16 11:09
 * @Version 1.0
 **/
public class TipException extends RuntimeException{

    public TipException() {
    }

    public TipException(String message) {
        super(message);
    }

    public TipException(String message, Throwable cause) {
        super(message, cause);
    }

    public TipException(Throwable cause) {
        super(cause);
    }

}
