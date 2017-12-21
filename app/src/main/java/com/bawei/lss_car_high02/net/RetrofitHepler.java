package com.bawei.lss_car_high02.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHepler {
        public static OkHttpClient okHttpClient;
        public static ServiceAPI serViceAPI;
      
        /** 
         * 优先执行 
         */  
        static {  
            getOkHttpClient();  
        }  
        public static OkHttpClient getOkHttpClient(){  
            if(okHttpClient==null){  
                synchronized (OkHttpClient.class){  
                    if(okHttpClient==null){  
                        okHttpClient=new OkHttpClient();  
                    }  
                }  
            }  
            return okHttpClient;  
        }  
        public static ServiceAPI getSerViceAPI(){
      
            if(serViceAPI==null){  
                synchronized (OkHttpClient.class){  
                    if(serViceAPI==null){  
                       serViceAPI=onCreate(ServiceAPI.class,API.HOST);
                    }  
                }  
            }  
            return serViceAPI;  
        }  
      
        public static <T> T onCreate(Class<T>tClass,String url){  
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)  
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();  
      
            return retrofit.create(tClass);  
        }  
    }  