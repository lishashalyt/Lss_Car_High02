package com.bawei.lss_car_high02;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lss_car_high02.Bean.XQBean;
import com.bawei.lss_car_high02.presenter.XQPersenter;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity implements Iview, View.OnClickListener {

    private SimpleDraweeView mXqsdv;
    private TextView mTvtitle;
    /**
     * 跳转到购物车
     */
    private Button mTiaozhuan;
    /**
     * 添加到购物车
     */
    private Button mTianjia;
    private XQPersenter xqPersenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //实例化p层
        xqPersenter = new XQPersenter(this);
        //调用p层的方法
        xqPersenter.getXQ();
    }

    @Override
    public void showBean(Object o) {
        XQBean xqBean= (XQBean) o;
        XQBean.DataBean data = xqBean.getData();
        String s = data.getImages().split("\\|")[0];
        Uri uri=Uri.parse(s);
        mXqsdv.setImageURI(uri);
        mTvtitle.setText(data.getTitle()+"\n"+"💴"+data.getPrice());
    }

    @Override
    public void showStr(String s) {
        //添加成功进行吐司
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mXqsdv = (SimpleDraweeView) findViewById(R.id.xqsdv);
        mTvtitle = (TextView) findViewById(R.id.tvtitle);
        mTiaozhuan = (Button) findViewById(R.id.tiaozhuan);
        mTiaozhuan.setOnClickListener(this);
        mTianjia = (Button) findViewById(R.id.tianjia);
        mTianjia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tiaozhuan:
                //跳转倒购物车
                startActivity(new Intent(this,Main2Activity.class));
                break;
            case R.id.tianjia:
                //请求添加到购物车的接口
                xqPersenter.getTJ();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        xqPersenter.getJB();
        xqPersenter=null;
    }
}
