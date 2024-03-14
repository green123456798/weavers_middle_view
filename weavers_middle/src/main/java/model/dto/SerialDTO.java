package model.dto;

import java.util.Date;

public class SerialDTO {
	
	private int spk;
	private String mid;
	private Date regdate;
	private String deliveryAddress;
	
	public int getSpk() {
		return spk;
	}
	public void setSpk(int spk) {
		this.spk = spk;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public synchronized String getDeliveryAddress() {
		return deliveryAddress;
	}
	public synchronized void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	@Override
	public String toString() {
		return "SerialDTO [spk=" + spk + ", mid=" + mid + ", regdate=" + regdate + ", deliveryAddress="
				+ deliveryAddress + "]";
	}
	
}
