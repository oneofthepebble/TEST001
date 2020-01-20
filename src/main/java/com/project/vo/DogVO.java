package com.project.vo;

public class DogVO {

	private int dog_no;
	private String dog_title;
	private String dog_cont;
	private String dog_file;

	/** 페이징 관련 변수 **/
	private int startrow;//시작행 번호
	private int endrow;//끝행번호
	
	/** 검색 필드와 검색어 **/
	private String find_field;
	private String find_name;
	
	public int getDog_no() {
		return dog_no;
	}
	public void setDog_no(int dog_no) {
		this.dog_no = dog_no;
	}
	public String getDog_title() {
		return dog_title;
	}
	public void setDog_title(String dog_title) {
		this.dog_title = dog_title;
	}
	public String getDog_cont() {
		return dog_cont;
	}
	public void setDog_cont(String dog_cont) {
		this.dog_cont = dog_cont;
	}
	public String getDog_file() {
		return dog_file;
	}
	public void setDog_file(String dog_file) {
		this.dog_file = dog_file;
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
