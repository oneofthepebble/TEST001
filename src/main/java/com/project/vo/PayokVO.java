package com.project.vo;

/** 결제 확인, 완료 테이블 PayokVO VO **/

public class PayokVO {
	
	private int payCom_no;//장바구니 번호
	private String payCom_id;//유저 아이디
	private int product_no;//상품번호
	private int basket_count;//담은 상품 수량
	private int basket_page;//해당상품의 페이지 값. 장바구니에서 해당상품클릭했을때 이동하기 위함.
	
	private String user_name;//유저 이름
	private String product_name;//상품명
	private String product_img;//상품 이미지 파일명
	private int price;//상품 개별 가격
	private int sumPrice;//총 가격
	private int stockCount;//상품 재고
	
	public int getPayCom_no() {
		return payCom_no;
	}
	public void setPayCom_no(int payCom_no) {
		this.payCom_no = payCom_no;
	}
	public String getPayCom_id() {
		return payCom_id;
	}
	public void setPayCom_id(String payCom_id) {
		this.payCom_id = payCom_id;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getBasket_count() {
		return basket_count;
	}
	public void setBasket_count(int basket_count) {
		this.basket_count = basket_count;
	}
	public int getBasket_page() {
		return basket_page;
	}
	public void setBasket_page(int basket_page) {
		this.basket_page = basket_page;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_img() {
		return product_img;
	}
	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	
}



