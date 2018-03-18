package com.example.hp.sahaya1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BookDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    AwesomeValidation awesomeValidation;
    ProgressDialog progressDialog;
    InputStream imageStream;
    SessionManager session;
    ResultSet user_details;
    DBHelper dbHelper;
    Uri contentURI;
    Intent intent;
    private static final int GALLERY = 1, CAMERA = 2;
    String address = "", user_mobile_no = "", imageName = "";
    private byte[] image;
    private EditText bookName;
    private RadioButton residence_location_btn, current_location_btn;
    private ImageView imageView;
    private Button upload_photo, make_post;

    public BookDetails() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        session = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        dbHelper = new DBHelper(this);
        
        bookName = (EditText) findViewById(R.id.book_name);
        residence_location_btn = (RadioButton) findViewById(R.id.residence_address);
        current_location_btn = (RadioButton) findViewById(R.id.current_location); 
        upload_photo = (Button)findViewById(R.id.upload_photo);
        imageView = (ImageView) findViewById(R.id.imageView);
        make_post = (Button) findViewById(R.id.btn_make_post);

        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPictureDialog();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        make_post.setOnClickListener(this);

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
        startActivity(new Intent(BookDetails.this, CategorySelection.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                contentURI = data.getData();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                     imageStream = new ByteArrayInputStream(bitmapdata);
                    image =  Utils.getImageBytes(bitmap);
                    imageView.setImageBitmap(bitmap);
                    UploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(BookDetails.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            image =  Utils.getImageBytes(thumbnail);
            UploadImage();
        }
    }

    @Override
    public void onClick(View view) {

        if (residence_location_btn.isChecked()) {
            dbHelper = new DBHelper(this);
            session = new SessionManager(getApplicationContext());
            if (session.isLoggedIn()) {
                HashMap<String, String> user = session.getUserDetails();
                user_details = dbHelper.fetch_users_related_details(user.get(session.KEY_PHONE_NUMBER));
                try {
                    address = user_details.getString("landmark") + ", " + user_details.getString("city") + ", "
                            + user_details.getString("district") + ", " + user_details.getString("user_state");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (current_location_btn.isChecked()) {
            address = HomePage.fullAddress;
        }
        if (view == make_post) {
            AddData();

        }
    }

    public  void AddData() {

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        user_mobile_no = user.get(session.KEY_PHONE_NUMBER);
        if(bookName.getText().toString().equals("")||address.equals("")||user_mobile_no.equals("")||imageName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.details);
            builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else {

            dbHelper.insert_book(bookName.getText().toString(), address, user_mobile_no, imageName);
            Toast.makeText(BookDetails.this,R.string.post, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BookDetails.this, Books.class);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_food:
                intent = new Intent(BookDetails.this, Food.class);
                startActivity(intent);
                break;
            case R.id.nav_clothes:
                intent = new Intent(BookDetails.this, Clothes.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                intent = new Intent(BookDetails.this, Books.class);
                startActivity(intent);
                break;
            case R.id.nav_home_app:
                intent = new Intent(BookDetails.this, HomeAppliances.class);
                startActivity(intent);
                break;
            case R.id.nav_others:
                intent = new Intent(BookDetails.this, Others.class);
                startActivity(intent);
                break;
            case R.id.nav_post:
                intent = new Intent(BookDetails.this, CategorySelection.class);
                startActivity(intent);
                break;
            case R.id.nav_my_posts:
                intent = new Intent(BookDetails.this, MyPosts.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent= new Intent(BookDetails.this, Profile.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void UploadImage() {
        try {
            progressDialog.setMessage("Uploading Image");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            imageStream = getContentResolver().openInputStream(this.contentURI);
            final int imageLength = imageStream.available();
            final Handler handler = new Handler();
            Thread th = new Thread(new Runnable() {
                public void run() {
                    try {
                        imageName = ImageManager.UploadImage(imageStream, imageLength);
                        handler.post(new Runnable() {
                            public void run() {
                                progressDialog.cancel();
                            }
                        });
                    }
                    catch(Exception ex) {
                        final String exceptionMessage = ex.getMessage();
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(BookDetails.this, R.string.image, Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
                    }
                }});
            th.start();
        }
        catch(Exception ex) {
            Toast.makeText(this, R.string.imagenot, Toast.LENGTH_SHORT).show();
        }
    }
 }