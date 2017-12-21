package com.bawei.lss_car_high02.net;


import com.bawei.lss_car_high02.Bean.CartBean;
import com.bawei.lss_car_high02.Bean.Tianjia;
import com.bawei.lss_car_high02.Bean.XQBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dell on 2017/12/19.
 */

public interface ServiceAPI {
    @GET("getProductDetail")
    Flowable<XQBean> getXQ(@Query("pid") int pid, @Query("source") String str);
    @GET("addCart")
    Flowable<Tianjia> getMsg(@Query("uid") int uid, @Query("pid") int pid, @Query("source") String str);
    @GET("getCarts")
    Flowable<CartBean> getCart(@Query("uid") int uid, @Query("source") String str);
}
