package com.example.hp.sahaya1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyPosts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper myDB;
    SessionManager sessionManager;
    final Context context = this;
    ResultSet food_details, book_details, clothes_details, home_app_details, others_details;
    AlertDialog.Builder builder;
    ArrayList<FoodPojo> foodlist;
    ArrayList<ClothesPojo> clothesList;
    ArrayList<BookPojo> booksList;
    ArrayList<HomeAppPojo> homeappList;
    ArrayList<OthersPojo> othersList;
    ListView listView;
    FoodPojo food;
    ClothesPojo clothes;
    BookPojo book;
    HomeAppPojo appliances;
    OthersPojo others;
    Spinner spinner;
    String spinnerValue = "", id = "", mobilenumber = "";
    int numRows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myposts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        mobilenumber = user.get(sessionManager.KEY_PHONE_NUMBER);

        builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert Message");
        builder.setMessage("Do you want to delete this post");

        listView = (ListView)findViewById(R.id.list_view);
        spinner = (Spinner)findViewById(R.id.spinner);

        myDB = new DBHelper(this);
        spinnerValue = (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SessionManager session = new SessionManager(getApplicationContext());
                if (session.isLoggedIn()) {
                    switch (position) {
                        case 1:
                            user_food_posts();
                            break;
                        case 2:
                            user_clothes_posts();
                            break;
                        case 3:
                            user_book_posts();
                            break;
                        case 4:
                            user_home_app_posts();
                            break;
                        case 5:
                            user_other_posts();
                            break;
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyPosts.this);
                    builder.setTitle("Alert");
                    builder.setMessage("You haven't LOGGED IN yet, Please LOGIN to view your donations");
                    builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MyPosts.this, LoginActivity.class));
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        startActivity(new Intent(MyPosts.this, HomePage.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.nav_food:
                i = new Intent(MyPosts.this, Food.class);
                startActivity(i);
                break;
            case R.id.nav_clothes:
                i = new Intent(MyPosts.this, Clothes.class);
                startActivity(i);
                break;
            case R.id.nav_books:
                i = new Intent(MyPosts.this, Books.class);
                startActivity(i);
                break;
            case R.id.nav_home_app:
                i = new Intent(MyPosts.this, HomeAppliances.class);
                startActivity(i);
                break;
            case R.id.nav_others:
                i = new Intent(MyPosts.this, Others.class);
                startActivity(i);
                break;
            case R.id.nav_post:
                i = new Intent(MyPosts.this, CategorySelection.class);
                startActivity(i);
                break;
            case R.id.nav_my_posts:
                i = new Intent(MyPosts.this, MyPosts.class);
                startActivity(i);
                break;
            case R.id.nav_profile:
                i = new Intent(MyPosts.this, Profile.class);
                startActivity(i);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void user_food_posts() {

        listView.setAdapter(null);
        foodlist = new ArrayList<>();
        food_details = myDB.fetch_user_food_posts(mobilenumber);
        try {
            do {
                food = new FoodPojo(food_details.getString("food_id"), food_details.getString("item_name"),
                            food_details.getString("quantity"), food_details.getString("quantity_units"),
                            food_details.getString("user_address"), food_details.getString("mobile_number"),
                            food_details.getString("image_name"));

                foodlist.add(food);
                FoodAdapter foodAdapter = new FoodAdapter(MyPosts.this, R.layout.food_item_list_view, foodlist);
                listView.setAdapter(foodAdapter);

            } while (food_details.next());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long viewId) {
                    TextView recordId = (TextView) view.findViewById(R.id.record_id);
                    final String id = recordId.getText().toString();
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.delete_particular_food_post(id);
                            startActivity(new Intent(MyPosts.this, Food.class));
                        }
                    });
                    noOptionClick();
                    showAlert();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void user_clothes_posts() {
        clothes_details = myDB.fetch_user_clothes_posts(mobilenumber);
        listView.setAdapter(null);
        clothesList = new ArrayList<>();
        try {
            do {
                clothes = new ClothesPojo(clothes_details.getString("cloth_id"),
                        clothes_details.getString("gender"), clothes_details.getString("age_group"),
                        clothes_details.getString("user_address"), clothes_details.getString("mobile_number"),
                        clothes_details.getString("image_name"));
                clothesList.add(clothes);
                ClothAdapter ClothAdapter = new ClothAdapter(MyPosts.this, R.layout.clothes_item_list_view, clothesList);
                listView.setAdapter(ClothAdapter);
            } while (clothes_details.next());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                    TextView recordId = (TextView) view.findViewById(R.id.record_id);
                    final String id = recordId.getText().toString();
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.delete_particular_clothes_post(id);
                            startActivity(new Intent(MyPosts.this, Clothes.class));
                        }
                    });
                    noOptionClick();
                    showAlert();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void user_book_posts() {
        book_details = myDB.fetch_user_books_posts(mobilenumber);
        listView.setAdapter(null);
        booksList = new ArrayList<BookPojo>();
        try {
            do {
                book = new BookPojo(book_details.getString("book_id"), book_details.getString("book_name"),
                        book_details.getString("user_address"),
                        book_details.getString("mobile_number"), book_details.getString("image_name"));
                booksList.add(book);
                BookAdapter bookAdapter = new BookAdapter(MyPosts.this, R.layout.book_item_list_view, booksList);
                listView.setAdapter(bookAdapter);
            } while (book_details.next());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                    TextView recordId = (TextView) view.findViewById(R.id.record_id);
                    final String id = recordId.getText().toString();
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.delete_particular_book_post(id);
                            startActivity(new Intent(MyPosts.this, Books.class));
                        }
                    });
                    noOptionClick();
                    showAlert();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void user_other_posts() {
        others_details = myDB.fetch_user_others_posts(mobilenumber);
        listView.setAdapter(null);
        othersList = new ArrayList<OthersPojo>();
        try {
            do {
                others = new OthersPojo(others_details.getString("others_id"),
                        others_details.getString("item_name"), others_details.getString("user_address"),
                        others_details.getString("mobile_number"), others_details.getString("image_name"));
                othersList.add(others);
                OthersAdapter othersAdapter = new OthersAdapter(MyPosts.this,
                        R.layout.homeappliances_list_view, othersList);
                listView.setAdapter(othersAdapter);
            } while (others_details.next());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                    TextView recordId = (TextView) view.findViewById(R.id.record_id);
                    final String id = recordId.getText().toString();
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.delete_particular_others_post(id);
                            startActivity(new Intent(MyPosts.this, Others.class));
                        }
                    });
                    noOptionClick();
                    showAlert();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void user_home_app_posts() {
        home_app_details = myDB.fetch_user_home_app_posts(mobilenumber);
        listView.setAdapter(null);
        homeappList = new ArrayList<HomeAppPojo>();
        try {
            home_app_details.afterLast();
            while (home_app_details.previous()){
                appliances = new HomeAppPojo(home_app_details.getString("home_app_id"),
                        home_app_details.getString("item_name"), home_app_details.getString("user_address"),
                        home_app_details.getString("mobile_number"), home_app_details.getString("image_name"));
                homeappList.add(appliances);
                HomeAppAdapter homeAppAdapter = new HomeAppAdapter(MyPosts.this, R.layout.homeappliances_list_view, homeappList);
                listView.setAdapter(homeAppAdapter);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                    TextView recordId = (TextView) view.findViewById(R.id.record_id);
                    final String id = recordId.getText().toString();
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.delete_particular_home_app_post(id);
                            startActivity(new Intent(MyPosts.this, HomeAppliances.class));
                        }
                    });
                    noOptionClick();
                    showAlert();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert() {
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void noOptionClick() {
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
}