package vo;

import java.io.Serializable;

public class ReservVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 회원아이디
	private String s_Day; // 출발날짜
//	private String e_Day; // 도착날짜
	private String departure; // 출발지
	private String arrival; // 도착지
	private String reservNo; // 예약번호
	private String a_Name; // 비행기이름

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getS_Day() {
		return s_Day;
	}

	public void setS_Day(String s_Day) {
		this.s_Day = s_Day;
	}

	/*
	 * public String getE_Day() { return e_Day; } public void setE_Day(String e_Day)
	 * { this.e_Day = e_Day; }
	 */
	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getReservNo() {
		return reservNo;
	}

	public void setReservNo(String reservNo) {
		this.reservNo = reservNo;
	}

	public String getA_Name() {
		return a_Name;
	}

	public void setA_Name(String a_Name) {
		this.a_Name = a_Name;
	}

}
