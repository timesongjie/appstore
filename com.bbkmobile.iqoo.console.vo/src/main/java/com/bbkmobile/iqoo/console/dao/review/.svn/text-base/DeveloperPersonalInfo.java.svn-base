package com.bbkmobile.iqoo.console.dao.review;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bbkmobile.iqoo.console.dao.appinfo.Developer;

public class DeveloperPersonalInfo {

	private Integer id;    
	private Developer developer=new Developer();
	private String  ture_name;    
	private String  address;     
	private String  zip_code;     
	private String phone_number; 
	private String qq;           
	private String msn;      
	private String account_name; 
	private String bank_account; 
	private String bank_name;    
	private String bank_branch;  
	private String bank_address; 
	private Character ID_type;     
	private String ID_number;   
	private String ID_scan_pic;  
	private Date updatetime;
	private Character verify_status;
	private Date sign_time;
	

	private Character status_select;
	private String status_view;  
	private Date from_date;     //合作资料提交时间（查询时开始时间）
	private Date to_date;       //合作资料提交时间（查询时结束时间）
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Developer getDeveloper() {
		return developer;
	}
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	public String getTure_name() {
		return ture_name;
	}
	public void setTure_name(String tureName) {
		ture_name = tureName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
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
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Character getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(Character verifyStatus) {
		this.verify_status = verifyStatus;
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
	
	public Date getSign_time() {
		return sign_time;
	}
	public void setSign_time(Date signTime) {
		sign_time = signTime;
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
