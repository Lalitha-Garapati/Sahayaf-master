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

public class Books extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    Intent intent;
    ArrayList<BookPojo> booksList;
    ListView listView;
    BookPojo book;

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
                Intent intent = new Intent(Books.this, BookDetails.class);
                startActivity(intent);
            }
        });

        booksList = new ArrayList<>();
        dbHelper = new DBHelper(this);
        ResultSet book_details = dbHelper.fetch_books();

        if (book_details != null) {
            try {
                do {
                    book = new BookPojo(book_details.getString("book_id"), book_details.getString("book_name"),
                            book_details.getString("user_address"),
                            book_details.getString("mobile_number"), book_details.getString("image_name"));
                    booksList.add(book);
                    BookAdapter bookAdapter = new BookAdapter(this, R.layout.book_item_list_view, booksList);
                    listView.setAdapter(bookAdapter);
                } while (book_details.next());

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                        intent = new Intent(Books.this, BookPostDetails.class);
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
        startActivity(new Intent(Books.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(Books.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(Books.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(Books.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(Books.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(Books.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(Books.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent= new Intent(Books.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent= new Intent(Books.this, Profile.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}