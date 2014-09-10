package com.bbkmobile.iqoo.console.dao.review;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.Developer;

public class DeveloperCompanyInfo {

	private Long id;
	private Developer developer=new Developer();
	private String company_name;
	private String company_address;
	private String zip_code;
	private String phone_number;
	private String license_number;              
	private String license_scan_pic;
	private String tax_number;
	private String tax_scan_pic;
	private String account_name;
	private String bank_account;
	private String bank_name;
	private String bank_branch;
	private String bank_address;
	private String bank_license_num;
	private String bank_license_pic;
	private String business_man;
	private Character ID_type;
	private String ID_number;
	private String ID_scan_pic;
	private String business_phone_number;
	private String business_email;
	private String business_address;
	private String business_code;
	private Short cooperation_type;
	private Date submit_time;
	private Character verify_status;
	private Date sign_time;
	
	
	private Character status_select;
	private String status_view;        //审核状态()
	private String cooperation_view;   //合作状态
	private String ID_type_review;     //证件类型
	private Date from_date;     //合作资料提交时间（查询时开始时间）
	private Date to_date;       //合作资料提交时间（查询时结束时间）
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Developer getDeveloper() {
		return developer;
	}
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String companyName) {
		company_name = companyName;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String companyAddress) {
		company_address = companyAddress;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zipCode) {
		zip_code = zipCode;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phoneNumber) {
		phone_number = phoneNumber;
	}
	public String getLicense_number() {
		return license_number;
	}
	public void setLicense_number(String licenseNumber) {
		license_number = licenseNumber;
	}
	public String getLicense_scan_pic() {
		return license_scan_pic;
	}
	public void setLicense_scan_pic(String licenseScanPic) {
		license_scan_pic = licenseScanPic;
	}
	public String getTax_number() {
		return tax_number;
	}
	public void setTax_number(String taxNumber) {
		tax_number = taxNumber;
	}
	public String getTax_scan_pic() {
		return tax_scan_pic;
	}
	public void setTax_scan_pic(String taxScanPic) {
		tax_scan_pic = taxScanPic;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String accountName) {
		account_name = accountName;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bankAccount) {
		bank_account = bankAccount;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bankBranch) {
		bank_branch = bankBranch;
	}
	public String getBank_address() {
		return bank_address;
	}
	public void setBank_address(String bankAddress) {
		bank_address = bankAddress;
	}
	public String getBank_license_num() {
		return bank_license_num;
	}
	public void setBank_license_num(String bankLicenseNum) {
		bank_license_num = bankLicenseNum;
	}
	public String getBank_license_pic() {
		return bank_license_pic;
	}
	public void setBank_license_pic(String bankLicensePic) {
		bank_license_pic = bankLicensePic;
	}
	public String getBusiness_man() {
		return business_man;
	}
	public void setBusiness_man(String businessMan) {
		business_man = businessMan;
	}
	
	public Character getID_type() {
		return ID_type;
	}
	public void setID_type(Character iDType) {
		ID_type = iDType;
	}
	public String getID_number() {
		return ID_number;
	}
	public void setID_number(String iDNumber) {
		ID_number = iDNumber;
	}
	public String getID_scan_pic() {
		return ID_scan_pic;
	}
	public void setID_scan_pic(String iDScanPic) {
		ID_scan_pic = iDScanPic;
	}
	public String getBusiness_phone_number() {
		return business_phone_number;
	}
	public void setBusiness_phone_number(String businessPhoneNumber) {
		business_phone_number = businessPhoneNumber;
	}
	public String getBusiness_email() {
		return business_email;
	}
	public void setBusiness_email(String businessEmail) {
		business_email = businessEmail;
	}
	public String getBusiness_address() {
		return business_address;
	}
	public void setBusiness_address(String businessAddress) {
		business_address = businessAddress;
	}
	public String getBusiness_code() {
		return business_code;
	}
	public void setBusiness_code(String businessCode) {
		business_code = businessCode;
	}
	public Short getCooperation_type() {
		return cooperation_type;
	}
	public void setCooperation_type(Short cooperationType) {
		cooperation_type = cooperationType;
	}
	public Date getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(Date submitTime) {
		submit_time = submitTime;
	}
	public Character getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(Character verifyStatus) {
		verify_status = verifyStatus;
	}
	public Date getSign_time() {
		return sign_time;
	}
	public void setSign_time(Date signTime) {
		sign_time = signTime;
	}
	public Character getStatus_select() {
		return status_select;
	}
	public void setStatus_select(Character statusSelect) {
		status_select = statusSelect;
	}
	public String getStatus_view() {
		
		switch (verify_status) {
		case '0':
			status_view = "已签约";
			break;
		case '1':
			status_view = "已注册";
			break;
		case '2':
			status_view = "资料审核中";
			break;
		case '3':
			status_view = "审核未通过";
			break;
		case '4':
			status_view = "待线下签约";
			break;
		case '5':
			status_view = "线下签约中";
			break;
		case '6':
			status_view = "线下签约未通过";
			break;
		}
		return status_view;
	}
	public void setStatus_view(String statusView) {
		
		
		status_view = statusView;
	}
	
	public String getCooperation_view() {
		
		switch(cooperation_type)
		{
			case 1:
				cooperation_view="普通合作";
				break;
			case 2:
				cooperation_view="资金合作";
				break;
		}
		return cooperation_view;
	}
	public void setCooperation_view(String cooperationView) {
		cooperation_view = cooperationView;
	}
	
	public String getID_type_review() {
		
		switch(ID_type)
		{
			case '1':
				ID_type_review="身份证";
				break;
			case '2':
				ID_type_review="军官证";
				break;
			case '3':
				ID_type_review="护照";
				break;
		}
		return ID_type_review;
	}
	public void setID_type_review(String iDTypeReview) {
		ID_type_review = iDTypeReview;
	}
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date fromDate) {
		from_date = fromDate;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date toDate) {
		to_date = toDate;
	}
	
	
}
