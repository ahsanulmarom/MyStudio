package com.user.mystudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BookingPhoto extends AppCompatActivity {
    //Mendefinisikan variabel
    CheckNetwork cn;
    MenuToolbar mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingphoto);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        cn.sessionCheck();

        mt = new MenuToolbar();
    }
}