package com.example.lcv_project.Models;

public class Wedding {
    private int weddingId;
    private String bride, groom, wedding_name, wedding_location, wedding_details;
    private int accompanier_num;    // max. number of guest can other guest bring
    private String weddingStart, weddingEnd;

    public Wedding(String bride, String groom, String weddingName, String weddingLocation,
                   String weddingDetails, int accompanier_num, String weddingStart, String weddingEnd) {
        this.bride = bride;
        this.groom = groom;
        this.wedding_name = weddingName;
        this.wedding_location = weddingLocation;
        this.wedding_details = weddingDetails;
        this.accompanier_num = accompanier_num;
        this.weddingStart = weddingStart;
        this.weddingEnd = weddingEnd;
    }

    public Wedding(int weddingId, String bride, String groom, String weddingName, String weddingLocation,
                   String weddingDetails, int accompanier_num, String weddingStart, String weddingEnd) {
        this.weddingId = weddingId;
        this.bride = bride;
        this.groom = groom;
        this.wedding_name = weddingName;
        this.wedding_location = weddingLocation;
        this.wedding_details = weddingDetails;
        this.accompanier_num = accompanier_num;
        this.weddingStart = weddingStart;
        this.weddingEnd = weddingEnd;
    }
    public void setWeddingId(int weddingId) {
        this.weddingId = weddingId;
    }

    public void setBride(String bride) {
        this.bride = bride;
    }

    public void setGroom(String groom) {
        this.groom = groom;
    }

    public void setWedding_name(String wedding_name) {
        this.wedding_name = wedding_name;
    }

    public void setWedding_location(String wedding_location) {
        this.wedding_location = wedding_location;
    }

    public void setWedding_details(String wedding_details) {
        this.wedding_details = wedding_details;
    }


    public void setAccompanier_num(int accompanier_num) {
        this.accompanier_num = accompanier_num;
    }

    public int getWeddingId() {
        return weddingId;
    }

    public String getBride() {
        return bride;
    }

    public String getGroom() {
        return groom;
    }

    public String getWedding_name() {
        return wedding_name;
    }

    public String getWedding_location() {
        return wedding_location;
    }

    public String getWedding_details() {
        return wedding_details;
    }


    public int getAccompanier_num() {
        return accompanier_num;
    }

    public String getWeddingStart() {
        return weddingStart;
    }

    public String getWeddingEnd() {
        return weddingEnd;
    }

    @Override
    public String toString() {
        return
                "Bride: " + bride + "\n" +
                "Groom: " + groom + '\n' +
                "Wedding Name: " + wedding_name + '\n' +
                "Location: " + wedding_location + '\n' +
                "Wedding Details: " + wedding_details + '\n' +
                "Number of People to Bring: " + accompanier_num + '\n' +
                "Wedding Start: " + weddingStart + '\n' +
                "Wedding End: " + weddingEnd + '\n';
    }
}
