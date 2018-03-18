package com.example.hp.sahaya1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CategorySelection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;
    Intent intent;
    Button button_food;
    Button button_books;
    Button button_others;
    Button button_clothes;
    Button button_home_appliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());

        button_food = (Button) findViewById(R.id.food_donation);
        button_books = (Button) findViewById(R.id.books_donation);
        button_clothes = (Button) findViewById(R.id.clothes_donation);
        button_home_appliances = (Button) findViewById(R.id.home_appliances_donation);
        button_others = (Button) findViewById(R.id.others_donations);

        button_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn() ) {
                    intent = new Intent(CategorySelection.this, FoodDetails.class);
                    startActivity(intent);
                }
                else {
                    showMessage();
                }
                sessionManager.isLoggedIn();
            }
        });

        button_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn() ) {
                    intent = new Intent(CategorySelection.this, BookDetails.class);
                    startActivity(intent);
                }
                else {
                    showMessage();
                }
            }
        });

        button_clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn() ) {
                    intent = new Intent(CategorySelection.this, ClothesDetails.class);
                    startActivity(intent);
                }
                else {
                    showMessage();
                }
            }
        });

        button_home_appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn()) {
                    intent = new Intent(CategorySelection.this, HomeAppDetails.class);
                    startActivity(intent);
                }
                else {
                    showMessage();
                }
            }
        });

        button_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.isLoggedIn()) {
                    intent = new Intent(CategorySelection.this, OthersDetails.class);
                    startActivity(intent);
                }
                else {
                    showMessage();
                }
            }
        });

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
        startActivity(new Intent(CategorySelection.this, HomePage.class));
        finish();
    }

    private void showMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CategorySelection.this);
        builder.setTitle("Alert");
        builder.setMessage(R.string.login);
        builder.setNegativeButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(CategorySelection.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.nav_food:
                i = new Intent(CategorySelection.this, Food.class);
                startActivity(i);
                break;
            case R.id.nav_clothes:
                i = new Intent(CategorySelection.this, Clothes.class);
                startActivity(i);
                break;
            case R.id.nav_books:
                i = new Intent(CategorySelection.this, Books.class);
                startActivity(i);
                break;
            case R.id.nav_home_app:
                i = new Intent(CategorySelection.this, HomeAppliances.class);
                startActivity(i);
                break;
            case R.id.nav_others:
                i = new Intent(CategorySelection.this, Others.class);
                startActivity(i);
                break;
            case R.id.nav_post:
                i = new Intent(CategorySelection.this, CategorySelection.class);
                startActivity(i);
                break;
            case R.id.nav_profile:
                i = new Intent(CategorySelection.this, Profile.class);
                startActivity(i);
                break;
            case R.id.nav_my_posts:
                i = new Intent(CategorySelection.this, MyPosts.class);
                startActivity(i);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}