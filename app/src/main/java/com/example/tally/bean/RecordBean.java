package com.example.tally.bean;

/**
 * @PackageName: com.example.tally.bean
 * @ClassName: RecordBean
 * @Author: winwa
 * @Date: 2023/2/3 8:12
 * @Description:
 **/
public class RecordBean {
    private int id;
    private String name;
    private int imageId;
    private float money;
    private String remark;
    private String time;
    private int year;
    private int month;
    private int day;
    private int type; // 收入 - 1, 支出 - 0

    public RecordBean() {
    }

    public RecordBean(int id, String name, int imageId, float money, String remark, String time, int year, int month, int day, int type) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.money = money;
        this.remark = remark;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
