package com.bawei.lss_car_high02.presenter;


import com.bawei.lss_car_high02.Bean.Tianjia;
import com.bawei.lss_car_high02.Bean.XQBean;
import com.bawei.lss_car_high02.Iview;
import com.bawei.lss_car_high02.model.XQModel;
import com.bawei.lss_car_high02.net.CGSB;

public class XQPersenter {

    private final XQModel xqModel;
    private Iview iview;
    public XQPersenter(Iview iview) {
        xqModel = new XQModel();
        this.iview=iview;

    }
    public void getXQ(){
        xqModel.getXQ(12, "android", new CGSB<XQBean>() {
            @Override
            public void chengGong(XQBean xqBean) {
                iview.showBean(xqBean);
            }
        });
    }
    public void getTJ(){
        xqModel.geTJ(3043, 12, "android", new CGSB<Tianjia>() {
            @Override
            public void chengGong(Tianjia tianjia) {
                iview.showStr(tianjia.getMsg());
            }
        });
    }
    public void getJB(){
        iview=null;
    }
}
