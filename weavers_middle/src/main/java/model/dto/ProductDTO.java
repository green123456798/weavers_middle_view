package model.dto;

import java.util.Date;

public class ProductDTO {
	
	private int ppk;
	private String pname;
	private int price;
	private int sales;
	private Date regdate;
	private String detail;
	private String img;
	
	private String searchCondition;
	private int wish;
	private String mid;
	
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getWish() {
		return wish;
	}

	public void setWish(int wish) {
		this.wish = wish;
	}

	public int getPpk() {
		return ppk;
	}

	public void setPpk(int ppk) {
		this.ppk = ppk;
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

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	
	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	@Override
	public String toString() {
		return "ProductDTO [ppk=" + ppk + ", pname=" + pname + ", price=" + price + ", sales=" + sales + ", regdate="
				+ regdate + ", detail=" + detail + ", img=" + img + ", searchCondition=" + searchCondition + ", wish="
				+ wish + ", mid=" + mid + "]";
	}

}
