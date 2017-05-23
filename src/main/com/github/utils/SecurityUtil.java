package github.utils;

import cn.dahe.rm.advice.DaheException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityUtil {
    private static final Log logger = LogFactory.getLog(SecurityUtil.class);
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static void main(String[] args) {

    }

    /**
     * 加密-base64
     *
     * @param str
     * @return
     */
    public static String base64Encode(String str) {
        String code = null;
        try {
            code = encoder.encodeToString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.info("加密失败");
            //加密失败返回原字符串
            code = str;
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 解密-base64
     *
     * @param str
     * @return
     */
    public static String base64Decode(String str) {
        byte[] code = null;
        try {
            code = decoder.decode(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (code != null) {
            try {
                return new String(code, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            String md5str = new BigInteger(1, md.digest()).toString(16);
            return StringUtils.leftPad(md5str, 32, "0");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String username, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(username.getBytes());
            md.update(password.getBytes());
            String md5str = new BigInteger(1, md.digest()).toString(16);
            return StringUtils.leftPad(md5str, 32, "0");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String hexSHA1(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes("utf-8"));
            byte[] digest = md.digest();
            return byteToHexString(digest);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String byteToHexString(byte[] bytes) {
        return String.valueOf(Hex.encodeHex(bytes));
    }

    /**
     * url加密
     * @param data
     * @return
     */
    public static String urlEncode(String data){
        try {
            return URLEncoder.encode(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new DaheException("urlEncode  is error ");
        }
    }

    /**
     * URL解密
     * @param data
     * @return
     */
    public static String urlDecode(String data){
        try {
            return URLDecoder.decode(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw  new DaheException("decodeurl  is error");
        }
    }

}
