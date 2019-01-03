package com.clearliang.frameworkdemo.utils;

import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;

/**
 * Created by ClearLiang on 2018/12/21
 * <p>
 * Function :单例模式保存cookie
 */
public class CookieUtil {
    private static CookieUtil instance;

    private CookieUtil() {
    }

    public static CookieUtil getCookieUtil() {
        if (instance == null) {
            synchronized (CookieUtil.class) {
                if (instance == null) {
                    instance = new CookieUtil();
                }
            }
        }
        return instance;
    }

    private HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public void saveCookie(String host, List<Cookie> list) {
        cookieStore.put(host, list);
    }

    public List<Cookie> loadCookie(String host) {
        return cookieStore.get(host);
    }

    public HashMap<String, List<Cookie>> getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(HashMap<String, List<Cookie>> cookieStore) {
        this.cookieStore = cookieStore;
    }
}
