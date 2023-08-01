package com.example.nasaphotopicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivePhoto extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_photo);

        //initializeing textviews to hold date and url;


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

        //grabs the intent and starts the async task with the date entered
        //from the previous activity.
        NasaPhoto photo = new NasaPhoto();
        Intent intent = getIntent();
        String date = intent.getStringExtra("datePicked");
        photo.execute("https://api.nasa.gov/planetary/apod?api_key=iTu0DP2adZCpEs0Nra2WDLrttfN69MOZoWdC47Ka&date=" + date);



    }

    class NasaPhoto extends AsyncTask<String, Integer, String> {
        TextView textDate = (TextView) findViewById(R.id.photo_date);
        TextView textUrl = (TextView) findViewById(R.id.photo_url);
        ImageView currentImageView = (ImageView) findViewById(R.id.current_photo_view);
        Bitmap spacePic;
        String urlString;
        String dateString;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        BufferedReader reader;
        int progressValue = 0;
        JSONObject nasaPicData;

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(strings[0]);
                Log.i("URL: ", url.toString());
                connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String result = buffer.toString();
                nasaPicData = new JSONObject(result);

                URL imageUrl = new URL(nasaPicData.getString("url"));
                urlString = imageUrl.toString();
                dateString = nasaPicData.getString("date");

                spacePic = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                progressValue = 1;
                publishProgress(progressValue);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
                return null;
            
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            currentImageView.setImageBitmap(spacePic);
            progressBar.setProgress(progressValue);
            if (progressValue == 1) {
                progressBar.setVisibility(View.GONE);
            }

            if (dateString != null && urlString != null) {
                textDate.setText(dateString);
                textUrl.setText(urlString);

            }
            //Saving the image as an object alongside it's other bits.
            Button saveButton = (Button) findViewById(R.id.save_photo_button);
            saveButton.setOnClickListener((click) -> {
                //Attempting to save bitmap with date as the list

                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(new File(getApplicationContext().getFilesDir(), dateString));
                    spacePic.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }



                //this is all an attempt to save things via a serializable object.

//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                spacePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                SavedImageBean savedImage = new SavedImageBean(urlString, dateString, byteArray);
//                try {
//                    FileOutputStream outputStream = new FileOutputStream(new File(getApplicationContext().getFilesDir(), savedImage.date));
//                    Log.i("savedImageDate", savedImage.date);
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//                    objectOutputStream.writeObject(savedImage);
//                    objectOutputStream.close();
//                    outputStream.close();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            });
        }
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