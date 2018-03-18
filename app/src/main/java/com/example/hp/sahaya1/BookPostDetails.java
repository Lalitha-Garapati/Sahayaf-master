package com.example.hp.sahaya1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class BookPostDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    ResultSet users_related, data;
    ProgressDialog progressDialog;
    DBHelper dbHelper;
    Intent intent;
    Bitmap bitmap;
    TextView donarName, bookName, address, mobileNumber;
    ImageView image;
    Button call;
    String mobileNo;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_post_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        dbHelper = new DBHelper(this);
        intent = getIntent();
        
        data = dbHelper.fetch_particular_book(intent.getStringExtra("ID"));

        try {
            users_related = dbHelper.fetch_users_related_details(data.getString("mobile_number"));

            mobileNo = data.getString("mobile_number");
            donarName = (TextView) findViewById(R.id.donar_name);
            bookName = (TextView) findViewById(R.id.book_name);
            mobileNumber = (TextView) findViewById(R.id.contact_number);
            address = (TextView) findViewById(R.id.address_location);
            call = (Button) findViewById(R.id.btn_call);
            image = (ImageView) findViewById(R.id.image);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callNumber();
                }
            });

            donarName.setText(users_related.getString("user_name"));
            bookName.setText(data.getString("book_name"));
            mobileNumber.setText(data.getString("mobile_number"));
            address.setText(data.getString("user_address"));
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
                    } catch(Exception ex) {
                        final String exceptionMessage = ex.getMessage();
                        handler.post(new Runnable() {
                            public void run() {

                                Toast.makeText(BookPostDetails.this, exceptionMessage, Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(BookPostDetails.this, Books.class));
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent= new Intent(BookPostDetails.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent= new Intent(BookPostDetails.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent= new Intent(BookPostDetails.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent= new Intent(BookPostDetails.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent= new Intent(BookPostDetails.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent= new Intent(BookPostDetails.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent= new Intent(BookPostDetails.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent= new Intent(BookPostDetails.this, Profile.class);
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