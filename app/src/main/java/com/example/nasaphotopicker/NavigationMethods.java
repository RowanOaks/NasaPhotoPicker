package com.example.nasaphotopicker;

import android.content.Context;
import android.content.Intent;

public class NavigationMethods {

    public static Intent DrawerChoice(Context context, int id) {
        Intent intent;
        if (id == R.id.nav_main) {
            intent = new Intent(context, MainActivity.class);
            ;
        }
        else if (id == R.id.nav_favourites) {
            intent = new Intent(context, FavouriteImages.class);
        }
        else if (id == R.id.nav_active_photo) {
            intent = new Intent(context, ActivePhoto.class);
        }
        else {
            intent = new Intent(context, DatePicker.class);
        }

        return intent;
    }
}
