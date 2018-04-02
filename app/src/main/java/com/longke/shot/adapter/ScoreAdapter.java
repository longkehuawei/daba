package com.longke.shot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longke.shot.R;
import com.longke.shot.entity.ItemBean;

import java.util.List;

/**
 * 作者：$ longke on 2018/3/27 14:32
 * 邮箱：373497847@qq.com
 */

public class ScoreAdapter extends BaseAdapter {
    private List<ItemBean.DataBean.ShootDetailListBean> mList;
    private LayoutInflater mInflater;

    // 通过构造器关联数据源与数据适配器
    public ScoreAdapter(Context context, List<ItemBean.DataBean.ShootDetailListBean> list){
        mList = list;
        // 使用当前要使用的界面对象context去初始化布局装载器对象mInflater
        mInflater = LayoutInflater.from(context);
    }
    public  void setData(List<ItemBean.DataBean.ShootDetailListBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    // 返回指定索引对应的数据项
    @Override
    public long getItemId(int position) {
        return position;
    }




    /**
     * 既利用了ListView的缓存，
     * 更通过ViewHolder类来显示数据的视图的缓存，避免了多次通过findViewById寻找控件
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){// View未被实例化，即缓冲池中无缓存才创建View
            // 将控件id保存在viewHolder中
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.score_item, null);
            viewHolder.index_tv = (TextView) convertView.findViewById(R.id.index_tv);
            viewHolder.score_tv = (TextView) convertView.findViewById(R.id.score_tv);
            viewHolder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            // 通过setTag将ViewHolder与convertView绑定
            convertView.setTag(viewHolder);
        } else{
            // 通过ViewHolder对象找到对应控件
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if(position%2==1){
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        ItemBean.DataBean.ShootDetailListBean bean = mList.get(position);
        viewHolder.index_tv.setText(""+bean.getBulletHoleIndex());
        viewHolder.score_tv.setText(""+bean.getCurrShootNum()+"");
        viewHolder.time_tv.setText(bean.getShootTime());
        return convertView;
    }

    // 避免重复的findViewById的操作
    class ViewHolder{
        public TextView time_tv;
        public TextView index_tv;
        public TextView score_tv;
    }
}
