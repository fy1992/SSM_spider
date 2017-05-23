package github.utils;

import java.util.ResourceBundle;

/**
 * 配置文件读取
 */
public class ResourcesUtils {

    private static final ResourceBundle bundel = ResourceBundle.getBundle("config");

    public static String getUserTimeOut() {
        return bundel.getString("user_time_out");
    }

    public static String getUserInfoPrefix() {
        return bundel.getString("rong_userinfo");
    }

    public static String getMyTalklist(){
        return bundel.getString("my_tolk_list");
    }

    public static String getRongTokenPrefix() {
        return bundel.getString("rong_token");
    }

    /**
     * 获取app版本号
     * @return
     */
    public static String getAppVersion(){
        return bundel.getString("app_version");
    }

    /**
     * 获取app下载地址
     * @return
     */
    public static String getAppDownloadURL(){
        return bundel.getString("app_downloadurl");
    }

    /**
     * 河南日报的路径
     *
     * @return
     */
    public static String getHeNanRb() {
        return bundel.getString("henanrb");
    }

    private void ResourcesUtils() {

    }

    public static String getScreenShotUrl() {
        return bundel.getString("screenshot");
    }

    /**
     * 用户经纬度信息（redis）
     *
     * @return
     */
    public static String getUserLngAndLat() {
        return bundel.getString("user_lng_lat");
    }

    /**
     * 获取半径
     *
     * @return
     */
    public static String getRadius() {
        return bundel.getString("radius");
    }

    public static String getPageLength() {
        return bundel.getString("pageLength");
    }

    /**
     * 登录公户名
     *
     * @return
     */
    public static String getLoginUser() {
        return bundel.getString("loginUser");
    }

    /**
     * 短信验证码
     */
    public static String getSMSCode() {
        return bundel.getString("sms_code");
    }

    /**
     * pc登录token
     */
    public static String getLogCookie() {
        return bundel.getString("token");
    }

    /**
     * 超级管理员登录名
     *
     * @return
     */
    public static String getAdminLoginName() {
        return bundel.getString("admin_loginName");
    }

    /**
     * 超级管理员密码
     *
     * @return
     */
    public static String getAdminPassword() {
        return bundel.getString("admin_password");
    }

    /**
     * 读取文件路径（图片）
     *
     * @return
     */
    public static String getFilePath() {
        return bundel.getString("filePath");
    }

    /**
     * 文件链接（图片）
     *
     * @return
     */
    public static String getFileUrl() {
        return bundel.getString("fileUrl");
    }


    /**
     * 上传文件的大小限制
     *
     * @return
     */
    public static String getFileSize() {
        return bundel.getString("fileSize");
    }


    /**
     * 获取推送app key
     */
    public static final String getPushAppKey() {
        return bundel.getString("app_key");
    }

    /**
     * 获取推送master key
     */
    public static final String getPushMasterKey() {
        return bundel.getString("master_key");
    }

    /**
     * 获取推送url
     */
    public static final String getPushUrl() {
        return bundel.getString("pushUrl");
    }

    /**
     * 舆情接口 host
     */
    public static final String getYqApiHost() {
        return bundel.getString("yq_api_host");
    }

    /**
     * 舆情方案每个关键词范围的词数
     */
    public static final String getYqPlanWordNum() {
        return bundel.getString("yq_plan_word_num");
    }

    /**
     * 每个用户的舆情方案上限
     */
    public static final String getYqUserPlanNum() {
        return bundel.getString("yq_user_plan_num");
    }

    /**
     * 舆情相似度值
     */
    public static final String getYqSimilarity() {
        return bundel.getString("yq_similarity");
    }

    public static final String getYqEchartsDays() {
        return bundel.getString("yq_echarts_days");
    }

    /**
     * redis的ip
     *
     * @return
     */
    public static final String redisip() {
        return bundel.getString("redis.ip");
    }

    /**
     * redis的端口
     *
     * @return
     */
    public static final String reidsport() {
        return bundel.getString("redis.port");
    }

    /**
     * redis的密码
     *
     * @return
     */
    public static final String redispwd() {
        return bundel.getString("redis.pwd");
    }


    /**
     * 获取默认头像
     */
    public static final String getDefaultUserIcon() {
        return bundel.getString("user_icon");
    }

    /**
     * 微信公众号发送消息
     *
     * @return
     */
    public static final String getWechatSendURL() {
        return bundel.getString("wechat_send");
    }

    /**
     * 公众号查询分析数据
     *
     * @return
     */
    public static final String getWechatSummary() {
        return bundel.getString("wechat_app_data");
    }

    /**
     * 飞腾登录用户的免登录时间
     */
    public static final int getFeiTengUserStayTime() {
        return Integer.parseInt(bundel.getString("feiteng_token_time"));
    }

    /**
     * 获取版心的存放路径
     */
    public static final String getLayoutStylePath() {
        return bundel.getString("layout_style_path");
    }

    /**
     * 获取小样文件的根路径
     */
    public static final String getNewsLayoutPath() {
        return bundel.getString("news_layout_path");
    }

    /**
     * 获取大样文件的根路径
     */
    public static final String getFTPdfPath() {
        return bundel.getString("ft_pdf_path");
    }

    /**
     * 飞腾 大样ftp
     */
    public static final String getFTFtpService() {
        return bundel.getString("ft_ftp_info");
    }

    /**
     * 飞腾大样上传后的访问路径
     */
    public static final String getFTFtpFileUrl() {
        return bundel.getString("ftp_file_url");
    }

    /**
     * 短信验证码有效时间
     */
    public static final int getSmsAvailableTime() {
        return Integer.parseInt(bundel.getString("sms_available_time"));
    }


    public static final String getSjbToken() {
        return bundel.getString("sjb_token_key");
    }


    public static final String getSjbAddNews() {
        return bundel.getString("sjb_AddNews_key");
    }

    /**
     * 大河报的url
     *
     * @return
     */
    public static final String getdahebao() {
        return bundel.getString("dahebao");
    }

    //获得手机报的
    public static final String getSjbLogo() {
        return bundel.getString("henansjb");
    }

    //获得河南日报
    public static final String gerhenanRiBaoWang() {
        return bundel.getString("henanribaowang");
    }

    //获得大河报的判断
    public static final String getHeNanDaHeBao() {
        return bundel.getString("henandahebao");
    }


    /**
     * 直播相关
     *
     * @return
     */
    public static final String getLiveSign() {
        return bundel.getString("sign");
    }

    //获取直播注册url
    public static final String getDaheLiveRegist() {
        return bundel.getString("dahe_live_regist");
    }

    //获取直播登陆url
    public static final String getDaheLiveLogin() {
        return bundel.getString("dahe_live_login");
    }

    //获取直播url
    public static final String getDaheLiveCreateRoom() {
        return bundel.getString("dahe_live_createroom");
    }

    //获取直播间信息
    public static final String getDaheLiveRoomInfo() {
        return bundel.getString("dahe_live_roominfo");
    }

    //获取订阅号下的直播列表
    public static final String getDaheLiveChannelList() {
        return bundel.getString("dahe_live_channellist");
    }

    //获得各省的请求数量
    public static final String getDaheLiveProReqNum() {
        return bundel.getString("dahe_live_proreqnum");
    }

    //获得当月每天的PV/UV数量
    public static final String getDailyPVUV() {
        return bundel.getString("dahe_live_dailypvuv");
    }

    //获得当月终端维度的统计数量
    public static final String getDaheLiveTerminal() {
        return bundel.getString("dahe_live_terminal");
    }

    //获得本周每天24小时的请求数量
    public static final String getDaheLiveWeekReq() {
        return bundel.getString("dahe_live_weekreq");
    }

    public static final String getQiniuImgUrl(){
        return bundel.getString("qiniu_img_url");
    }




    /**
     * 短信发送模式
     *
     * @return
     */
    public static final String smsType() {
        return bundel.getString("sms_type");
    }


    public static final String getAllSearch() {
        return bundel.getString("all_search");
    }

}
