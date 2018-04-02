package com.longke.shot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longke.shot.adapter.RankAdapter;
import com.longke.shot.entity.GetStudentRankingDetail;
import com.longke.shot.entity.ItemBean;
import com.longke.shot.event.CloseEvent;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class RankActivity extends AppCompatActivity {

    @InjectView(R.id.back_iv)
    ImageView backIv;
    @InjectView(R.id.desc_tv)
    TextView descTv;
    @InjectView(R.id.listView)
    ListView listView;
    private List<ItemBean> itemBeanList;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            //其他配置
            .build();
    private MyOkHttp mMyOkhttp;
    private String TrainId;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.inject(this);
        mMyOkhttp = new MyOkHttp(okHttpClient);
        studentId=getIntent().getStringExtra("studentId");
        TrainId=getIntent().getStringExtra("TrainId");
        GetStudentRankingDetail();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 成绩详情
     */
    private void GetStudentRankingDetail() {
        mMyOkhttp.get().url(Urls.BASE_URL + Urls.GetStudentRankingDetail)
                .addParam("trainId", TrainId + "")
                .addParam("studentId", studentId + "")
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        GetStudentRankingDetail info = new Gson().fromJson(response.toString(), GetStudentRankingDetail.class);
                        GetStudentRankingDetail.DataEntity data= info.getData();
                        if(data!=null){
                            if(data.getRankingList()!=null){
                                listView.setAdapter(new RankAdapter(RankActivity.this, data.getRankingList()));
                            }
                            descTv.setText(data.getClassName()+" | 第"+data.getGroupIndex()+"组");

                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(RankActivity.this,"请检查网络，及服务器配置",Toast.LENGTH_SHORT).show();
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CloseEvent messageEvent) {
        finish();
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
          finish();
    }
}
