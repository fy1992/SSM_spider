package github.intercaptor;

import cn.dahe.rm.model.User;
import cn.dahe.rm.util.ResourcesUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author 李新栋 lxd2773@163.com
 * @Date 2017/4/14 8:47
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(SessionInterceptor.class);

    private List<String> excludedUrls;

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        /**
         * 模拟登录用户
         * TODO 后期注意去掉
         */
        /*User user = new User();
        user.setId(1);
        user.setCnName("晓明");
        user.setIcon("这是地址");
        user.setPhone("10086");*/

        /*User user = (User) session.getAttribute(ResourcesUtils.getLoginUser());
        logger.info("SessionInterceptor user:"+user.toString());*/
//        user.setPhone("10086");
       /* user.setId(8);
        user.setCnName("LEX");
        user.setIcon("这是地址");
        user.setPhone("18600000000");*/

//        session.setAttribute(ResourcesUtils.getLoginUser(), user);

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        logger.info("-----------拦截的url------------------------" + url);
        boolean flag = false;

        for (String excludeUrl : excludedUrls) {
            if (url.indexOf(excludeUrl) >= 0) {
                return true;
            }
        }
        User loginUser = (User) session.getAttribute(ResourcesUtils.getLoginUser());
        if (loginUser == null) {
            //登录页面跳出iframe
            PrintWriter out = response.getWriter();
            out.write("<script>window.parent.location.href='" + request.getContextPath() + "/login'</script>");
            logger.info(request.getContextPath() + "/login");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
//        return true;
    }
}
