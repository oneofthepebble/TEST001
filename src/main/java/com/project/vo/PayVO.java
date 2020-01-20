package com.project.vo;

/** shop 구매목록 VO **/
public class PayVO {
	
	private int pay_no;//구매목록 번호
	private String user_id;//구매자 아이디
	private int pay_price;//총 구매금액
	private String pay_date;//구매날짜
	private int validity;//판매 승인 여부. 판매승인전 -> 1, 판매승인후 -> 2
	
	private String product_name;//상품명
	
	/** 페이징 변수 **/
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호
	
	/** 검색 필드와 검색어 **/
	private String find_field;
	private String find_name;
	
	
	public int getPay_no() {
		return pay_no;
	}
	public void setPay_no(int pay_no) {
		this.pay_no = pay_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getPay_price() {
		return pay_price;
	}
	public void setPay_price(int pay_price) {
		this.pay_price = pay_price;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date.substring(0,10);
		//0이상 10미만 사이의 년,월,일 만 반환
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getStartrow() {
		return startrow;
	}
	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}
	public int getEndrow() {
		return endrow;
	}
	public void setEndrow(int endrow) {
		this.endrow = endrow;
	}
	public String getFind_field() {
		return find_field;
	}
	public void setFind_field(String find_field) {
		this.find_field = find_field;
	}
	public String getFind_name() {
		return find_name;
	}
	public void setFind_name(String find_name) {
		this.find_name = find_name;
	}
	
}
