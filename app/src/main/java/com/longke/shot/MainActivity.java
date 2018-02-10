package com.longke.shot;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.longke.shot.entity.Heartbeat;
import com.longke.shot.entity.Info;
import com.longke.shot.media.IRenderView;
import com.longke.shot.media.IjkVideoView;
import com.longke.shot.view.DialogUtil;
import com.longke.shot.view.PointView;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int HIDE = 100;
    private static final int UPDATE_PROGRESS = 101;
    @InjectView(R.id.sheshouxinxi)
    TextView mSheshouxinxi;
    @InjectView(R.id.name)
    TextView mName;
    @InjectView(R.id.xuehao)
    TextView mXuehao;
    @InjectView(R.id.zuhao)
    TextView mZuhao;
    @InjectView(R.id.kemu)
    TextView mKemu;
    @InjectView(R.id.bencisheji)
    TextView mBencisheji;
    @InjectView(R.id.shengyuzidan)
    TextView mShengyuzidan;
    @InjectView(R.id.zongchengji)
    TextView mZongchengji;
    @InjectView(R.id.shengyushijian)
    TextView mShengyushijian;
    @InjectView(R.id.shot_btn)
    TextView mShotBtn;
    @InjectView(R.id.ready_layout)
    LinearLayout mReadyLayout;
    @InjectView(R.id.end_layout)
    LinearLayout mEndLayout;

    @InjectView(R.id.num_tv)
    TextView mNumTv;
    @InjectView(R.id.num_layout)
    LinearLayout mNumLayout;
    @InjectView(R.id.kaishi)
    TextView mKaishi;
    private IjkVideoView mVideoView;
    private PointView shotPoint;
    private int mDuration;
    private int CONTINUE_TIME;
    TextView numTv;
    MqttAndroidClient mqttAndroidClient;

     String serverUri = "tcp://120.76.153.166:1883";

    String clientId = "ExampleAndroidClient";
    final String ShootReady = "ShootReady";
    final String CompleteNotice = "CompleteNotice";
    final String Shoot = "Shoot";
    final String InitData = "InitData";
    final String publishMessage = "{\"Type\":\"Ready\",\"TargetId\":11}";
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            //其他配置
            .build();
    private MyOkHttp mMyOkhttp;
    CountDownTimer timer;
    List<Info.DataBean.ShootDetailListBean> list = new ArrayList<Info.DataBean.ShootDetailListBean>();
    List<Info.DataBean.ShootDetailListBean> tempList = new ArrayList<Info.DataBean.ShootDetailListBean>();
    Info info;
    Dialog ShowLoginDialog;
    String TrainId;
    String GroupIndex;
    String VideoStreamUrl;
    boolean isFrist=true;
    boolean isStart=true;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;
    private String music = "avchat_ring.mp3";
    private long[] pattern = { 0, 2000, 1000 };
    private int clickCount;
    private long preClickTime;
    private boolean isShowRed=true;
    private boolean isShowRedOpen=true;
    private ArrayList<String> mMusicList = new ArrayList<>();
    private int mPosition;
    private boolean mIsPlaying = false;
    List<Integer> listRadio = new ArrayList<Integer>();


    String sn;
    int i=0;
    //存放音效的HashMap
    private Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位

                case 1:
                    /**
                     获取数据，更新UI
                     */
                    tempList.clear();
                    SpTools.putStringValue(MainActivity.this,info.getData().getStudentCode(),"");
                    shotPoint.setTempShootDetailListBean(tempList);

                    mReadyLayout.setBackgroundResource(R.drawable.red_shape);
                    mReadyLayout.setClickable(true);
                    mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                    break;
                case 2:
                    /** 倒计时60秒，一次1秒 */
                    // ShowCountDialog("3");
                    timer.start();

                    break;
                case 3:
                    getData();
                    //startHeCheng("环");

                    if (list != null) {
                        if(tempList.size()>0){
                            shotPoint.setTempShootDetailListBean(tempList);

                        }
                        shotPoint.setShootDetailListBean(list);
                        if(msg.getData()!=null){
                            if(msg.getData().getInt("ID")==-1){
                                return;
                            }
                            listRadio.add(msg.getData().getInt("ID"));
                            if(listRadio.size()==1){

                                playAlarm(msg.getData().getInt("ID"));
                            }
                        }




                    }
                    break;
                case 4://结束
                    mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                    mReadyLayout.setClickable(false);
                    mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                    mEndLayout.setClickable(false);
                    break;
                case 5://强制刷新
                    GetTrainStudentDataByGroupId();
                    break;
                case 6:

                    break;
            }
        }
    };

    Timer timer1 = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        sn = UUIDS.getUUID();
        Urls.BASE_URL= (String) SharedPreferencesUtil.get(MainActivity.this,SharedPreferencesUtil.BASE_URL,"");
        if(TextUtils.isEmpty(Urls.BASE_URL)){
            startActivity(new Intent(MainActivity.this,ConfigureActivity.class).putExtra("isFromMain",true));
            finish();
            return;
        }
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=5a7d9660");
        isShowRedOpen= (boolean) SharedPreferencesUtil.get(MainActivity.this,SharedPreferencesUtil.IS_RED,true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 通过Resources获取
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        // 获取屏幕的默认分辨率
        Display display = getWindowManager().getDefaultDisplay();
        initView();
        if (display.getWidth() == 1280) {
            shotPoint.setBilu(0.6f);
        }

        System.out.println("width-display :" + display.getWidth());
        System.out.println("heigth-display :" + display.getHeight());
        mMyOkhttp = new MyOkHttp(okHttpClient);
        shotPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCount == 0) {
                    preClickTime = System.currentTimeMillis();
                    clickCount++;
                } else if (clickCount == 1) {
                    long curTime = System.currentTimeMillis();
                    if((curTime - preClickTime) < 500){
                        doubleClick();
                    }
                    clickCount = 0;
                    preClickTime = 0;
                }else{
                    Log.e(TAG, "clickCount = " + clickCount);
                    clickCount = 0;
                    preClickTime = 0;
                }
            }
        });
        shotPoint.setShowRed(isShowRedOpen);

        initData();
        initConnection();
        DeviceIsRegist();
        GetConfigData();

       // getData();


       // map.put(1, soundPool.load(this,R.raw.wrong,1));
        timer = new CountDownTimer(4 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                if(millisUntilFinished / 1000==4){
                    mKaishi.setVisibility(View.VISIBLE);
                    mNumTv.setVisibility(View.GONE);
                }else{
                    mKaishi.setVisibility(View.GONE);
                    mNumTv.setVisibility(View.VISIBLE);
                    mNumTv.setText(millisUntilFinished / 1000 + "");
                }
                mNumLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                mNumLayout.setVisibility(View.GONE);
            }
        };
        timer1.schedule(new TimerTask() {

            @Override
            public void run() {
                publishMessage();

            }
        }, 30000, 30000);

    }


    /**
     * 开始音乐服务并传输数据
     */
    private void startMusicService() {
        Intent musicService = new Intent();
        musicService.setClass(getApplicationContext(), MusicService.class);
        /*Info.DataBean.ShootDetailListBean info=new Info.DataBean.ShootDetailListBean();
        info.setScore(3);
        list.add(info);*/
        musicService.putExtra("music_list",(Serializable)list);

        //musicService.putExtra("messenger", new Messenger(handler));
        startService(musicService);
        //Info.DataBean.ShootDetailListBean info=new Info.DataBean.ShootDetailListBean();

       /* info.setScore(6);
        list.add(info);
        sendBroadcast(new Intent(Constants.ACTION_PLAY).putExtra("music_list",(Serializable)list));*/
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Info.DataBean.ShootDetailListBean info=new Info.DataBean.ShootDetailListBean();

                info.setScore(6);
                list.add(info);
                sendBroadcast(new Intent(Constants.ACTION_PLAY).putExtra("music_list",(Serializable)list));
            }
        },1000);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer=null;
        }


    }

    private void playAlarm(int id) {
        if(listRadio.size()==0){
            return;
        }
         id=listRadio.get(0);
		/*
		 * timerVibrate=new Timer(); timerVibrate.sc
		 */
       /* vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);*/

		/*
		 * Uri alert = RingtoneManager
		 * .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		 */
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            //mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                 if(listRadio.size()>0){
                     listRadio.remove(0);
                    playAlarm(2);

                 }
            }
        });
        // mMediaPlayer = new MediaPlayer();
        // mMediaPlayer.setDataSource(getApplicationContext(), alert);
		/*if (alert == null) {
			music = "bugu.mp3";
		} else {
			*//*if ("0".equals(alert.getAlertmusic())) {
				music = "bugu.mp3";
			} else if ("1".equals(alert.getAlertmusic())) {
				music = "lingdang.mp3";
			} else if ("2".equals(alert.getAlertmusic())) {
				music = "menghuan.mp3";
			}*//*
		}*/
        int fd = 0;
        switch (id){
            case 0:
                fd=R.raw.f0;
                break;
            case  1:
                fd=R.raw.f1;
                break;
            case  2:
                fd=R.raw.f2;
                break;
            case  3:
                fd=R.raw.f3;
                break;
            case  4:
                fd=R.raw.f4;
                break;
            case  5:
                fd=R.raw.f5;
                break;
            case  6:
                fd=R.raw.f6;
                break;
            case  7:
                fd=R.raw.f7;
                break;
            case  8:
                fd=R.raw.f8;
                break;
            case  9:
                fd=R.raw.f9;
                break;
            case  10:
                fd=R.raw.f10;
                break;

        }
        try {

            AssetFileDescriptor file = getResources().openRawResourceFd(fd);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                        file.getLength());
                mMediaPlayer.prepare();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getSystemService(AUDIO_SERVICE);
            mMediaPlayer.setVolume(0.5f, 0.5f);
           // mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // }

    }
    public static void initDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.densityDpi = DisplayMetrics.DENSITY_XHIGH;//densityDpi 值越大，那显示时 dp对应的pix就越大
        context.getResources().updateConfiguration(configuration, displayMetrics);
    }

    /**
     * 建立连接
     */
    private void initConnection() {
        clientId = clientId + System.currentTimeMillis();
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                    Log.e("longke", "Reconnected to : " + serverURI);
                    //addToHistory("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    // subscribeToTopic();
                } else {
                    Log.e("longke", "Connected to: " + serverURI);
                    // addToHistory("Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.e("longke", "The Connection was lost.");
                // addToHistory("The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e("longke", "Incoming message: " + new String(message.getPayload()));
                //addToHistory("Incoming message: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                    subscribeToTopic1();
                    subscribeToTopic2();//shot
                    InitData();//强制刷新
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        mMyOkhttp.get().url(Urls.BASE_URL+Urls.GetTrainStudentData)
                .addParam("padCode", sn)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        info = new Gson().fromJson(response.toString(), Info.class);
                        Info.DataBean data = info.getData();
                        if (data == null) {
                            Toast.makeText(MainActivity.this, info.getMessage(), Toast.LENGTH_SHORT).show();
                            //finish();
                            return;
                        }
                        mName.setText("姓名 ：" + data.getStudentName());
                        mZuhao.setText("组号 ：第" + data.getGroupIndex() + "组");
                        mXuehao.setText("学号 ：" + data.getStudentCode() + "");
                        mKemu.setText("科目 ：" + data.getShootModeName() + "");
                        mBencisheji.setText(data.getCurrScore() + "");

                        mZongchengji.setText(data.getTotalScore() + "");
                        if(data.getShootDetailList()==null||data.getShootDetailList().size()==0){
                            mShengyuzidan.setText( "0");
                        }else{
                            mShengyuzidan.setText(data.getShootDetailList().get(data.getShootDetailList().size()-1).getBulletIndex()+"");

                        }
                        mShengyushijian.setText(data.getRemainTime());
                        if(isFrist){
                            setVideoUri();
                            list = data.getShootDetailList();
                            String temp=SpTools.getStringValue(MainActivity.this,info.getData().getStudentCode(),"");
                            if(!TextUtils.isEmpty(temp)){
                                Gson gson=new Gson();
                                tempList = gson.fromJson(temp,
                                        new TypeToken<List<Info.DataBean.ShootDetailListBean>>() {
                                        }.getType());
                                shotPoint.setTempShootDetailListBean(tempList);

                            }
                            if (list != null) {
                                shotPoint.setShootDetailListBean(list);
                            } else {
                                list = new ArrayList<Info.DataBean.ShootDetailListBean>();
                                shotPoint.setShootDetailListBean(list);
                            }
                            isFrist=false;
                        }
                        if (info.getData().getStatus() == 0 || info.getData().getStatus() == 2 || info.getData().getStatus() == 4) {
                            mReadyLayout.setClickable(false);
                            mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setClickable(false);
                        } else if (info.getData().getStatus() == 1) {
                            mReadyLayout.setClickable(true);
                            mReadyLayout.setBackgroundResource(R.drawable.red_shape);
                            mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setClickable(false);
                        } else if (info.getData().getStatus() == 3) {
                            mReadyLayout.setClickable(false);
                            mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setBackgroundResource(R.drawable.red_shape);
                            mEndLayout.setClickable(true);
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }
    /**
     * 获取配置
     */
    private void GetConfigData() {
        mMyOkhttp.get().url(Urls.BASE_URL+Urls.GetConfigData)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            JSONObject object=  response.getJSONObject("Data");
                            String MqttServerIP=object.getString("MqttServerIP");
                            String MqttPort=object.getString("MqttPort");
                            serverUri="tcp://"+MqttServerIP+":"+MqttPort;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }
    /**
     * 获取数据
     */
    private void DeviceIsRegist() {

        mMyOkhttp.get().url(Urls.BASE_URL+Urls.DeviceIsRegist)
                .addParam("type", "1")
                .addParam("code", sn)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        try {
                            JSONObject object=  response.getJSONObject("Data");
                            boolean Data=object.getBoolean("IsRegist");
                            if(!Data){
                                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                                finish();
                            }else{
                                getData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }

    /**
     * 获取数据
     */
    private void GetTrainStudentDataByGroupId() {
        mMyOkhttp.get().url(Urls.BASE_URL+Urls.GetTrainStudentDataByGroupId)
                .addParam("trainId", TrainId + "")
                .addParam("groupIndex", GroupIndex + "")
                .addParam("padCode", sn)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        boolean isNull = false;
                        if(info==null||info.getData()==null){
                            isNull=true;
                        }
                        info = new Gson().fromJson(response.toString(), Info.class);
                        Info.DataBean data = info.getData();
                        mName.setText("姓名 ：" + data.getStudentName());
                        mZuhao.setText("组号 ：第" + data.getGroupIndex() + "组");
                        mXuehao.setText("学号 ：" + data.getStudentCode() + "");
                        mKemu.setText("科目 ：" + data.getShootModeName() + "");
                        mBencisheji.setText(data.getCurrScore() + "");
                        if(data.getShootDetailList()==null||data.getShootDetailList().size()==0){
                            mShengyuzidan.setText( "0");
                        }else{
                            mShengyuzidan.setText(data.getShootDetailList().get(data.getShootDetailList().size()-1).getBulletIndex()+"");

                        }
                        mZongchengji.setText(data.getTotalScore() + "");
                        mShengyushijian.setText(data.getRemainTime());
                        if(isNull){
                            setVideoUri();
                        }
                        String temp=SpTools.getStringValue(MainActivity.this,info.getData().getStudentCode(),"");
                        if(!TextUtils.isEmpty(temp)){
                            Gson gson=new Gson();
                            tempList = gson.fromJson(temp,
                                    new TypeToken<List<Info.DataBean.ShootDetailListBean>>() {
                                    }.getType());
                            shotPoint.setTempShootDetailListBean(tempList);

                        }else{
                            tempList.clear();
                            shotPoint.setTempShootDetailListBean(tempList);
                        }

                        list = data.getShootDetailList();
                        if (list != null) {
                            shotPoint.setShootDetailListBean(list);
                        } else {
                            list = new ArrayList<Info.DataBean.ShootDetailListBean>();
                            shotPoint.setShootDetailListBean(list);
                        }
                        if (info.getData().getStatus() == 0 || info.getData().getStatus() == 2 || info.getData().getStatus() == 4) {
                            mReadyLayout.setClickable(false);
                            mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setClickable(false);
                        } else if (info.getData().getStatus() == 1) {
                            mReadyLayout.setClickable(true);
                            mReadyLayout.setBackgroundResource(R.drawable.red_shape);
                            mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setClickable(false);
                        } else if (info.getData().getStatus() == 3) {
                            mReadyLayout.setClickable(false);
                            mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                            mEndLayout.setBackgroundResource(R.drawable.red_shape);
                            mEndLayout.setClickable(true);
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }

    private void ShowCountDialog(String num) {
        if (ShowLoginDialog == null) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.count_down_layout, null);
            numTv = (TextView) view.findViewById(R.id.num_tv);
            numTv.setText(num);
            ShowLoginDialog = DialogUtil.dialog(this, view);
        } else {
            numTv.setText(num);
        }

        if (!ShowLoginDialog.isShowing()) {
            ShowLoginDialog.show();
        }

    }

    /**
     * 开始射击
     *
     * @param trainId
     * @param studentId
     */
    private void startShot(final String trainId, String studentId) {
        mMyOkhttp.get().url(Urls.BASE_URL+Urls.StartShoot)
                .addParam("trainId", trainId)
                .addParam("studentId", studentId)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        try {
                            if (response.getBoolean("Success")) {
                                mReadyLayout.setClickable(false);
                                mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                                mEndLayout.setBackgroundResource(R.drawable.red_shape);
                                mEndLayout.setClickable(true);
                                Toast.makeText(MainActivity.this, "准备射击", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }

    /**
     * 结束射击
     *
     * @param trainId
     * @param studentId
     */
    private void endShot(String trainId, String studentId) {
        mMyOkhttp.get().url(Urls.BASE_URL+Urls.EndShoot)
                .addParam("trainId", trainId)
                .addParam("studentId", studentId)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        try {
                            if (response.getBoolean("Success")) {
                                mReadyLayout.setClickable(false);
                                mReadyLayout.setBackgroundResource(R.drawable.gray_shape);
                                mEndLayout.setBackgroundResource(R.drawable.gray_shape);
                                mEndLayout.setClickable(false);
                                Toast.makeText(MainActivity.this, "结束射击", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                        // ToastUtil.showShort(BaseApplication.context,error_msg);
                    }
                });
    }


    /**
     * 添加订阅，接受消息
     */
    public void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(ShootReady, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e("longke", "Subscribed!");
                    //addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("longke", "Failed to subscribe");
                    // addToHistory("Failed to subscribe");
                }
            });

            // THIS DOES NOT WORK!
            mqttAndroidClient.subscribe(ShootReady, 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // 收到指令
                    Log.e("longke", "Message: " + topic + " : " + new String(message.getPayload()));
                    JSONObject object = new JSONObject(new String(message.getPayload()));
                    if (object.has("Type")) {
                        String type = object.getString("Type");
                        if ("Ready".equals(type)) {
                            handler.sendEmptyMessage(1);

                        } else if ("Start".equals(type)) {
                            handler.sendEmptyMessage(2);

                        } else if ("Shoot".equals(type)) {

                        }

                    }
                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    /**
     * 添加订阅，接受消息
     */
    public void subscribeToTopic1() {
        try {
            mqttAndroidClient.subscribe(CompleteNotice, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e("longke", "Subscribed!");
                    //addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("longke", "Failed to subscribe");
                    // addToHistory("Failed to subscribe");
                }
            });

            // THIS DOES NOT WORK!
            mqttAndroidClient.subscribe(CompleteNotice, 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // 收到指令
                    Log.e("longke", "Message: " + topic + " : " + new String(message.getPayload()));
                    JSONObject object = new JSONObject(new String(message.getPayload()));
                    if (object.has("Type")) {
                        String type = object.getString("Type");
                        if(info==null||info.getData()==null){
                            return;
                        }
                        if(info.getData().getStatus() ==1||info.getData().getStatus() ==2){
                            if (type.equals("Complete")) {
                                String TargetId = object.getString("TargetId");

                                if (TargetId.equals(info.getData().getTargetId()+"")) {

                                    Info.DataBean.ShootDetailListBean bean = new Info.DataBean.ShootDetailListBean();
                                    boolean isHas = false;
                                    for (int i = 0; i < list.size(); i++) {
                                        Info.DataBean.ShootDetailListBean yi = list.get(i);
                                        if (yi.getBulletIndex() == object.getInt("BulletIndex")) {
                                            isHas = true;
                                        }
                                    }
                                    if (isHas) {
                                        return;
                                    }
                                    bean.setX(object.getInt("X"));
                                    bean.setBulletIndex(object.getInt("BulletIndex"));
                                    bean.setY(object.getInt("Y"));
                                    bean.setWidth(object.getInt("Width"));
                                    bean.setHeight(object.getInt("Height"));
                                    bean.setScore(object.getInt("Score"));
                                    tempList.add(bean);
                                    Gson gson=new Gson();
                                    String a=gson.toJson(tempList);
                                    SpTools.putStringValue(MainActivity.this,info.getData().getStudentCode(),a);
                                    Message msg = handler.obtainMessage();
                                    Bundle b = new Bundle();
                                    b.putInt("ID", -1);
                                    //startHeCheng( bean.getScore()+"环");
                                    msg.setData(b);
                                    msg.what = 3;
                                    handler.sendMessage(msg);
                                    // handler.sendEmptyMessage(3);

                                }
                            }

                        }else if(info.getData().getStatus() == 3){
                            if (type.equals("Complete")) {
                                String TargetId = object.getString("TargetId");

                                if (TargetId.equals(info.getData().getTargetId()+"")) {

                                    Info.DataBean.ShootDetailListBean bean = new Info.DataBean.ShootDetailListBean();
                                    boolean isHas = false;
                                    for (int i = 0; i < list.size(); i++) {
                                        Info.DataBean.ShootDetailListBean yi = list.get(i);
                                        if (yi.getBulletIndex() == object.getInt("BulletIndex")) {
                                            isHas = true;
                                        }
                                    }
                                    if (isHas) {
                                        return;
                                    }
                                    bean.setX(object.getInt("X"));
                                    bean.setBulletIndex(object.getInt("BulletIndex"));
                                    bean.setY(object.getInt("Y"));
                                    bean.setWidth(object.getInt("Width"));
                                    bean.setHeight(object.getInt("Height"));
                                    bean.setScore(object.getInt("Score"));
                                    list.add(bean);
                                    Message msg = handler.obtainMessage();
                                    //利用bundle对象来传值
                                    Bundle b = new Bundle();
                                    b.putInt("ID", bean.getScore());
                                    //startHeCheng( bean.getScore()+"环");
                                    msg.setData(b);
                                    msg.what = 3;
                                    handler.sendMessage(msg);
                                    // handler.sendEmptyMessage(3);

                                }
                            }
                        }

                    }
                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    /**
     * 添加订阅，接受消息
     */
    public void subscribeToTopic2() {
        try {
            mqttAndroidClient.subscribe(Shoot, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e("longke", "Subscribed!");
                    //addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("longke", "Failed to subscribe");
                    // addToHistory("Failed to subscribe");
                }
            });

            // THIS DOES NOT WORK!
            mqttAndroidClient.subscribe(Shoot, 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    JSONObject object = new JSONObject(new String(message.getPayload()));
                    if (object.has("Type")) {
                        String type = object.getString("Type");
                        if ("End".equals(type)) {
                            String TargetId = object.getString("TargetId");
                            if (info != null && info.getData() != null) {
                                if (TargetId.equals(info.getData().getTargetId()+"")) {
                                    handler.sendEmptyMessage(4);
                                }
                            }


                        }

                    }
                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    /**
     * 添加订阅，接受消息
     */
    public void InitData() {
        try {
            mqttAndroidClient.subscribe(InitData, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e("longke", "Subscribed!");
                    //addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("longke", "Failed to subscribe");
                    // addToHistory("Failed to subscribe");
                }
            });

            // THIS DOES NOT WORK!
            mqttAndroidClient.subscribe(InitData, 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    JSONObject object = new JSONObject(new String(message.getPayload()));
                    if (object.has("Type")) {
                        String type = object.getString("Type");
                        if ("Refresh".equals(type)) {

                            TrainId = object.getString("TrainId");
                            GroupIndex = object.getString("GroupIndex");

                            handler.sendEmptyMessage(5);

                        }

                    }
                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    /**
     * 发布消息
     */
    public void publishMessage() {

        try {
            MqttMessage message = new MqttMessage();
            Heartbeat heartbeat=new Heartbeat();
            heartbeat.setCode(sn);
            heartbeat.setType("Pad");
            Gson gson=new Gson();

            message.setPayload(gson.toJson(heartbeat).getBytes());
            mqttAndroidClient.publish("Heartbeat", message);
            if (!mqttAndroidClient.isConnected()) {
                //addToHistory(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initView() {
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        shotPoint = (PointView) findViewById(R.id.shot_point);
       /* LinearLayout shot_layout = (LinearLayout) findViewById(R.id.shot_layout);
        shot_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage();
            }
        });*/
    }

    private void initData() {

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        setVideoUri();

    }

    private void setVideoUri() {
        if (info != null && info.getData() != null) {
            mVideoView.setVideoURI(Uri.parse(info.getData().getVideoStreamUrl()));
            mVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
            mVideoView.start();

        }

    }
    private void doubleClick() {
        Log.i(TAG, "double click");
        //if(isShowRedOpen){
            isShowRed=!isShowRed;
            shotPoint.setShowAll(isShowRed);
        //}

    }

    @OnClick({R.id.ready_layout, R.id.end_layout,R.id.sheshouxinxi})
    public void onClick(View view) {        switch (view.getId()) {
            case R.id.ready_layout:
                //playAlarm();
               // sendBroadcast(Constants.ACTION_PLAY);
                if (info != null && info.getData() != null) {
                    startShot(info.getData().getTrainId() + "", info.getData().getStudentId() + "");
                }
                break;
            case R.id.end_layout:
                if (info != null &&info.getData() == null) {
                    return;
                }
                endShot(info.getData().getTrainId() + "", info.getData().getStudentId() + "");
                break;
            case R.id.sheshouxinxi:
                startActivity(new Intent(MainActivity.this,ConfigureActivity.class));
                break;
        }
    }
}
