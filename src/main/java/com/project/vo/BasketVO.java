package com.project.vo;

/** shop 장바구니 VO **/
public class BasketVO {
	
	private int basket_no;//장바구니 상품 번호
	private String basket_id;//유저 아이디
	private int product_no;//원래 상품 번호
	private int basket_count;//담은 상품 개수
	private int validity;//상품의 상태. 1-> 장바구니에 담긴 상품, 2-> 주문완료 처리 된 상품
	private int basket_page;//해당 상품 page값 
	private int pay_no;//주문 내역 번호.
	
	private String user_name;//유저 이름
	private String product_name;//상품명
	private String product_img;//상품 이미지 파일명
	private int price;//상품 개별 가격
	private int sumPrice;//총 가격
	private int stockCount;//상품 재고
	
	
	/** getter // setter **/
	public int getBasket_no() {
		return basket_no;
	}
	public void setBasket_no(int basket_no) {
		this.basket_no = basket_no;
	}
	public String getBasket_id() {
		return basket_id;
	}
	public void setBasket_id(String basket_id) {
		this.basket_id = basket_id;
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
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
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
	public int getBasket_page() {
		return basket_page;
	}
	public void setBasket_page(int basket_page) {
		this.basket_page = basket_page;
	}
	public int getPay_no() {
		return pay_no;
	}
	public void setPay_no(int pay_no) {
		this.pay_no = pay_no;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	
}
