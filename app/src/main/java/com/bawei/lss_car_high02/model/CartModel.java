package com.bawei.lss_car_high02.model;


import com.bawei.lss_car_high02.Bean.CartBean;
import com.bawei.lss_car_high02.net.CGSB;
import com.bawei.lss_car_high02.net.RetrofitHepler;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartModel {
    public void getCart(int uid, String str, final CGSB<CartBean> cgsb){
        Flowable<CartBean> flowable = RetrofitHepler.getSerViceAPI().getCart(uid, str);
        flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CartBean>() {
                    @Override
                    public void accept(CartBean cartBean) throws Exception {
                        cgsb.chengGong(cartBean);
                    }
                });
    }
}
