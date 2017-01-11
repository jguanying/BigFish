package crown.dafish.com.dafishtv;

import android.app.Activity;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import crown.dafish.com.model.ProgramData;
import crown.dafish.com.model.ProgramModel;
import crown.dafish.com.model.TvModel;
import crown.dafish.com.utils.Constants;
import crown.dafish.com.utils.ConvertUtil;
import crown.dafish.com.utils.Util;
import crown.dafish.com.view.TimeBar;

public class MainActivity extends Activity {

    private static final String TAG = "DaFishTv";

    private ListView mListView;

    private String[] mProgramId;

    private String[] mTvUrl;

    private DrawerLayout mDrawerLayout;

    private ArrayList<TvModel> mTvModels = new ArrayList<>();

    private SurfaceView mVideoSurfaceView = null;

    private SurfaceHolder mSurfaceHolder = null;

    private KSYMediaPlayer mKSYMediaPlayer;

    private int mVideoWidth = 0;

    private int mVideoHeight = 0;

    private String mCurrentUrl = "";

    private boolean mUseHwCodec = false;

    private boolean mPause = false;

    private LinearLayout mPlayerPanel;

    private ImageView mPlayerStartBtn;

    private SeekBar mPlayerSeekbar;

    private TextView mPlayerPosition;

    private int mVideoProgress = 0;

    private static final int MSG_HIDE_PANEL = 0;

    private static final int MSG_REFRESH_PROGRAM = 1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initPlayer();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(this);
        mMask = findViewById(R.id.mask);
        mTimeBar = (TimeBar) findViewById(R.id.timeBar);
        mLoading = (ProgressBar) findViewById(R.id.progressBar);
        mProgramLayoutPanel = findViewById(R.id.program_panel);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                tonggleProgramPanel();
            }
        });

        mListView = (ListView) findViewById(R.id.tv_list);
        for (int i = 0; i < mProgramId.length; i++) {
            TvModel tvModel = new TvModel();
            tvModel.setId(mProgramId[i]);
            tvModel.setUrl(Constants.TV_BASE_URL + mTvUrl[i]);
            mTvModels.add(tvModel);
        }

        TvAdapter adapter = new TvAdapter(getApplicationContext(), mTvModels);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                playTv(mTvModels.get(position).getUrl());
            }
        });
        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListHeight = mListView.getMeasuredHeight();
                ((TvAdapter) mListView.getAdapter()).setItemHeight(mListHeight);
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
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void initPlayer() {
        mVideoSurfaceView = (SurfaceView) findViewById(R.id.player_surface);
        mVideoSurfaceView.setVisibility(View.GONE);
        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mVideoSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKSYMediaPlayer != null) {
                    dealTouchEvent();
                }
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

        mKSYMediaPlayer = new KSYMediaPlayer.Builder(this.getApplicationContext()).build();
        mKSYMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mKSYMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mKSYMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mKSYMediaPlayer.setOnInfoListener(mOnInfoListener);
        mKSYMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mKSYMediaPlayer.setOnErrorListener(mOnErrorListener);
//        mKSYMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener);
        mKSYMediaPlayer.setScreenOnWhilePlaying(true);
        mKSYMediaPlayer.setBufferTimeMax(300.0f);
        mKSYMediaPlayer.setTimeout(5, 300);

        if (mUseHwCodec) {
            //硬解264&265
            Log.e(TAG, "Hardware !!!!!!!!");
            mKSYMediaPlayer.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
        }
    }


    private void getProgramList() {
        mRequestQueue = Volley.newRequestQueue(this);
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

    private void makeRequest(final String id,final String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());
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
                    message = "UnknownError Happened When Get Program";
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
            long duration = mKSYMediaPlayer.getDuration();
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
            Toast.makeText(MainActivity.this, "节目已播放完毕", Toast.LENGTH_LONG).show();
            mDrawerLayout.openDrawer(GravityCompat.START);
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

            mVideoWidth = mKSYMediaPlayer.getVideoWidth();
            mVideoHeight = mKSYMediaPlayer.getVideoHeight();

            // Set Video Scaling Mode
            mKSYMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);

            //start player
            mKSYMediaPlayer.start();
            mLoading.setVisibility(View.GONE);
            mVideoSurfaceView.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "开始播放节目", Toast.LENGTH_LONG).show();
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
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    // Player find a new stream(video or audio), and we could reload the video.
                    if (mKSYMediaPlayer != null)
                        mKSYMediaPlayer.reload(mCurrentUrl, false, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_ACCURATE);
                    mLoading.setVisibility(View.GONE);
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

                    if (mKSYMediaPlayer != null)
                        mKSYMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
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
                    break;
                default:
                    Log.e(TAG, "OnErrorListener, Error:" + what + ",extra:" + extra);
            }
            mLoading.setVisibility(View.GONE);
//            videoPlayEnd();

            return false;
        }
    };

    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mKSYMediaPlayer != null && mKSYMediaPlayer.isPlaying())
                mKSYMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mKSYMediaPlayer != null)
                mKSYMediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mKSYMediaPlayer != null) {
                mKSYMediaPlayer.setDisplay(null);
            }
        }
    };


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
            mKSYMediaPlayer.seekTo(mVideoProgress);
            setVideoProgress(mVideoProgress);
        }
    };

    private int setVideoProgress(int currentProgress) {

        if (mKSYMediaPlayer == null)
            return -1;

        long time = currentProgress > 0 ? currentProgress : mKSYMediaPlayer.getCurrentPosition();
        long length = mKSYMediaPlayer.getDuration();

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
        if (mKSYMediaPlayer.isPlaying()) {
//            mKSYMediaPlayer.stop();
            mKSYMediaPlayer.reset();
            mKSYMediaPlayer.setDisplay(mSurfaceHolder);
            mKSYMediaPlayer.setScreenOnWhilePlaying(true);
            mKSYMediaPlayer.setBufferTimeMax(30.0f);
            mKSYMediaPlayer.setTimeout(5, 30);
        }
        mCurrentUrl = url;
//        mCurrentUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        try {
            mLoading.setVisibility(View.VISIBLE);
            mKSYMediaPlayer.setDataSource(mCurrentUrl);
            mKSYMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mKSYMediaPlayer.start();
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    private void stopTv() {
        //TODO
    }

    private void videoPlayEnd() {
        if (mKSYMediaPlayer != null) {
            mKSYMediaPlayer.release();
            mKSYMediaPlayer = null;
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

        if (mKSYMediaPlayer != null) {
            mKSYMediaPlayer.pause();
            mPause = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mKSYMediaPlayer != null) {
            mKSYMediaPlayer.start();
            mPause = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayEnd();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    private View.OnClickListener mStartBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPause = !mPause;
            hidePanel();
            if (mPause) {
                mPlayerStartBtn.setBackgroundResource(R.drawable.qyvideo_pause_btn);
                mKSYMediaPlayer.pause();
            } else {
                mPlayerStartBtn.setBackgroundResource(R.drawable.qyvideo_start_btn);
                mKSYMediaPlayer.start();

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
            List<ProgramModel> list = data.getPrograms();
            int resId = 0;
            switch (id) {
                case Constants.TV_DONGFANGSTV:
                    resId = R.id.dongfangstv;
                    break;
                case Constants.TV_GUANGDONGSTV:
                    resId = R.id.guangdongstv;
                    break;
                case Constants.TV_GUZHUANGJUCHANGTV:
                    resId = R.id.guzhuangjc;
                    break;
                case Constants.TV_BEIJINGSTV:
                    resId = R.id.beijingstv;
                    break;
                case Constants.TV_JINGPANZONGYITV:
                    resId = R.id.saishijx;
                    break;
                case Constants.TV_KAKUKATONGTV:
                    resId = R.id.kakukaton;
                    break;
            }
            LinearLayout container = (LinearLayout) mProgramLayoutPanel.findViewById(resId);

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    ProgramModel model = list.get(i);
                    View item = mLayoutInflater.inflate(R.layout.program_item,null,false);
                    TextView programName = (TextView) item.findViewById(R.id.program_name);
                    programName.setText(model.getProgramName());
                    TextView programDuration = (TextView) item.findViewById(R.id.program_duration);
                    programDuration.setText(model.getStartTime().substring(0,5) + "-" + model.getEndTime().substring(0,5));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.height = mListHeight / mTvModels.size();

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

    private void tonggleProgramPanel() {
        if (mProgramLayoutPanel.getVisibility() == View.GONE) {
            mProgramLayoutPanel.setVisibility(View.VISIBLE);
            //蒙层
            computeMaskPosition();
            mMask.setVisibility(View.VISIBLE);
            mClose.setVisibility(View.VISIBLE);
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
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) mProgramLayoutPanel.findViewById(R.id.program_container).getLayoutParams();
        layoutParams1.height = rect.bottom - rect.top;
        layoutParams1.topMargin = rect.top;

        //蒙层
        computeMaskPosition();

        //时间线
        FrameLayout.LayoutParams timeBarLayoutParams = (FrameLayout.LayoutParams) mTimeBar.getLayoutParams();
        timeBarLayoutParams.topMargin = rect.top - mTimeBar.getViewHeight();
        mTimeBar.setUnitInPixel(mFiveMinuteInPixel);

        //取得节目列表
        getProgramList();
    }

    private void computeMaskPosition() {
        Rect rect = new Rect();
        mListView.getGlobalVisibleRect(rect);
        FrameLayout.LayoutParams maskLayoutParams = (FrameLayout.LayoutParams) mMask.getLayoutParams();
        maskLayoutParams.topMargin = rect.top;
        maskLayoutParams.height = rect.bottom - rect.top;
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
    }
}
