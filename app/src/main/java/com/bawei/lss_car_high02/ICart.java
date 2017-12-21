package com.bawei.lss_car_high02;


import com.bawei.lss_car_high02.Bean.CartBean;

import java.util.List;

public interface ICart {
    void showlist(List<CartBean.DataBean> grouplist, List<List<CartBean.DataBean.ListBean>> childlist);
}
