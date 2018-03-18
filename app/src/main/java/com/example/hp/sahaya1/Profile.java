package com.example.hp.sahaya1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    ResultSet resultSet;
    SessionManager sessionManager;
    DBHelper dbHelper;
    Context context;
    TextView user_name, mobile_number, landmark, city, district, state;
    ImageView edit_button;
    private int GALLERY = 1, CAMERA = 2;
    private byte[] image;
    String mobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {

            dbHelper = new DBHelper(this);

            user_name = (TextView) findViewById(R.id.user_name);
            mobile_number = (TextView) findViewById(R.id.mobile_number);
            landmark = (TextView) findViewById(R.id.landmark);
            city = (TextView) findViewById(R.id.city);
            district = (TextView) findViewById(R.id.district);
            state = (TextView) findViewById(R.id.state);
            edit_button = (ImageView) findViewById(R.id.edit_button);
            HashMap<String, String> user = sessionManager.getUserDetails();
            mobilenumber = user.get(sessionManager.KEY_PHONE_NUMBER);

            resultSet = dbHelper.fetch_users_related_details(mobilenumber.toString());

            try {
                user_name.setText(resultSet.getString("user_name"));
                mobile_number.setText(user.get(sessionManager.KEY_PHONE_NUMBER));
                landmark.setText(resultSet.getString("landmark"));
                city.setText(resultSet.getString("city"));
                district.setText(resultSet.getString("district"));
                state.setText(resultSet.getString("user_state"));

                edit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Profile.this, UpdateUserDetails.class);
                        intent.putExtra("MobileNumber", mobile_number.getText().toString());
                        startActivity(intent);
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(Profile.this, "You haven't LOGGED IN, Please login to view your details", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Profile.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(Profile.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(Profile.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(Profile.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(Profile.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(Profile.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(Profile.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(Profile.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
