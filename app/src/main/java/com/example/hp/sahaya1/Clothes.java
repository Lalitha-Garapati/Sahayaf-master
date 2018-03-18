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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Clothes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    DBHelper dbHelper;
    ResultSet clothes_details;
    ListView listView;
    ClothesPojo clothes;
    ArrayList<ClothesPojo> clothesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.item_list_view);
        listView.setEmptyView(findViewById(R.id.no_update_text));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clothes.this, ClothesDetails.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);
        clothesList = new ArrayList<>();
        clothes_details = dbHelper.fetch_clothes();

        if (clothes_details != null) {
            try {
                do {
                    clothes = new ClothesPojo(clothes_details.getString("cloth_id"),
                            clothes_details.getString("gender"), clothes_details.getString("age_group"),
                            clothes_details.getString("user_address"), clothes_details.getString("mobile_number"),
                            clothes_details.getString("image_name"));
                    clothesList.add(clothes);
                    ClothAdapter ClothAdapter = new ClothAdapter(this, R.layout.clothes_item_list_view, clothesList);
                    listView.setAdapter(ClothAdapter);
                } while (clothes_details.next());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                        intent = new Intent(Clothes.this, ClothesPostDetails.class);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(Clothes.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(Clothes.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(Clothes.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(Clothes.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(Clothes.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(Clothes.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(Clothes.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(Clothes.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(Clothes.this, Profile.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}