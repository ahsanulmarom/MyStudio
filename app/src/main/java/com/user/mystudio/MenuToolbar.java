package com.user.mystudio;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by user on 23/09/2017.
 */

public class MenuToolbar extends Menu {
    Context context;

    public MenuToolbar(Context context) {
        this.context = context;
    }

    public boolean setNavToolbar(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
            //dengan intent activity
            case R.id.nav_home:
                startActivity(new Intent(MenuToolbar.this, Home.class));
                return true;
            case R.id.navigation2:
                startActivity(new Intent(MenuToolbar.this, BookingPhoto.class));
                return true;
            case R.id.navigation3:
                Toast.makeText(getApplication(), "Daftar Telah Dipilih", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation4:
                Toast.makeText(getApplication(), "Setting telah dipilih", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation5:
                Toast.makeText(getApplication(), "About telah dipilih", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getApplication(), "Kesalahan Terjadi ", Toast.LENGTH_SHORT).show();
                return true;
        }
    }
}
