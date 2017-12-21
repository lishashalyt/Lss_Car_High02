package com.bawei.lss_car_high02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bawei.lss_car_high02.Apdater.MyExpandableAdapter;
import com.bawei.lss_car_high02.Bean.CartBean;
import com.bawei.lss_car_high02.Bean.CountAndPrice;
import com.bawei.lss_car_high02.Bean.MessgeEvent;
import com.bawei.lss_car_high02.presenter.CartPersenter;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class Main2Activity extends AppCompatActivity implements ICart {

    private ExpandableListView mElv;
    private CheckBox mQuanxuan;
    /**
     * 总价：0.0
     */
    private TextView mZongjia;
    /**
     * 共0件商品
     */
    private TextView mTvCount;
    private MyExpandableAdapter myExpandableAdapter;
    private CartPersenter cartPersenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        //实例化购物车的p层
        cartPersenter = new CartPersenter(this);
        cartPersenter  .getCart();
        //注册EventBus
        EventBus.getDefault().register(this);
        mQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用适配器里的全选的方法
                myExpandableAdapter.qx(mQuanxuan.isChecked());
            }
        });
    }

    @Override
    public void showlist(List<CartBean.DataBean> grouplist, List<List<CartBean.DataBean.ListBean>> childlist) {
        //实例化二级的适配器
        myExpandableAdapter = new MyExpandableAdapter(grouplist, this,childlist);
        //默认展开
        mElv.setAdapter(myExpandableAdapter);
        for (int i = 0; i <grouplist.size() ; i++) {
            mElv.expandGroup(i);
        }

    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mQuanxuan = (CheckBox) findViewById(R.id.quanxuan);
        mZongjia = (TextView) findViewById(R.id.zongjia);
        mTvCount = (TextView) findViewById(R.id.tv_count);
    }
    //接收传过来的值
    @Subscribe
    public void onPriceAndCount(CountAndPrice cp){

        mZongjia.setText("共"+cp.getCount()+"件商品");

        mTvCount.setText("总计："+cp.getPrice());

    }
    //接收传过来的值
    @Subscribe
    public void onPriceAndCount(MessgeEvent event){
        //改变全选的状态
        mQuanxuan.setChecked(event.isCheck());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        cartPersenter=null;
    }
}
