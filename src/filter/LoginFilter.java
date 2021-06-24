package filter;

/**
 * assn 402
 * yiqingw
 */

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    /**
     * helper method 查找指定名字的cookie
     * @param cookies
     * @param name
     * @return
     */
    public static Cookie getCookieByName(Cookie[] cookies, String name) {
        if (null != cookies && 0 != cookies.length) {
            for (Cookie c : cookies) {
                if (name.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 重写doFilter
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        // 查看user是否成功登录
        Object user = session.getAttribute("user");
        // 获取servlet的请求路径
        String servletPath = httpServletRequest.getServletPath();
        // 获取cookie，查看是否在免登录期限内
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = httpServletRequest.getCookies();
        cookie = getCookieByName(cookies, "userName");
        // 若没有登录，回到登录页面；已经登录则放行
        if (null == user && !servletPath.contains("login") && null == cookie) {
           servletRequest.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
