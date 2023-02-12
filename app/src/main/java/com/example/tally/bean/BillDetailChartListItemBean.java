package com.example.tally.bean;

/**
 * @PackageName: com.example.tally.bean
 * @ClassName: BillDetailChartListItem
 * @Author: winwa
 * @Date: 2023/2/11 8:16
 * @Description:
 **/
public class BillDetailChartListItemBean {
    private int imageId;
    private String name;
    private float percent;
    private float money;

    public BillDetailChartListItemBean() {
    }

    public BillDetailChartListItemBean(int imageId, String name, float percent, float money) {
        this.imageId = imageId;
        this.name = name;
        this.percent = percent;
        this.money = money;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
