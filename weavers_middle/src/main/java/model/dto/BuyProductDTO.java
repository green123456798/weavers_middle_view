package model.dto;

import java.util.Date;

public class BuyProductDTO {
	private int bpk;
	private int ppk;
	private int cnt;
	private int spk;
	
	private Date regdate;
	private String pname;
	private int price;
	private String img;
	private String mid;
	private String deliveryAddress;
	
	private int reviewCheck;
	private String searchCondition;
	
	
	public int getReviewCheck() {
		return reviewCheck;
	}
	public void setReviewCheck(int reviewCheck) {
		this.reviewCheck = reviewCheck;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public int getBpk() {
		return bpk;
	}
	public void setBpk(int bpk) {
		this.bpk = bpk;
	}
	public int getPpk() {
		return ppk;
	}
	public void setPpk(int ppk) {
		this.ppk = ppk;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getSpk() {
		return spk;
	}
	public void setSpk(int spk) {
		this.spk = spk;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public synchronized String getDeliveryAddress() {
		return deliveryAddress;
	}
	public synchronized void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	@Override
	public String toString() {
		return "BuyProductDTO [bpk=" + bpk + ", ppk=" + ppk + ", cnt=" + cnt + ", spk=" + spk + ", regdate=" + regdate
				+ ", pname=" + pname + ", price=" + price + ", img=" + img + ", mid=" + mid + ", deliveryAddress="
				+ deliveryAddress + ", reviewCheck=" + reviewCheck + ", searchCondition=" + searchCondition + "]";
	}
	

}