package com.clearliang.frameworkdemo.model.api;

/**
 * Created by ClearLiang on 2018/11/23
 * <p>
 * Function :
 */

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器，修改请求header
 */
public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Cookie", "sessionid=8cbeedrjhvlbxpbnfb2r5dbyd4234xeu; expires=Fri, 07-Dec-2018 09:24:05 GMT; HttpOnly; Max-Age=1209600; Path=/")
                .build();

        LogUtils.v("zcb", "request:" + request.toString());
        LogUtils.v("zcb", "request headers:" + request.headers().toString());

        return chain.proceed(request);
    }
}