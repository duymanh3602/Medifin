package com.btl.medifin.model;

public class Medicine {

    private int id;
    private String name;
    private String description;
    private int dose; // per day
    private String imgLink;

    public Medicine() {
    }

    public Medicine(int id, String name, String description, int dose, String imgLink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dose = dose;
        this.imgLink = imgLink;
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

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
