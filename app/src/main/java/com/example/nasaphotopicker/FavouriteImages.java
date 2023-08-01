package com.example.nasaphotopicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FavouriteImages extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_images);

        //Block for setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Block for setting up the navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up the arraylist of the saved images for use by the listview and the fragment
//        ArrayList<SavedImageBean> picList = new ArrayList<>();
        String[] files = getApplicationContext().fileList();
//        if (files != null) {
//            for (int i = 0; i < files.length; i++) {
//                try {
//                    String filePath = this.getFilesDir().toString() + "/" + files[i];
//                    Log.i("Files:", filePath);
//                    FileInputStream fileInputStream = new FileInputStream(filePath);
//                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//                    SavedImageBean savedImageBean = (SavedImageBean) objectInputStream.readObject();
//                    picList.add(savedImageBean);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }



        //The adaptor class is created for the Listview
        class NewAdaptor extends BaseAdapter {

            @Override
            public int getCount() {
                return files.length;
            }

            @Override
            public Object getItem(int position) {
                return files[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View newView = inflater.inflate(R.layout.favourite_list_item, parent, false);
                TextView tView = newView.findViewById(R.id.favourites_list_item);
                tView.setText(files[position]);
                return newView;
            }
        }

        //Setting up the listview

        NewAdaptor adaptor = new NewAdaptor();
        ListView listView = (ListView) findViewById(R.id.favourites_list_view);
        listView.setAdapter(adaptor);

        listView.setOnItemLongClickListener( (p, b, pos, id ) -> {

            //Creating a bundle to pass the clicked on item
            //based on it's position to the fragment FavouriteImage
            Bundle bundle = new Bundle();
            bundle.putCharSequence("date", files[pos]);
            bundle.putCharSequence("filePath", this.getFilesDir().toString() + "/" + files[pos]);
            Intent moveIntent = new Intent(FavouriteImages.this, EmptyActivity.class);
            moveIntent.putExtras(bundle);
            FavouriteImages.this.startActivity(moveIntent);
            return false;
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        startActivity(NavigationMethods.DrawerChoice(this,id));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}