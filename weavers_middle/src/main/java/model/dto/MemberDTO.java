package model.dto;

import java.util.Date;

public class MemberDTO {

	private String mid;
	private String mpw;
	private String name;
	private Date birth;
	private String phone;
	private String nickname;
	private String email;
	private int marketing;
	private int gpk;
	private String grade;
	
	private String searchCondition;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMpw() {
		return mpw;
	}

	public void setMpw(String mpw) {
		this.mpw = mpw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMarketing() {
		return marketing;
	}

	public void setMarketing(int marketing) {
		this.marketing = marketing;
	}


	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	

	public int getGpk() {
		return gpk;
	}

	public void setGpk(int gpk) {
		this.gpk = gpk;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	@Override
	public String toString() {
		return "MemberDTO [mid=" + mid + ", mpw=" + mpw + ", name=" + name + ", birth=" + birth + ", phone=" + phone
				+ ", nickname=" + nickname + ", email=" + email + ", marketing=" + marketing + ", gpk=" + gpk
				+ ", grade=" + grade + ", searchCondition=" + searchCondition + "]";
	}

}
