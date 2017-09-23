package com.user.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    //Mendefinisikan variabel
    CheckNetwork cn;
    MenuToolbar mt;
    Button booking, print, galery, promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        cn.sessionCheck();
        booking = (Button) findViewById(R.id.menu_booking);
        print = (Button) findViewById(R.id.menu_print);
        galery = (Button) findViewById(R.id.menu_galery);
        promotion = (Button) findViewById(R.id.menu_promotion);

        booking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, BookingPhoto.class));
                finish();
            }
        });

        mt = new MenuToolbar();
    }


}