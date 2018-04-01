package com.example.android.restukuatno_1202150015_modul6;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 01-Apr-18.
 */

@IgnoreExtraProperties
public class databasepost {
    String gmbr, judul, deskripsi, usr, key;

    //method kosong yang diperlukan untuk melakukan pengecekan
    public databasepost(){

    }

    //konstruktor
    public databasepost(String gmbr, String judul, String deskripsi,String usr){
        this.gmbr=gmbr;
        this.judul=judul;
        this.deskripsi=deskripsi;
        this.usr=usr;
    }

    //method getter
    public String getKey(){
        return key;
    }

    public void setKey(String key){
        key = key;
    }

    public String getGmbr() {
        return gmbr;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getUsr() {
        return usr;
    }
}
