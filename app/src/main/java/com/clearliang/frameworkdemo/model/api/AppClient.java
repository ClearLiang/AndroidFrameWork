package com.clearliang.frameworkdemo.model.api;

import android.os.Environment;

import com.blankj.utilcode.util.SPUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.clearliang.frameworkdemo.model.api.ApiService.API_SERVER_URL;
import static com.clearliang.frameworkdemo.view.base.GlobalConstants.SP_TOKEN;
import static com.clearliang.frameworkdemo.view.base.GlobalConstants.SP_USERINFO;


/**
 * @作者 ClearLiang
 * @日期 2018/4/26
 * @描述 @desc
 **/

public class AppClient {
    public static Retrofit retrofit = null;

    private static final long cacheSize = 1024 * 1024 * 4;// 缓存文件最大限制大小4M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/okhttpcaches"; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //


    public static Retrofit retrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存
             */
            builder.cache(cache);//设置缓存目录和缓存大小


            /**
             *  拦截器
             *  公共参数
             */
            Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request;
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            // Provide your custom parameter here
                            // .addQueryParameter("token", User.getUser().getToken())
                            .addQueryParameter("token", SPUtils.getInstance(SP_USERINFO).getString(SP_TOKEN))
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                }
            };
            builder.addInterceptor(addQueryParameterInterceptor);

            /**
             * 认证
             * 401，认证
             */
            /*builder.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic("username", "password");
                    return response.request().newBuilder().header("Authorization", credential).build();
                }
            });*/

            /**
             * 设置头
             */

            /**
             * Log信息拦截器
             */
            //builder.addInterceptor(new LogInterceptor());

            /**
             * 设置cookie
             */
            /*builder.cookieJar(new CookieJar() {

                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
                    CookieUtil.getCookieUtil().saveCookie(httpUrl.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = CookieUtil.getCookieUtil().getCookieStore().get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            });*/

            /**
             * 设置超时和重连
             */
            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //以上设置结束，build()
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER_URL)
                    //设置 Json 转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    //RxJava 适配器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
