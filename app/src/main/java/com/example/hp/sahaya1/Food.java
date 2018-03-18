package com.example.hp.sahaya1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Food extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper myDB;
    Intent intent;
    ResultSet food_details;
    ArrayList<FoodPojo> foodlist;
    ListView listView;
    FoodPojo food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.item_list_view);
        listView.setEmptyView(findViewById(R.id.no_update_text));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food.this, FoodDetails.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);
        foodlist = new ArrayList<>();
        food_details = myDB.fetch_food();

        if (food_details != null) {
            try {
                do {
                    food = new FoodPojo(food_details.getString("food_id"), food_details.getString("item_name"),
                            food_details.getString("quantity"),
                            food_details.getString("quantity_units"), food_details.getString("user_address"),
                            food_details.getString("mobile_number"), food_details.getString("image_name"));
                    foodlist.add(food);
                    FoodAdapter foodAdapter = new FoodAdapter(this, R.layout.food_item_list_view, foodlist);
                    listView.setAdapter(foodAdapter);
                } while (food_details.next());

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                        intent = new Intent(Food.this, FoodPostDetails.class);
                        TextView recordId = (TextView) view.findViewById(R.id.record_id);
                        intent.putExtra("ID", recordId.getText().toString());
                        startActivity(intent);
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(Food.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(Food.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(Food.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(Food.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(Food.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(Food.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(Food.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(Food.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(Food.this, Profile.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}