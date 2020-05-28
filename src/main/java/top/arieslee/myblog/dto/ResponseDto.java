package top.arieslee.myblog.dto;

/**
 * @ClassName ResponseDto
 * @Description 封装响应请求传输数据
 * @Author Aries
 * @Date 2019/5/9 15:23
 * @Version 1.0
 **/
public class ResponseDto<T> {

    /**
     * 服务器响应数据
     */
    private T payload;

    /**
     * @Description 响应状态
     **/
    private boolean success;

    /**
     * @Description 状态码
     **/
    private int code = -1;

    /**
     * @Description 封装数据
     **/
    private T data;

    /**
     * @Description 响应信息
     **/
    private String msg;

    /**
     * 服务器响应时间
     */
    private long timestamp;

    public ResponseDto() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public ResponseDto(boolean success) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
    }

    public ResponseDto(boolean success, T payload) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
    }

    public ResponseDto(boolean success, T payload, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
        this.code = code;
    }

    public ResponseDto(boolean success, String msg) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
    }

    public ResponseDto(boolean success, String msg, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResponseDto ok() {
        return new ResponseDto(true);
    }

    public static <T> ResponseDto ok(T payload) {
        return new ResponseDto(true, payload);
    }

    public static <T> ResponseDto ok(int code) {
        return new ResponseDto(true, null, code);
    }

    public static <T> ResponseDto ok(T payload, int code) {
        return new ResponseDto(true, payload, code);
    }

    public static ResponseDto fail() {
        return new ResponseDto(false);
    }

    public static ResponseDto fail(String msg) {
        return new ResponseDto(false, msg);
    }

    public static ResponseDto fail(int code) {
        return new ResponseDto(false, null, code);
    }

    public static ResponseDto fail(int code, String msg) {
        return new ResponseDto(false, msg, code);
    }
}
