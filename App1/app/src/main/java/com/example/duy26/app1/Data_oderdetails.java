package com.example.duy26.app1;

public class Data_oderdetails {
    int id;
    String food,id_food,number_details,prince,toatal,id_bill;

    public Data_oderdetails(int id, String food, String id_food, String number_details, String prince, String toatal, String id_bill) {
        this.id = id;
        this.food = food;
        this.id_food = id_food;
        this.number_details = number_details;
        this.prince = prince;
        this.toatal = toatal;
        this.id_bill = id_bill;
    }

    public Data_oderdetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public String getNumber_details() {
        return number_details;
    }

    public void setNumber_details(String number_details) {
        this.number_details = number_details;
    }

    public String getPrince() {
        return prince;
    }

    public void setPrince(String prince) {
        this.prince = prince;
    }

    public String getToatal() {
        return toatal;
    }

    public void setToatal(String toatal) {
        this.toatal = toatal;
    }

    public String getId_bill() {
        return id_bill;
    }

    public void setId_bill(String id_bill) {
        this.id_bill = id_bill;
    }
}
