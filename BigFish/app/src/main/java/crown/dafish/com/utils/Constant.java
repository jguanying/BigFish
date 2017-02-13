package crown.dafish.com.utils;


public class Constant {

	/**
	 * 默认编码类型
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * db名称
	 */
	public static final String DB_NAME = "huaiAnDB";
	
	/**
	 * 新闻页里面的图片下载时在SD卡中保存的路径
	 */
	public static final String IMAGE_DOWNLOAD_PATH = "/zsha_images/";
	
	// 请求路径定义 Start----------

    //==本地测试服务器
    //后台非新闻类接口地址
//    public static final String SERVER_URL = "http://10.10.82.101/";
//    //前台新闻相关接口的URL地址
//    public static final String SERVER_URL_FRONT = "http://10.10.82.103/server_front/";
    
	
    //==实际服务器
    //后台非新闻类接口地址
    public static final String SERVER_URL = "http://218.2.27.251/hainhand/";
    //前台新闻相关接口的URL地址
    public static final String SERVER_URL_FRONT = "http://218.2.27.242/server_front/";
    
    
    /**
     * 得到数字报中三个报纸的相关信息
     * 数字报实际环境,发布时打开
     */
    public static final String REQ_DIGITAL_COVER_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_get_ePaper";
    
    
    /**
     * 得到数字报中三个报纸的相关信息
     * 数字报本地环境
     */
    //public static final String REQ_DIGITAL_COVER_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_get_ePaper_local";
    
    

	/**
	 * 闪屏图片取得请求URL
	 */
	public static final String REQ_FLASH_IMG = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_first_screen";
	
	/**
	 * 登录请求URL
	 */
	public static final String REQ_LOGIN = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_android_login";
	
	
	/**
	 * 注册请求URL
	 */
	public static final String REQ_REGISTER = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_user_regist";

	
	/**
	 * 获取用户信息URL
	 */
	public static final String REQ_USER_INFO = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_android_getuserinfo";

	
	/**
	 * 用户评论请求URL
	 */
	public static final String REQ_USER_COMMENT = SERVER_URL_FRONT + "/index.php?m=comment&c=index&a=public_app_android_user_list&commentid=1";

	/**
	 * 用户收藏请求URL
	 */
	public static final String REQ_USER_LIKE = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_android_favorite";

	/**
	 * 数字报图片列表请求URL
	 */
	public static final String REQ_SHUZIBAO_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_digital_newspaper";

	/**
	 * 新闻栏目请求URL
	 */
	public static final String REQ_NEWS_COLUMN = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_category";
	
	/**
	 * 热点新闻请求URL
	 */
	public static final String REQ_HOT_NEWS = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_recommend_article_list";
	
	/**
     * 专题列表URL
     */
    public static final String REQ_TOPIC_NEWS_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_get_special_list";
	
	/**
	 * 新闻列表请求URL
	 * 普通新闻，政务大厅新闻,机构栏目新闻
	 */
	public static final String REQ_NEWS_LIST_COLUMN = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_article_list";
	
	/**
	 * 内容页评论列表请求URL
	 */
	public static final String REQ_COMMENT_LIST = SERVER_URL_FRONT + "/index.php?m=comment&c=index&a=public_app_android_comment_list&commentid=1";
	
	/**
	 * 提交收藏信息URL
	 */
	public static final String REQ_COMMIT_STORE = SERVER_URL_FRONT + "/api.php?op=add_app_favorite";
	
	/**
	 * 取消收藏信息URL
	 */
    public static final String REQ_CANCEL_STORE = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_delete_favorite";
	

    
    /**
	 * 提交评论信息URL
	 */
	public static final String REQ_COMMIT_COMMENT = SERVER_URL_FRONT + "/index.php?m=comment&c=index&a=public_app_android_post_comment&commentid=1";
	
	/**
	 *点赞URL
	 */
	public static final String REQ_COMMIT_SUPPORT = SERVER_URL_FRONT + "/index.php?m=comment&c=index&a=app_android_support&commentid=1";
	
	/**
	 * 新版本取得URL
	 */
	public static final String REQ_VERSION = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_get_last_version";
	
	/**
	 * 日志上传URL
	 * 确定是否为后台
	 */
	public static final String REQ_LOG_UPLOAD = SERVER_URL + "/index.php?m=content&c=api&a=public_app_android_upload_logs";
	
	/**
	 * 专题URL
	 */
	public static final String REQ_TOPIC = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_get_special_c_list";
	
	/**
     * 图集URL
     */
    public static final String REQ_IMAGE = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_get_image_set";
    
    /**
     * 新闻检索URL
     */
    public static final String REQ_SEARCH = SERVER_URL_FRONT + "/index.php?m=search&c=index&a=public_app_android_search";
    
    /**
     * 判断是否已经收藏
     */
    public static final String REQ_CHECK_STORE = SERVER_URL_FRONT + "/api.php?op=check_app_favorite";
	
    /**
     * 取得广告内容URL
     */
    public static final String REQ_AD_LIST = SERVER_URL_FRONT + "/index.php?m=poster&c=index&a=public_app_startup_poster";
    
    /**
     * 音视频列表URL
     */
    public static final String REQ_MEDIA_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=api&a=public_app_android_media_list";
    
    /**
     * 投诉列表检索一览URL
     */
    public static final String REQ_QUESTION_LIST = SERVER_URL + "/index.php?m=question&c=index&a=public_app_get_question_list";
    

    /**
     * 组织机构类型检索一览URL
     */
    public static final String REQ_ORGTYPE_LIST = SERVER_URL + "/index.php?m=insitution&c=insitution_column&a=public_app_get_insitution_column_list";
    
    /**
     * 组织机构检索一览URL
     */
    public static final String REQ_ORGANIZATION_LIST = SERVER_URL + "/index.php?m=insitution&c=index&a=public_app_get_insitution_list";

    /**
     * 组织机构检索订阅/取消订阅 
     */
    public static final String REQ_ORGANIZATION_SUBSCRIBE = SERVER_URL + "/index.php?m=insitution&c=index&a=public_app_set_insitution_subscribe";

    
    /**
     * 机构明细的栏目检索
     */
    public static final String REQ_ORGANIZATION_DETAIL_COLUMN =  SERVER_URL + "/index.php?m=insitution&c=insitution_category&a=public_app_get_insitution_category_list";

    /**
     * 地方查询检索
     */
    public static final String REQ_ORGANIZATION_EXPAND_COLUMN = SERVER_URL + "index.php?m=insitution&c=insitution_column&a=public_app_get_columnInsitutionList";

    
    /**
     * 推荐机构检索
     */
    public static final String REQ_ORGANIZATION_RECOMMEND_COLUMN = SERVER_URL + "index.php?m=insitution&c=index&a=public_app_get_sticked_insitutionList";

    
    /**
     * 发出投诉接口(我的提问,问政)
     * post
     */
    public static final String REQ_REGIST_QUESTION = SERVER_URL + "index.php?m=question&c=index&a=public_app_regist_question";

    /**
     * 投诉详细 说两句 
     * post
     */
    public static final String REQ_QUESTION_INSERT_ANSWER = SERVER_URL + "index.php?m=question&c=index&a=public_app_insert_answer";
    
    /**
     * 问题类型查询
     * post
     */
    public static final String REQ_QUESTION_TYPE_LIST = SERVER_URL + "index.php?m=question&c=classify_manage&a=public_app_getClassifyList";


    /**
     * 取验证码
     * post
     */
    public static final String REQ_REGISTER_GET_CODE = SERVER_URL_FRONT + "index.php?m=member&c=verification_member&a=public_app_get_verification_code";
    
    
    /**
     * 上传邀请码
     * post
     */
    public static final String REQ_INVITE_CODE_UPLOAD = SERVER_URL + "index.php?m=insitution&c=invite_code&a=public_app_regist_invite_code";
    
    
    /**
     * 用户资料修改
     * post
     */
    public static final String REQ_USER_INFO_EDIT = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_update_user";
    
    
    /**
     * 用户密码修改
     * post
     */
    public static final String REQ_USER_INFO_EDIT_PASSWORD = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_update_user_password";
    
    /**
     * 我的订阅
     * post
     */
    public static final String REQ_MY_INSITUTION_SCRIBE = SERVER_URL + "index.php?m=insitution&c=insitution_subscribe&a=public_app_get_my_insitution_scribe";

    /**
     * 新闻权值减少
     * post
     */
    public static final String REQ_NEWS_DEEL_WITH = SERVER_URL_FRONT + "index.php?m=content&c=api&a=public_app_dealwith_news_favorites";
    

    /**
     * 投诉问题关注度增加
     * post
     */
    public static final String REQ_QUESTION_FOCUS_ADD = SERVER_URL + "index.php?m=question&c=index&a=public_app_get_question_by_id";
    

    /**
     * 银联的签名串码
     * post
     */
    public static final String REQ_GET_UNIONPAYKEY = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_get_unionpaykey";
    
    
    
    /**
     * 分享成功后增加积分URL
     */
    public static final String REQ_SHARE_SUCCESS = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_share_addCredits";
    
    
    /**
     * 获得积分列表 URL
     */
    public static final String REQ_GET_CREDITSLIST = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_getCreditsList";
    

    /**
     * 获得积分列表 URL
     */
    public static final String REQ_DO_JD = SERVER_URL_FRONT + "/index.php?m=member&c=index&a=public_app_checkin_addCredits";
    

    
    /**
     * 反馈意见提交
     * 
     */
    public static final String REQ_SEND_SUGGESTION = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_insert_feedback";
    
    
    /**
     * 获得积分商城商品列表
     * 
     */
    public static final String REQ_GET_GOODS_LIST = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_getCreditsShopping";
    
    
    /**
     * 用户消费积分兑换物品的列表
     */
    public static final String REQ_USER_EXCHANGE_LIST = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_getCreditsExchangeList";
    
    
    /**
     * 用户商品兑换
     */
    public static final String REQ_USER_EXCHANGE_REDEEM = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_credits_exchange";
    

    /**
     * 修改手机号
     */
    public static final String REQ_USER_MODIFY_PHONE = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_update_mobile";
    


    /**
     * 忘记密码之后的重置密码
     */
    public static final String REQ_PASSWORD_RESET_PHONE = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_reset_password";
    
    
    
    /**
     * 问政点赞和取消点赞
     */
    public static final String REQ_QUESTION_LIKE = SERVER_URL + "index.php?m=question&c=index&a=public_app_add_question_likeCount";
    
    
    /**
     * 问政回复点赞和取消点赞
     */
    public static final String REQ_QUESTION_REPLY_LIKE = SERVER_URL + "index.php?m=question&c=index&a=public_app_add_question_answer_likeCount";
    
    
    
    /**
     * 热词列表
     */
    public static final String REQ_HOT_WORD_LIST = SERVER_URL_FRONT + "index.php?m=search&c=index&a=public_app_get_hot_words";
    

    /**
     * 用户升级请求URL
     */
    public static final String REQ_UPGRADE_USER = SERVER_URL_FRONT + "index.php?m=member&c=index&a=public_app_old_user_upgraded";

    
    /**
     * 专题分类请求URL
     */
    public static final String REQ_SPECIAL_CATEGORY = SERVER_URL + "index.php?m=special&c=index&a=public_app_get_special_category";

    
    
    
    
    /**
     * 以下均为暂时没用的接口定义
     * 取得爆料列表URL
     */
    public static final String REQ_REPORT_LIST = SERVER_URL_FRONT + "/index.php?m=content&c=baoliao&a=public_app_android_baoliao_list";
    
    /**
     * 提交爆料内容URL
     */
    public static final String REQ_REPORT_CONTENT = SERVER_URL_FRONT + "/index.php?m=content&c=baoliao&a=public_app_ios_uploadfile";
    
    /**
     * 提交活动内容URL
     */
    public static final String REQ_ACTION_CONTENT = SERVER_URL + "/index.php?m=content&c=activity&a=public_app_ios_join";
    
    /**
     * 提交爆料附件URL
     */
    public static final String REQ_REPORT_ATTACH = SERVER_URL + "/index.php?m=content&c=baoliao&a=public_app_android_uploadfile";
    
    /**
     * 活动URL
     */
    public static final String REQ_ACTION_LIST = SERVER_URL + "/index.php?m=content&c=activity&a=public_app_android_activity_list";
    
    /**
     * 贵阳网请求URL
     */
    public static final String REQ_GUIYANG_WEB = SERVER_URL + "/index.php?m=content&c=api&a=public_app_android_gywb_url";
    
    /**
     * 论坛BBS请求URL
     */
    public static final String REQ_BBS = SERVER_URL + "/index.php?m=content&c=api&a=public_app_android_report";
    
    /**
     * 读报地址
     * 这个要换成淮安的实际地址
     * 没有用了
     */
    public static final String READ_PAPER_URL = "http://ipaper.gywb.cn/index.htm";
    

    // 请求路径定义 End----------

	// 广播Action定义 Start----------
	/**
	 * 登录成功
	 */
	public static String ACTION_LOGIN_SUCCESS = "action_login_success";

	 /**
     * 切换夜间模式成功
     */
    public static String ACTION_NIGHT_SUCCESS = "action_night_success";
    
	/**
	 * 取得数字报的日期广播的key
	 */
	public static final String ACTION_DATE = "action_date";
	// 广播Action定义 End----------

	// 消息key定义 Start----------
	/**
	 * 请求结果CODE
	 */
	public static String KEY_CODE = "code";

	/**
	 * 请求结果消息
	 */
	public static String KEY_MSG = "msg";
	
	/**
	 * 请求结果消息
	 */
	public static String KEY_FLAG = "flag";
	
	/**
	 * 用于圈子的参数
	 */
	public static String KEY_SUBMIT = "dosubmit";
	
	/**
	 * 用于圈子的参数
	 */
	public static String KEY_FORWARD = "forward";

	/**
	 * 设备ID
	 */
	public static String KEY_UDID = "udid";

	/**
	 * 用户ID
	 */
	public static String KEY_USERID = "userId";
	/**
	 * 用户名
	 */
	public static String KEY_USERNAME = "username";
	/**
	 * 用户密码
	 */
	public static String KEY_PASSWORD = "password";
	
	/**
	 * 邮箱
	 */
	public static String KEY_EMAIL = "email";

	/**
	 * 数据起始index
	 */
	public static String KEY_STARTRECORD = "startRecord";

	/**
	 * 每页数据长度
	 */
	public static String KEY_LEN = "len";
	
	/**
	 * 热点新闻数
	 */
	public static String KEY_HOT_LEN = "hotlen";

	/**
	 * 类型
	 */
	public static String KEY_TYEP = "type";
	
    /**
     * 内容区分
     */
    public static String KEY_CONTENT_TYEP = "contentType";
	
	/**
     * 专题图片URl
     */
    public static String KEY_TOPIC_PIC_URL = "topicPicUrl";
    
    /**
     * 专题说明
     */
    public static String KEY_TOPIC_DESC = "topicDesc";
    
    /**
     * 专题标题
     */
    public static String KEY_TOPIC_TITLE = "topicTitle";
    
    /**
     * 专题URL
     */
    public static String KEY_TOPIC_URL = "topicUrl";

	/**
	 * 请求结果CODE
	 */
	public static String KEY_FLASH_PIC_URL = "picUrl";
	
	// add by zhangyingbo 2014/08/07 begin
	/**
	 * 数字报请求结果
	 */
	public static final String KEY_DIGITAL_RESULT = "columns";

	/**
	 * 数字报的日期key
	 */
	public static final String KEY_DIGITAL_DATE = "date";

	/**
	 * 数字报的版面名称key
	 */
	public static final String KEY_DIGITAL_NAME = "name";

	/**
	 * 数字报的版面排序key
	 */
	public static final String KEY_DIGITAL_ORDER = "originalPlateOrder";

	/**
	 * 数字报的版面图片下载地址key
	 */
	public static final String KEY_DIGITAL_URL = "imagePublishPath";

	/**
	 * 数字报的版面缩略图下载地址key
	 */
	public static final String KEY_DIGITAL_NAVI_URL = "imageNavigationPublishPath";
	/**
	 * 新闻/服务ID key
	 */
	public static final String KEY_DETAILID = "detailId";
	/**
	 * 评论ID key
	 */
	public static final String KEY_COMMENTID = "commentId";
	/**
	 * 栏目ID key
	 */
	public static final String KEY_COLUMNID = "columnId";
	/**
     * 专题ID key
     */
    public static final String KEY_SPECIALID = "specialId";

	/**
	 * 新闻/服务ID key
	 */
	public static final String KEY_NEWSID = "detailId";
	
	/**
	 * 上传log文件名称
	 */
	public static final String KEY_LOG_NAME = "name";
	
	/**
	 * 上传log文件
	 */
	public static final String KEY_LOG = "logfile";
	
	
    /**
     * 日期
     */
    public static final String             KEY_DATE                = "date";
    
	
	/**
	 * Mac地址
	 */
	public static final String KEY_MAC = "macAddress";
	
	/**
     * keyword key
     */
    public static final String KEY_KEYWORD = "keyword";
    
    /**
     * news
     */
    public static final String KEY_NEWS = "news";
    
    /**
     * 新闻类型
     */
    public static final String KEY_NEWS_TYPE = "newsType";
	
	// add by zhangyingbo 2014/08/07 end
	/**
	 * 新闻/服务类别（新闻）
	 */
	public static final String TYPE_NEWS_STRING = "news";
	/**
	 * 新闻/服务类别（服务）
	 */
	public static final String TYPE_SERVICE_STRING = "service";

	// 消息key定义 End----------

	/**
	 * 图片文件前缀
	 */
	public static final String CAPTURE_PREFIX = "CAPTURE_";

	/**
	 * Sqlite目录
	 */
	public static final String DB_DIR = "/db";
	/**
	 * 图片缓存目录
	 */
	public static final String IMG_DIR = "/img";
	/**
	 * 离线包目录
	 */
	public static final String OFFLINE_DIR = "/offline";

	/**
	 * 日志目录
	 */
	public static final String LOG_DIR = "/log";
	
	/**
     * 拍照图片缓存目录
     */
    public static final String CAMERA_DIR = "/scrapSpace";
	
	/**
	 * 下载更新的文件名字
	 */
	public static final String UPDATE_FILE_NAME = "zsha_";
	
	/**
     * 下载更新的文件格式
     */
    public static final String UPDATE_FILE_TYPE = ".apk";

	/**
	 * 日历中可以阅读数字报的开始日期
	 */
	public static final String PAPER_BEGIN_DATE = "20141011";

	// ------------常量定义-----------------
	public static Integer TIMEOUT = 20;

	public static String TYPE_NEWS = "1";
	public static String TYPE_SERVICE = "2";
	public static String TYPE_HOTNEWS = "3";
	public static String TYPE_TOPICNEWS = "4";
	public static String TYPE_PUBLISHNEWS = "5";
	public static String TYPE_CAN_COMMENT = "1";
	public static String TYPE_NO_COMMENT = "0";
	
	/**
	 * 热点评论个数
	 */
	public static int HOT_COMMENT_NUM = 0;
	
	/**
	 * 每次加载评论个数
	 */
	public static final int LOAD_RECORD_NUM = 10;
	/**
	 * 热点新闻加载个数
	 */
	public static final int HOT_NEWS_NUM = 3;
	
    /**
     * 版本信息文件
     */
    public static final String VERSION_CODE = "version_code";
    
    /**
     * 目录列表高度
     */
    public static final int POPUPWINDOW_HEIGHT = 280;
    
    /**
     * 目录列表子列表的宽度
     */
    public static final int POPUPWINDOW_ITEM_WIDTH = 160;
    
    /**
     * 存储字体大小
     */
    public static final String SHARE_WORD_SIZE = "wordSize";
    
    /**
     * 存储是否推送
     */
    public static final String SHARE_PUSH = "newsPush";
    
    /**
     * 存储是否wifi下载图片
     */
    public static final String SHARE_WIFI = "wifioadimage";
    
    /**
     * 存储是否为夜间模式
     */
    public static final String SHARE_NIGHT_MODE = "nightmode";
    
    
    /**
     * 存储是否为第三方登录 true为第三方登录； false为正常登录
     */
    public static final String SHARE_LOGIN_MODE = "loginmode";
    
    
    /**
     * 存储是否启动新手引导
     */
    public static final String SHARE_NEWUSER_GUIDE_MODE = "newuserguidemode";
    
    
    /**
     * 字体大小
     */
    public static final String WORD_SIZE = "size";
    
    /**
     * 手机上传
     */
    public static final int FILECHOOSER_RESULTCODE = 1;
    
    /**
     * 新闻或者服务列表字体大小: 小号字
     */
    public static final float WORD_SIZE_SMALL  = 12;
    
    /**
     * 新闻或者服务列表字体大小: 中号字
     */
    public static final float WORD_SIZE_MIDDLE = 14;
    
    /**
     * 新闻或者服务列表字体大小: 大号字
     */
    public static final float WORD_SIZE_BIG = 16;
    
    /**
     * 手指向右滑动时的最小速度
     */ 
    public static final int XSPEED_MIN = 200;  
    
    /**
     * 手指向右滑动时的最小距离  
     */
    public static final int XDISTANCE_MIN = 60;
    
    /**
     * 屏幕中显示的新闻栏目个数  
     */
    public static final int COLUMN_NUM = 6;
    
    /**
     * 屏幕中显示的发布栏目个数  
     */
    public static final int PUBLISH_COLUMN_NUM = 3; 
    
    /**
     * 屏幕中显示的爆料栏目个数  
     */
    public static final int REPORT_COLUMN_NUM = 2; 
    
    /**
     * 各种不同的布局 
     */
    public static final int  LAYOUT_IMAGE_TEXT  = 0;
    public static final int  LAYOUT_LARGE_IMAGE = 1;
    public static final int  LAYOUT_THREE_IMAGE = 2;
    public static final int  LAYOUT_SUBFIELD_TITLE = 3;
    public static final int  LAYOUT_VIDEO = 4;
    public static final int  LAYOUT_AUDIO = 5;
    public static final int  LAYOUT_LIVE = 6;
    public static final int  TYPE_MAX_COUNT = 9;
    public static final int  TYPE_VOTE = 8;
    public static final int  TYPE_VOTE_MORE = 9;
    
    /**
     * 新闻列表项左下角的脚标图片
     * */
    public static final int  LAYOUT_IMAGE_NULL = 0;   //无
    public static final int  LAYOUT_IMAGE_TOPIC = 1;     //专题
    public static final int  LAYOUT_IMAGE_TG = 2;     //推广
    public static final int  LAYOUT_IMAGE_DJ = 3;     //独家
    public static final int  LAYOUT_IMAGE_ZB = 4;     //直播
    public static final int  LAYOUT_IMAGE_HA = 5;     //淮安
    public static final int  LAYOUT_IMAGE_SZ = 6;     //市州
    public static final int  LAYOUT_IMAGE_QX = 7;     //区县
    public static final int  LAYOUT_IMAGE_YC = 8;     //原创
    public static final int  LAYOUT_IMAGE_TUJIAN = 9; //推荐
    public static final int  LAYOUT_IMAGE_TUJI = 10;  //图集
    public static final int  LAYOUT_IMAGE_PL = 11;    //评论
   
    
    /**
     * 新闻打开的不同类型
     */
    public static final int  CONTENT_TYPE_NORMAL  = 0;
    public static final int  CONTENT_TYPE_TOPIC  = 1;
    public static final int  CONTENT_TYPE_IMAGE  = 2;
    public static final int  CONTENT_TYPE_LIVE  = 3;
    public static final int  CONTENT_TYPE_VIDEO  = 5;
    public static final int  CONTENT_TYPE_AUDIO  = 4;
    
    public static final int  MEDIA_TYPE_VIDEO  = 2;
    public static final int  MEDIA_TYPE_AUDIO  = 1;
    
    /**
     * 栏目管理:选择
     */
    public static final int COLUMN_SELECTED = 1;
    
    /**
     * 栏目管理:未选择
     */
    public static final int COLUMN_SELECTED_NO = 0;
    
    /**
     * 栏目管理:固定
     */
    public static final int COLUMN_STATIC = 1;
    
    /**
     * 栏目管理:不固定
     */
    public static final int COLUMN_STATIC_NO = 0;
    
    /** 调整返回的RESULTCODE */
    public  static final int CHANNELRESULT = 10;
    
    /**
     * 逗号分隔符
     */
    public static final String SPLIT_CHAR = ",";
    
    /**
     * js调用java接口
     */
    public static final String Js2JavaInterfaceName = "JsUseJava";
    

    /**
     * 推荐应用显示的图标地址
     */
    public static final String recommand_app_logo_URL = "http://218.2.27.242/server_front/statics/images/28.png";
    
    /**
     * 推荐应用显示二维码页面的URL
     * 原来的二维码地址: http://218.2.27.242/server_front/show_details.html
     */
    public static final String recommand_app_qrcode_page_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zsha.activity";
    
    
    /**
     * 全民付web 生产环境地址
     */
    public static final String QMF_WEB_PAGE_URL = "https://mpos.quanminfu.com/QmfWeb/";
    
    
    /**
     * 全民付web渠道号
     * 由银商分配提供
     * 我们的100050
     */
    public static final String CHANNELID = "100050";
    
    /**
     * 全民付web定制号
     * 由银商分配提供
     * 我们的1150
     */
    public static final String CUSTOMIZEID = "1150";
    
    
    /**
     * 房贷计算器 
     */
    public static final String LIFE_WEB_FD_URL = "http://dy.ha101.cn/zsha1/index.php/Queryloan";
    
    /**
     * 淮安人事档案查询 
     */
    public static final String LIFE_WEB_RS_URL = "http://dy.ha101.cn/zsha1/index.php/Queryfile";
    
    /**
     * 公交查询 
     */
    public static final String LIFE_WEB_GJ_URL = "http://218.94.158.108/MobileBusWeb/xtxbBusWeb.jsp?frmuser=oxbbZji-BIBcqoPiDv6TBtEVF5oY&pcity=jsha";
    
    /**
     * 新华社 
     */
    public static final String LIFE_WEB_XHS_URL = "http://www.news.cn/webmobile.htm";
    
    /**
     * 身份证查询 
     */
    public static final String LIFE_WEB_XFJ_URL = "http://idquery.duapp.com/index.php?mobi=true";
    
    
    /**
     * 快递查询
     */
    public static final String LIFE_WEB_KD_URL = "http://www.kuaidi100.com/smart/cmb/mbank.html";
    
    /**
     * 公共自行车
     */
    public static final String LIFE_WEB_GGZXC_URL = "http://dy.ha101.cn/zsha1/index.php/querybike";
    
    /**
     * 电费查询 
     */
    public static final String LIFE_WEB_DF_URL = "http://dy.ha101.cn/zsha1/index.php/Queryelectricity";
    
    /**
     * 客运班次
     */
    public static final String LIFE_WEB_KYBC_URL = "http://www.jshqjt.com:8880/wxbccx.aspx";
    
    /**
     * 拼车
     */
    public static final String LIFE_WEB_PC_URL = "http://huaian.baopinche.cn/";
    
    /**
     * 数据淮安
     */
    public static final String LIFE_WEB_SQHA_URL = "http://dy.ha101.cn/zsha1/index.php/QueryHAData";
    
    /**
     * 旅游
     */
    public static final String LIFE_WEB_LU_URL = "http://lvyou.baidu.com/huaian";
    
    /**
     * 天气
     */
    public static final String LIFE_WEB_TQ_URL = "http://weather1.sina.cn/?code=jiangsu_huaian&vt=4";
    
    /**
     * 影讯
     */
    public static final String LIFE_WEB_YX_URL = "http://m.maoyan.com/#type=movies";
    
    /**
     * 租车
     */
    public static final String LIFE_WEB_ZC_URL = "http://dy.ha101.cn/zsha1/index.php/querycar";
    
    /**
     * 交通违章 
     */
    public static final String LIFE_WEB_JTWZ_URL = "http://dy.ha101.cn/zsha1/index.php/querycart";
    
    /**
     * 号码通
     */
    public static final String LIFE_WEB_LMT_URL = "http://dy.ha101.cn/zsha1/index.php/queryphonecate";
    
    
    
    /**
     * 挂号 
     */
    public static final String LIFE_WEB_GH_URL = "http://xinhua.wy.guahao.com/";
    
    /**
     * 列车
     */
    public static final String LIFE_WEB_LC_URL = "http://touch.train.qunar.com/?bd_source=weixin_train";
    
    /**
     * 航班 
     */
    public static final String LIFE_WEB_HB_URL = "http://touch.qunar.com/h5/flight/";
    
    /**
     * 客运
     */
    public static final String LIFE_WEB_KY_URL = "http://touch.qunar.com/h5/bus?bd_source=weixin_train";
    
    /**
     * 号码通
     */
    public static final String LIFE_WEB_HMT_URL = "http://dy.ha101.cn/zsha1/index.php/queryphonecate";
    
    /**
     * 淮安发布
     */
    public static final String LIFE_WEB_HAFB_URL = " http://m.weibo.cn/n/淮安发布";
    
   
    /**
     * 酒送达
     */
    public static final String LIFE_WEB_JSD_URL = "http://shop.52v5.com/mobile/";
    
    
    /**
     * 问律师
     */
    public static final String LIFE_WEB_WLS_URL = "http://ilvdo.com/newmob/ConsultStep1?type=zsha";
    
    

        
    
    public static final int  LIVE_NOT_STARTED  = 0;
    public static final int  LIVE_ING  = 1;
    public static final int  LIVE_FINISH  = 2;
    
    
    

    /**
     * 图片文件前缀
     */
    public static final String             ICON_PREFIX             = "icon_";
    /**
     * 图片文件前缀
     */
    public static final String             PDF_PREFIX              = "pdf_";
    /**
     * 其他类型文件前缀(例如离线)
     */
    public static final String             OTHER_PREFIX            = "offline_";
    /**
     * 下载临时文件扩展名
     */
    public static final String             TEMP_EXTENSION          = ".tmp";
    
    /**
     * 加载错误网页
     */
    public static final String             LOAD_ERROR_HTML         = "file:///android_asset/error.html";
    
    /**
     * 新闻详细Bundle key值
     */
    public static final String             BUNDLE_VALUE_NEWS       = "NewsEntity";
    
    /**
     * 图片圆角大小
     */
    public static final int             IMAGE_CORNER_RADIIDP  =  8;
    
    
}


