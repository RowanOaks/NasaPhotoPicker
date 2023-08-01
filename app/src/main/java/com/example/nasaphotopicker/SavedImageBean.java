package com.example.nasaphotopicker;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SavedImageBean implements Serializable {
    String url;
    String date;

    public SavedImageBean(String url, String date){
        this.url = url;
        this.date = date;
    }

    public SavedImageBean(SavedImageBean bean) {
        this.url = bean.url;
        this.date = bean.date;
    }

}
