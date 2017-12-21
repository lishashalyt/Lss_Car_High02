package com.bawei.lss_car_high02.model;


import com.bawei.lss_car_high02.Bean.XQBean;
import com.bawei.lss_car_high02.net.CGSB;

public interface XQjieKou {
    public void getXQ(int pid, String str, final CGSB<XQBean> cgsb);
}
