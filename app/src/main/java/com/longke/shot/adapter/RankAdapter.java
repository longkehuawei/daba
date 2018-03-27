package com.longke.shot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longke.shot.R;
import com.longke.shot.entity.ItemBean;

import java.util.List;

/**
 * 作者：$ longke on 2018/3/27 16:14
 * 邮箱：373497847@qq.com
 */

public class RankAdapter extends BaseAdapter {
    private List<ItemBean> mList;
    private LayoutInflater mInflater;

    // 通过构造器关联数据源与数据适配器
    public RankAdapter(Context context, List<ItemBean> list){
        mList = list;
        // 使用当前要使用的界面对象context去初始化布局装载器对象mInflater
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.rank_item, null);
           /* viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);*/
            // 通过setTag将ViewHolder与convertView绑定
            convertView.setTag(viewHolder);
        } else{
            // 通过ViewHolder对象找到对应控件
            viewHolder = (ViewHolder) convertView.getTag();
           /* ItemBean bean = mList.get(position);
            viewHolder.imageView.setImageResource(bean.getItemImageResid());
            viewHolder.title.setText(bean.getItemContent());
            viewHolder.content.setText(bean.getItemContent());*/
        }
        return convertView;
    }

    // 避免重复的findViewById的操作
    class ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView content;
    }
}
