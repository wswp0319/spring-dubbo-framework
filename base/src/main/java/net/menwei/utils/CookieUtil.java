package net.menwei.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public CookieUtil() {
    }

    public static void setCookie(HttpServletResponse response, String domain, String name, String value) {
        setCookie(response, domain, name, value, -1);
    }

    public static void setCookie(HttpServletResponse response, String domain, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        //cookie.setDomain(PropertiesUtil.getConfigInfo("DOMAIN_PATH"));
        cookie.setDomain(domain);
        cookie.setPath("/");
        if (maxAge >= 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String domain, String name) {
        setCookie(response, domain, name, (String) null, 0);
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Cookie c = var3[var5];
                if (name.equals(c.getName())) {
                    return c;
                }
            }
        }

        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie == null ? "" : cookie.getValue();
    }
}
