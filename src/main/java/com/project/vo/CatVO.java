package com.project.vo;

public class CatVO {

	private int cat_no;
	private String cat_title;
	private String cat_cont;
	private String cat_file;

	/** 페이징 관련 변수 **/
	private int startrow;//시작행 번호
	private int endrow;//끝행번호
	
	/** 검색 필드와 검색어 **/
	private String find_field;
	private String find_name;
	
	public int getCat_no() {
		return cat_no;
	}
	public void setCat_no(int cat_no) {
		this.cat_no = cat_no;
	}
	public String getCat_title() {
		return cat_title;
	}
	public void setCat_title(String cat_title) {
		this.cat_title = cat_title;
	}
	public String getCat_cont() {
		return cat_cont;
	}
	public void setCat_cont(String cat_cont) {
		this.cat_cont = cat_cont;
	}
	public String getCat_file() {
		return cat_file;
	}
	public void setCat_file(String cat_file) {
		this.cat_file = cat_file;
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
