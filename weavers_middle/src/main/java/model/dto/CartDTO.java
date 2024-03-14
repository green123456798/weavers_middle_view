package model.dto;

public class CartDTO {
	
	private int cpk;
	private String mid;
	private int ppk;
	private int cnt;

	private String pname;
	private int price;
	private String img;
	
	private String searchCondition;
	
	
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
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
	public int getCpk() {
		return cpk;
	}
	public void setCpk(int cpk) {
		this.cpk = cpk;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
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
	@Override
	public String toString() {
		return "CartDTO [cpk=" + cpk + ", mid=" + mid + ", ppk=" + ppk + ", cnt=" + cnt + ", pname=" + pname
				+ ", price=" + price + ", img=" + img + ", searchCondition=" + searchCondition + "]";
	}

}
