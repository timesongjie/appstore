package com.bbkmobile.iqoo.console.dao.appinfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.AdvertisementApp;
import com.bbkmobile.iqoo.console.dao.apptest.AppTestRecords;
import com.bbkmobile.iqoo.console.dao.apptype.AppType;
import com.bbkmobile.iqoo.console.dao.review.ReviewRecords;

public class AppHistoryInfo {
    
    private Long id;
    private Developer developer = new Developer();
    private AppType appType = new AppType();
    private String appCnName;
    private String appEnName;
    private String appVersion;
    private String appAuthor;
    private Character sellType;
    private Character payType;
    private Character payTypeInApp;
    private String priceReason;
    private BigDecimal price;
    private Integer discount;
    private Integer largess;
    private String appDesc;
    private String app_remark;
    private String appKeyWord;
    private String appIcon;
    private String resolution;
    private String platform;
    private String appApk;
    private Integer apkSize;
    private String appVersionCode;
    private String appPackage;
    private Short appStatus;
    private Date updateDate;
    private Date onSaleDate;
    private Date offSaleDate;
    private Set<TAppPermission> TAppPermissions;
    private Set<TAppScreenshot> TAppScreenshots;
    
    private Set<ReviewRecords> review_records;
    private Set<AppTestRecords> app_test_records;
    private Set<AdvertisementApp> advertisementApp;
    private Set<Advertisement> advertisement;
    private List<TAppHistoryScreenshot> list_history_screenshot = new ArrayList<TAppHistoryScreenshot>();
    
    private int type;
    private String status_view;
    private Short status_select;
    private Date from_date;     //提交时间（查询时开始时间）
    private Date to_date;       //提交时间（查询时结束时间）
    private Date on_from_date;  //上架时间（查询时开始时间）
    private Date on_to_date;    //上架时间（查询时结束时间）
    private Date off_from_date; //下架时间（查询时开始时间）
    private Date off_to_date;   //下架时间（查询时结束时间）
    
    private int avg_click;
    private float avg_comment;     //非数据库字段，后续去掉    
    private int avg_download;
    
    private int comment_count;        //非数据库字段，后续去掉
    private Integer commentCount;         //评论总条数
    private Float commentSum;           //评级总分数
    private Float avgComment;            //评级平均分
    private Integer downloadCount;        //下载总数     
    private String androidPermission; 
    
    
    private Integer minSdkVersion;
    private Integer maxSdkVersion;
    private Integer targetSdkVersion;
    private String drawable_dpi;
    private String uses_feature;
    private String CPU_ABI;
    private String filter_model;            //按机型过滤
    private String filter_screen;           //按分辨率过滤
    
    private String signature;               //签名信息
    private char auto_update;                    //自动更新
    
    private char official;                    //是否官方应用
    private String patchs;            //patch包
    private Short tag;					//标签

    private Long ad_id;   //记录软件设置页面传过来的广告id，以便取消设置
    
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developer getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }


    public String getAppAuthor() {
        return this.appAuthor;
    }

    public void setAppAuthor(String appAuthor) {
        this.appAuthor = appAuthor;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppApk() {
        return this.appApk;
    }

    public void setAppApk(String appApk) {
        this.appApk = appApk;
    }

    public Short getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(Short appStatus) {
        this.appStatus = appStatus;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getOnSaleDate() {
        return this.onSaleDate;
    }

    public void setOnSaleDate(Date onSaleDate) {
        this.onSaleDate = onSaleDate;
    }

    public Date getOffSaleDate() {
        return this.offSaleDate;
    }

    public void setOffSaleDate(Date offSaleDate) {
        this.offSaleDate = offSaleDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus_view() {
        switch (appStatus) {
        case 0:
            status_view = "已上架";
            break;
        case 1:
            status_view = "草稿";
            break;
        case 2:
            status_view = "待审核";
            break;
        case 3:
            status_view = "审核中";
            break;
        case 4:
            status_view = "审核未通过";
            break;
        case 5:
            status_view = "审核通过待测试";
            break;
        case 6:
            status_view = "测试中";
            break;
        case 7:
            status_view = "测试未通过";
            break;
        case 8:
            status_view = "测试待发";
            break;
        case 9:
            status_view = "已加入自动上架";
            break;
        case 10:
            status_view = "已加入手动上架";
            break;
        case 11:
            status_view = "已加入下架列表";
            break;
        case 12:
            status_view = "已下架";
            break;
        case 13:
            status_view = "部分机型已下架";
            break;
        }
        return status_view;
    }

    public String getAppCnName() {
        return appCnName;
    }

    public void setAppCnName(String appCnName) {
        this.appCnName = appCnName;
    }

    public String getAppEnName() {
        return appEnName;
    }

    public void setAppEnName(String appEnName) {
        this.appEnName = appEnName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Character getSellType() {
        return sellType;
    }

    public void setSellType(Character sellType) {
        this.sellType = sellType;
    }

    public Character getPayTypeInApp() {
        return payTypeInApp;
    }

    public void setPayTypeInApp(Character payTypeInApp) {
        this.payTypeInApp = payTypeInApp;
    }

    public String getPriceReason() {
        return priceReason;
    }

    public void setPriceReason(String priceReason) {
        this.priceReason = priceReason;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppKeyWord() {
        return appKeyWord;
    }

    public void setAppKeyWord(String appKeyWord) {
        this.appKeyWord = appKeyWord;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getApkSize() {
        return this.apkSize;
    }

    public void setApkSize(Integer apkSize) {
        this.apkSize = apkSize;
    }

    public void setStatus_view(String status_view) {
        this.status_view = status_view;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    public Short getStatus_select() {
        return status_select;
    }

    public void setStatus_select(Short status_select) {
        this.status_select = status_select;
    }

    public Set<ReviewRecords> getReview_records() {
        return review_records;
    }

    public void setReview_records(Set<ReviewRecords> review_records) {
        this.review_records = review_records;
    }

    public Character getPayType() {
        return payType;
    }

    public void setPayType(Character payType) {
        this.payType = payType;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getLargess() {
        return largess;
    }

    public void setLargess(Integer largess) {
        this.largess = largess;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public Set<TAppPermission> getTAppPermissions() {
        return TAppPermissions;
    }

    public void setTAppPermissions(Set<TAppPermission> tAppPermissions) {
        TAppPermissions = tAppPermissions;
    }

    public Set<TAppScreenshot> getTAppScreenshots() {
        return TAppScreenshots;
    }

    public void setTAppScreenshots(Set<TAppScreenshot> tAppScreenshots) {
        TAppScreenshots = tAppScreenshots;
    }

    public Set<AppTestRecords> getApp_test_records() {
        return app_test_records;
    }

    public void setApp_test_records(Set<AppTestRecords> appTestRecords) {
        app_test_records = appTestRecords;
    }

    public Set<AdvertisementApp> getAdvertisementApp() {
        return advertisementApp;
    }

    public void setAdvertisementApp(Set<AdvertisementApp> advertisementApp) {
        this.advertisementApp = advertisementApp;
    }

    public Set<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Set<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public Long getAd_id() {
        return ad_id;
    }

    public void setAd_id(Long adId) {
        ad_id = adId;
    }

    public Date getOn_from_date() {
        return on_from_date;
    }

    public void setOn_from_date(Date on_from_date) {
        this.on_from_date = on_from_date;
    }

    public Date getOn_to_date() {
        return on_to_date;
    }

    public void setOn_to_date(Date on_to_date) {
        this.on_to_date = on_to_date;
    }

    public Date getOff_from_date() {
        return off_from_date;
    }

    public void setOff_from_date(Date off_from_date) {
        this.off_from_date = off_from_date;
    }

    public Date getOff_to_date() {
        return off_to_date;
    }

    public void setOff_to_date(Date off_to_date) {
        this.off_to_date = off_to_date;
    }

    public int getAvg_click() {
        return avg_click;
    }

    public void setAvg_click(int avgClick) {
        avg_click = avgClick;
    }

    public float getAvg_comment() {
        return avg_comment;
    }

    public void setAvg_comment(float avgComment) {
        avg_comment = avgComment;
    }

    public int getAvg_download() {
        return avg_download;
    }

    public void setAvg_download(int avgDownload) {
        avg_download = avgDownload;
    }

    public int getComment_count() {
        return this.comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getAndroidPermission() {
        return this.androidPermission;
    }

    public void setAndroidPermission(String androidPermission) {
        this.androidPermission = androidPermission;
    }

    public Float getCommentSum() {
        return this.commentSum;
    }

    public void setCommentSum(Float commentSum) {
        this.commentSum = commentSum;
    }

    public Float getAvgComment() {
        return this.avgComment;
    }

    public void setAvgComment(Float avgComment) {
        this.avgComment = avgComment;
    }

    public Integer getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getDownloadCount() {
        return this.downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getMinSdkVersion() {
        return this.minSdkVersion;
    }

    public void setMinSdkVersion(Integer minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public Integer getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    public void setMaxSdkVersion(Integer maxSdkVersion) {
        this.maxSdkVersion = maxSdkVersion;
    }

    public Integer getTargetSdkVersion() {
        return this.targetSdkVersion;
    }

    public void setTargetSdkVersion(Integer targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public String getDrawable_dpi() {
        return this.drawable_dpi;
    }

    public void setDrawable_dpi(String drawable_dpi) {
        this.drawable_dpi = drawable_dpi;
    }

    public String getUses_feature() {
        return this.uses_feature;
    }

    public void setUses_feature(String uses_feature) {
        this.uses_feature = uses_feature;
    }

    public String getCPU_ABI() {
        return this.CPU_ABI;
    }

    public void setCPU_ABI(String cPU_ABI) {
        CPU_ABI = cPU_ABI;
    }

    public String getFilter_model() {
        return this.filter_model;
    }

    public void setFilter_model(String filter_model) {
        this.filter_model = filter_model;
    }
    
    public String getFilter_screen() {
        return filter_screen;
    }

    public void setFilter_screen(String filterScreen) {
        filter_screen = filterScreen;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AppInfo)){
            return false;
        }
        AppInfo temp=(AppInfo)o;
        return temp.getId()==this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public char getAuto_update() {
        return this.auto_update;
    }

    public void setAuto_update(char auto_update) {
        this.auto_update = auto_update;
    }

    public String getPatchs() {
        return this.patchs;
    }

    public void setPatchs(String patchs) {
        this.patchs = patchs;
    }

    public char getOfficial() {
        return this.official;
    }

    public void setOfficial(char official) {
        this.official = official;
    }

	public String getApp_remark() {
		return app_remark;
	}

	public void setApp_remark(String app_remark) {
		this.app_remark = app_remark;
	}

	public List<TAppHistoryScreenshot> getList_history_screenshot() {
		return list_history_screenshot;
	}

	public void setList_history_screenshot(
			List<TAppHistoryScreenshot> list_history_screenshot) {
		this.list_history_screenshot = list_history_screenshot;
	}

	public Short getTag() {
		return tag;
	}

	public void setTag(Short tag) {
		this.tag = tag;
	}   
	
	
}
