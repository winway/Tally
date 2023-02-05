package com.example.tally.bean;

/**
 * @PackageName: com.example.tally.bean
 * @ClassName: MoneyType
 * @Author: winwa
 * @Date: 2023/2/2 8:18
 * @Description:
 **/
public class MoneyTypeBean {
    private int id;
    private String name;
    private int imageId;
    private int ImageIdSelected;
    private int type; // 收入 - 1, 支出 - 0

    public MoneyTypeBean() {
    }

    public MoneyTypeBean(int id, String name, int imageId, int imageIdSelected, int type) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.ImageIdSelected = imageIdSelected;
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

    public int getImageIdSelected() {
        return ImageIdSelected;
    }

    public void setImageIdSelected(int imageIdSelected) {
        ImageIdSelected = imageIdSelected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
