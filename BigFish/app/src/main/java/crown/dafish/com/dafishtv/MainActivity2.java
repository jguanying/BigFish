package crown.dafish.com.dafishtv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import crown.dafish.com.model.Channel;
import crown.dafish.com.model.ProgramData;
import crown.dafish.com.model.ProgramInfo;
import crown.dafish.com.model.ProgramModel;
import crown.dafish.com.model.TvModel;
import crown.dafish.com.utils.Constants;
import crown.dafish.com.utils.ConvertUtil;
import crown.dafish.com.utils.DensityUtil;
import crown.dafish.com.utils.LocalCacheUtil;
import crown.dafish.com.utils.MyBitmapUtils;
import crown.dafish.com.utils.Util;
import crown.dafish.com.view.ScrollViewEx;
import crown.dafish.com.view.TimeBar;


public class MainActivity2 extends Activity {

    private static final String TAG = "DaFishTv";

    private ListView mListView;

    private String[] mProgramId;

    private String[] mTvUrl;

    private DrawerLayout mDrawerLayout;

    private ArrayList<TvModel> mTvModels = new ArrayList<>();

    private KSYTextureView mVideoSurfaceView = null;

//    private SurfaceHolder mSurfaceHolder = null;
//    private KSYMediaPlayer mKSYMediaPlayer;

    private int mVideoWidth = 0;

    private int mVideoHeight = 0;

    private String mCurrentUrl = "";

    private boolean mUseHwCodec = false;

    private int mPause = 0;  // 0:play  1:pause  2:stop

    private LinearLayout mPlayerPanel;

    private ImageView mPlayerStartBtn;

    private SeekBar mPlayerSeekbar;

    private TextView mPlayerPosition;

    private LinearLayout voiceLayout;

    private Button voiceButton;

    private int mVideoProgress = 0;

    private static final int MSG_HIDE_PANEL = 0;

    private static final int MSG_REFRESH_PROGRAM = 1;

    private ImageView logoImageView;
    private ImageView backgroundImageView;

    private int mSteam = AudioManager.STREAM_MUSIC;

    private AudioManager mAudioManager;
    private int curPosition = 0;

    private boolean isChanged = false;

    private float mLastMotionX;
    private float mLastMotionY;
    private int startX;
    private int startY;
    private int threshold;
    private boolean isClick = true;
    private float width;
    private float height;
    // 声音调节Toast
    private VolumnController volumnController;

    private int errCount = 0;
    private int maxErrorCount = 40;
    private boolean displayRetryDialog = false;

    private LinearLayout voiceControlLayout;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_HIDE_PANEL:
                    dealTouchEvent();
                    break;
                case MSG_REFRESH_PROGRAM:
                    String id = (String) msg.obj;
                    buildProgram(id);
                    break;
            }
        }
    };


    private RequestQueue mRequestQueue;

    private static final String CONNECTOR = "&";

    /**
     * 节目单列表
     */
    private ArrayMap<String,ProgramData> mProgramData = new ArrayMap<>();

    /**
     * 请求失败列表
     */
    private ArrayMap<String,String> mErrorRequest = new ArrayMap<>();

    private LayoutInflater mLayoutInflater;

    private View mProgramLayoutPanel;

    private int mListHeight;

    /**
     * 每5分钟对应的像素
     */
    private float mFiveMinuteInPixel;

    /**
     * 一个单位5分钟，一屏显示30个单位
     */
    private static final int UNIT_IN_PAGE = 20;

    private static final int FIVE_MINUTE = 5;

    private View mMask;

    private static final String FORMAT = "HH:mm:ss";

    private ProgressBar mLoading;

    private TimeBar mTimeBar;

    private ImageView mClose;

    private View mSlideBtn;

    private ScrollViewEx mScrollView;

    private Channel mChannel;

    private  ArrayMap<String,LinearLayout> mRaws = new ArrayMap<>();

    private boolean isListScroll = false;

    private boolean isProgramScroll = false;

    public MyBitmapUtils bitmapUtils;

    //是否第一次点击
    private int clickCount = 0;
    Timer timer = new Timer();
    int m_firstpos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        CrashReport.initCrashReport(getApplicationContext(), "faf1388c3a", true);

        // 初始化图片加载控件
        initImageLoaderConfiguration();

        init();
        initPlayer();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

    }

    private void init() {

        width = DensityUtil.getWidthInPx(this);
        height = DensityUtil.getHeightInPx(this);
        threshold = DensityUtil.dip2px(this, 18);
        volumnController = new VolumnController(this);

        //音量控制,初始化定义
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mLayoutInflater = LayoutInflater.from(this);
        mMask = findViewById(R.id.mask);
        mTimeBar = (TimeBar) findViewById(R.id.timeBar);
        mLoading = (ProgressBar) findViewById(R.id.progressBar);
        mProgramLayoutPanel = findViewById(R.id.program_panel);
        mScrollView = (ScrollViewEx) findViewById(R.id.scrollView);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isProgramScroll = true;
                isListScroll = false;
                return false;
            }
        });
        mScrollView.setOnScrollChangeListener(new ScrollViewEx.OnScrollChangeListener() {
            @Override
            public void onScrollChange(int l, int t, int oldl, int oldt) {
                if (!isListScroll) {
                    int scroll = t - oldt;
                    mListView.scrollListBy(scroll);

                }
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (mProgramLayoutPanel.getVisibility() == View.VISIBLE) {
                    mProgramLayoutPanel.setVisibility(View.GONE);
                    mMask.setVisibility(View.GONE);
                    mClose.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mProgramId = getResources().getStringArray(R.array.program_id);
        mTvUrl = getResources().getStringArray(R.array.tv_url);

        findViewById(R.id.program_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一次点击的时候有bug，右边没刷新，所以单独做一个逻辑
                clickCount ++ ;
                tonggleProgramPanel();
            }
        });

        mListView = (ListView) findViewById(R.id.tv_list);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isProgramScroll = false;
                isListScroll = true;
                return false;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (totalItemCount > 0 && !isProgramScroll) {
                    int position = firstVisibleItem * (getResources().getDimensionPixelOffset(R.dimen.item_height)
                            + getResources().getDimensionPixelOffset(R.dimen.divider_height));
                    int top = view.getChildAt(0).getTop();
                    position = position - top;
                        Log.d(TAG, "mScrollView.getChildCount():" + mScrollView.getChildCount());
                    mScrollView.scrollTo(0, position);
                    m_firstpos = position;
                }
            }
        });

//        for (int i = 0; i < mProgramId.length; i++) {
//            TvModel tvModel = new TvModel();
//            tvModel.setId(mProgramId[i]);
//            tvModel.setUrl(Constants.TV_BASE_URL + mTvUrl[i]);
//            mTvModels.add(tvModel);
//        }i


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                resetPlayer();
                playTv(mTvModels.get(position).getUrl());
                curPosition = position;
                isChanged = false;
            }
        });
        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                mListHeight = mListView.getMeasuredHeight();
//                ((TvAdapter) mListView.getAdapter()).setItemHeight(mListHeight);
                mListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Rect rect = new Rect();
                mListView.getGlobalVisibleRect(rect);
                initProgramPanelLayout(rect);
            }
        });
        DisplayMetrics metrics = Util.getDisplayMetrics(this);
        ViewGroup.LayoutParams lp = mListView.getLayoutParams();
        if (metrics.widthPixels > 0) {
            lp.width = metrics.widthPixels / 3;
            mFiveMinuteInPixel = (1f *(metrics.widthPixels - lp.width) / UNIT_IN_PAGE);
        }
        mClose = (ImageView) findViewById(R.id.close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tonggleProgramPanel();
                mClose.setVisibility(View.GONE);
            }
        });

        mSlideBtn = findViewById(R.id.slide_btn);
        mSlideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        logoImageView = (ImageView)findViewById(R.id.logo);

        backgroundImageView = (ImageView)findViewById(R.id.background_imageview);

        SharedPreferences sp = getSharedPreferences("BigFish", Context.MODE_PRIVATE);
        String backgroundURL = sp.getString("backgroundURL", null);

        bitmapUtils = new MyBitmapUtils();
        bitmapUtils.display(backgroundImageView, backgroundURL, R.drawable.background);

    }

    private void initPlayer() {

        mVideoSurfaceView = (KSYTextureView) findViewById(R.id.player_surface);
        mVideoSurfaceView.setOnTouchListener(mTouchListener);
        mVideoSurfaceView.setVisibility(View.GONE);
//        mSurfaceHolder = mVideoSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(mSurfaceCallback);
        mVideoSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealTouchEvent();
            }
        });
        mVideoSurfaceView.setKeepScreenOn(true);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mPlayerPanel = (LinearLayout) findViewById(R.id.player_panel);
        mPlayerStartBtn = (ImageView) findViewById(R.id.player_start);
        mPlayerSeekbar = (SeekBar) findViewById(R.id.player_seekbar);
        mPlayerPosition = (TextView) findViewById(R.id.player_time);

        mPlayerStartBtn.setOnClickListener(mStartBtnListener);
        mPlayerSeekbar.setOnSeekBarChangeListener(mSeekBarListener);
        mPlayerSeekbar.setEnabled(true);

        mVideoSurfaceView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoSurfaceView.setOnCompletionListener(mOnCompletionListener);
        mVideoSurfaceView.setOnPreparedListener(mOnPreparedListener);
        mVideoSurfaceView.setOnInfoListener(mOnInfoListener);
        mVideoSurfaceView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoSurfaceView.setOnErrorListener(mOnErrorListener);
//        mVideoSurfaceView.setOnSeekCompleteListener(mOnSeekCompletedListener);
        mVideoSurfaceView.setScreenOnWhilePlaying(true);
        mVideoSurfaceView.setBufferTimeMax(300.0f);
        mVideoSurfaceView.setTimeout(5, 300);
        mVideoSurfaceView.setLooping(true);

        if (true) {
            //硬解264&265
            Log.e(TAG, "Hardware !!!!!!!!");
            mVideoSurfaceView.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
        }

        voiceLayout = (LinearLayout)findViewById(R.id.voice_layout);
        voiceLayout.setVisibility(View.GONE);
        voiceButton = (Button)findViewById(R.id.voice_button);

        voiceButton.setOnClickListener(new myOnClick());

    }

    private void resetPlayer() {

//        backgroundImageView.setVisibility(View.VISIBLE);

        mPlayerPanel.setVisibility(View.GONE);
        mPause = 0;
        errCount = 0;
        displayRetryDialog = false;

        //当前有视频正在播放，需先停止当前播放
        mVideoSurfaceView.stop();
        //重置播放
        mVideoSurfaceView.reset();

        mVideoSurfaceView.setBufferTimeMax(300.0f);
        mVideoSurfaceView.setTimeout(5, 300);
        mVideoSurfaceView.setLooping(true);

    }

    private class myOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mAudioManager.adjustStreamVolume(mSteam, AudioManager.ADJUST_RAISE,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);

        }

    }
    private void getChannel() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }

        String url = Constants.PROGRAM_INFO_BASE_URL + Util.getMac();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d(TAG,"channel: " + response);
//                    response = "{ \"programs\" : [ { \"logo\" : \"bj.png\", \"title\" : \"北京\", \"source\" : \"http://103.198.18.22:8088/live/bjtv.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"beijingstv\" }, { \"logo\" : \"gd.png\", \"title\" : \"广东\", \"source\" : \"http://103.198.18.22:8088/live/ngdtvsd.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"gd\" }, { \"logo\" : \"df.png\", \"title\" : \"东方\", \"source\" : \"http://103.198.18.22:8088/live/ndftvhd.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"df\" }, { \"logo\" : \"sr.png\", \"title\" : \"卡酷卡通\", \"source\" : \"http://103.198.18.22:8088/live/kkdh.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"kakukaton\" }, { \"logo\" : \"dsj.png\", \"title\" : \"古装剧场\", \"source\" : \"http://103.198.18.22:8088/live/gtjc.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"guzhuangjc\" }, { \"logo\" : \"zy.png\", \"title\" : \"赛事\", \"source\" : \"http://103.198.18.22:8088/live/wpzy.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"saishijx\" }, { \"logo\" : \"xw.png\", \"title\" : \"cctv-2\", \"source\" : \"http://103.198.18.22:8082/TV4020.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"cctv-2\" }, { \"logo\" : \"cj.png\", \"title\" : \"cctv-3\", \"source\" : \"http://103.198.18.22:8082/TV4013.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"cctv-3\"}, { \"logo\" : \"ms.png\", \"title\" : \"描述\", \"source\" : \"http://103.198.18.22:8082/TV3005.m3u8\", \"epg\" : \"http://crown.da-fish.com:8888/service/program\", \"uuid\" : \"saishijx\"} ], \"info\" : { \"version\" : 1, \"customer\" : \"Crown\" } }";
                    mChannel = gson.fromJson(response, Channel.class);
//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                    imageLoader.displayImage(Constants.PROGRAM_ICON_URL + mChannel.getExtraInfo().getLogo(), logoImageView);
//                    imageLoader.displayImage(Constants.PROGRAM_ICON_URL + mChannel.getExtraInfo().getBackground(), backgroundImageView);

                    SharedPreferences sp = getSharedPreferences("BigFish", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("backgroundURL", Constants.PROGRAM_ICON_URL + mChannel.getExtraInfo().getBackground());
                    editor.commit();

                    bitmapUtils.display(backgroundImageView, Constants.PROGRAM_ICON_URL + mChannel.getExtraInfo().getBackground(), R.drawable.background);

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
//                    Toast.makeText(MainActivity2.this, "该设备未经过认证，请联系大鱼公司进行认证!", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                    builder.setMessage("该设备未经过认证，请联系大鱼公司进行认证!");
                    builder.setTitle("提示");
                    builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                getProgramList();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "get channel error");
            }
        });

        mRequestQueue.add(request);
    }

    private void getProgramList() {
        if (mChannel != null) {
            ArrayList<ProgramInfo> programInfos = mChannel.getPrograms();
            if (programInfos == null) {
                return;
            }
            for (int i = 0; i < programInfos.size(); i++) {
                if (programInfos.get(i) == null) {
                    break;
                }
                TvModel tvModel = new TvModel();
                tvModel.setId(programInfos.get(i).getUuid());
                tvModel.setUrl(programInfos.get(i).getSource());
//                tvModel.setUrl(Util.getInnerSDCardPath() + "/" + programInfos.get(i).getSource());
                tvModel.setBackupSource(programInfos.get(i).getBackupSource());
                tvModel.setTvName(programInfos.get(i).getTitle());
                tvModel.setTvIconPath(Constants.PROGRAM_ICON_URL + programInfos.get(i).getLogo());
                mTvModels.add(tvModel);
                buildListRaw(programInfos.get(i).getUuid());
            }
            TvAdapter adapter = new TvAdapter(getApplicationContext(), mTvModels);
            mListView.setAdapter(adapter);
            mDrawerLayout.openDrawer(GravityCompat.START);
            //蒙层
            computeMaskPosition();
        } else {
            return;
        }

        String date = ConvertUtil.date2String(new Date(),"yyyyMMdd");
        String start = "start=" + date + CONNECTOR;
        String end = "end=" + date;
        for (int i = 0; i < mTvModels.size(); i++) {
            final String id = mTvModels.get(i).getId();
            String url = Constants.PROGRAM_BASE_URL
                    + "uuid=" + id
                    + CONNECTOR
                    + start
                    + end;
            makeRequest(id,url);
        }
    }

    private void buildListRaw(String id) {
        LinearLayout raw = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.item_height) + getResources().getDimensionPixelSize(R.dimen.divider_height));
        LinearLayout container = (LinearLayout) mProgramLayoutPanel.findViewById(R.id.program_container);
        container.addView(raw,layoutParams);
        mRaws.put(id,raw);
    }

    private void makeRequest(final String id,final String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, id + ":" + response.toString());
                try {
                    JSONArray array = new JSONArray(response.toString());
                    if (array.length() > 0) {
                        JSONObject jsonObject = array.getJSONObject(0);
                        Gson gson = new Gson();
                        ProgramData programData = gson.fromJson(jsonObject.toString(), ProgramData.class);
                        if (programData != null) {
                            mProgramData.put(id, programData);
                            if (mErrorRequest.containsKey(id)) {
                                mErrorRequest.remove(id);
                            }
                            Message message = mHandler.obtainMessage();
                            message.what = MSG_REFRESH_PROGRAM;
                            message.obj = id;
                            mHandler.sendMessage(message);
                        }
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    mErrorRequest.put(id,url);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = error.getLocalizedMessage();
                if (message == null) {
                    message = "UnknownError Happened When Get ProgramInfo";
                }
                Log.e(TAG, message);
                mErrorRequest.put(id,url);
            }
        });
        mRequestQueue.add(request);
    }


    /**
     * 当前进度监听
     */
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            long duration = mVideoSurfaceView.getDuration();
            long progress = duration * percent / 100;
//            mPlayerSeekbar.setSecondaryProgress((int)progress);
        }
    };

    /**
     * 播放结束监听
     */
    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(MainActivity2.this, "节目已播放完毕", Toast.LENGTH_LONG).show();
//            mDrawerLayout.openDrawer(GravityCompat.START);
            mLoading.setVisibility(View.GONE);
//            videoPlayEnd();
        }
    };

    /**
     * 流信息监听
     */
    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {

            backgroundImageView.setVisibility(View.GONE);

            mVideoWidth = mVideoSurfaceView.getVideoWidth();
            mVideoHeight = mVideoSurfaceView.getVideoHeight();

            // Set Video Scaling Mode
            mVideoSurfaceView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);

            //start player
            mVideoSurfaceView.start();
            mLoading.setVisibility(View.GONE);
            mVideoSurfaceView.setVisibility(View.VISIBLE);
            voiceLayout.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity2.this, "开始播放节目", Toast.LENGTH_LONG).show();
            //set progress
            setVideoProgress(0);
        }
    };

    /**
     * 播放状态监听
     */
    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {

            String error = "";
            Log.e(TAG, "OnInfoListener, i:" + i);

            if (i ==  KSYMediaPlayer.MEDIA_INFO_BUFFERING_START
                    || i == KSYMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                if (errCount >= maxErrorCount) {
                    Toast.makeText(MainActivity2.this, "当前网络状况不佳，播放已暂停！", Toast.LENGTH_LONG).show();
                    mVideoSurfaceView.stop();
                    mPause = 2;
                    mPlayerStartBtn.setBackgroundResource(R.drawable.restart);
                    mPlayerPanel.setVisibility(View.VISIBLE);
                } else {
                    errCount++;
                }
            } else {
                errCount = 0;
            }

            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    // Player find a new stream(video or audio), and we could reload the video.
                    if (mVideoSurfaceView != null)
                        mVideoSurfaceView.reload(mCurrentUrl, false, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_ACCURATE);
                    mLoading.setVisibility(View.GONE);
                    break;

                case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                case KSYMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                case KSYMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                case KSYMediaPlayer.MEDIA_ERROR_IO:
                case KSYMediaPlayer.MEDIA_ERROR_MALFORMED:
                case KSYMediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                case KSYMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                case KSYMediaPlayer.MEDIA_ERROR_UNSUPPORT_PROTOCOL:
                case KSYMediaPlayer.MEDIA_ERROR_DNS_PARSE_FAILED:
                case KSYMediaPlayer.MEDIA_ERROR_CREATE_SOCKET_FAILED:
                case KSYMediaPlayer.MEDIA_ERROR_CONNECT_SERVER_FAILED:
                case KSYMediaPlayer.MEDIA_ERROR_BAD_REQUEST:
                case KSYMediaPlayer.MEDIA_ERROR_UNAUTHORIZED_CLIENT:
                case KSYMediaPlayer.MEDIA_ERROR_ACCESSS_FORBIDDEN:
                case KSYMediaPlayer.MEDIA_ERROR_TARGET_NOT_FOUND:
                case KSYMediaPlayer.MEDIA_ERROR_OTHER_ERROR_CODE:
                case KSYMediaPlayer.MEDIA_ERROR_SERVER_EXCEPTION:
                case KSYMediaPlayer.MEDIA_ERROR_INVALID_DATA:
                case KSYMediaPlayer.MEDIA_ERROR_UNSUPPORT_VIDEO_CODEC:
                case KSYMediaPlayer.MEDIA_ERROR_UNSUPPORT_AUDIO_CODEC:
                case KSYMediaPlayer.MEDIA_ERROR_VIDEO_DECODE_FAILED:
                case KSYMediaPlayer.MEDIA_ERROR_AUDIO_DECODE_FAILED:
                case KSYMediaPlayer.MEDIA_ERROR_3XX_OVERFLOW:
                    if (!isChanged) {
                        isChanged = true;
                        error = "错误码：" + i + "! 切换到备用播放源！";
                        Toast.makeText(MainActivity2.this, error, Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        resetPlayer();
                        playTv(mTvModels.get(curPosition).getBackupSource());
                    } else {
                        retryPlayer(i);
                    }

                    break;
            }
            return false;
        }
    };

    /**
     * 屏幕变化监听
     */
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    if (mVideoSurfaceView != null)
                        mVideoSurfaceView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                }
            }
        }
    };

    /**
     * 异常监听
     */
    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
                    Toast.makeText(MainActivity2.this, "播放失败！(extra:)" + extra, Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.e(TAG, "OnErrorListener, Error:" + what + ",extra:" + extra);
                    Toast.makeText(MainActivity2.this, "播放失败！(extra:)" + extra, Toast.LENGTH_LONG).show();
            }

//            Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
//            String error = "";
//            if (!isChanged) {
//                isChanged = true;
//                error = "错误码：" + extra + "! 切换到备用播放源！";
//                Toast.makeText(MainActivity2.this, error, Toast.LENGTH_LONG).show();
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                resetPlayer();
//                playTv(mTvModels.get(curPosition).getBackupSource());
//            } else {
//                retryPlayer(extra);
//            }
            mLoading.setVisibility(View.GONE);
//            videoPlayEnd();

            return false;
        }
    };

//    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            if (mKSYMediaPlayer != null && mKSYMediaPlayer.isPlaying())
//                mKSYMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            if (mKSYMediaPlayer != null)
//                mKSYMediaPlayer.setDisplay(holder);
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            if (mKSYMediaPlayer != null) {
//                mKSYMediaPlayer.setDisplay(null);
//            }
//        }
//    };


    private SeekBar.OnSeekBarChangeListener mSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mVideoProgress = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mVideoSurfaceView.seekTo(mVideoProgress);
            setVideoProgress(mVideoProgress);
        }
    };

    private int setVideoProgress(int currentProgress) {

        if (mVideoSurfaceView == null)
            return -1;

        long time = currentProgress > 0 ? currentProgress : mVideoSurfaceView.getCurrentPosition();
        long length = mVideoSurfaceView.getDuration();

        mPlayerSeekbar.setMax((int) length);
        mPlayerSeekbar.setProgress((int) time);

        if (time >= 0) {
            //TODO
//            String progress = Strings.millisToString(time) + "/" + Strings.millisToString(length);
//            mPlayerPosition.setText(progress);
        }

        return (int) time;
    }


    private void playTv(String url) {
        if (mVideoSurfaceView.isPlaying()) {
//            mKSYMediaPlayer.stop();
            mVideoSurfaceView.reset();
//            mKSYMediaPlayer.setDisplay(mSurfaceHolder);
            mVideoSurfaceView.setScreenOnWhilePlaying(true);
            mVideoSurfaceView.setBufferTimeMax(30.0f);
            mVideoSurfaceView.setTimeout(5, 30);
        }
        mCurrentUrl = url;
//        mCurrentUrl = "/storage/emulated/0/Movies/HUAWEI_Mediapad_M3.mp4";
        try {
            mLoading.setVisibility(View.VISIBLE);
            mVideoSurfaceView.setDataSource(mCurrentUrl);
            mVideoSurfaceView.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    private void stopTv() {
        //TODO
    }

    private void videoPlayEnd() {
        if (mVideoSurfaceView != null) {
            mVideoSurfaceView.release();
            mVideoSurfaceView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoSurfaceView != null) {
            mVideoSurfaceView.pause();
            mPause = 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mVideoSurfaceView != null) {
            mVideoSurfaceView.start();
            mPause = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayEnd();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        timer.cancel();
    }

    private View.OnClickListener mStartBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hidePanel();
            if (mPause == 0) {
                mPause = 1;
                mPlayerStartBtn.setBackgroundResource(R.drawable.pause);
                mVideoSurfaceView.start();
            } else if (mPause == 1) {
                mPause = 0;
                mPlayerStartBtn.setBackgroundResource(R.drawable.play);
                mVideoSurfaceView.pause();
            } else {
                mPause = 0;
                mPlayerStartBtn.setVisibility(View.GONE);
                mPlayerStartBtn.setBackgroundResource(R.drawable.pause);
                resetPlayer();
                playTv(mTvModels.get(curPosition).getUrl());
                isChanged = false;
            }
        }
    };

    private void hidePanel() {
        mHandler.removeMessages(MSG_HIDE_PANEL);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_PANEL,3000);
    }

    private void dealTouchEvent() {
        if (mPlayerPanel.getVisibility() != View.VISIBLE) {
            mPlayerPanel.setVisibility(View.VISIBLE);
            hidePanel();
        } else {
            mHandler.removeMessages(MSG_HIDE_PANEL);
            mPlayerPanel.setVisibility(View.GONE);
        }


    }

    private void buildProgram(String id) {

        if (mProgramData.containsKey(id)) {
            ProgramData data = mProgramData.get(id);
            final List<ProgramModel> list = data.getPrograms();
//            int resId = 0;
//            switch (id) {
//                case Constants.TV_DONGFANGSTV:
//                    resId = R.id.dongfangstv;
//                    break;
//                case Constants.TV_GUANGDONGSTV:
//                    resId = R.id.guangdongstv;
//                    break;
//                case Constants.TV_GUZHUANGJUCHANGTV:
//                    resId = R.id.guzhuangjc;
//                    break;
//                case Constants.TV_BEIJINGSTV:
//                    resId = R.id.beijingstv;
//                    break;
//                case Constants.TV_JINGPANZONGYITV:
//                    resId = R.id.saishijx;
//                    break;
//                case Constants.TV_KAKUKATONGTV:
//                    resId = R.id.kakukaton;
//                    break;
//            }
            LinearLayout container = (LinearLayout) mRaws.get(id);

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    ProgramModel model = list.get(i);
                    View item = mLayoutInflater.inflate(R.layout.program_item,null,false);
                    TextView programName = (TextView) item.findViewById(R.id.program_name);
                    programName.setText(model.getProgramName());
                    TextView programDuration = (TextView) item.findViewById(R.id.program_duration);
                    programDuration.setText(model.getStartTime().substring(0,5) + "-" + model.getEndTime().substring(0,5));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                    layoutParams.height = mListHeight / mTvModels.size();
                    layoutParams.height = Util.dp2px(this, 85);
                    int duration = getProgramDuration(model);

                    if (i == 0) {
                        long zero = ConvertUtil.string2Date("00:00:00",FORMAT).getTime();
                        long start = ConvertUtil.string2Date(model.getStartTime(),FORMAT).getTime();
                        int firstPosition = (int)(1f * (start - zero) / (1000 * 60f) + 0.5); //分钟
                        layoutParams.leftMargin = (int) ((1f * firstPosition / FIVE_MINUTE) * mFiveMinuteInPixel + 0.5);
                    }

                    layoutParams.width = (int)((1f * Math.abs(duration) / FIVE_MINUTE) * mFiveMinuteInPixel + 0.5);
                    Log.d(TAG,"layoutParams.width " + (1f * Math.abs(duration) / FIVE_MINUTE) * mFiveMinuteInPixel);

                    container.addView(item,layoutParams);
                    if (duration < 0) {
                        break;
                    }
                }

                container.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.d("jianggy", list.get(0).getProgramName());

                        Intent intent = new Intent(MainActivity2.this, ProgramListActivity.class);
                        intent.putExtra("program_list", (Serializable)list);
                        startActivity(intent);
                        return true;
                    }
                });
            }

        }

    }

    private int getProgramDuration(ProgramModel programModel) {
        int duration = 0;
        long start = ConvertUtil.string2Date(programModel.getStartTime(),FORMAT).getTime();
        long end = ConvertUtil.string2Date(programModel.getEndTime(),FORMAT).getTime();
        long zero = ConvertUtil.string2Date("00:00:00",FORMAT).getTime();
        long oneMinuteToZero = ConvertUtil.string2Date("23:59:00",FORMAT).getTime();
        Log.d(TAG,programModel.getStartTime() + "=Start:" + start + "###" +programModel.getEndTime() + "=End:" + end);
        if (start <= end) {
            duration = (int)((end - start) / (1000 * 60f)); //分钟
        } else {
//            duration = 1 + (int) (oneMinuteToZero - start + end - zero) / (1000 * 60) ;
            duration = 1 + (int) ((oneMinuteToZero - start) / (1000 * 60f));
            Log.d(TAG,"Start > End " + duration);
            duration = -1 * duration;
        }
        return  duration;
    }
    private class Task extends TimerTask {
        @Override
        public void run() {
            mScrollView.scrollTo(0, m_firstpos);
        }
    }
    private void tonggleProgramPanel() {
        if (mProgramLayoutPanel.getVisibility() == View.GONE) {
            mProgramLayoutPanel.setVisibility(View.VISIBLE);
            //蒙层
            computeMaskPosition();
            mMask.setVisibility(View.VISIBLE);
            mClose.setVisibility(View.VISIBLE);
            if(clickCount == 1) {
                Task task = new Task();
                timer.schedule(task, 300);
            }
//            mScrollView.scrollTo(0, m_firstpos);
        } else {
            mProgramLayoutPanel.setVisibility(View.GONE);
            mMask.setVisibility(View.GONE);
            mClose.setVisibility(View.GONE);
        }

        if (mErrorRequest.keySet().size() != 0) {
            for (String key : mErrorRequest.keySet()) {
                makeRequest(key,mErrorRequest.get(key));
            }
        }
    }

    private void initProgramPanelLayout(Rect rect) {
        Log.d(TAG,rect.toString());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mProgramLayoutPanel.getLayoutParams();
        layoutParams.leftMargin = rect.right - rect.left;

        FrameLayout.LayoutParams mScrollViewLayoutParams = (FrameLayout.LayoutParams) mScrollView.getLayoutParams();
//        mScrollViewLayoutParams.leftMargin = rect.right - rect.left;
        mScrollViewLayoutParams.height = rect.bottom - rect.top;
        mScrollViewLayoutParams.topMargin = rect.top;


        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) mProgramLayoutPanel.findViewById(R.id.program_container).getLayoutParams();
//        layoutParams1.height = rect.bottom - rect.top;
//        layoutParams1.topMargin = rect.top;

        //时间线
        FrameLayout.LayoutParams timeBarLayoutParams = (FrameLayout.LayoutParams) mTimeBar.getLayoutParams();
        timeBarLayoutParams.topMargin = rect.top - mTimeBar.getViewHeight();
        mTimeBar.setUnitInPixel(mFiveMinuteInPixel);

        getChannel();
//        //取得节目列表
//        getProgramList();
    }

    private void computeMaskPosition() {
        Rect rect = new Rect();
        mListView.getGlobalVisibleRect(rect);
        FrameLayout.LayoutParams maskLayoutParams = (FrameLayout.LayoutParams) mMask.getLayoutParams();
//        maskLayoutParams.topMargin = rect.top;
        maskLayoutParams.height = (int) ((getResources().getDimensionPixelSize(R.dimen.item_height) + getResources().getDimensionPixelSize(R.dimen.divider_height)) * mTvModels.size() + 0.5f);
        Log.d("computeMaskPosition", "maskLayoutParams.height:" +maskLayoutParams.height);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        String hour = Util.formatWithZero("" + calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Util.formatWithZero("" + calendar.get(Calendar.MINUTE));
        String second = Util.formatWithZero("" + calendar.get(Calendar.SECOND));

        long end = ConvertUtil.string2Date(hour + ":" + minute + ":" + second,FORMAT).getTime();
        long zero = ConvertUtil.string2Date("00:00:00",FORMAT).getTime();
        int duration = (int)((end - zero) / (1000 * 60) + 0.5); //分钟
        maskLayoutParams.width = (int)((1f * duration / FIVE_MINUTE) * mFiveMinuteInPixel + 0.5);

        //滚动到当前时间附近
        computeTimePosition(maskLayoutParams.width);
    }

    private void computeTimePosition(final int nowPosition) {

        try {
            int position = (int)(nowPosition - mFiveMinuteInPixel * 2);
            if (position < 0) {
                position = 0;
            }
            final int result = position;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mProgramLayoutPanel.scrollTo(result,0);
                }
            });
        } catch (Exception e) {

        }

    }

    /**
     * 初始化加载图片控件
     */
    private void initImageLoaderConfiguration() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, LocalCacheUtil.getCacheImgPath(this)),
                        null, new Md5FileNameGenerator()))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建

        ImageLoader.getInstance().init(config);//全局初始化此配置
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final float x = event.getX();
            final float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (x < width - 300) {
                        dealTouchEvent();
                        return false;
                    }
                    mLastMotionX = x;
                    mLastMotionY = y;
                    startX = (int) x;
                    startY = (int) y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mLastMotionX;
                    float deltaY = y - mLastMotionY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    // 声音调节标识
                    boolean isAdjustAudio = false;
                    if (absDeltaX > threshold && absDeltaY > threshold) {
                        if (absDeltaX < absDeltaY) {
                            isAdjustAudio = true;
                        } else {
                            isAdjustAudio = false;
                        }
                    } else if (absDeltaX < threshold && absDeltaY > threshold) {
                        isAdjustAudio = true;
                    } else if (absDeltaX > threshold && absDeltaY < threshold) {
                        isAdjustAudio = false;
                    } else {
                        return true;
                    }
                    if (isAdjustAudio) {
                        if (x < width / 2) {
                            if (deltaY > 0) {
                                lightDown(absDeltaY);
                            } else if (deltaY < 0) {
                                lightUp(absDeltaY);
                            }
                        } else {
                            if (deltaY > 0) {
                                volumeDown(absDeltaY);
                            } else if (deltaY < 0) {
                                volumeUp(absDeltaY);
                            }
                        }

                    } else {
                        if (deltaX > 0) {
                            forward(absDeltaX);
                        } else if (deltaX < 0) {
                            backward(absDeltaX);
                        }
                    }
                    mLastMotionX = x;
                    mLastMotionY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(x - startX) > threshold
                            || Math.abs(y - startY) > threshold) {
                        isClick = false;
                    }
                    mLastMotionX = 0;
                    mLastMotionY = 0;
                    startX = (int) 0;
                    if (isClick) {
                        showOrHide();
                    }
                    isClick = true;
                    break;

                default:
                    break;
            }
            return true;
        }

    };

    private void volumeDown(float delatY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int down = (int) (delatY / height * max * 3);
        int volume = Math.max(current - down, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int transformatVolume = volume * 100 / max;
        volumnController.show(transformatVolume);
    }

    private void volumeUp(float delatY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int up = (int) ((delatY / height) * max * 3);
        int volume = Math.min(current + up, max);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int transformatVolume = volume * 100 / max;
        volumnController.show(transformatVolume);
    }

    private void lightDown(float delatY) {
//        int down = (int) (delatY / height * 255 * 3);
//        int transformatLight = LightnessController.getLightness(this) - down;
//        LightnessController.setLightness(this, transformatLight);
    }

    private void lightUp(float delatY) {
//        int up = (int) (delatY / height * 255 * 3);
//        int transformatLight = LightnessController.getLightness(this) + up;
//        LightnessController.setLight(this, transformatLight);
    }

    private void showOrHide() {

    }

    private void backward(float delataX) {
//        int current = mVideo.getCurrentPosition();
//        int backwardTime = (int) (delataX / width * mVideo.getDuration());
//        int currentTime = current - backwardTime;
//        mVideo.seekTo(currentTime);
//        mSeekBar.setProgress(currentTime * 100 / mVideo.getDuration());
//        mPlayTime.setText(formatTime(currentTime));
    }

    private void forward(float delataX) {
//        int current = mVideo.getCurrentPosition();
//        int forwardTime = (int) (delataX / width * mVideo.getDuration());
//        int currentTime = current + forwardTime;
//        mVideo.seekTo(currentTime);
//        mSeekBar.setProgress(currentTime * 100 / mVideo.getDuration());
//        mPlayTime.setText(formatTime(currentTime));
    }

    protected void retryPlayer(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("连接服务器网络异常,是否重试？");
        builder.setTitle("提示");
        builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isChanged = false;
//                String error = "错误码：" + i + "! 切换到主播放源！";
//                Toast.makeText(MainActivity2.this, error, Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                resetPlayer();
                playTv(mTvModels.get(curPosition).getUrl());
                displayRetryDialog = false;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
 }

}
