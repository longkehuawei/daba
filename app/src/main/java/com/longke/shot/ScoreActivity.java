package com.longke.shot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longke.shot.adapter.ScoreAdapter;
import com.longke.shot.entity.ItemBean;
import com.longke.shot.event.CloseEvent;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

import static com.longke.shot.R.id.listView;

public class ScoreActivity extends AppCompatActivity {

    @InjectView(R.id.user_name_tv)
    TextView mUserNameTv;
    @InjectView(R.id.zu_tv)
    TextView mZuTv;
    @InjectView(R.id.delete_iv)
    ImageView mDeleteIv;
    @InjectView(R.id.time_tv)
    TextView mTimeTv;
    @InjectView(listView)
    ListView mListView;
    @InjectView(R.id.activity_score)
    LinearLayout mActivityScore;
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
        setContentView(R.layout.activity_score);
        ButterKnife.inject(this);
        studentId=getIntent().getStringExtra("studentId");
        TrainId=getIntent().getStringExtra("TrainId");
        mMyOkhttp = new MyOkHttp(okHttpClient);
        GetStudentScoreDetail(TrainId,studentId);

        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CloseEvent messageEvent) {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 成绩详情
     */
    private void GetStudentScoreDetail(String trainId, String studentId) {
        mMyOkhttp.get().url(Urls.BASE_URL + Urls.GetStudentScoreDetail)
                .addParam("trainId", trainId + "")
                .addParam("studentId", studentId + "")
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        ItemBean info = new Gson().fromJson(response.toString(), ItemBean.class);
                        ItemBean.DataBean data = info.getData();
                        List<ItemBean.DataBean.ShootDetailListBean> mList;
                        if (data != null) {
                            mList = data.getShootDetailList();

                            if (mList == null) {
                                mList = new ArrayList<ItemBean.DataBean.ShootDetailListBean>();
                            }
                        } else {
                            mList = new ArrayList<ItemBean.DataBean.ShootDetailListBean>();
                        }
                        if(data!=null){
                            if(data.getStudentData()!=null){
                                mTimeTv.setText(data.getStudentData().getUseTime());
                                mZuTv.setText(data.getStudentData().getClassName() + "/" + data.getStudentData().getGroupIndex()+"组");
                                mUserNameTv.setText(data.getStudentData().getStudentName());
                                ScoreAdapter adapter = new ScoreAdapter(ScoreActivity.this, mList);
                                View view= LayoutInflater.from(ScoreActivity.this).inflate(R.layout.score_footer,null);
                                TextView zonghuanshu_tv= (TextView) view.findViewById(R.id.zonghuanshu_tv);
                                zonghuanshu_tv.setText("总环数 ：" + data.getStudentData().getTotalScore() );
                                mListView.addFooterView(view);
                                mListView.setAdapter(adapter);
                            }

                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(ScoreActivity.this, "请检查网络，及服务器配置", Toast.LENGTH_SHORT).show();
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }

    @OnClick(R.id.delete_iv)
    public void onClick() {
        finish();
    }
}
