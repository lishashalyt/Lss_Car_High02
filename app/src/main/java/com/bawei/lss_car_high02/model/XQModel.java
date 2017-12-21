package com.bawei.lss_car_high02.model;


import com.bawei.lss_car_high02.Bean.Tianjia;
import com.bawei.lss_car_high02.Bean.XQBean;
import com.bawei.lss_car_high02.net.CGSB;
import com.bawei.lss_car_high02.net.RetrofitHepler;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XQModel implements XQjieKou{
    @Override
    public void getXQ(int pid, String str, final CGSB<XQBean> cgsb){
        Flowable<XQBean> flowable = RetrofitHepler.getSerViceAPI().getXQ(pid, str);
        flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<XQBean>() {
                    @Override
                    public void accept(XQBean xqBean) throws Exception {
                        cgsb.chengGong(xqBean);
                    }
                });
    }
    public void geTJ(int uid,int pid, String str, final CGSB<Tianjia>cgsb){
        Flowable<Tianjia> flowable = RetrofitHepler.getSerViceAPI().getMsg(uid, pid, str);
        flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Tianjia>() {
                    @Override
                    public void accept(Tianjia tianjia) throws Exception {
                        cgsb.chengGong(tianjia);
                    }
                });
    }
}
