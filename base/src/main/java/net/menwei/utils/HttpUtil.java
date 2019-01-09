package net.menwei.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {
    private static final String IE10 = "MSIE 10.0";
    private static final String IE9 = "MSIE 9.0";
    private static final String IE8 = "MSIE 8.0";
    private static final String IE7 = "MSIE 7.0";
    private static final String IE6 = "MSIE 6.0";
    private static final String MAXTHON = "Maxthon";
    private static final String QQ = "QQBrowser";
    private static final String GREEN = "GreenBrowser";
    private static final String SE360 = "360SE";
    private static final String FIREFOX = "Firefox";
    private static final String OPERA = "Opera";
    private static final String CHROME = "Chrome";
    private static final String SAFARI = "Safari";
    private static final String OTHER = "其它";
    public static final String NGINX_REMOTE_ADDR = "nginx_remote_addr";
    private static final String[] BROWSER_TYPE = new String[]{"MSIE 6.0", "MSIE 7.0", "MSIE 8.0", "MSIE 9.0", "MSIE 10.0", "Maxthon", "QQBrowser", "GreenBrowser", "360SE", "Firefox", "Opera", "Chrome", "Safari"};

    public HttpUtil() {
    }

    public static String getParamStr(HttpServletRequest request) {
        String[] UNDEAL_PARAM = new String[]{"userAccount", "userPass"};
        StringBuffer sbArgs = new StringBuffer();
        Enumeration paramNames = request.getParameterNames();

        try {
            while (paramNames.hasMoreElements()) {
                String e = (String) paramNames.nextElement();
                String paramValue = URLEncoder.encode(request.getParameter(e), "UTF-8");
                boolean isHave = false;
                String[] arr$ = UNDEAL_PARAM;
                int len$ = UNDEAL_PARAM.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String undealParam = arr$[i$];
                    if (e.contains(undealParam)) {
                        isHave = true;
                    }
                }

                if (!isHave) {
                    sbArgs.append(e);
                    sbArgs.append("=");
                    sbArgs.append(paramValue);
                    sbArgs.append("&");
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        if (sbArgs.length() > 0) {
            sbArgs.deleteCharAt(sbArgs.length() - 1);
        }

        return sbArgs.toString();
    }


    public static void setSessionAttr(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    public static void removeSessionAttr(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    public static Object getSessionAttr(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    public static String getRedirectUrl(HttpServletRequest request, String url) {
        return getRedirectUrl(request, url, (Map) null);
    }

    public static String getRedirectUrl(HttpServletRequest request, String url, Map<String, Object> paramMap) {
        String contextPath = request.getContextPath();
        StringBuilder path = new StringBuilder();
        path.append("redirect:/").append(contextPath).append(url);
        if (paramMap == null) {
            return path.toString();
        } else {
            path.append("?");
            Iterator keyIterator = paramMap.keySet().iterator();
            String key = "";

            try {
                while (keyIterator.hasNext()) {
                    key = (String) keyIterator.next();
                    path.append(key).append("=").append(URLEncoder.encode(paramMap.get(key).toString(), "UTF-8")).append("&");
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }

            return path.substring(0, path.length() - 1);
        }
    }

    public static Map getAjaxSuccessResult() {
        return getAjaxResult(true, (Object) null, (Object) null);
    }

    public static Map getAjaxSuccessResult(Object data) {
        return getAjaxResult(true, data, (Object) null);
    }

    public static Map getAjaxErrorResult(Object exception) {
        return getAjaxResult(false, (Object) null, exception);
    }

    public static Map getAjaxResult(boolean result, Object data, Object exception) {
        HashMap resultMap = new HashMap();
        resultMap.put("success", result);
        resultMap.put("data", data);
        resultMap.put("exception", exception);
        return resultMap;
    }

    public static void pushInfo(HttpServletResponse response, String scriptHtml) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter e = response.getWriter();
            e.write("<script type=\"text/javascript\">\r\n");
            e.write(scriptHtml);
            e.write("\r\n</script>\r\n");
            e.flush();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static void pushInfo(HttpServletResponse response, String dialogMsg, String winLocation) {
        StringBuilder scriptHtml = new StringBuilder();
        scriptHtml.append("alert('").append(dialogMsg).append("');");
        if (winLocation != null) {
            scriptHtml.append("window.location.href='").append(winLocation).append("';");
        }

        pushInfo(response, scriptHtml.toString());
    }

    public static String getNginxClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("nginx_remote_addr");
        return clientIp == null ? request.getRemoteAddr() : clientIp;
    }

    public static String getBrowseType(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");
        String[] arr$ = BROWSER_TYPE;
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String type = arr$[i$];
            if (userAgent.indexOf(type) != -1) {
                return type;
            }
        }

        return "其它";
    }

    public static final String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }

        return ip;
    }
}
