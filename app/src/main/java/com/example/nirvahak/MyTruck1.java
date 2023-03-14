package com.example.nirvahak;

public class MyTruck1 {
    String Truck,TruckCategory,TruckRegisterNo,TruckTonnage;


    public MyTruck1(){}

    public MyTruck1(String truck, String truckCategory, String truckRegisterNo, String truckTonnage) {
        Truck = truck;
        TruckCategory = truckCategory;
        TruckRegisterNo = truckRegisterNo;
        TruckTonnage = truckTonnage;
    }

    public String getTruck() {
        return Truck;
    }

    public void setTruck(String truck) {
        Truck = truck;
    }

    public String getTruckCategory() {
        return TruckCategory;
    }

    public void setTruckCategory(String truckCategory) {
        TruckCategory = truckCategory;
    }

    public String getTruckRegisterNo() {
        return TruckRegisterNo;
    }

    public void setTruckRegisterNo(String truckRegisterNo) {
        TruckRegisterNo = truckRegisterNo;
    }

    public String getTruckTonnage() {
        return TruckTonnage;
    }

    public void setTruckTonnage(String truckTonnage) {
        TruckTonnage = truckTonnage;
    }
}
