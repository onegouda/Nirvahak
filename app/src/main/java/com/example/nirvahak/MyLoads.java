package com.example.nirvahak;

public class MyLoads {
    String LoadCategory,LoadSource,LoadDestination,LoadTonnage,LoadRent;

    public MyLoads() {    }

    public MyLoads(String loadCategory, String loadSource, String loadDestination, String loadTonnage, String loadRent) {
        LoadCategory = loadCategory;
        LoadSource = loadSource;
        LoadDestination = loadDestination;
        LoadTonnage = loadTonnage;
        LoadRent = loadRent;
    }

    public String getLoadCategory() {
        return LoadCategory;
    }

    public void setLoadCategory(String loadCategory) {
        LoadCategory = loadCategory;
    }

    public String getLoadSource() {
        return LoadSource;
    }

    public void setLoadSource(String loadSource) {
        LoadSource = loadSource;
    }

    public String getLoadDestination() {
        return LoadDestination;
    }

    public void setLoadDestination(String loadDestination) {
        LoadDestination = loadDestination;
    }

    public String getLoadTonnage() {
        return LoadTonnage;
    }

    public void setLoadTonnage(String loadTonnage) {
        LoadTonnage = loadTonnage;
    }

    public String getLoadRent() {
        return LoadRent;
    }

    public void setLoadRent(String loadRent) {
        LoadRent = loadRent;
    }
}
