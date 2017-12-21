package com.bawei.lss_car_high02.Apdater;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lss_car_high02.Bean.CartBean;
import com.bawei.lss_car_high02.Bean.CountAndPrice;
import com.bawei.lss_car_high02.Bean.MessgeEvent;
import com.bawei.lss_car_high02.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import de.greenrobot.event.EventBus;


public class MyExpandableAdapter extends BaseExpandableListAdapter {
    private List<CartBean.DataBean> groupdata;
    private Context context;
    private List<List<CartBean.DataBean.ListBean>> childlist;
  
    public MyExpandableAdapter( List<CartBean.DataBean> groupdata, Context context, List<List<CartBean.DataBean.ListBean>> childlist) {
        this.groupdata = groupdata;  
        this.context = context;  
        this.childlist = childlist;  
    }  
  
    @Override  
    public int getGroupCount() {  
        return groupdata.size();  
    }  
  
    @Override  
    public int getChildrenCount(int groupPosition) {  
        return childlist.get(groupPosition).size();  
    }  
  
    @Override  
    public Object getGroup(int groupPosition) {  
        return groupdata.get(groupPosition);  
    }  
  
    @Override  
    public Object getChild(int groupPosition, int childPosition) {  
        return null;  
    }  
  
    @Override  
    public long getGroupId(int groupPosition) {  
        return groupPosition;  
    }  
  
    @Override  
    public long getChildId(int groupPosition, int childPosition) {  
        return childPosition;  
    }  
  
    @Override  
    public boolean hasStableIds() {  
        return false;  
    }  
  
    @Override  
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;  
        if (convertView == null) {  
            convertView = View.inflate(context, R.layout.groupitem, null);
            holder = new GroupViewHolder();  
            holder.tv_group = convertView.findViewById(R.id.tv_group);  
            holder.group_cb = convertView.findViewById(R.id.gx_group);  
  
            convertView.setTag(holder);  
        } else {  
            holder = (GroupViewHolder) convertView.getTag();  
        }
        final CartBean.DataBean dataBean = groupdata.get(groupPosition);

        String sellerName = dataBean.getSellerName();  
        holder.group_cb.setChecked(dataBean.isCheck());
        holder.tv_group.setText(sellerName);  
        //点击一级checkbox的状态  
        holder.group_cb.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                dataBean.setCheck(holder.group_cb.isChecked());  
                //改变所有孩子的状态  
                  changeChildState(groupPosition,holder.group_cb.isChecked());  
                  EventBus.getDefault().post(jS());
                //通过判断一级的checkbox判断全选的状态  
                   changeMianQXstatus(checkGroupAll());  
                notifyDataSetChanged();  
            }  
        });  
  
  
        return convertView;  
    }  
  
    @Override  
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {  
        final ChildViewHolder holder;  
        if (convertView == null) {  
            convertView = View.inflate(context, R.layout.childitem, null);  
            holder = new ChildViewHolder();  
  
               holder.tv_title=convertView.findViewById(R.id.title);  
               holder.price=convertView.findViewById(R.id.price);                //价格  
               holder.child_cb=convertView.findViewById(R.id.gouxuan_child);   //子勾选  
               holder.jian=convertView.findViewById(R.id.jia);     //减
               holder.del=convertView.findViewById(R.id.del);      //删除
              holder.add=convertView.findViewById(R.id.jian);    //加
              holder.sdv=convertView.findViewById(R.id.title_img);
              holder.tv_num=convertView.findViewById(R.id.tv_num);  //数量  
              holder.yanse=convertView.findViewById(R.id.yanse);  
            convertView.setTag(holder);  
        } else {  
            holder = (ChildViewHolder) convertView.getTag();  
        }
           final CartBean.DataBean.ListBean listBean = childlist.get(groupPosition).get(childPosition);
         int price = (int) listBean.getPrice();
             holder.child_cb.setChecked(listBean.isCheck());  
        holder.price.setText("¥："+price+"");  
        String title = listBean.getTitle();  
        holder.tv_title.setText(title);  
        String subhead = listBean.getSubhead();  
        String substring = subhead.substring(1, 10)+".....";  
        holder.yanse.setText(substring);  
        String images = listBean.getImages();  
        String[] split = images.split("\\|");  
        String s = split[0];
        //加载图片
        Uri uri=Uri.parse(s);
        holder.sdv.setImageURI(uri);
      //   listBean.setCou(Integer.parseInt(holder.tv_num.getText().toString()));
        //给子条目的checkBox点击事件  
        holder.child_cb.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                listBean.setCheck(holder.child_cb.isChecked());  
  
                EventBus.getDefault().post(jS());  
                //如果二级的checkBox选中，就让一级的checkBox勾选  
                if(holder.child_cb.isChecked()) {  
  
                    if (ischeckAllchild(groupPosition)) {  
                        //改变一级checkBox的状态  
                        changeGroupstatus(groupPosition,true);  
                        //通过判断一级的CheckBox的状态来改变全选的状态  
                         changeMianQXstatus(checkGroupAll());  
  
                    }  
                }else{  
                    changeGroupstatus(groupPosition,false);  
                    changeMianQXstatus(checkGroupAll());  
                }  
                notifyDataSetChanged();  
            }  
  
        });  
  
        //点击加加的功能  
        holder.add.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                int cou = listBean.getCou();  
                holder.tv_num.setText(++cou+"");  
                listBean.setCou(cou);  
                if(holder.child_cb.isChecked()){  
                    EventBus.getDefault().post(jS());  
                }  
  
            }  
        });  
        //点击减减的功能  
        holder.jian.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                int cou = listBean.getCou();  
  
                if(cou==1) {
                    Toast.makeText(context, "最小数量为1", Toast.LENGTH_SHORT).show();
                    return;}
                holder.tv_num.setText(--cou+"");  
                listBean.setCou(cou);  
                if (holder.child_cb.isChecked()) {  
                        EventBus.getDefault().post(jS());  
  
                }  
            }  
        });  
      //点击删除  
        holder.del.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {
                List<CartBean.DataBean.ListBean> listBeen = childlist.get(groupPosition);

              CartBean.DataBean.ListBean remove = listBeen.remove(childPosition);
                if(listBeen.size()==0){  
                    childlist.remove(childPosition);  
                    groupdata.remove(groupPosition);  
                }  
               EventBus.getDefault().post(jS());  
                notifyDataSetChanged();  
  
            }  
        });  
  
        return convertView;  
    }  
  
    @Override  
    public boolean isChildSelectable(int groupPosition, int childPosition) {  
        return true;  
    }  
  
    class GroupViewHolder {  
        CheckBox group_cb;
        TextView tv_group;
    }  
  
    class ChildViewHolder {  
        CheckBox child_cb;  
        TextView tv_title,price,yanse,tv_num;  
        ImageView del,add,jian;
        SimpleDraweeView sdv;
  
  
    }  
    /** 
     * 判断全部的二级是CheckBox否选中 
     * 
     */  
    private boolean ischeckAllchild(int g){
        List<CartBean.DataBean.ListBean> listBeen = childlist.get(g);

        for (int i = 0; i <listBeen.size() ; i++) {
            CartBean.DataBean.ListBean listBean = listBeen.get(i);

            if(!listBean.isCheck()){  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * 计算数量和价格 
     * 通过遍历一级的得到二级的CheckBox，如果是选择的选中状态的话，就统计它们的总计的数量和价格 
     */  
    private CountAndPrice jS(){
        int cou=0;  
        int jiage=0;  
        for (int i = 0; i <groupdata.size() ; i++) {  
            for (int j = 0; j <childlist.get(i).size() ; j++) {
                CartBean.DataBean.ListBean listBean = childlist.get(i).get(j);
                if(listBean.isCheck()){
                    cou+=listBean.getCou();  
                    jiage+=(int)listBean.getPrice()*listBean.getCou();  
                }  
  
            }  
  
        }  
        CountAndPrice countAndPrice = new CountAndPrice();  
        countAndPrice.setCount(cou);  
        countAndPrice.setPrice(jiage);  
        return countAndPrice;  
    }  
    /** 
     * 改变一级列表的状态 
     */  
    private void changeGroupstatus(int groupPosition,boolean flag ){
        CartBean.DataBean dataBean = groupdata.get(groupPosition);

        dataBean.setCheck(flag);  
    }  
    /** 
     * 改变全选的状态 
     * 
     */  
    private void changeMianQXstatus(boolean flag){  
        MessgeEvent messgeEvent = new MessgeEvent();
         messgeEvent.setCheck(flag);  
        EventBus.getDefault().post(messgeEvent);  
    }  
    /** 
     * 判断一级的是否全部选中 
     */  
    private boolean checkGroupAll(){  
        for (int i = 0; i < groupdata.size(); i++) {  
            if(!groupdata.get(i).isCheck()){  
                return false;  
            }  
        }  
        return true;  
    }  
    /** 
     * 改变所有孩子的状态 
     */  
    private void changeChildState(int grouPistion,boolean flag){  
        List<CartBean.DataBean.ListBean> listBeen = childlist.get(grouPistion);
        for (int i = 0; i < listBeen.size(); i++) {  
            listBeen.get(i).setCheck(flag);  
        }  
    }  
    /** 
     * 改变全选的 
     */  
    public void qx(boolean flag){  
        for (int i = 0; i < groupdata.size(); i++) {  
           changeGroupstatus(i, flag);  
           changeChildState(i, flag);  
        }  
        EventBus.getDefault().post(jS());  
        notifyDataSetChanged();  
    }  
}  