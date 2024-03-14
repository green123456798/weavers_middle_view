package model.dto;

public class AddressDTO {

	private int apk;
	private String mid;
	private String zonecode;
	private String jibunaddress;
	private String roadaddress;
	private String detail;
	private String aname;
	
	
	
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public int getApk() {
		return apk;
	}
	public void setApk(int apk) {
		this.apk = apk;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getZonecode() {
		return zonecode;
	}
	public void setZonecode(String zonecode) {
		this.zonecode = zonecode;
	}
	public String getJibunaddress() {
		return jibunaddress;
	}
	public void setJibunaddress(String jibunaddress) {
		this.jibunaddress = jibunaddress;
	}
	public String getRoadaddress() {
		return roadaddress;
	}
	public void setRoadaddress(String roadaddress) {
		this.roadaddress = roadaddress;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "AddressDTO [apk=" + apk + ", mid=" + mid + ", zonecode=" + zonecode + ", jibunaddress=" + jibunaddress
				+ ", roadaddress=" + roadaddress + ", detail=" + detail + ", aname=" + aname + "]";
	}
	
}
