/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.xml.vo;

/**
 * @author wangbo
 * 
 */
public class AppVO {

    private String sname;
    private String catename;
    private String cateid;
    private String description;
    private String docid; // 应用ID
    private String platform;
    private String url;
    private String site;
    private String type;
    private String packageformat;
    private String releasedate;
    private String language;
    private String fee;
    private String icon;
    private String smallmaplink1;
    private String smallmaplink2;
    private String bigmaplink1;
    private String bigmaplink2;
    private long packagesize; // apk大小,单位字节
    private int versioncode;
    private String packagename;
    private String score;
    private String score_count;
    private String versionname;
    private String permission_cn;
    private String download_count; // 下载量
    private String official = "0"; // 是否官方

    private String developername;
    private String authentication;

    private String iconlow;
    private String iconhigh;
    private String iconalading;
    private String iconhdpi;
    private String adapi;

    private String size;
    private String signmd5;
    private String updatetime;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackageformat() {
        return packageformat;
    }

    public void setPackageformat(String packageformat) {
        this.packageformat = packageformat;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSmallmaplink1() {
        return smallmaplink1;
    }

    public void setSmallmaplink1(String smallmaplink1) {
        this.smallmaplink1 = smallmaplink1;
    }

    public String getSmallmaplink2() {
        return smallmaplink2;
    }

    public void setSmallmaplink2(String smallmaplink2) {
        this.smallmaplink2 = smallmaplink2;
    }

    public String getBigmaplink1() {
        return bigmaplink1;
    }

    public void setBigmaplink1(String bigmaplink1) {
        this.bigmaplink1 = bigmaplink1;
    }

    public String getBigmaplink2() {
        return bigmaplink2;
    }

    public void setBigmaplink2(String bigmaplink2) {
        this.bigmaplink2 = bigmaplink2;
    }

    public long getPackagesize() {
        return packagesize;
    }

    public void setPackagesize(long packagesize) {
        this.packagesize = packagesize;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore_count() {
        return score_count;
    }

    public void setScore_count(String scoreCount) {
        score_count = scoreCount;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getPermission_cn() {
        return permission_cn;
    }

    public void setPermission_cn(String permissionCn) {
        permission_cn = permissionCn;
    }

    public String getDownload_count() {
        return this.download_count;
    }

    public void setDownload_count(String download_count) {
        this.download_count = download_count;
    }

    public String getOfficial() {
        return this.official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    public String getDevelopername() {
        return this.developername;
    }

    public void setDevelopername(String developername) {
        this.developername = developername;
    }

    public String getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getIconlow() {
        return this.iconlow;
    }

    public void setIconlow(String iconlow) {
        this.iconlow = iconlow;
    }

    public String getIconhigh() {
        return this.iconhigh;
    }

    public void setIconhigh(String iconhigh) {
        this.iconhigh = iconhigh;
    }

    public String getIconalading() {
        return this.iconalading;
    }

    public void setIconalading(String iconalading) {
        this.iconalading = iconalading;
    }

    public String getIconhdpi() {
        return this.iconhdpi;
    }

    public void setIconhdpi(String iconhdpi) {
        this.iconhdpi = iconhdpi;
    }

    public String getAdapi() {
        return this.adapi;
    }

    public void setAdapi(String adapi) {
        this.adapi = adapi;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSignmd5() {
        return this.signmd5;
    }

    public void setSignmd5(String signmd5) {
        this.signmd5 = signmd5;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

}
