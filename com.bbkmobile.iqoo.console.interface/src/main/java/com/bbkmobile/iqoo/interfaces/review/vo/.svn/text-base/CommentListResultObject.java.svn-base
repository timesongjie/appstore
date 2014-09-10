package com.bbkmobile.iqoo.interfaces.review.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;

public class CommentListResultObject extends ResultObject<List<AppComment>> {

	private String commented = "N";
	private String forbidComment = "N";
	private String reponse = "";
	
	private Float score;
	private int score1;
	private int score2;
	private int score3;
	private int score4;
	private int score5;

	public String getCommented() {
		return commented;
	}

	public void setCommented(String commented) {
		this.commented = commented;
	}

	public String getForbidComment() {
		return forbidComment;
	}

	public void setForbidComment(String forbidComment) {
		this.forbidComment = forbidComment;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getScore3() {
		return score3;
	}

	public void setScore3(int score3) {
		this.score3 = score3;
	}

	public int getScore4() {
		return score4;
	}

	public void setScore4(int score4) {
		this.score4 = score4;
	}

	public int getScore5() {
		return score5;
	}

	public void setScore5(int score5) {
		this.score5 = score5;
	}

	@Override
	public List<AppComment> getValue() {
		 List<AppComment> clone = super.getValue();
		 if(clone != null && clone.size() >0){
			 for(AppComment comment : clone){
				 if(comment != null){
					 comment.setAppInfo(null);
					 comment.setComment_review_records(null);
					 comment.setAppversion(StringUtils.defaultIfEmpty(comment.getAppversion(), ""));;
				 }
			 }
		 }
		 return clone;
	}
	
}
