package com.project.vo;

public class NormalBoardVO {
    private int normal_no;
    private String normal_id;
    private String normal_title;
    private int normal_hit;
    private String normal_cont;
    private String normal_date;
    private int normal_ref;
    private int normal_step;

    private String title;
    private String field;

    private int startrow;//시작행번호
    private int endrow;//끝행번호


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    public int getNormal_no() {
        return normal_no;
    }

    public void setNormal_no(int normal_no) {
        this.normal_no = normal_no;
    }

    public String getNormal_id() {
        return normal_id;
    }

    public void setNormal_id(String normal_id) {
        this.normal_id = normal_id;
    }

    public String getNormal_title() {
        return normal_title;
    }

    public void setNormal_title(String normal_title) {
        this.normal_title = normal_title;
    }

    public int getNormal_hit() {
        return normal_hit;
    }

    public void setNormal_hit(int normal_hit) {
        this.normal_hit = normal_hit;
    }

    public String getNormal_cont() {
        return normal_cont;
    }

    public void setNormal_cont(String normal_cont) {
        this.normal_cont = normal_cont;
    }

    public String getNormal_date() {
        return normal_date;
    }

    public void setNormal_date(String normal_date) {
        this.normal_date = normal_date.substring(0,10);
    }

    public int getNormal_ref() {
        return normal_ref;
    }

    public void setNormal_ref(int normal_ref) {
        this.normal_ref = normal_ref;
    }

    public int getNormal_step() {
        return normal_step;
    }

    public void setNormal_step(int normal_step) {
        this.normal_step = normal_step;
    }
}
