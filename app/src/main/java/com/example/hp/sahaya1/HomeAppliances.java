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

public class HomeAppliances extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    ResultSet data;
    Intent intent;
    ArrayList<HomeAppPojo> homeAppliancesList;
    ListView listView;
    HomeAppPojo appliances;

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
                Intent intent = new Intent(HomeAppliances.this, HomeAppDetails.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);
        homeAppliancesList = new ArrayList<>();
        data = dbHelper.fetch_home_appliances();

        if (data != null) {
            try {
                do {
                    appliances = new HomeAppPojo(data.getString("home_app_id"), data.getString("item_name"),
                            data.getString("user_address"), data.getString("mobile_number"),
                            data.getString("image_name"));
                    homeAppliancesList.add(appliances);
                    HomeAppAdapter homeAppAdapter = new HomeAppAdapter(this, R.layout.homeappliances_list_view, homeAppliancesList);
                    listView.setAdapter(homeAppAdapter);
                } while (data.next());

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                        intent = new Intent(HomeAppliances.this, HomeappPostDetails.class);
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
        startActivity(new Intent(HomeAppliances.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(HomeAppliances.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(HomeAppliances.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(HomeAppliances.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(HomeAppliances.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(HomeAppliances.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(HomeAppliances.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(HomeAppliances.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(HomeAppliances.this, Profile.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}