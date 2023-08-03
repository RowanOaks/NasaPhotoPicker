package com.example.nasaphotopicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteImage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouriteImage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteImage.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteImage newInstance(String param1, String param2) {
        FavouriteImage fragment = new FavouriteImage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("inside the Frag:", "inside the frag");
        View view = inflater.inflate(R.layout.fragment_favourite_image, container, false);
        //setting up widgets from the XML
        TextView dateText = view.findViewById(R.id.saved_photo_date);
        ImageView spaceImageView = view.findViewById(R.id.saved_photo_image);
        Button deleteButton = (Button) view.findViewById(R.id.delete_favourite_button);
        //getting the passed data into a bundle
        Bundle theData;
        theData = getArguments();
        Bitmap spacePic = null;

        //setting the image and the date to the file of the favourited image
        dateText.setText(theData.getString("date"));
        Bitmap pic = BitmapFactory.decodeFile(theData.getString("filePath"));
        spaceImageView.setImageBitmap(pic);

        //Setting up the delete Button
       deleteButton.setOnClickListener( (click) -> {
           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
           alertDialogBuilder.setMessage(R.string.delete_alert_title);
           alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   File file = new File(theData.getString("filePath"));
                   file.delete();
                   getActivity().finish();
                   Intent intent = new Intent(getActivity(), FavouriteImages.class);
                   intent.putExtra("isDeleted", true);
                   startActivity(intent);
               }
           });
           alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

               }
           });
           alertDialogBuilder.create().show();
       });

        return view;

//        return inflater.inflate(R.layout.fragment_favourite_image, container, false);
    }
}