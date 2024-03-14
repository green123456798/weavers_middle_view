package model.dto;

import java.util.Date;

public class ReviewDTO {
	
	private int rpk;
	private String mid;
	private int bpk;
	private String content;
	private int scope;
	private String img;
	private Date regdate;
	
	private String nickname;
	private int gpk;
	private String pname;
	private int ppk;
	private String pimg;
	
	private String searchCondition;
	
	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public int getRpk() {
		return rpk;
	}

	public void setRpk(int rpk) {
		this.rpk = rpk;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getBpk() {
		return bpk;
	}

	public void setBpk(int bpk) {
		this.bpk = bpk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getGpk() {
		return gpk;
	}

	public void setGpk(int gpk) {
		this.gpk = gpk;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPpk() {
		return ppk;
	}

	public void setPpk(int ppk) {
		this.ppk = ppk;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	@Override
	public String toString() {
		return "ReviewDTO [rpk=" + rpk + ", mid=" + mid + ", bpk=" + bpk + ", content=" + content + ", scope=" + scope
				+ ", img=" + img + ", regdate=" + regdate + ", nickname=" + nickname + ", gpk=" + gpk + ", pname="
				+ pname + ", ppk=" + ppk + ", pimg=" + pimg + ", searchCondition=" + searchCondition + "]";
	}

}
