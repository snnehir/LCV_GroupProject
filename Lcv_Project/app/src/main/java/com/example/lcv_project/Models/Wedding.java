package com.example.lcv_project.Models;

public class Wedding {
    private String KanalId;
    private String KullaniciId;

 public Wedding(String KanalId, String KullaniciId){
    this.KanalId=KanalId;
    this.KullaniciId=KullaniciId;
 }
  public Wedding() {

  }
    public String getKanalId() {

        return KanalId;
    }

    public void setKanalId(String KanalId) {
        this.KanalId = KanalId;
    }
    public String getKullaniciId() {

        return KullaniciId;
    }

    public void setKayitKullaniciadi(String KullaniciId) {
        this.KullaniciId = KullaniciId;
    }
}