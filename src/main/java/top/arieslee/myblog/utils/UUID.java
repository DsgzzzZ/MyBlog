package top.arieslee.myblog.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName UUID
 * @Description 对Java UUID工具类进行封装
 * @Author Aries
 * @Date 2019/5/15 15:46
 * @Version 1.0
 **/
public class UUID {

    static Random r = new Random();

    /**
     * 根据一个范围，生成一个随机的整数
     *
     * @param min
     *            最小值（包括）
     * @param max
     *            最大值（包括）
     * @return 随机数
     */
    public static int random(int min, int max) {
        return r.nextInt(max - min + 1) + min;
    }
    //64进制表示
    private static final char[] _UUID64 = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();
    //32进制表示
    private static final char[] _UUID32 = "0123456789abcdefghijklmnopqrstuv".toCharArray();

    private static final char[] _UU16 = "0123456789abcdef".toCharArray();

    public static String UUID64() {
        return UUID64(java.util.UUID.randomUUID());
    }

    /**
     * @return java.lang.String
     * @Description 用64进制来表示UUID
     * @Param
     **/
    public static String UUID64(java.util.UUID uu) {
        //用于存放压缩后的uuid值
        char[] uuid64 = new char[22];
        //uuid64数组偏移指针
        int index = 0;
        //获取源uuid的前64位和后64位(返回的是10进制数)
        long most = uu.getMostSignificantBits();
        long least = uu.getLeastSignificantBits();
        //进行与运算(&)的基值，由于我们使用64进制，即可用6bit表示，所以我们取63（111111）来进行切割
        long mask = 63L;

        //将前64位切割10次，还剩余4位参与后64位切割
        for (int off = 58; off >= 4; off -= 6) {
            long hex = (most & (mask << off)) >>> off;//截取高位后不要忘记无符号右移回对应位数
            uuid64[index++] = _UUID64[(int) hex];
        }
        //前64位的低4位和后64位的高2位拼接
        int i=(int) (((most & 15) << 2) | ((least & (3 << 62))>>>62));
        uuid64[index++] = _UUID64[i];
        //前64位剩余的62位先分割10次，剩余2位单独处理
        for (int off = 56; off >= 2; off -= 6) {
            long hex = (least & (mask << off)) >>> off;
            uuid64[index++] = _UUID64[(int) hex];
        }
        //处理剩余2位
        uuid64[index] = _UUID64[(int) least & 3];
        //返回字符串
        return uuid64.toString();
    }

    public static long parseUnsignedLong(String s, int radix) {
        int len = s.length();
        long first = Long.parseLong(s.substring(0, len - 1), radix);
        int second = Character.digit(s.charAt(len - 1), radix);
        return first * radix + second;
    }

    /**
     * 将一个 UU64 表示的紧凑字符串，变成 UU16 表示的字符串
     *
     * <pre>
     * 每次取2个字符，恢复成3个byte，重复10次， 最后一次，是用最后2个字符，恢复回2个byte </prev>
     *
     * @param uu64
     *            uu64 64进制表示的 UUID, 内容为 [\\-0-9a-zA-Z_]
     * @return 16进制表示的紧凑格式的 UUID
     */
    public static String UU16FromUU64(String uu64) {
        byte[] bytes = new byte[32];
        char[] cs = uu64.toCharArray();
        int index = 0;
        // 每次取2个字符，恢复成3个byte，重复10次，
        for (int i = 0; i < 10; i++) {
            int off = i * 2;
            char cl = cs[off];
            char cr = cs[off + 1];
            int l = Arrays.binarySearch(_UUID64, cl);
            int r = Arrays.binarySearch(_UUID64, cr);
            int n = (l << 6) | r;
            bytes[index++] = (byte) ((n & 0xF00) >>> 8);
            bytes[index++] = (byte) ((n & 0xF0) >>> 4);
            bytes[index++] = (byte) (n & 0xF);
        }
        // 最后一次，是用最后2个字符，恢复回2个byte
        char cl = cs[20];
        char cr = cs[21];
        int l = Arrays.binarySearch(_UUID64, cl);
        int r = Arrays.binarySearch(_UUID64, cr);
        int n = (l << 2) | r;
        bytes[index++] = (byte) ((n & 0xF0) >>> 4);
        bytes[index++] = (byte) (n & 0xF);

        // 返回 UUID 对象
        char[] names = new char[32];
        for (int i = 0; i < bytes.length; i++)
            names[i] = _UU16[bytes[i]];
        return new String(names);
    }

    /**
     * 从一个 UU64 恢复回一个 UUID 对象
     *
     * @param uu64
     *            64进制表示的 UUID, 内容为 [\\-0-9a-zA-Z_]
     * @return UUID 对象
     */
    public static java.util.UUID fromUU64(String uu64) {
        String uu16 = UU16FromUU64(uu64);
        return java.util.UUID.fromString(UU(uu16));
    }

    /**
     * 将紧凑格式的 UU16 字符串变成标准 UUID 格式的字符串
     *
     * @param uu16
     * @return 标准 UUID 字符串
     */
    public static String UU(String uu16) {
        StringBuilder sb = new StringBuilder();
        sb.append(uu16.substring(0, 8));
        sb.append('-');
        sb.append(uu16.substring(8, 12));
        sb.append('-');
        sb.append(uu16.substring(12, 16));
        sb.append('-');
        sb.append(uu16.substring(16, 20));
        sb.append('-');
        sb.append(uu16.substring(20));
        return sb.toString();
    }

    public static String UU32(java.util.UUID uu) {
        StringBuilder sb = new StringBuilder();
        long m = uu.getMostSignificantBits();
        long l = uu.getLeastSignificantBits();
        for (int i = 0; i < 13; i++) {
            sb.append(_UUID32[(int) (m >> ((13 - i - 1) * 5)) & 31]);
        }
        for (int i = 0; i < 13; i++) {
            sb.append(_UUID32[(int) (l >> ((13 - i - 1)) * 5) & 31]);
        }
        return sb.toString();
    }

    public static String UU32() {
        return UU32(java.util.UUID.randomUUID());
    }

    public static java.util.UUID fromUU32(String u32) {
        return new java.util.UUID(parseUnsignedLong(u32.substring(0, 13), 32),
                parseUnsignedLong(u32.substring(13), 32));
    }

    /**
     * 返回指定长度由随机数字+小写字母组成的字符串
     *
     * @param length
     *            指定长度
     * @return 随机字符串
     */
    public static String captchaChar(int length) {
        return captchaChar(length, false);
    }

    /**
     * 返回指定长度随机数字+字母(大小写敏感)组成的字符串
     *
     * @param length
     *            指定长度
     * @param caseSensitivity
     *            是否区分大小写
     * @return 随机字符串
     */
    public static String captchaChar(int length, boolean caseSensitivity) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();// 随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(caseSensitivity ? 3 : 2);
            // 目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);// 仅仅会生成0~9, 0~9的ASCII为48~57
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 97;// 保证只会产生ASCII为97~122(a-z)之间的整数,
                    sb.append((char) data);
                    break;
                case 2: // caseSensitivity为true的时候, 才会有大写字母
                    data = randdata.nextInt(26) + 65;// 保证只会产生ASCII为65~90(A~Z)之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 返回指定长度随机数字组成的字符串
     *
     * @param length
     *            指定长度
     * @return 随机字符串
     */
    public static String captchaNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

}
