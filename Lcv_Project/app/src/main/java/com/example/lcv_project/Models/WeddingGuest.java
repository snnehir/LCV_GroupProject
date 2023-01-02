package com.example.lcv_project.Models;

// NtoM relation
public class WeddingGuest {
    private int weddingGuestId;
    private int userId;
    private int weddingId;
    private int peopleBring;
    private int willCome; // enum: 1- yes, 2- no, 3- unknown
}
