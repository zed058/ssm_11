package cn.zed058.smbms.interceptor;

import cn.zed058.smbms.pojo.User;
import cn.zed058.smbms.util.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor extends HandlerInterceptorAdapter {

    // preHandle拦截的方法 ，返回true就是放行，返回false就是拦截此请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 把未登录的用户请求拦截
        User user = (User) request.getSession().getAttribute(Constant.CURRENT_USR);
        if(user==null){
            // 说明用户没有登录
            request.setAttribute("error","您还没有登录，无权访问该页面，请先去登录");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request,response);
            return false;
        }
        return true;
    }
}
