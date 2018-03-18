package com.example.hp.sahaya1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserDetails extends AppCompatActivity {

    Intent intent;
    ResultSet user_details;
    DBHelper dbHelper;
    Button save;
    EditText user_name, landmark, city, district, state;
    String mobile_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_details);

        dbHelper = new DBHelper(this);

        intent = getIntent();
        mobile_number = intent.getStringExtra("MobileNumber");
        user_name = (EditText) findViewById(R.id.user_name);
        landmark = (EditText) findViewById(R.id.landmark);
        city = (EditText) findViewById(R.id.city);
        district = (EditText) findViewById(R.id.district);
        state = (EditText) findViewById(R.id.state);
        save = (Button) findViewById(R.id.btn_save_details);

        user_details = dbHelper.fetch_users_related_details(mobile_number);
        try {
            user_name.setText(user_details.getString("user_name"));
            landmark.setText(user_details.getString("landmark"));
            city.setText(user_details.getString("city"));
            district.setText(user_details.getString("district"));
            state.setText(user_details.getString("user_state"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.updateUserDetails(user_name.getText().toString(), mobile_number, landmark.getText().toString()
                        , city.getText().toString(), district.getText().toString(), state.getText().toString()) != -1)
                    Toast.makeText(UpdateUserDetails.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                intent = new Intent(UpdateUserDetails.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
