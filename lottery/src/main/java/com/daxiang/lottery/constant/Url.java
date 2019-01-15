package com.daxiang.lottery.constant;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Url {
    public static enum Env {
        DEBUG, //测试环境
        RELEASE,  //正式环境
        LOCAL     //访问本地
    }

    //设置正式还是测试环境的入口
    public static final Env ENV = Env.RELEASE;
    //数据 域名
    public static final String DATA_URL = getDataUrl();
    //用户信息 域名
    public static final String USER_URL = getUserUrl();
    //tikcet信息，域名
    public static final String TICKET_URL = getTicketUrl();
    //新闻 广告 轮播图 用户反馈
    public static final String CMS_URL = getCmsUrl();
    //viewpager 轮播图的根地址
    public static final String PICTURE_URL = getPictureUrl();
    //H5链接的跟地址
    public static final String H5_URL = getH5Url();
    public static final String URL = getUrl();
    //http 或者https   目前正式的是https  测试为http；
    public static String HTTP = getHttp();

    public static String getDataUrl() {
        switch (ENV) {
            case DEBUG:
                //  return "192.168.1.192:8090/base";
                return "distapi.51caixiangtest.com/base";
            case RELEASE:
                //   return "114.55.176.106:8090";
                // return "distapi.51caixiang.com/data";
                return "distapi.51caixiang.com/base";
            default:
                return "distapi.51caixiang.com/base";
        }
    }

    public static String getUserUrl() {
        switch (ENV) {
            case DEBUG:
                //  return "192.168.1.231:16689/account";
                return "distapi.51caixiangtest.com/account";

            case RELEASE:
                //    return "114.55.30.31:16688/account";
                //return "distapi.51caixiang.com/user";
                return "distapi.51caixiang.com/account";
            default:
                return "distapi.51caixiang.com/account";
        }
    }

    public static String getUrl() {
        switch (ENV) {
            case DEBUG:
                //  return "192.168.1.231:16689/account";
                return "distapi.51caixiangtest.com";

            case RELEASE:
                //    return "114.55.30.31:16688/account";
                //return "distapi.51caixiang.com/user";
                return "distapi.51caixiang.com";
            default:
                return "distapi.51caixiang.com";
        }
    }

    public static String getTicketUrl() {
        switch (ENV) {
            case DEBUG:
                //  return "192.168.1.199:8090/lottery";
                return "distapi.51caixiangtest.com/lottery";
            case RELEASE:
                //  return "114.55.30.31:8090";
                // return "distapi.51caixiang.com/ticket";
                return "distapi.51caixiang.com/lottery";
            default:
                return "distapi.51caixiang.com/lottery";
        }
    }

    public static String getCmsUrl() {
        switch (ENV) {
            case DEBUG:
                //  return "120.26.73.1:2001/cms";
                return "distapi.51caixiangtest.com/cms";
            case RELEASE:
                //     return "120.26.73.1:2002";
                // return "distapi.51caixiang.com/cms";
                return "distapi.51caixiang.com/cms";
            default:
                return "distapi.51caixiang.com/cms";
        }
    }


    //"admin.51caixiangtest.com
    public static String getPictureUrl() {
        switch (ENV) {
            case DEBUG:
                return "res.51caixiangtest.com";
            case RELEASE:
                return "res.51caixiang.com";
            default:
                return "res.51caixiang.com";
        }
    }

    public static String getH5Url() {
        switch (ENV) {
            case DEBUG:
                return "h5.51caixiangtest.com";
            case RELEASE:
                return "h5.51caixiang.com";
            default:
                return "h5.51caixiang.com";

        }
    }

    public static String getHttp() {
        switch (ENV) {
            case DEBUG:
                return "http://";
            case RELEASE:
                return "https://";
            default:
                return "https://";
        }

    }

    //是否为审核状态
    public final static String ISBUY = HTTP + TICKET_URL + "/ticket/app/review";
    //比分直播
    public final static String SCORE_URL = "https://api.51caixiang.com/live/api/";
    public final static String SCORE_NEW_URL = HTTP + DATA_URL + "/data/queryLiveFootballMatchAppH5";
    //球队比分
    public final static String SCORE = HTTP + DATA_URL + "/data/queryUliaoFootballLiveByMid";
    //比分详情
    public final static String SCORE_DETAIL_URL = HTTP + DATA_URL + "/data/football/event";
    //竞彩篮球比分直播
    public final static String SCORE_BASKET_URL = HTTP + DATA_URL + "/data/queryLiveBasketballMatchAPPH5";
    //竞彩篮球比分详情
    public final static String SCORE_BASKET_DETAIL_URL = HTTP + DATA_URL + "/data/queryLiveBasketballDetailByMid";
    //童（post请求都会请求这个网址，后台进行中转）
    public final static String ALLOCATION = HTTP + TICKET_URL + "/handle/allocation";
    // public final static String ALLOCATION="http://114.55.30.31:8090/handle/allocation";
    //检查版本信息
    public final static String VERSION_URL = HTTP + DATA_URL + "/data/queryVersionInfo?osType=2";
    //彩票信息
    public final static String HOME_LOTTERY_URL = HTTP + DATA_URL + "/data/queryAllowSale";
    public final static String BUYLOTTERY_URL = "create/v1";
    //继续付款
    public final static String BUYCONTINUE_URL = "payment";
    // 查询手机号是否存在
    public final static String PHONE_EXIST_URL = HTTP + USER_URL + "/user/existPhone";
    //查询用户名是否存在
    public final static String USERNAME_EXIST_URL = HTTP + USER_URL + "/user/existUserName";
    //获取验证码//114.55.30.31//192.168.1.29
    public final static String SMS_CODE_URL = HTTP + USER_URL + "/common/sendsms";
    //注册
    public final static String REGISTER_URL = HTTP + USER_URL + "/user/register/phone";
    // public final static String REGISTER_URL = "http://192.168.1.41:16688/account/user/register/phone";
    //普通注册
    public final static String COMMON_REGISTER_URL = HTTP + USER_URL + "/user/register";
    //登录
    public final static String LOGIN_URL = HTTP + USER_URL + "/user/login";
    // public final static String LOGIN_URL="http://192.168.1.41:16688/account/user/login";
    //获取密码后的六位数
    public final static String SALT_URL = HTTP + USER_URL + "/user/getSalt";
    //public final static String SALT_URL = "http://192.168.1.41:16688/account/user/getSalt";
    //忘记密码 修改密码
    public final static String FORGET_PASSWORD_URL = HTTP + USER_URL + "/user/resetPassword";
    //获取用户信息
    public final static String USER_INFO_URL = HTTP + USER_URL + "/user/query";
    //public final static String USER_INFO_URL = "http://192.168.1.41:16688/account/user/query";
    //支持的充值方式
    public final static String RECHARGE_METHOD_URL = HTTP + USER_URL + "/payment/queryThirdPay";
    //账单明细总资金情况
    public final static String BILL_WALLET_URL = HTTP + USER_URL + "/payment/account/query";
    //更改手机号码绑定
    public final static String CHANG_MOBILE_BIND_URL = HTTP + USER_URL + "/user/resetMobile";
    //添加手机号绑定
    public final static String ADD_MOBILE_BIND_URL = HTTP + USER_URL + "/user/addMobile";
    //修改昵称,简介
    public final static String NICKNAME_URL = HTTP + USER_URL + "/user/updateUser";
    // public final static String NICKNAME_URL = "http://192.168.1.55:16688/account/user/updateUser";

    //获取各个银行对应的id
    public final static String BANKID_URL = HTTP + USER_URL + "/user/findBankInfo";
    //校验验证码
    public final static String CHECKCODE_URL = HTTP + USER_URL + "/common/checkcode";
    //提款
    public final static String DRAW_MONEY_URL = HTTP + USER_URL + "/payment/account/withdraw";
    //提款记录
    public final static String DRAW_MONEY_RECORD_URL = HTTP + USER_URL + "/payment/queryFinanceApply";
    //竞彩shuju展示数据
    public final static String JCZQ_URL = HTTP + DATA_URL + "/data/gamelist";
    //推荐中心,合买中心
    public final static String HEMAI_URL = HTTP + TICKET_URL + "/ticket/togetherBuy/list";
    public final static String GENDAN_URL = HTTP + TICKET_URL + "/ticket/followBuy/list";
    //跟单合买参与者
    public final static String JOINER_URL = HTTP + TICKET_URL + "/ticket/";
    //
    //竞彩订票详情(合买，推荐又称跟单)
    public final static String TOGETHER_DETAIL_URL = HTTP + TICKET_URL + "/ticket/";
    //投注记录
    public final static String RECORD_FORM_URL = HTTP + TICKET_URL + "/ticket/order/list";
    //竞彩订票详情
    public final static String RECORD_DETAIL_URL = HTTP + TICKET_URL + "/ticket/";
    //竞彩出票详情
    public final static String OUT_TICKET_DETAIL_URL = HTTP + TICKET_URL + "/ticket/split/allList";
    //U付充值
    public final static String RECHARGE_RUL = HTTP + USER_URL + "/payment/upay/charge";
    //微信充值
    public final static String RECHARGE_WX_URL = HTTP + USER_URL + "/payment/weChat/charge";
    //微信充值H5
    public final static String RECHARGE_WX_H5_URL = HTTP + USER_URL + "/payment/weChat/h5/charge";
    //qq充值
    public final static String RECHARGE_QQ = HTTP + USER_URL + "/payment/QQ/charge";
    //jd充值
    public final static String RECHARGE_JD = HTTP + USER_URL + "/payment/JD/charge";
    // public final static String RECHARGE_JD="http://192.168.1.41:16688/account/payment/JD/charge";
    //支付宝充值（手动）
    public final static String RECHARGE_ALIPAY_URL = HTTP + USER_URL + "/payment/alipay/offlineCharge";
    //支付宝充值（自动）
    public final static String RECHARGE_ALIPAY_ON_URL = HTTP + USER_URL + "/payment/alipay/onlineCharge";
    //public final static String RECHARGE_ALIPAY_ON_URL="http://192.168.1.81:16688/payment/alipay/onlineCharge";
    //获取本公司支付宝账户信息
    public final static String ALIPAY_URL = HTTP + USER_URL + "/payment/alipayInfo";
    //中奖通知(最新一期的中奖订单)
    public final static String WINNING_URL = HTTP + TICKET_URL + "/ticket/win/latestOne";
    //获取用户权限(查询用户配置)
    public final static String PRIVILEGE_URL = HTTP + USER_URL + "/user/findUserConfig";
    //开奖结果
    public final static String LOTTERY_RESULT_URL = HTTP + DATA_URL + "/data/queryAllAwardInfo";
    //具体彩种开奖结果详情
    public final static String RESULT_DETAIL_URL = HTTP + DATA_URL + "/data/queryAllAwardInfoDetail";
    //数字彩本期截止日期
    public final static String END_DATE_URL = HTTP + DATA_URL + "/data/queryStopSaleTime";
    //获取服务器当前时间
    public final static String SERVER_DATE_URL = HTTP + DATA_URL + "/data/serverTime";
    //胜负彩和任九场 列表
    public final static String SFC_RJC_URL = HTTP + DATA_URL + "/data/queryGameOdds";
    //数字彩遗漏号码接口
    public final static String OMIT_URL = HTTP + TICKET_URL + "/ticket/omit";
    //账单明细流水账
    public final static String BILL_STREAM_URL = HTTP + USER_URL + "/payment/account/querylist";
    //轮播图 和新闻列表
    public final static String NEWS_URL = HTTP + CMS_URL + "/PublicService.aspx?sn=queryPageArticles";
    //新闻头条viewpager图片的根地址
    public final static String ROOT_URL = HTTP + PICTURE_URL + "/";
    //新闻公告详情
    public final static String NEWS_DETAIL_URL = HTTP + CMS_URL + "/PublicService.aspx?sn=queryarticledetails";
    //修改头像 114.55.30.31   192.168.1.29:16688
    public final static String CHANGE_HEADER_URL = HTTP + USER_URL + "/servlet/fileUpload";
    // public final static String CHANGE_HEADER_URL = HTTP + "192.168.1.81:16689" + "/account/servlet/fileUpload";

    //数字彩中奖号码
    public final static String WIN_NUMBER_URL = HTTP + DATA_URL + "/data/queryAwardNumber";
    //用户反馈
    public final static String USER_BACK_SMS = HTTP + CMS_URL + "/PublicService.aspx?sn=newfeedback";

    //第三方登录
    public final static String THIRD_LOGIN_URL = HTTP + USER_URL + "/third/thirdPartLogin";
    //  public final static String THIRD_LOGIN_URL="http://192.168.1.81:16689/account/third/thirdPartLogin";

    //第三方账户和已经存在的账户进行绑定
    public final static String THIRD_BIND_URL = HTTP + USER_URL + "/third/bindingOrUnbundling";
    // public final static String THIRD_BIND_URL="http://192.168.1.55:16688/account/third/bindingOrUnbundling";

    //第三方登录，绑定手机号
    public final static String THIRD_BIND_PHONE_URL = HTTP + USER_URL + "/third/bindingPhone";
    //public final static String THIRD_BIND_PHONE_URL="http://192.168.1.29:16688/account/third/bindingPhone";
    //判断token是否过期
    public final static String ISTOKENVALID_URL = HTTP + USER_URL + "/user/isTokenValid";
    //查询下线用户
    public final static String DOWNLINE_USERS_URL = HTTP + USER_URL + "/channel/queryChildsByUserId";
    // public final static String DOWNLINE_USERS_URL = "http://192.168.1.81:16689/account/channel/queryChildsByUserId";
    //查询用户所有的返点信息
    public final static String MY_REBATE_URL = HTTP + USER_URL + "/channel/queryUserAllRebateRate";
    //查询用户返点详情
    public final static String REBATE_DETAIL_URL = HTTP + USER_URL + "/channel/queryUserRebateDetails";
    // public final static String REBATE_DETAIL_URL = "http://192.168.1.81:16689/account/channel/countAndQuerySaleAndBackPotByUserId";
    //批量更新用户返点
    public final static String UPDATE_REBATE_URL = HTTP + USER_URL + "/channel/updateUserLotteriesRebateInfo";
    //获取返点组的信息
    public final static String FANDIANS_URL = HTTP + USER_URL + "/channel/queryRebateGroupInfo";
    //推荐h5链接
    public final static String H5GENDAN_URL = HTTP + H5_URL + "/order_copy/detail/";
    //合买h5链接
    public final static String H5HEMAI_URL = HTTP + H5_URL + "/crowd/detail/";
    //红包使用规则h5
    public final static String H5REDRULE_URL = HTTP + H5_URL + "/mobile/couponRule?showH5=no";
    //推广链接分享
    public final static String H5EXPAND_URL = HTTP + H5_URL + "/register?parentUserId=";
    //用户红包信息
    public final static String USERREDINFO_URL = HTTP + USER_URL + "/userVoucher/getUserVoucherInfo";
    //查询活动信息
    public final static String USERVOUCHER_URL = HTTP + USER_URL + "/userVoucher/getActivity";
    //付款
    public final static String PAYMENT_URL = HTTP + USER_URL + "/payment/onLinePay";
    // public final static String PAYMENT_URL = "http://192.168.1.81:16689/account/payment/onLinePay";
    //银行卡列表
    public final static String BANK_CARD_FORM = HTTP + URL + "/pay/payment/llpGetBankCardInfo";
    // public final static String BANK_CARD_FORM = "http://192.168.1.81:16690/pay/payment/llpGetBankCardInfo";
    //连连支付验证银行卡信息
    public final static String BANK_CARD_INFO = HTTP + URL + "/pay/payment/llpCheckBankCardInfo";
    // public final static String BANK_CARD_INFO = "http://192.168.1.81:16690/pay/payment/llpCheckBankCardInfo";
    //连连支付解绑卡信息
    public final static String UNBIND_CARD_INFO = HTTP + URL + "/pay/payment/unBindCardInfo";
    //练练支付请求参数
    public final static String LL_PAY_URL = HTTP + USER_URL + "/payment/llpayCharge";
    //  public final static String LL_PAY_URL = "http://192.168.1.81:16689/account/payment/llpayCharge";
    //查询订单支付情况
    public final static String GETBUY_RESULT_URL = HTTP + USER_URL + "/payment/getTradeRecordByOutTradeNo";
    //提款备注
    public final static String TIKUAN_REMARK = HTTP + USER_URL + "/admin/getWithdrawRemarkInfo";
    //历史数据
    public final static String HISTORY_DATA = HTTP + DATA_URL + "/data/football/history/stat";
    //历史数据详情
    public final static String HISTORY_DETAIL_DATA = HTTP + DATA_URL + "/data/queryUliaoFootballHistoryByMid";
    //中奖历史结果
    public final static String AWARD_RESULT = HTTP + DATA_URL + "/data/queryNumCaiAwardInfoBylotCode";
    //开奖详细信息
    public final static String AWARD_DETAIL_RESULT = HTTP + DATA_URL + "/data/queryNumCaiAwardDetailByIssueNoAndLotCode";
    //排行榜
    public final static String RANKING_URL = HTTP + TICKET_URL + "/ticket/";
    //大神榜
    public final static String GODINFO = HTTP + TICKET_URL + "/ticket/user/certified";//固定的大神榜
    // public final static String GODINFO = HTTP + TICKET_URL + "/ticket/rank/vipUser/7/10";//自动排序的
    //用户购彩信息
    public final static String USER_BUYINFO_URL = HTTP + TICKET_URL + "/ticket/user/statInfo/";
    //个人推荐订单列表
    //public final static String USER_FOLLOWBUY_URL = HTTP + TICKET_URL + "/ticket/order/followBuy";
    public final static String USER_FOLLOWBUY_URL = HTTP + TICKET_URL + "/ticket/recommend/all";
    //大神的个人中心列表信息
    public final static String GOD_INFO_FORM = HTTP + TICKET_URL + "/ticket/recommend/user";
    //追号列表
    public final static String ZHUIHAO_FORM_URL = HTTP + TICKET_URL + "/ticket/zhuihao/list";
    //追号详情
    public final static String ZHUIHAO_DETAIL_URL = HTTP + TICKET_URL + "/ticket/zhuihao/info";
    //停止追号
    public final static String ZHUIHAO_STOP_URL = "/zhuihao/cancel";
    //查询是否充值成功
    public final static String QUERY_CHARGE_STATUS = HTTP + USER_URL + "/payment/queryWechatChargeStatus";
    //首页中奖公告(接口后面数字是请求的数据个数)
    public final static String AWARD_AD = HTTP + TICKET_URL + "/ticket/win/recent/20";
    //玩法说明
    public final static String LOTTERY_RULE = HTTP + H5_URL + "/help/lists/";
    //大神说明
    public final static String RECOMMEND_RULE = HTTP + H5_URL + "/help/copy/m";
    //精彩发现，获取最新一期竞足赛事
    public final static String NEW_MATCH = HTTP + DATA_URL + "/data/match/recent";
    //获取html的域名
    public final static String HTML_HEADER = HTTP + TICKET_URL + "/ticket/domain";
    //获取发单金额的限制
    public final static String GENDAN_MONEY = HTTP + TICKET_URL + "/ticket/config";
    //用户战绩接口
    public final static String GOD_HIT = HTTP + TICKET_URL + "/ticket/user/hit";
    //首页热门赛事
    public final static String HOT_MATCH = HTTP + DATA_URL + "/data/match/hot";
    //首页热门推荐
    public final static String HOT_RECOMMEND = HTTP + TICKET_URL + "/ticket/recommend/hot";
    //搜索大神
    public final static String QUERY_BY_NAME = HTTP + USER_URL + "/user/queryByName";
    //精彩预测
    public final static String FORECAST_MATCH = HTTP + TICKET_URL + "/manage/forecast/list";
    //删除订单
    public final static String DELETE_ORDER = "hidden";
    //用户资金统计接口
    public final static String USER_MONEY = HTTP + USER_URL + "/user/stat";
    //大神的个人简介，粉丝 关注
    public final static String GOD_INFO_NUM = HTTP + USER_URL + "/user/profile";
    //关注接口
    public final static String FOCUS_GOD = HTTP + USER_URL + "/user/focus/";
    //关注列表接口
    public final static String FOLLOW_LIST = HTTP + USER_URL + "/user/focus/list";
    //粉丝列表接口
    public final static String FANS_LIST = HTTP + USER_URL + "/user/fans/list";
    //亚盘数据列表
    //  public final static String ASIAODDS_FOREM = "http://192.168.1.26:8090/base/data/queryUliaoAhandicapByMid";
    public final static String ASIAODDS_FOREM = HTTP + DATA_URL + "/data/queryUliaoAhandicapByMid";
    //亚盘详情接口
    //public final static String ASIAODDS_DETAIL = "http://192.168.1.26:8090/base/data/queryUliaoAhandicapDetailByMid";
    public final static String ASIAODDS_DETAIL = HTTP + DATA_URL + "/data/queryUliaoAhandicapDetailByMid";
    //欧赔数据列表
    //public final static String ODDS_FORM = "http://192.168.1.26:8090/base/data/queryUliaoEoddsByMid";
    public final static String ODDS_FORM = HTTP + DATA_URL + "/data/queryUliaoEoddsByMid";
    //欧赔数据详情接口
    //  public final static String ODDS_DETAIL = "http://192.168.1.26:8090/base/data/queryUliaoEoddsDetailByMid";
    public final static String ODDS_DETAIL = HTTP + DATA_URL + "/data/queryUliaoEoddsDetailByMid";
    //赛事库列表
    //public final static String LEAGUE_LIST = "http://192.168.1.26:8090/base/data/queryUliaoLeagueList";
    public final static String LEAGUE_LIST = HTTP + DATA_URL + "/data/queryUliaoLeagueList";
    //赛程
    // public final static String LEAGUE_SCHEDULE = "http://192.168.1.26:8090/base/data/queryUliaoLeagueSchedule";
    public final static String LEAGUE_SCHEDULE = HTTP + DATA_URL + "/data/queryUliaoLeagueSchedule";
    //积分榜
    //public final static String SCORE_BOARD = "http://192.168.1.26:8090/base/data/queryUliaoScoreBoard";
    public final static String SCORE_BOARD = HTTP + DATA_URL + "/data/queryUliaoScoreBoard";
    //射手榜
    //public final static String SHOOTER_BOARD = "http://192.168.1.26:8090/base/data/queryUliaoShooterBoard";
    public final static String SHOOTER_BOARD = HTTP + DATA_URL + "/data/queryUliaoShooterBoard";

    //获取用户推送标签(开启状态)
    public final static String PUSH_TAG = HTTP + TICKET_URL + "/ticket/push/tag";
    //获取用户推送配置
    public final static String PUSH_LIST = HTTP + TICKET_URL + "/ticket/push/config/list";
    //用户推送配置设置接口
    public final static String PUSH_SETTING = "push/config/modify";
    //世界杯
    public final static String CHAMPION = HTTP + DATA_URL + "/data/queryChampionFinal";
    // public final static String CHAMPION = "http://192.168.1.26:8090/base/data/queryChampionFinal";
    //乐善奖说明
    public final static String LESAN_RULE = HTTP + H5_URL + "/help/leshan/m";
    //开奖号码(冠亚军)
    public final static String GJ_RESULT = HTTP + DATA_URL + "/data/queryChampionFinalResult";
    //首页昨日赛事，直播赛事
    public final static String FINISHGAME = HTTP + DATA_URL + "/data/finishGame";

    //上传图片
    public final static String UPDATE_IMAGE = HTTP + PICTURE_URL + "/api/image/upload";
    //public final static String UPDATE_IMAGE = "https://res.51caixiang.com/api/image/upload";

    //头像的根目录
    public final static String HEADER_ROOT_URL = HTTP + PICTURE_URL + "/api/image/avatar/";

    //发表帖子
    public final static String POST_ADD = HTTP + URL + "/bbs/info/add/post";
    // public final static String POST_ADD = "http://192.168.1.41:8888/discuz/info/add/post";
    //更新帖子状态
    public final static String POST_UPDATE = HTTP + URL + "/bbs/info/update/post";
    // public final static String POST_UPDATE = "http://192.168.1.95:8888/discuz/info/update/post";
    //点赞
    public final static String POST_LIKE = HTTP + URL + "/bbs/info/like";
    // public final static String POST_LIKE = "http://192.168.1.41:8888/discuz/info/like";

    //帖子列表
    public final static String POST_FORM = HTTP + URL + "/bbs/info/post/list";
    // public final static String POST_FORM = "http://192.168.1.41:8888/discuz/info/post/list";

    //评论帖子
    public final static String POST_COMMENT = HTTP + URL + "/bbs/info/add/comment";
    // public final static String POST_COMMENT = "http://192.168.1.41:8888/discuz/info/add/comment";

    //回复评论
    public final static String POST_REPLY = HTTP + URL + "/bbs/info/add/reply";
    // public final static String POST_REPLY = "http://192.168.1.41:8888/discuz/info/add/reply";

    //举报
    public final static String POST_REPORT = HTTP + URL + "/bbs/info/report";
    //public final static String POST_REPORT = "http://192.168.1.41:8888/discuz/info/report";

    //获取用户系统消息列表
    public final static String SYSTEM_SMS_FORM = HTTP + URL + "/bbs/info/system/messageList";
    //public final static String POST_REPORT = "http://192.168.1.41:8888/discuz/info/system/messageList";

    //获取帖子消息列表
    public final static String POST_SMS_FORM = HTTP + URL + "/bbs/info/post/messageList";
    //public final static String POST_REPORT = "http://192.168.1.41:8888/discuz/info/post/messageList";

    //获取头条新闻
    public final static String POST_HEADER = HTTP + URL + "/bbs/info/post/header";
    //public final static String POST_HEADER = "http://192.168.1.41:8888/discuz/info/post/header";

    //帖子详情
    public final static String POST_DETAIL = HTTP + URL + "/bbs/info/postDetail";
    //获取帖子评论列表
    public final static String POST_COMMENT_FORM = HTTP + URL + "/bbs/info/commentList";
    //获取用户未读消息数
    public final static String POST_MESSAGENUM = HTTP + URL + "/bbs/info/user/messageNum";
    //获取期号对应的赛事信息
    public final static String MATCH_BY_SESSION = HTTP + DATA_URL + "/data/queryGameInfo";
    //public final static String MATCH_BY_SESSION = "http://192.168.1.30:8090/base/data/queryGameInfo";
    //分享帖子的链接
    public final static String POST_SHARE_URL=HTTP+H5_URL+"/bbs/detail/";
}

