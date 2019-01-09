package net.menwei.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * 执行拦截器之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.debug("interceptor....在执行前...url:{}", request.getRequestURL());
        return true; //返回false将不会执行了
    }

    /**
     * 调用完处理器，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.debug("interceptor.......url:{}", request.getRequestURL());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug("interceptor....在执行后...url:{}", request.getRequestURL());
    }
}
