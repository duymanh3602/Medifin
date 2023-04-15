package com.btl.medifin.model;

public class Medicine {

    private int id;
    private String name;
    private String description;
    private String dose; // per day
    private String img;

    public Medicine() {
    }

    public Medicine(int id, String name, String description, String dose, String imgLink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dose = dose;
        this.img = imgLink;
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
