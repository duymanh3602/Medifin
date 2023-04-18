package com.btl.medifin.model;

public class Medicine {

    private String mid;
    private String name;
    private String description;
    private String dose; // per day
    private String img;

    public Medicine() {
    }

    public Medicine(String id, String name, String description, String dose, String imgLink) {
        this.mid = id;
        this.name = name;
        this.description = description;
        this.dose = dose;
        this.img = imgLink;
    }

    public String getmId() {
        return mid;
    }

    public void setId(String id) {
        this.mid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getImg() {
        return img;
    }

    public void setImgLink(String imgLink) {
        this.img = imgLink;
    }
}
