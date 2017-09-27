package com.user.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {
    //Mendefinisikan variabel
    CheckNetwork cn;
    public FirebaseAuth fAuth;
    public FirebaseAuth.AuthStateListener fStateListener;
    public final String TAG = getClass().getSimpleName();
    Button booking, print, galery, promotion;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Please check your connection!", Toast.LENGTH_LONG).show();
        }

        cn.checkSession();

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
        setToolbar();
    }

    public void setToolbar() {
        // Menginisiasi Toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
               toolbarNav(menuItem);
                return true;
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();
    }

    public boolean toolbarNav(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
            //dengan intent activity
            case R.id.nav_home:
                startActivity(new Intent(getApplication(), Menu.class));
                return true;
            case R.id.navigation2:
                startActivity(new Intent(getApplication(), BookingPhoto.class));
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