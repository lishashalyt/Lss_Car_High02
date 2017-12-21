package com.bawei.lss_car_high02.presenter;


import com.bawei.lss_car_high02.Bean.CartBean;
import com.bawei.lss_car_high02.ICart;
import com.bawei.lss_car_high02.model.CartModel;
import com.bawei.lss_car_high02.net.CGSB;

import java.util.ArrayList;
import java.util.List;

public class CartPersenter {

    private final CartModel cartModel;
       private ICart iCart;
    public CartPersenter(ICart iCart) {
        cartModel = new CartModel();
        this.iCart=iCart;

    }
    public void getCart(){
        cartModel.getCart(3043, "android", new CGSB<CartBean>() {
            @Override
            public void chengGong(CartBean cartBean) {
                List<CartBean.DataBean> grouplist = cartBean.getData();
                List<List<CartBean.DataBean.ListBean>> childlist=new ArrayList<>();
                for (int i = 0; i <grouplist.size() ; i++) {
                    CartBean.DataBean dataBean = grouplist.get(i);
                    List<CartBean.DataBean.ListBean> list = dataBean.getList();
                    childlist.add(list);
                }
                iCart.showlist(grouplist,childlist);
            }
        });
    }
}
