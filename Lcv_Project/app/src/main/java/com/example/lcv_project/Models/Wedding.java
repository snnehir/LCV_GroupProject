package com.example.lcv_project.Models;

import java.util.ArrayList;
import java.util.Date;

public class Wedding {
    private int weddingId;
    private String bride, groom, weddingName, weddingLocation, weddingDetails, invitation_img_url;
    private int tableCapacity; // number of guests can be sit on a table
    private int numberOfTables; // number of tables in wedding area
    private int accompanier_num;    // max. number of guest can other guest bring
    private Date weddingStart, weddingEnd;

    public Wedding(String bride, String groom, String weddingName, String weddingLocation, String weddingDetails, String invitation_img_url, int tableCapacity, int numberOfTables, int accompanier_num, Date weddingStart, Date weddingEnd) {
        this.bride = bride;
        this.groom = groom;
        this.weddingName = weddingName;
        this.weddingLocation = weddingLocation;
        this.weddingDetails = weddingDetails;
        this.invitation_img_url = invitation_img_url;
        this.tableCapacity = tableCapacity;
        this.numberOfTables = numberOfTables;
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

    public void setWeddingName(String weddingName) {
        this.weddingName = weddingName;
    }

    public void setWeddingLocation(String weddingLocation) {
        this.weddingLocation = weddingLocation;
    }

    public void setWeddingDetails(String weddingDetails) {
        this.weddingDetails = weddingDetails;
    }

    public void setInvitation_img_url(String invitation_img_url) {
        this.invitation_img_url = invitation_img_url;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public void setAccompanier_num(int accompanier_num) {
        this.accompanier_num = accompanier_num;
    }

    public void setWeddingStart(Date weddingStart) {
        this.weddingStart = weddingStart;
    }

    public void setWeddingEnd(Date weddingEnd) {
        this.weddingEnd = weddingEnd;
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

    public String getWeddingName() {
        return weddingName;
    }

    public String getWeddingLocation() {
        return weddingLocation;
    }

    public String getWeddingDetails() {
        return weddingDetails;
    }

    public String getInvitation_img_url() {
        return invitation_img_url;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public int getAccompanier_num() {
        return accompanier_num;
    }

    public Date getWeddingStart() {
        return weddingStart;
    }

    public Date getWeddingEnd() {
        return weddingEnd;
    }
}
