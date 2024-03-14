package model.dto;

public class WishListDTO {

	private int wpk;
	private String mid;
	private int ppk;
	
	private String img;
	private String pname;
	
	public int getWpk() {
		return wpk;
	}
	public void setWpk(int wpk) {
		this.wpk = wpk;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	@Override
	public String toString() {
		return "WishListDTO [wpk=" + wpk + ", mid=" + mid + ", ppk=" + ppk + ", img=" + img + ", pname=" + pname + "]";
	}
	
	
}
