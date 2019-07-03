package org.zjw.web.util;

/**
 * Created by zhoujiawei on 2019/4/3.
 */
public class StringUtils {
    /**
     * 判断是否gbk编码
     * java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(strName);
     * @return 是否是乱码 true乱码
     */
    public static boolean isMessyCode(String strName) {
        return !java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(strName);

    }
}
