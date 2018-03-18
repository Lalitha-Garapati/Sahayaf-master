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
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FoodDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    Intent intent;
    Uri contentURI;
    String imageName;
    DBHelper dbHelper;
    ResultSet user_details;
    SessionManager session;
    AwesomeValidation awesomeValidation;
    ProgressDialog progressDialog;
    private byte[] image;
    private static final int GALLERY = 1, CAMERA = 2;
    String address = "", user_mobile_no = "";
    private ImageView imageView;
    private Spinner quantity_units;
    private Button upload_photo, make_post;
    private EditText itemName, quantity_value;
    private RadioButton residence_location_btn, current_location_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        progressDialog = new ProgressDialog(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        itemName = (EditText) findViewById(R.id.item_name);
        quantity_value = (EditText) findViewById(R.id.quantity_number);
        quantity_units = (Spinner) findViewById(R.id.quantity_units);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    image =  Utils.getImageBytes(bitmap);
                    imageView.setImageBitmap(bitmap);
                    UploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FoodDetails.this, "Failed!", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(FoodDetails.this, CategorySelection.class));
        finish();
    }

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

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        user_mobile_no = user.get(session.KEY_PHONE_NUMBER);
        if(itemName.getText().toString().equals("")||address.equals("")||user_mobile_no.equals("")||imageName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.details);
            builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {     @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else{
        dbHelper.insert_food(itemName.getText().toString(), quantity_value.getText().toString(),
                quantity_units.getSelectedItem().toString(), address, user_mobile_no, imageName);
            Toast.makeText(FoodDetails.this,R.string.post, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FoodDetails.this, Food.class);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.nav_food:
                i = new Intent(FoodDetails.this, Food.class);
                startActivity(i);
                break;
            case R.id.nav_clothes:
                i = new Intent(FoodDetails.this, Clothes.class);
                startActivity(i);
                break;
            case R.id.nav_books:
                i = new Intent(FoodDetails.this, Books.class);
                startActivity(i);
                break;
            case R.id.nav_home_app:
                i = new Intent(FoodDetails.this, HomeAppliances.class);
                startActivity(i);
                break;
            case R.id.nav_others:
                i = new Intent(FoodDetails.this, Others.class);
                startActivity(i);
                break;
            case R.id.nav_post:
                i = new Intent(FoodDetails.this, CategorySelection.class);
                startActivity(i);
                break;
            case R.id.nav_my_posts:
                i = new Intent(FoodDetails.this, MyPosts.class);
                startActivity(i);
                break;
            case R.id.nav_profile:
                i = new Intent(FoodDetails.this, Profile.class);
                startActivity(i);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void UploadImage() {

        try {
            progressDialog.setMessage("Uploading Image");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final InputStream imageStream = getContentResolver().openInputStream(this.contentURI);
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
                                Toast.makeText(FoodDetails.this, "Image size is too large", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
                    }
                }});
            th.start();
        }
        catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}