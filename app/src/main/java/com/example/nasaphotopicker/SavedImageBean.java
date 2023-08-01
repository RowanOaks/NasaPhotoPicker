package com.example.nasaphotopicker;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SavedImageBean implements Serializable {
    String url;
    String date;
    byte[] pic;

    public SavedImageBean(String url, String date, byte[] pic){
        this.url = url;
        this.date = date;
        this.pic = pic;
    }

    public SavedImageBean(SavedImageBean bean) {
        this.url = bean.url;
        this.date = bean.date;
        this.pic = bean.pic;
    }

}
