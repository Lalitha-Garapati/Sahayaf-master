package com.example.hp.sahaya1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String USERS_TABLE = "USERS";
    private static final String FOOD_TABLE = "FOOD_DETAILS";
    private static final String BOOK_TABLE = "BOOK_DETAILS";
    private static final String CLOTH_TABLE = "CLOTH_DETAILS";
    private static final String HOME_APP_TABLE = "HOME_APP_DETAILS";
    private static final String STATIONERY_TABLE = "STATIONERY_DETAILS";
    private static final String OTHERS_TABLE = "OTHERS_DETAILS";
    public static final String user_name = "name";
    public static final String user_address = "address";
    public static final String donar_name = "donar_name";
    public static final String book_name = "book_name";
    public static final String item = "item";
    public static final String image = "image";
    private static final String DB_NAME = "SAHAYA.DB";
    private static final int DB_VERSION = 37;
    public Cursor res;
    private Context context;
    private Connection connect;
    private ResultSet resultSet;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ USERS_TABLE + "(name text not null, " +
                "mobile text not null, password text not null, " +
                "address text not null, city text not null, " +
                "district text not null, state text not null);");

        db.execSQL("create table "+ FOOD_TABLE + "(id integer primary key autoincrement, " +
                "item text not null, quantity text not null, " +
                "quantity_unit text not null, area text, " +
                "mobile text not null, image blob not null);");

        db.execSQL("create table "+ BOOK_TABLE + "(id integer primary key autoincrement, " +
                "book_name text not null, area text, " +
                "mobile text not null, image blob not null);");

        db.execSQL("create table "+ CLOTH_TABLE + "(id integer primary key autoincrement, " +
                "item text not null, gender text not null, " +
                "age_group text not null, area text not null, " +
                "mobile not null, image blob not null);");

        db.execSQL("create table "+ HOME_APP_TABLE + "(id integer primary key autoincrement, " +
                "item text not null, area text not null, " +
                "mobile text not null,  image blob not null);");

        db.execSQL("create table "+ OTHERS_TABLE + "(id integer primary key autoincrement, " +
                "item text not null, area text not null, " +
                "mobile text not null,  image blob not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert_users(String name, String mobile_specified, String password_specified, String address,
                             String city, String district, String state) {

        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null) {
                return -1;
            } else {
                String query = "INSERT INTO USERS_DETAILS VALUES('" + name +"', '" + mobile_specified
                        + "', '" + password_specified + "', '" + address + "', '" + city + "', '" + district + "', '"
                        + state + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void insert_food(String item_specified, String quantity_specified, String quantity_units_given,
                            String place_specified, String mobile, String imageName) {
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {

            } else {
                String query = "INSERT INTO FOOD_TABLE VALUES('" + item_specified +"', '" + quantity_specified + "', '" + quantity_units_given
                        + "', '" + place_specified + "', '" + mobile + "','" + imageName + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insert_book(String book_name_specified, String place_specified, String mobile,String imageName) {
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {

            } else {
                String query = "INSERT INTO BOOK_TABLE VALUES('" + book_name_specified +"', '" + place_specified
                        + "', '" + mobile + "', '" + imageName + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void insert_clothes(String item_name_given, String gender_specified, String age_group_specified,
                               String place_specified, String mobile, String imageName) {
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {

            } else {
                String query = "INSERT INTO CLOTH_TABLE VALUES('" + item_name_given +"', '" + gender_specified
                        + "', '"+age_group_specified+"','" +place_specified+"','"+ mobile + "', '" + imageName + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insert_home_app(String item_specified, String place_specified, String mobile, String imageName) {
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {

            } else {
                String query = "INSERT INTO HOME_APP_TABLE VALUES('" + item_specified +"', '" + place_specified
                        + "', '" + mobile + "', '" + imageName + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insert_others(String item_specified, String place_specified, String mobile,String imageName) {
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {

            } else {
                String query = "INSERT INTO OTHERS_TABLE VALUES('" + item_specified +"', '" + place_specified
                        + "', '" + mobile + "', '" + imageName + "');";
                Statement stmt = connect.createStatement();
                int i = stmt.executeUpdate(query);
                if (i > 0) {
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet fetch_food() {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from FOOD_TABLE ORDER BY food_id DESC;";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public ResultSet fetch_clothes() {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from CLOTH_TABLE ORDER BY cloth_id DESC;";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; }

    public ResultSet fetch_books() {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from BOOK_TABLE ORDER BY book_id DESC;";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_home_appliances() {

        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from home_app_table ORDER BY home_app_id DESC;";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_others() {

        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from OTHERS_TABLE ORDER BY others_id DESC;";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

            }

    public ResultSet fetch_particular_book(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from BOOK_TABLE where book_id = " + id + ";";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
      }

    public ResultSet fetch_particular_cloth(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from CLOTH_TABLE where cloth_id = " + id + ";";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

            }

    public ResultSet fetch_particular_food(String id) {

        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from FOOD_TABLE where food_id = " + id + ";";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
         }

    public ResultSet fetch_particular_home_app(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from HOME_APP_TABLE where home_app_id = " + id + ";";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public ResultSet fetch_particular_others(String id) {

        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from OTHERS_TABLE where others_id = " + id + ";";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String checkProfileDetails(String phoneNo) {
        String password = "";
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            if (connect == null)
            {
            } else {
                String query = "SELECT user_mobile, user_password" +
                        " FROM USERS_DETAILS WHERE user_mobile = '" + phoneNo + "';";
                Statement stmt = connect.createStatement();
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet.getString("user_password");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return password;
    }

    public ResultSet fetch_users_related_details(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "SELECT user_name, user_mobile, landmark, city, district, user_state" +
                        " FROM USERS_DETAILS WHERE user_mobile = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_user_food_posts(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect = conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {

                String query = "select * from FOOD_TABLE where convert(varchar, mobile_number) = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_user_clothes_posts(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from CLOTH_TABLE where convert(varchar, mobile_number) = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_user_books_posts(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from BOOK_TABLE where convert(varchar, mobile_number) = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_user_home_app_posts(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from HOME_APP_TABLE where convert(varchar, mobile_number) = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet fetch_user_others_posts(String phoneNo) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "select * from OTHERS_TABLE where convert(varchar, mobile_number) = '" + phoneNo +"';";
                resultSet = stmt.executeQuery(query);
                if (resultSet.next())
                    return resultSet;
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean delete_particular_clothes_post(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "delete from CLOTH_TABLE where cloth_id = " + id +";";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return true;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete_particular_food_post(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "delete from FOOD_TABLE where food_id = " + id +";";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return true;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete_particular_home_app_post(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
            } else {
                String query = "delete from HOME_APP_TABLE where home_app_id = " + id +";";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return true;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete_particular_others_post(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
                Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String query = "delete from OTHERS_TABLE where others_id = " + Integer.parseInt(id) +";";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return true;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete_particular_book_post(String id) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
                Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String query = "delete from BOOK_TABLE where book_id = " + Integer.parseInt(id) +";";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return true;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public long updateUserDetails(String userName, String userMobile, String landmark,
                                  String city, String district, String state) {
        try {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();
            Statement stmt = connect.createStatement();
            if (connect == null)
            {
                Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String query = "UPDATE USERS_DETAILS SET user_name = '"+ userName
                        + "', landmark = '" + landmark + "', city = '" + city + "', district = '"
                        + district + "', user_state = '" + state + "' where user_mobile = '" + userMobile + "';";
                int i = stmt.executeUpdate(query);
                if (i > 0)
                    return 0;
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}