package com.example.lcv_project.Models;

public class Invitation {
    private String kanalId;
    private String KullaniciId;
    public Invitation(String kanalId, String KullaniciId){
        this.kanalId=kanalId;
        this.KullaniciId=KullaniciId;
    }
    public Invitation(){

    }
    public String getKanalId(){
        return kanalId;
    }
    public void setKanalId(String kanalId){
        this.kanalId=kanalId;
    }
    public String getKullaniciId(){
        return KullaniciId;
    }
    public void setKullaniciId(String KullaniciId){
        this.KullaniciId=KullaniciId;
    }
}
