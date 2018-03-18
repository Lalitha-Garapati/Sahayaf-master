package com.example.hp.sahaya1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;

public class LoginActivity extends Activity {

    public SessionManager session;
    Button login;
    EditText pNo, psw;
    String PhoneNo,Password;
    Cursor data;
    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        pNo = (EditText) findViewById(R.id.input_phone);
        psw = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);

        awesomeValidation.addValidation(this, R.id.input_phone, "^[2-9]{0}[0-9]{8}$", R.string.mobileerror);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == login) {
                    submitForm();
                }
                pNo.setText("");
                psw.setText("");
            }
        });
    }

    private void submitForm() {

        if (awesomeValidation.validate()) {
            PhoneNo = pNo.getText().toString();
            Password = psw.getText().toString();
            dbHelper = new DBHelper(this);
            String password = dbHelper.checkProfileDetails(PhoneNo);
            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            if (password.equals(Password)) {
                session.createLoginSession(pNo.getText().toString(), psw.getText().toString());
                Intent intent = new Intent(LoginActivity.this, CategorySelection.class);
                Toast.makeText(LoginActivity.this, "Successfully LOGGED IN", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Invalid Phone Number or Password");
                builder.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, HomePage.class));
        finish();
    }

    public void signUp(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}

