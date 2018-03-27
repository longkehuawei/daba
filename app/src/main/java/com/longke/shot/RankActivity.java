package com.longke.shot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.longke.shot.adapter.RankAdapter;
import com.longke.shot.entity.ItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RankActivity extends AppCompatActivity {

    @InjectView(R.id.back_iv)
    ImageView backIv;
    @InjectView(R.id.desc_tv)
    TextView descTv;
    @InjectView(R.id.listView)
    ListView listView;
    private List<ItemBean> itemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.inject(this);
        itemBeanList=new ArrayList<>();
        for(int i=0;i<5;i++){
            ItemBean itemBean=new ItemBean();
            itemBeanList.add(itemBean);
        }
        listView.setAdapter(new RankAdapter(this, itemBeanList));
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
          finish();
    }
}
