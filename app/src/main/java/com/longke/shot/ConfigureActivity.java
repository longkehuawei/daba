package com.longke.shot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ConfigureActivity extends AppCompatActivity {

    @InjectView(R.id.addrss_name_tv)
    TextView addrssNameTv;
    @InjectView(R.id.tcp_name)
    EditText tcpName;
    @InjectView(R.id.url_name)
    EditText urlName;
    @InjectView(R.id.isRed)
    RadioButton isRed;
    @InjectView(R.id.bt_queding)
    Button btQueding;
    private boolean isFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        ButterKnife.inject(this);
        isFromMain=getIntent().getBooleanExtra("isFromMain",false);
        tcpName.setText("tcp://120.76.153.166:1883");
        urlName.setText("http://120.76.153.166:6002");
    }

    @OnClick(R.id.bt_queding)
    public void onViewClicked() {
        SharedPreferencesUtil.put(ConfigureActivity.this,SharedPreferencesUtil.ServerUri,tcpName.getText().toString());
        SharedPreferencesUtil.put(ConfigureActivity.this,SharedPreferencesUtil.BASE_URL,urlName.getText().toString());
        SharedPreferencesUtil.put(ConfigureActivity.this,SharedPreferencesUtil.IS_RED,isRed.isChecked());
        if(isFromMain){
            startActivity(new Intent(ConfigureActivity.this,MainActivity.class));
        }
        finish();

    }
}
