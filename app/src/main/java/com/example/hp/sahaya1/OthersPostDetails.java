package com.example.hp.sahaya1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lalitha Garapati on 2/22/2018.
 */

public class OthersPostDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    ResultSet users_related, data;
    Intent intent;
    Bitmap bitmap;
    TextView donarName, itemName, address, mobileNumber;
    Button call;
    ImageView image;
    String mobileNo;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_app_post_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        progressDialog = new ProgressDialog(this);
        Intent intent2 = getIntent();

        data = dbHelper.fetch_particular_others(intent2.getStringExtra("ID"));
        try {
            mobileNo = data.getString("mobile_number");
            users_related = dbHelper.fetch_users_related_details(mobileNo);

            donarName = (TextView) findViewById(R.id.donar_name);
            itemName = (TextView) findViewById(R.id.item_name);
            address = (TextView) findViewById(R.id.address_location);
            mobileNumber = (TextView) findViewById(R.id.contact_number);
            call = (Button) findViewById(R.id.btn_call);
            image = (ImageView) findViewById(R.id.image);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callNumber();
                }
            });

            donarName.setText(users_related.getString("user_name"));
            itemName.setText(data.getString("item_name"));
            address.setText(data.getString("user_address"));
            mobileNumber.setText(data.getString("mobile_number"));

            final String imageName = data.getString("image_name");
            progressDialog.setMessage("Loading Image");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            final Handler handler = new Handler();
            Thread th = new Thread(new Runnable() {
                public void run() {
                    try {
                        long imageLength = 0;
                        ImageManager.GetImage(imageName, imageStream, imageLength);
                        handler.post(new Runnable() {
                            public void run() {
                                byte[] buffer = imageStream.toByteArray();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                                image.setImageBitmap(bitmap);
                                progressDialog.cancel();
                            }
                        });
                    }
                    catch(Exception ex) {
                        final String exceptionMessage = ex.getMessage();
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(OthersPostDetails.this, "Image size is too large to load", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }});
            th.start();

        } catch (SQLException e) {
            e.printStackTrace();
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
        startActivity(new Intent(OthersPostDetails.this, Others.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(OthersPostDetails.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(OthersPostDetails.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(OthersPostDetails.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(OthersPostDetails.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(OthersPostDetails.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(OthersPostDetails.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(OthersPostDetails.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(OthersPostDetails.this, Profile.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getPhoneNumber() {
        return mobileNumber.getText().toString();
    }

    private void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getPhoneNumber()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }
    }
}
