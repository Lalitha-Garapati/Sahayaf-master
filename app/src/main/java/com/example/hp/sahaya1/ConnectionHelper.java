package com.example.hp.sahaya1;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Alaeddin on 5/21/2017.
 */

public class ConnectionHelper {

    String ip,db,DBUserNameStr,DBPasswordStr;

    @SuppressLint("NewApi")
    public Connection connectionclasss()
    {
        // Declaring Server ip, username, database name and password
        ip = "sahaya.database.windows.net";
        db = "sahaya";
        DBUserNameStr = "sahayaadmin@sahaya";
        DBPasswordStr = "Sahaya@2018";
        // Declaring Server ip, username, database name and password

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip +";databaseName="+ db + ";user=" + DBUserNameStr+ ";password=" + DBPasswordStr + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
