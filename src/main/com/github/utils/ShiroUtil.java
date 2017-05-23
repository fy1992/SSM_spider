package github.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro工具类
 */
public class ShiroUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShiroUtil.class);

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static void setSessionAttribute(Object key,Object value){
        getSession().setAttribute(key,value);
    }


    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

    public static boolean isLogin(){
        return SecurityUtils.getSubject().getPrincipal()!=null;
    }


    public static String md5(String pwd,String salt){
        String p = null;
        p = new Md5Hash(pwd,salt).toHex();
        return  p;
    }


}
