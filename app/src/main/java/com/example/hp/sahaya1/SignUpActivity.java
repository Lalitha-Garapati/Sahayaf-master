package com.example.hp.sahaya1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.sql.ResultSet;

public class SignUpActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ResultSet users_details;
    private EditText editTextName, editTextMobile, editTextPassword, editTextAddress, editTextCity,
            editTextDistrict, editTextState;
    private Button buttonSubmit;
    private AwesomeValidation awesomeValidation;
    byte[] inputImage;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        editTextName = (EditText) findViewById(R.id.input_name);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        editTextMobile = (EditText) findViewById(R.id.input_mobile);
        editTextAddress = (EditText) findViewById(R.id.address_location);
        editTextCity = (EditText) findViewById(R.id.address_city);
        editTextDistrict = (EditText) findViewById(R.id.address_district);
        editTextState = (EditText) findViewById(R.id.address_state);
        buttonSubmit = (Button) findViewById(R.id.btn_signup);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation(view)) {
                    if(AddData()) {
                        Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, HomePage.class));
        finish();
    }

    private boolean AddData() {
        dbHelper = new DBHelper(this);
            if(dbHelper.insert_users(editTextName.getText().toString(), editTextMobile.getText().toString(),
                    editTextPassword.getText().toString(), editTextAddress.getText().toString(),
                    editTextCity.getText().toString(), editTextDistrict.getText().toString(),
                    editTextState.getText().toString()) != -1) {
                return true;
            }
        return false;
    }

    private boolean validation(View view) {
        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        if (!(editTextAddress.getText().toString().equals("") || editTextCity.getText().toString().equals("") ||
                editTextDistrict.getText().toString().equals("") || editTextState.getText().toString().equals("")
                || editTextMobile.getText().toString().length() != 10)) {
            if (view == buttonSubmit) {
                return submitForm();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter all details");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));
                }
            });
            builder.show();
        }
        return false;
    }

    private boolean submitForm() {
        if (awesomeValidation.validate()) {
            return true;
        }
        return false;
    }
}