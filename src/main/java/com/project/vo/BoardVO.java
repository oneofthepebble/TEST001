package com.project.vo;

public class BoardVO {
    private int back_end_list_no;
    private String back_end_list_img;
    private String back_end_list_title;
    private String back_end_list_cont;
    private String back_end_list_id;
    private String back_end_list_date;

    private int step;
    private int ref;

    //페이징 관련변수
    private int startrow;//시작행번호
    private int endrow;//끝행번호


    /*검색 필드*/
    private String back_end_title; //검색 타이틀 ex: 제목으로 검색,내용 으로 검색
    private String back_end_field; //검색하고자하는 검색어

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getBack_end_list_no() {
        return back_end_list_no;
    }

    public void setBack_end_list_no(int back_end_list_no) {
        this.back_end_list_no = back_end_list_no;
    }

    public String getBack_end_list_img() {
        return back_end_list_img;
    }

    public void setBack_end_list_img(String back_end_list_img) {
        this.back_end_list_img = back_end_list_img;
    }

    public String getBack_end_list_title() {
        return back_end_list_title;
    }

    public void setBack_end_list_title(String back_end_list_title) {
        this.back_end_list_title = back_end_list_title;
    }

    public String getBack_end_list_cont() {
        return back_end_list_cont;
    }

    public void setBack_end_list_cont(String back_end_list_cont) {
        this.back_end_list_cont = back_end_list_cont;
    }

    public String getBack_end_list_id() {
        return back_end_list_id;
    }

    public void setBack_end_list_id(String back_end_list_id) {
        this.back_end_list_id = back_end_list_id;
    }

    public String getBack_end_list_date() {
        return back_end_list_date;
    }

    public void setBack_end_list_date(String back_end_list_date) {
        this.back_end_list_date = back_end_list_date;
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

    public String getBack_end_title() {
        return back_end_title;
    }

    public void setBack_end_title(String back_end_title) {
        this.back_end_title = back_end_title;
    }

    public String getBack_end_field() {
        return back_end_field;
    }

    public void setBack_end_field(String back_end_field) {
        this.back_end_field = back_end_field;
    }
}
