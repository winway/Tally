package com.example.tally.bean;

/**
 * @PackageName: com.example.tally.bean
 * @ClassName: BillDetailChartItemBean
 * @Author: winwa
 * @Date: 2023/2/12 9:55
 * @Description:
 **/
public class BillDetailChartItemBean {
    private int year;
    private int month;
    private int day;
    private float money;

    public BillDetailChartItemBean() {
    }

    public BillDetailChartItemBean(int year, int month, int day, float money) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
