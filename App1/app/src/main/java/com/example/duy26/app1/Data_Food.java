package com.example.duy26.app1;

public class Data_Food {
    String name_food;
    String details;
    String image;
    String idfood;
    String gia;

    public Data_Food(String name_food, String details, String image, String idfood, String gia) {
        this.name_food = name_food;
        this.details = details;
        this.image = image;
        this.idfood = idfood;
        this.gia = gia;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String idfood) {
        this.idfood = idfood;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
