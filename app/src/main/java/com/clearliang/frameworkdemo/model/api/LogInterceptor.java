package com.clearliang.frameworkdemo.model.api;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Administrator on 2018/8/4.
 */

public class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);
        //不一定Request与Response对应（多数是的）
        try {
            RequestBody requestBody = request.body();
            Buffer bufferRequest = new Buffer();
            requestBody.writeTo(bufferRequest);
            LogUtils.e("RequestBody", bufferRequest.readString(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bodyString.length() > 2000) {
            for (int i = 0; i < bodyString.length(); i += 2000) {
                if (i + 2000 < bodyString.length()) {
                    LogUtils.e("ResponseBody:" + i, bodyString.substring(i, i + 2000));
                } else {
                    LogUtils.e("ResponseBody:" + i, bodyString.substring(i, bodyString.length()));
                }
            }
        } else {
            LogUtils.e("ResponseBody:", bodyString);
        }
        return response;
    }
}
