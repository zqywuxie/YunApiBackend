package yunapiCommon.utils;


import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author wuxie
 * @date 2023/5/24 17:24
 * @description 该文件的描述 todo
 */
public class SignUtil {

    public static String sign(String body, String secretKey) {
        String content = body + secretKey;
        return DigestUtil.md5Hex(content);
    }
}
